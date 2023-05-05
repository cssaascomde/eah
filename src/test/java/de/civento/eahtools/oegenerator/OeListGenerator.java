package de.civento.eahtools.oegenerator;

import de.civento.eahtools.routingrepository.db.analysis.*;
import de.civento.eahtools.routingrepository.impl.ResponsibilityRequest;
import de.civento.eahtools.routingrepository.impl.ResponsibilityResponse;
import de.civento.eahtools.routingrepository.impl.ResponsibilityService;
import de.civento.eahtools.routingrepository.impl.objects.Area;
import de.civento.eahtools.routingrepository.impl.objects.Ou;
import de.civento.eahtools.routingrepository.impl.objects.Service;
import de.civento.eahtools.routingrepository.impl.xzufi.XZuFiService;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OeListGenerator {

    @Autowired private XZuFiService zuFiService;
    @Autowired private LeistungEntityRepository leistungEntityRepository;
    @Autowired private GebietEntityRepository gebietEntityRepository;
    @Autowired private ZustaendigkeitEntityRepository zustaendigkeitEntityRepository;


    // @Test
    void checkLeistungen() throws IOException {
        ArrayList<Leistung> leistungen = getLeistungen();
        for (Leistung leistung : leistungen) {
            Service service = this.zuFiService.getServiceByLeikaId(leistung.getLeikaId());
            LeistungEntity entity = new LeistungEntity();
            entity.setLeikaId(leistung.getLeikaId());
            entity.setName(leistung.getName());
            if (service != null) {
                entity.setName(service.getName());
                entity.setZufiId(service.getId());
            }

            this.leistungEntityRepository.save(entity);
        }
    }

    // @Test
    void checkGemeinden() throws IOException {

        for (Gemeinde gemeinde : getGemeiden()) {
            if (isGemeindeFromHessen(gemeinde)) {
                Area area = this.zuFiService.getAreaByAgs(gemeinde.getAgs());
                GebietEntity entity = new GebietEntity();
                entity.setAgs(gemeinde.getAgs());
                entity.setName(gemeinde.getName());
                if (area != null) {
                    entity.setName(area.getName());
                    entity.setRs(area.getRs());
                    entity.setZufiId(area.getId());
                }
                this.gebietEntityRepository.save(entity);
            }
        }

    }

    @Test
    void checkZustaendigkeit() throws IOException, JSONException {

        for (GebietEntity gebiet : this.gebietEntityRepository.findAll()) {
            if (gebiet.getZufiId() != null && isAGSfromHessen(gebiet.getAgs())) {
                for (LeistungEntity leistung : this.leistungEntityRepository.findAll()) {
                    if (leistung.getZufiId() != null) {
                        List<ZustaendigkeitEntity> entities =
                                this.zustaendigkeitEntityRepository.findByAgsAndLeikaId(
                                        gebiet.getAgs(), leistung.getLeikaId());
                        if (entities.isEmpty()) {

                            Ou ou = this.zuFiService.getOuByServiceIdAndAreaId(leistung.getZufiId(), gebiet.getZufiId());
                            ZustaendigkeitEntity entity = new ZustaendigkeitEntity();
                            entity.setAgs(gebiet.getAgs());
                            entity.setLeikaId(leistung.getLeikaId());
                            if (ou != null) {
                                entity.setName(ou.getName());
                                entity.setZufiId(ou.getId());
                            }

                            this.zustaendigkeitEntityRepository.save(entity);
                        }
                    }
                }

            }

        }
    }

    private ArrayList<Leistung> getLeistungen() throws IOException {
        ArrayList<Leistung> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("basicdata/Leistungen.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(createLeistungFromCSVLine(line));
            }
        }
        return list;
    }

    private ArrayList<Gemeinde> getGemeiden() throws IOException {
        ArrayList<Gemeinde> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("basicdata/Gemeinden.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(createGemeindeFromCSVLine(line));
            }
        }
        return list;
    }

    private Gemeinde createGemeindeFromCSVLine(String line) {
        Gemeinde gemeinde = new Gemeinde();
        String[] values = line.split(";");
        if (values.length >= 2) {
            String ags = values[0];
            if (ags.length() == 7)
                gemeinde.setAgs("0" + ags);
            else
                gemeinde.setAgs(ags);
            gemeinde.setName(values[1]);
        } else
            throw new IllegalArgumentException(line);
        return gemeinde;
    }

    private Leistung createLeistungFromCSVLine(String line) {
        Leistung leistung = new Leistung();
        String[] values = line.split(";");
        if (values.length >= 3) {

            leistung.setName(values[0]);
            leistung.setLeikaId(values[2].replace(".", ""));
        } else
            throw new IllegalArgumentException(line);
        return leistung;
    }

    private boolean isGemeindeFromHessen(Gemeinde gemeinde) {
        return isAGSfromHessen(gemeinde.getAgs());
    }

    private boolean isAGSfromHessen(String ags) {
        return ags.startsWith("06");
    }
}
