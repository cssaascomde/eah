package de.civento.eahtools.routingrepository.db.migration;

import lombok.extern.java.Log;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.Scanner;

@Component
@Log
public class V2__services extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        DecimalFormat codeFormat = new DecimalFormat("00");
        String sql = "insert into service " +
                "(id, sys_modified_at, sys_modified_by, sys_version, leika_key, civento_key, " +
                "name, responsibility_type, delivery_type) " +
                "values " +
                "(newid(), CURRENT_TIMESTAMP, 'Import', 0, 'nicht definiert', ?, ?, 'unknown', 'internal')";
        try (PreparedStatement stmt = context.getConnection().prepareStatement(sql)) {
            Scanner sc = new Scanner(this.getClass().getResourceAsStream("/db/default_data/services.csv"));
            sc.useDelimiter(System.lineSeparator());
            int i = 0;
            while (sc.hasNext())  //returns a boolean value
            {
                i++;
                String[] values = sc.next().trim().split(";");
                if (values.length == 2 && !values[0].startsWith("#")) {
                    stmt.setString(1, values[1]);
                    stmt.setString(2, values[0]);
                    stmt.addBatch();
                }
            }
            sc.close();  //closes the scanner
            int updateCount = stmt.executeBatch().length;
            log.info(String.format(".migrate: %d Leistungen imporiert", updateCount));

        } catch (Exception e) {
            log.severe(".migrate: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}