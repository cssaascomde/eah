package de.civento.eahtools.routingrepository.base.businessobjects;


import de.civento.eahtools.routingrepository.base.entities.BaseEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

/**
 * Basisklasse für die Konvertierung von Datenbankentitäten in Businessobjects
 * @param <BO> Geschäftsobjekt
 * @param <Entity> Datenbankentität
 * @param <PageOfBO> Page von Geschäftsobjekten
 */
public abstract class EntityToBusinessObjectConverter
    <BO, Entity extends BaseEntity, PageOfBO extends IPageBusinessObjects<BO>> {

    /**
     * Die Funktion liefert ein neues Page-Objekt für das DTO zurück
     * @return Page-Objekt für das DTO
     */
    @SuppressWarnings (value="unchecked")
    protected PageOfBO getPagesOfBusinessObjects() {
        return (PageOfBO) new PageOfBusinessObjects<BO>();
    }

    /**
     * Die Funktion konvertiert eine Datenbankentität in ein DTO
     * @param entity Die Datenbankentität sie kopiert werden soll
     * @return Das neu erzeugte DTO
     */
    public abstract BO convert(Entity entity);

    /**
     * Die Funktion konvertiert eine Liste/Page von Entitäten in eine Page von DTOs
     * @param page Die zu konvertierende Liste
     * @return Die Liste der DTOs
     */
    public PageOfBO convertList(@NonNull Page<Entity> page) {
        // Initialisierung
        final PageOfBO pageOfBusinessObjects = getPagesOfBusinessObjects();

        // Paging - Informationen setzen
        pageOfBusinessObjects.setTotalElements(page.getTotalElements());
        pageOfBusinessObjects.setTotalPages(page.getTotalPages());
        pageOfBusinessObjects.setPageNumber(page.getNumber());
        pageOfBusinessObjects.setPageSize(page.getSize());

        // Liste erzeugen und konvertieren
        pageOfBusinessObjects.setContent(new ArrayList<>());
        page.getContent().forEach(entity -> pageOfBusinessObjects.getContent().add(convert(entity)));

        return pageOfBusinessObjects;
    }

}
