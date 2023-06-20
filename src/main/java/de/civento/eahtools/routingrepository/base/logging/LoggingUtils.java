package de.civento.eahtools.routingrepository.base.logging;

/**
 * Hilfsklasse für Logging
 */
public class LoggingUtils {
    /**
     * Die Funktion formatiert einen Log-Eintrag
     * @param function Name der Funktion
     * @param code interner Logging-Code {@link ErrorCode}
     * @param message Log-Nachricht
     * @return Log-Eintrag
     */
    public static String formatLogMessage(String function, ErrorCode code, String message) {
        return String.format(".%s: [%s] [Message:%s]", function, code.toString(), message);
    }

    /**
     * Die Funktion formatiert die Fehlermeldung für einen nicht gefunden Datensatz in der Datenbank
     * @param object Name des Objektes, nachdem gesucht wurde
     * @param id Id des Objektes
     * @return Formatierte Fehlermeldung
     */
    public static String getRecordNotFoundMsg(String object, String id) {
        return String.format("Datensatz vom Typ %s mit der Id %s wurde nicht gefunden", 
            object, id);
    }
}
