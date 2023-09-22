package de.civento.eahtools.routingrepository.impl.responsibility;

import de.civento.eahtools.routingrepository.base.businessobjects.BaseService;
import de.civento.eahtools.routingrepository.base.businessobjects.IPageBusinessObjects;
import de.civento.eahtools.routingrepository.base.logging.LoggingUtils;
import de.civento.eahtools.routingrepository.db.*;
import de.civento.eahtools.routingrepository.impl.RegionalKeyTools;
import de.civento.eahtools.routingrepository.impl.ou.Ou;
import de.civento.eahtools.routingrepository.impl.service.ServiceSearchObject;
import de.civento.eahtools.routingrepository.impl.service.ServiceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Log
public class ResponsibilityService extends BaseService<Responsibility, ResponsibilityEntity> {
    private static final String OU_CIVENTO_KEY = "00.00.EAH.ZS.9999";
    private static final String DEFAULT_CENTRAL_REGIONAL_KEY = "0";
    private static final ResponsibilityModelMapper MAPPER = new ResponsibilityModelMapper();
    private final ResponsibilityEntityRepository repository;
    private final ApplicationContext applicationContext;



    public ResponsibilityService(ResponsibilityEntityRepository repository,
                                 ApplicationContext applicationContext) {
        this.repository = repository;
        this.applicationContext = applicationContext;
    }

    @Override
    protected JpaRepository<ResponsibilityEntity, String> getRepository() {
        return this.repository;
    }

    @Override
    protected String getBusinessObjectName() {
        return Responsibility.class.getSimpleName();
    }

    @Override
    protected IPageBusinessObjects<Responsibility> convertList(Page<ResponsibilityEntity> page) {
        return MAPPER.convertList(page);
    }

    @Override
    protected Responsibility convert(ResponsibilityEntity entity) {
        return MAPPER.convert(entity);
    }

    @Override
    public Responsibility create(@NonNull Responsibility obj) {
        ResponsibilityEntity entity = new ResponsibilityEntity();
        entity.setDeliveryType(obj.getDeliveryType());
        entity.setInterfaceType(obj.getInterfaceType());
        entity.setRegionalKey(obj.getRegionalKey());

        if (obj.getService() != null) {
            entity.setServiceEntity(this.applicationContext.getBean(ServiceEntityRepository.class)
                    .findById(obj.getService().getId()).orElse(null));
        }
        if (obj.getOu() != null) {
            entity.setOuEntity(this.applicationContext.getBean(OuEntityRepository.class)
                    .findById(obj.getOu().getId()).orElse(null));
        }
        return convert(getRepository().save(entity));
    }

    @Override
    public Responsibility update(@NonNull Responsibility obj) {
        ResponsibilityEntity entity = getRepository().findById(obj.getId()).orElse(null);
        if (entity != null) {
            if (obj.getOu() != null && !obj.getOu().getId().equals(entity.getOuEntity().getId())) {
                entity.setOuEntity(this.applicationContext.getBean(OuEntityRepository.class)
                        .findById(obj.getOu().getId()).orElse(null));
            }
            if (obj.getService() != null && !obj.getService().getId().equals(entity.getServiceEntity().getId())) {
                entity.setServiceEntity(this.applicationContext.getBean(ServiceEntityRepository.class)
                        .findById(obj.getService().getId()).orElse(null));
            }

            entity.setDeliveryType(obj.getDeliveryType());
            entity.setInterfaceType(obj.getInterfaceType());
            entity.setRegionalKey(obj.getRegionalKey());
            entity.setSysVersion(obj.getSysVersion());


            return convert(getRepository().save(entity));

        } else {
            throw new EntityNotFoundException(LoggingUtils.getRecordNotFoundMsg(
                    getBusinessObjectName(), obj.getId()));
        }
    }

    public IPageBusinessObjects<Responsibility> search(@NonNull ResponsibilitySearchObject so) {
        return convertList(this.repository.search(
                StringUtils.hasLength(so.getOuId()) ? so.getOuId() : null,
                StringUtils.hasLength(so.getServiceId()) ? so.getServiceId() : null,
                so.getDeliveryType(),
                StringUtils.hasLength(so.getRegionalKey()) ? so.getRegionalKey() : null,
                so.isWithoutPaging() ? Pageable.unpaged() : PageRequest.of(so.getPageNumber(), so.getPageSize())));
    }

    public ResponsibilityLookupResponse processLookupRequest(@NonNull @Valid ResponsibilityLookupRequest request) {
        ResponsibilityLookupResponse response = ResponsibilityLookupResponse.builder()
                .responsibility(new Responsibility()).build();

        try {

            // Suche der Zuständigkeit: Kommunal
            ResponsibilityEntity entity;
            entity = this.repository.findByOuRegionalKeyAndServiceCiventoKey(
                    RegionalKeyTools.getMunicipalKey(request.getOuRegionalKey()),
                    request.getServiceCiventoKey()).orElse(null);
            // Suche der Zuständigkeit: Landkreis
            if (entity == null) {
                entity = this.repository.findByOuRegionalKeyAndServiceCiventoKey(
                        RegionalKeyTools.getCountyKey(request.getOuRegionalKey()),
                        request.getServiceCiventoKey()).orElse(null);
            }
            // Suche der Zuständigkeit: Regierungsbezirk
            if (entity == null) {
                entity = this.repository.findByOuRegionalKeyAndServiceCiventoKey(
                        RegionalKeyTools.getRegionalCouncilKey(request.getOuRegionalKey()),
                        request.getServiceCiventoKey()).orElse(null);
            }
            // Suche der Zuständigkeit: Zentral
            if (entity == null) {
                entity = this.repository.findByOuRegionalKeyAndServiceCiventoKey(
                        DEFAULT_CENTRAL_REGIONAL_KEY,
                        request.getServiceCiventoKey()).orElse(null);
            }
            if (entity != null) {
                response.setSuccessful(true);
                response.setResponsibility(convert(entity));
            } else {
                // Fehlerbehandlung
                IPageBusinessObjects<de.civento.eahtools.routingrepository.impl.service.Service> result =
                        applicationContext.getBean(ServiceService.class).search(
                                ServiceSearchObject.builder().civentoKey(request.getServiceCiventoKey()).build());
                if (result.getTotalElements() == 1) {
                    response.setMsg(String.format("Keine Zuständigkeit mit der Dienstleistung '%s' und dem " +
                                    "Regionalschlüssel '%s' gefunden.",
                            request.getServiceCiventoKey(), request.getOuRegionalKey()));
                    response.setResponsibility(getDefaultResponsibility());
                    response.getResponsibility().setService(result.getContent().get(0));
                } else if (result.getTotalElements() == 0) {
                    response.setMsg(String.format("Dienstleistung mit der civento-ID '%s' konnte nicht gefunden werden.",
                            request.getServiceCiventoKey()));
                    response.setResponsibility(getDefaultResponsibility());
                } else if (result.getTotalElements() > 1) {
                    response.setMsg(String.format("Mehrere Dienstleistungen mit der civento-ID '%s' gefunden.",
                            request.getServiceCiventoKey()));
                    response.setResponsibility(getDefaultResponsibility());
                }
            }
        } catch (Exception e) {
            log.severe(".processLookupRequest: " + e.getMessage());
            response.setSuccessful(false);
            response.setMsg(e.getMessage());
            response.setResponsibility(getDefaultResponsibility());
        }

        // Sonderzeichen entfernen
        response.getResponsibility().getOu().setCiventoKey(
                response.getResponsibility().getOu().getCiventoKey().replace("_", "")
        );

        return response;
    }

    private Responsibility getDefaultResponsibility() {
        return new Responsibility(
                Ou.builder().civentoKey(OU_CIVENTO_KEY).build(),
                null,
                DeliveryType.internal,
                "06??????",
                null);
    }
}
