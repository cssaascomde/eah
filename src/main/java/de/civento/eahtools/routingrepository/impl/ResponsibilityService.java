package de.civento.eahtools.routingrepository.impl;

import de.civento.eahtools.routingrepository.db.AreaEntity;
import de.civento.eahtools.routingrepository.db.AreaEntityRepository;
import de.civento.eahtools.routingrepository.db.ServiceEntity;
import de.civento.eahtools.routingrepository.db.ServiceEntityRepository;
import de.civento.eahtools.routingrepository.impl.xzufi.XZuFiService;
import org.springframework.stereotype.Component;

/**
 * Die Services-Klasse ermittelt die Zuständigkeit für eine Leistung in einem Gebiet
 */
@Component
public class ResponsibilityService {
    private final XZuFiService zuFiService;
    private final AreaEntityRepository areaEntityRepository;
    private final ServiceEntityRepository serviceEntityRepository;

    public ResponsibilityService(XZuFiService zuFiService, AreaEntityRepository areaEntityRepository, ServiceEntityRepository serviceEntityRepository) {
        this.zuFiService = zuFiService;
        this.areaEntityRepository = areaEntityRepository;
        this.serviceEntityRepository = serviceEntityRepository;
    }

    /**
     * Die Funktion ermittelt die Zuständigkeit für eine Leistung in einem Gebiet
     * @param request Das {@link ResponsibilityRequest} Objekt mit AGS und LeikaID
     * @return Das {@link ResponsibilityResponse}-Objekt mit Informationen über die Zuständigkeit
     */
    public ResponsibilityResponse get(ResponsibilityRequest request) {
        ResponsibilityResponse response = new ResponsibilityResponse();

        ServiceEntity serviceEntity = this.serviceEntityRepository.findByLeikaId(request.getLeikaId());
        AreaEntity areaEntity = this.areaEntityRepository.findByAgs(request.getAgs());


        if ((serviceEntity == null || serviceEntity.isOverloading() == false) &&
                (areaEntity == null || areaEntity.isOverloading() == false)) {
            // Suche nach der Leistung
            response.setService(this.zuFiService.getServiceByLeikaId(request.getLeikaId()));

            // Suche nach dem Gebiet
            response.setArea(this.zuFiService.getAreaByAgs(request.getAgs()));

            // Suche nach der zuständigen Organisationseinheit
            if (response.getArea() != null && response.getService() != null) {
                response.setOu(this.zuFiService.getOuByServiceIdAndAreaId(
                        response.getService().getId(),
                        response.getArea().getId()));
            } else if (serviceEntity != null) {

            }
        }
        return response;
    }
}
