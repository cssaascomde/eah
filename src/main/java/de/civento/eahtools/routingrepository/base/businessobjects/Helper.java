package de.civento.eahtools.routingrepository.base.businessobjects;

import de.civento.eahtools.routingrepository.base.entities.BaseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Hilfsklasse für Funktionen Rund um die Klasse {@link SearchObject}
 */
public class Helper {

    /**
     * Feldmapping für die Standardfelder wie z.B. Id, ModifiedAt und ModifiedBy
     * @param entity {@link BaseEntity} aus der die Werte entnommen werden
     * @param bo {@link SearchObject} in das die Werte Übernommen werden
     */
    public static void mapDefaultFieldsFromEntityToBusinessObject(BaseEntity entity, BusinessObject bo) {
        bo.setId(entity.getId());
        bo.setSysModifiedAt(
                LocalDateTime.ofInstant(
                        entity.getSysModifiedAt().toInstant(), ZoneId.systemDefault()));

        bo.setSysModifiedBy(entity.getSysModifiedBy());
        bo.setSysVersion(entity.getSysVersion());
    }

    /**
     * Die Funktion erzeugt aus einem {@link SearchObject} ein {@link Pageable} Objekt für die Erstellung von Suchabfragen
     * @param so Das {@link SearchObject}, welches konvertiert werden soll
     * @return Das konvertierte {@link Pageable}
     */
    public static Pageable getPageableFromSearchObject(SearchObject so) {
        if (so.getSortBy() == null || so.getSortBy().isEmpty()) {
            return PageRequest.of(so.getPageNumber(), so.getPageSize());
        } else {
            return PageRequest.of(
                    so.getPageNumber(),
                    so.getPageSize(),
                    so.isAscending() ? Direction.ASC : Direction.DESC,
                    so.getSortBy());
        }
    }

    /**
     * Die Funktion ersetzt den logischen Platzhalter "*" mit dem SQL Platzhalter "%"
     * @param searchValue Der Suchwert in dem die Zeichen ersetzt werden sollen
     * @return Der Suchwert mit den ersetzten Zeichen
     */
    public static String replacePlaceholder(String searchValue) {
        if (searchValue != null)
            return searchValue.replace("*", "%");
        else
            return null;
    }

    
}
