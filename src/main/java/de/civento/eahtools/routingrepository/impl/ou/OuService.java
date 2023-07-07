package de.civento.eahtools.routingrepository.impl.ou;

import de.civento.eahtools.routingrepository.base.businessobjects.BaseService;
import de.civento.eahtools.routingrepository.base.businessobjects.Helper;
import de.civento.eahtools.routingrepository.base.businessobjects.IPageBusinessObjects;
import de.civento.eahtools.routingrepository.base.logging.LoggingUtils;
import de.civento.eahtools.routingrepository.db.OuEntity;
import de.civento.eahtools.routingrepository.db.OuEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OuService extends BaseService<Ou, OuEntity> {
    private static final OuModelMapper MAPPER = new OuModelMapper();
    private final OuEntityRepository repository;

    public OuService(OuEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<OuEntity, String> getRepository() {
        return this.repository;
    }

    @Override
    protected String getBusinessObjectName() {
        return Ou.class.getSimpleName();
    }

    @Override
    protected IPageBusinessObjects<Ou> convertList(Page<OuEntity> page) {
        return OuService.MAPPER.convertList(page);
    }

    @Override
    protected Ou convert(OuEntity entity) {
        return OuService.MAPPER.convert(entity);
    }

    @Override
    @Transactional
    public Ou create(@NonNull Ou obj) {
        OuEntity entity = new OuEntity();
        performMapping(entity, obj);
        return convert(this.repository.save(entity));
    }

    @Override
    public Ou update(@NonNull Ou obj) {
        Optional<OuEntity> entity = this.repository.findById(obj.getId());
        if (entity.isPresent()) {
            performMapping(entity.get(), obj);
            entity.get().setSysVersion(obj.getSysVersion());

            return OuService.MAPPER.convert(this.repository.save(entity.get()));
        } else {
            throw new EntityNotFoundException(LoggingUtils.getRecordNotFoundMsg(
                    getBusinessObjectName(), obj.getId()));
        }
    }

    public IPageBusinessObjects<Ou> search(OuSearchObject so) {
        return convertList(this.repository.search(
                Helper.replacePlaceholder(so.getCiventoKey()),
                Helper.replacePlaceholder(so.getRegionalKey()),
                Helper.replacePlaceholder(so.getName()),
                so.getType(),
                so.isWithoutPaging() ? Pageable.unpaged() : PageRequest.of(so.getPageNumber(), so.getPageSize())));
    }

    private void performMapping(OuEntity entity, Ou obj) {
        entity.setName(obj.getName());
        entity.setType(obj.getType());
        entity.setCiventoKey(obj.getCiventoKey());
        entity.setRegionalKey(obj.getRegionalKey());
        entity.setAddress(obj.getAddress());
        entity.setZipCode(obj.getZipCode());
        entity.setCity(obj.getCity());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());
    }
}
