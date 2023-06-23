package de.civento.eahtools.routingrepository.impl.service;

import de.civento.eahtools.routingrepository.base.businessobjects.BaseService;
import de.civento.eahtools.routingrepository.base.businessobjects.Helper;
import de.civento.eahtools.routingrepository.base.businessobjects.IPageBusinessObjects;
import de.civento.eahtools.routingrepository.base.logging.LoggingUtils;
import de.civento.eahtools.routingrepository.db.ServiceEntity;
import de.civento.eahtools.routingrepository.db.ServiceEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService extends BaseService<Service, ServiceEntity> {
    private static final ServiceModelMapper MAPPER = new ServiceModelMapper();
    private final ServiceEntityRepository repository;

    public ServiceService(ServiceEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<ServiceEntity, String> getRepository() {
        return this.repository;
    }

    @Override
    protected String getBusinessObjectName() { return Service.class.getSimpleName(); }

    @Override
    protected IPageBusinessObjects<Service> convertList(Page<ServiceEntity> page) {
        return ServiceService.MAPPER.convertList(page);
    }

    @Override
    protected Service convert(ServiceEntity entity) {
        return ServiceService.MAPPER.convert(entity);
    }

    @Override
    @Transactional
    public Service create(@NonNull Service obj) {
        ServiceEntity entity = new ServiceEntity();
        entity.setName(obj.getName());
        entity.setCiventoKey(obj.getCiventoKey());
        entity.setLeikaKey(obj.getLeikaKey());
        entity.setDeliveryType(obj.getDeliveryType());
        entity.setResponsibilityType(obj.getResponsibilityType());
        return convert(this.repository.save(entity));
    }

    @Override
    public Service update(@NonNull Service obj) {
        Optional<ServiceEntity> entity = this.repository.findById(obj.getId());
        if (entity.isPresent()) {
            entity.get().setName(obj.getName());
            entity.get().setLeikaKey(obj.getLeikaKey());
            entity.get().setCiventoKey(obj.getCiventoKey());
            entity.get().setDeliveryType(obj.getDeliveryType());
            entity.get().setResponsibilityType(obj.getResponsibilityType());

            entity.get().setSysVersion(obj.getSysVersion());

            return ServiceService.MAPPER.convert(this.repository.save(entity.get()));
        } else {
            throw new EntityNotFoundException(LoggingUtils.getRecordNotFoundMsg(
                    getBusinessObjectName(), obj.getId()));
        }
    }

    public IPageBusinessObjects<Service> search(ServiceSearchObject so) {
        return convertList(this.repository.search(
                Helper.replacePlaceholder(so.getLeikaKey()),
                Helper.replacePlaceholder(so.getCiventoKey()),
                Helper.replacePlaceholder(so.getName()),
                so.getResponsibilityType(),
                so.getDeliveryType(),
                so.isWithoutPaging() ? Pageable.unpaged() : PageRequest.of(so.getPageNumber(), so.getPageSize())));
    }
}
