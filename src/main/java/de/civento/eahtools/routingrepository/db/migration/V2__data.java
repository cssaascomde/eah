package de.civento.eahtools.routingrepository.db.migration;

import de.civento.eahtools.routingrepository.db.migration.v2.ImportDataOu;
import de.civento.eahtools.routingrepository.db.migration.v2.ImportDataService;
import lombok.extern.java.Log;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Component
@Log
public class V2__data extends BaseJavaMigration {
    private static final String SPLITTER = "\\|";
    private static final String INSERT_SERVICE = "insert into service " +
            "(id, sys_modified_at, sys_modified_by, sys_version, leika_key, civento_key, " +
            "name, responsibility_type, delivery_type) " +
            "values " +
            "(?, CURRENT_TIMESTAMP, 'Import', 0, 'nicht definiert', ?, ?, 'unknown', 'internal')";

    private static final String INSERT_OU = "insert into ou " +
            "(id, sys_modified_at, sys_modified_by, sys_version, civento_key, name, type, " +
            "regional_key, address, zip_code, city, email, phone) " +
            "values " +
            "(?, CURRENT_TIMESTAMP, 'Import', 0, ?, ?, 'unknown', ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_RESPONSIBILITY = "insert into responsibility " +
            "(id, sys_modified_at, sys_modified_by, sys_version, ou_entity_id, service_entity_id, " +
            "regional_key, delivery_type) " +
            "values " +
            "(newid(), CURRENT_TIMESTAMP, 'Import', 0, ?, ?, ?, 'internal')";

    @Override
    public void migrate(Context context) throws SQLException {
        // Basisdaten laden
        Map<String, String> regionalKeyMap = getRegionalKeyMap();
        List<ImportDataOu> ous = getOus(regionalKeyMap);
        List<ImportDataService> services = getServices();

        // Ous importieren
        try (PreparedStatement stmt = context.getConnection().prepareStatement(INSERT_OU)) {
            for (ImportDataOu ou : ous) {
                    stmt.setString(1, ou.getUuid());
                    stmt.setString(2, ou.getId());
                    stmt.setString(3, ou.getName());
                    stmt.setString(4, ou.getRegionalKey());
                    stmt.setString(5, ou.getAddress());
                    stmt.setString(6, ou.getZipcode());
                    stmt.setString(7, ou.getCity());
                    stmt.setString(8, ou.getEmail());
                    stmt.setString(9, ou.getPhone());
                    stmt.addBatch();
            }
            int updateCount = stmt.executeBatch().length;
            log.info(String.format(".migrate: %d Organisationseinheiten imporiert", updateCount));
        }

        // Services importieren
        try (PreparedStatement stmt = context.getConnection().prepareStatement(INSERT_SERVICE)) {
            for (ImportDataService service : services) {
                stmt.setString(1, service.getUuid());
                stmt.setString(2, service.getId());
                stmt.setString(3, service.getName());

                stmt.addBatch();
            }
            int updateCount = stmt.executeBatch().length;
            log.info(String.format(".migrate: %d Services imporiert", updateCount));
        }

        // Zustänidgkeiten verarbeiten
        try (PreparedStatement stmt = context.getConnection().prepareStatement(INSERT_RESPONSIBILITY)) {
            Scanner sc = new Scanner(Objects.requireNonNull(
                    this.getClass().getResourceAsStream("/db/default_data/data.csv")));
            sc.useDelimiter(System.lineSeparator());

            int i = 0;
            while (sc.hasNext())  //returns a boolean value
            {
                i++;
                String[] values = sc.next().trim().split(SPLITTER);
                if (!values[0].startsWith("#")) {
                    // Gebiet für die Zuständigkeit
                    String areaRegionalKey = regionalKeyMap.getOrDefault(values[4], null);
                    if (StringUtils.hasLength(areaRegionalKey)) {
                        // Zuständige Organisationseinheit
                        Optional<ImportDataOu> ou = ous.stream().filter(o -> o.getName().equalsIgnoreCase(values[6])).findFirst();
                        if (ou.isPresent()) {
                            Optional<ImportDataService> service = services.stream().filter(
                                    s -> s.getId().equalsIgnoreCase(values[7])).findFirst();
                            if (service.isPresent()) {
                                stmt.setString(1, ou.get().getUuid());
                                stmt.setString(2, service.get().getUuid());
                                stmt.setString(3, areaRegionalKey);

                                stmt.addBatch();

                            } else {
                                log.severe(String.format(".migrate: Zur ID '%s' konnte keine Diensleitung ermittelt werden",
                                        values[7]));
                            }
                        } else {
                            log.severe(String.format(".migrate: Zum Name '%s' konnte keine Organisationseinheit ermittelt werden",
                                    values[6]));
                        }
                    } else {
                        log.severe(String.format(".migrate: Zum Gebiet '%s' konnte keine AGS ermittelt werden", values[4]));
                    }
                }
            }
            int updateCount = stmt.executeBatch().length;
            log.info(String.format(".migrate: %d Zuständigkeiten imporiert", updateCount));
        }
    }

    private List<ImportDataOu> getOus(Map<String, String> regionalKeys) {
        ArrayList<ImportDataOu> ous = new ArrayList<>();

        Scanner sc = new Scanner(Objects.requireNonNull(
                this.getClass().getResourceAsStream("/db/default_data/ous.csv")));
        sc.useDelimiter(System.lineSeparator());
        while (sc.hasNext())  //returns a boolean value
        {
            String[] values = sc.next().trim().split(SPLITTER);
            ous.add(ImportDataOu.builder()
                    .id(String.format("00.00.EAH.ZS.%s", values[7]))
                    .phone(values[6])
                    .email(values[5])
                    .address(String.format("%s %s", values[3], values[4]))
                    .city(values[2])
                    .zipcode(values[1])
                    .name(values[0])
                    .regionalKey(regionalKeys.getOrDefault(values[2], "nicht def."))
                    .uuid(UUID.randomUUID().toString())
                    .build());
        }
        sc.close();
        return ous;
    }

    private List<ImportDataService> getServices() {
        ArrayList<ImportDataService> services = new ArrayList<>();
        Scanner sc = new Scanner(Objects.requireNonNull(
                this.getClass().getResourceAsStream("/db/default_data/services.csv")));
        sc.useDelimiter(System.lineSeparator());
        while (sc.hasNext())  //returns a boolean value
        {
            String[] values = sc.next().trim().split(SPLITTER);
            if (values.length == 2 && !values[0].startsWith("#")) {
                services.add(ImportDataService.builder()
                        .id(values[1])
                        .name(values[0])
                        .uuid(UUID.randomUUID().toString())
                        .build());
            }
        }
        sc.close();
        return services;
    }

    private Map<String, String> getRegionalKeyMap() {
        HashMap<String, String> map = new HashMap<>();
        // Eintrag für Hessen hinzufügen
        map.put("Hessen", "06");
        Scanner sc = new Scanner(Objects.requireNonNull(
                this.getClass().getResourceAsStream("/db/default_data/regional_keys.csv")));
        sc.useDelimiter(System.lineSeparator());
        while (sc.hasNext())  //returns a boolean value
        {
            String[] values = sc.next().trim().split(SPLITTER);
            if (StringUtils.hasLength(values[3])) {
                map.put(values[0], values[1]);
                map.put(values[3], "06" + values[2]);
            } else {
                // Kreisfreie Stadt
                map.put(values[0], "06" + values[2]);
            }
        }
        sc.close();
        return map;
    }
}
