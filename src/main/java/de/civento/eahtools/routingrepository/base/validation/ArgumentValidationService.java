package de.civento.eahtools.routingrepository.base.validation;

import de.civento.eahtools.routingrepository.base.i18n.AppMessageSource;
import de.civento.eahtools.routingrepository.base.logging.ErrorCode;
import de.civento.eahtools.routingrepository.base.logging.LoggingUtils;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Service für die Validierung von Feldern
 */
@Log
@Component
public class ArgumentValidationService {
    private final AppMessageSource messageSource;

    public ArgumentValidationService(AppMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Die Funktion prüft, ob ein Objekt Null ist, wenn nicht,
     * wird eine {@link IllegalArgumentException} geworfen wird
     * @param o Das zu prüfende Projekt
     * @param fieldName Feldname für die Fehlermeldung
     */
    public void checkNull(Object o, String fieldName)  {
        checkNull(o, fieldName, "get");
    }

    /**
     * Die Funktion prüft, ob ein Objekt Null ist, wenn nicht,
     * wird eine {@link IllegalArgumentException} geworfen wird
     * @param o Das zu prüfende Projekt
     * @param fieldName Feldname für die Fehlermeldung
     * @param getterPrefix Prefix der GET-Methode
     */
    public void checkNull(Object o, String fieldName, String getterPrefix)  {
        if (getValue(o, fieldName, getterPrefix) != null) {
            ErrorCode code = ErrorCode.CODING_ERROR;
            String msg = this.messageSource.get(
                    "standard.error.shouldnotbeemtpy",
                    o.getClass().getSimpleName(),
                    fieldName
            );
            log.severe(LoggingUtils.formatLogMessage("checkNull", code, msg));
            throw new IllegalArgumentException(String.format("%s - %s", code.toStringForException(), msg));
        }
    }



    /**
     * Die Funktion prüft, ob ein Objekt nicht Null ist, wenn nicht, wird eine {@link IllegalArgumentException} geworfen wird
     * @param o Das zu prüfende Projekt
     * @param fieldName Feldname für die Fehlermeldung
     */
    public void checkNotNull(Object o, String fieldName) {
        checkNotNull(o, fieldName, "get");
    }

    /**
     * Die Funktion prüft, ob ein Objekt nicht Null ist, wenn nicht,
     * wird eine {@link IllegalArgumentException} geworfen wird
     * @param o Das zu prüfende Projekt
     * @param fieldName Feldname für die Fehlermeldung
     * @param getterPrefix Prefix der GET-Methode
     */
    public void checkNotNull(Object o, String fieldName, String getterPrefix) {
        if (getValue(o, fieldName, getterPrefix) == null) {
            ErrorCode code = ErrorCode.CODING_ERROR;
            String msg = this.messageSource.get(
                    "standard.error.shouldbeemtpy",
                    o.getClass().getSimpleName(),
                    fieldName
            );
            log.severe(LoggingUtils.formatLogMessage("checkNotNull", code, msg));
            throw new IllegalArgumentException(String.format("%s - %s", code.toStringForException(), msg));
        }
    }

    /**
     * Die Funktion sucht über Reflextions die GET-Methode des Felds und gibt den Wert zurück.
     * Wenn es beim Zugriff zu einem Fehler kommt, wird eine {@link RuntimeException} mit {@link ErrorCode} geworfen.
     * @param o Objekt
     * @param fieldName Name des Feldes
     * @param getterPrefix Prefix des Getters
     * @return Inhalt des Feldes
     */
    private Object getValue(Object o, String fieldName, String getterPrefix) {
        try {
            Method method = o.getClass().getDeclaredMethod(getterPrefix + fieldName);
            return  method.invoke(o);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            ErrorCode code = ErrorCode.CODING_ERROR;
            log.severe(String.format(".create: %s Message %s",
                    code,
                    e.getLocalizedMessage()));
            throw new RuntimeException(code.toStringForException());
        }
    }
}
