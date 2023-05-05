package de.civento.eahtools.routingrepository.api.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {

    @Schema(name = "msg", description = "Beschreibung des aufgetretenen Fehlers", example = "Fehlermeldung 42")
    private String msg;
    @Schema(name = "timestamp", description = "Aktueller Zeitstemple")
    private Date timestamp;
    
}