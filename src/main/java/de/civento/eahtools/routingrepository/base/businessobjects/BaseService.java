package de.civento.eahtools.routingrepository.base.businessobjects;

import de.civento.eahtools.routingrepository.base.entities.BaseEntity;
import de.civento.eahtools.routingrepository.base.logging.LoggingUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class BaseService<T extends BusinessObject, E extends BaseEntity> {
    protected abstract JpaRepository<E, String> getRepository();

    protected abstract String getBusinessObjectName();
    protected abstract IPageBusinessObjects<T> convertList(Page<E> page);
    protected abstract T convert(E entity);
    /**
     * Die Funktion gibt die einträge als Liste (Page) zurück
     *
     * @param pageable Seiten und Sortierungsinformationen
     * @return List der Einträge
     */
    public IPageBusinessObjects<T> findAll(Pageable pageable) {
        return convertList(getRepository().findAll(pageable));
    }

    /**
     * Die Funktion lädt einen Eintrag auf Basis der Id
     *
     * @param id Id der Umgebung
     * @return Der korrespondierender Eintrag zur Id
     */
    public T getById(@NonNull String id) {
        Optional<E> entity = getRepository().findById(id);
        if (entity.isPresent())
            return convert(entity.get());
        else
            throw new EntityNotFoundException(LoggingUtils.getRecordNotFoundMsg(
                    getBusinessObjectName(), id));
    }

    /**
     * Die Funktion speichert eine neue Umgebung
     *
     * @param obj Die zu speichernde Umgebung
     * @return Der gespeicherte Eintrag
     */

    @Transactional
    public abstract T create(@NonNull T obj);

    /**
     * Die Funktion aktualisiert einen Eintrag
     *
     * @param obj Der zu speichernde Eintrag
     * @return Der gespeicherte Eintrag
     */
    @Transactional
    public abstract T update(@NonNull T obj);

    /**
     * Die Funktion aktualisiert einen Eintrag
     * oder legt einen Eintrag an, wenn er noch nicht
     * gespeichert wurde
     *
     * @param obj Der zu speichernde Eintrag
     * @return Der gespeicherte Eintrag
     */
    @Transactional
    public T createOrUpdate(@NonNull T obj) {
        if (obj.getSysModifiedAt() == null)
            return create(obj);
        else
            return update(obj);
    }

    /**
     * Die Funktion löscht eine Entität auf Basis der übergebenen Id
     *
     * @param id Die Id des zu löschenden Entität
     */
    public void deleteById(@NonNull String id) {
        getRepository().deleteById(id);
    }

    /**
     * Die Funktion löscht alle Objekte dieser Entität
     */
    public void deleteAll() {
        getRepository().deleteAll();
    }
}
