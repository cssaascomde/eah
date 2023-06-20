package de.civento.eahtools.routingrepository.impl.responsibility;

import de.civento.eahtools.routingrepository.base.businessobjects.BaseService;
import de.civento.eahtools.routingrepository.base.businessobjects.IPageBusinessObjects;
import de.civento.eahtools.routingrepository.base.logging.LoggingUtils;
import de.civento.eahtools.routingrepository.db.OuEntityRepository;
import de.civento.eahtools.routingrepository.db.ResponsibilityEntity;
import de.civento.eahtools.routingrepository.db.ResponsibilityEntityRepository;
import de.civento.eahtools.routingrepository.db.ServiceEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ResponsibilityService extends BaseService<Responsibility, ResponsibilityEntity> {
    private static final ResponsibilityModelMapper MAPPER = new ResponsibilityModelMapper();
    private final ResponsibilityEntityRepository repository;
    private final OuEntityRepository ouEntityRepository;
    private final ServiceEntityRepository serviceEntityRepository;

    public ResponsibilityService(ResponsibilityEntityRepository repository,
                                 OuEntityRepository ouEntityRepository,
                                 ServiceEntityRepository serviceEntityRepository) {
        this.repository = repository;
        this.ouEntityRepository = ouEntityRepository;
        this.serviceEntityRepository = serviceEntityRepository;
    }

    @Override
    protected JpaRepository<ResponsibilityEntity, String> getRepository() {
        return this.repository;
    }

    @Override
    protected String getSimpleClassName() {
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

        if (obj.getService() != null) {
            entity.setServiceEntity(this.serviceEntityRepository.findById(obj.getService().getId()).orElse(null));
        }
        if (obj.getOu() != null) {
            entity.setOuEntity(this.ouEntityRepository.findById(obj.getOu().getId()).orElse(null));
        }
        return convert(getRepository().save(entity));
    }

    @Override
    public Responsibility update(@NonNull Responsibility obj) {
        ResponsibilityEntity entity = getRepository().findById(obj.getId()).orElse(null);
        if (entity != null) {
            if (obj.getOu() != null && !obj.getOu().getId().equals(entity.getOuEntity().getId())) {
                entity.setOuEntity(this.ouEntityRepository.findById(obj.getOu().getId()).orElse(null));
            }
            if (obj.getService() != null && !obj.getService().getId().equals(entity.getServiceEntity().getId())) {
                entity.setServiceEntity(this.serviceEntityRepository.findById(obj.getService().getId()).orElse(null));
            }

            entity.setDeliveryType(obj.getDeliveryType());
            entity.setSysVersion(obj.getSysVersion());


            return convert(getRepository().save(entity));

        } else {
            throw new EntityNotFoundException(LoggingUtils.getRecordNotFoundMsg(
                    getSimpleClassName(), obj.getId()));
        }
    }

    public IPageBusinessObjects<Responsibility> search(ResponsibilitySearchObject so) {
        return convertList(this.repository.search(
                StringUtils.hasLength(so.getOuId()) ? so.getOuId() : null,
                StringUtils.hasLength(so.getServiceId()) ? so.getServiceId() : null,
                so.getDeliveryType(),
                so.isWithoutPaging() ? Pageable.unpaged() : PageRequest.of(so.getPageNumber(), so.getPageSize())));
    }
}
