package de.civento.eahtools.routingrepository.api;

import de.civento.eahtools.routingrepository.base.api.ErrorResponse;
import de.civento.eahtools.routingrepository.impl.responsibility.ResponsibilityLookupRequest;
import de.civento.eahtools.routingrepository.impl.responsibility.ResponsibilityLookupResponse;
import de.civento.eahtools.routingrepository.impl.responsibility.ResponsibilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.extern.java.Log;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.*;

@RequestMapping(path = { ExternResponsibilityController.PATH }, produces = APPLICATION_JSON_VALUE)
@Tag(
        name = "ResponsibilityController",
        description = "Schnittstelle für die Abfrage von Zuständigkeiten"
)
@Log
@RestController
public class ExternResponsibilityController {
    public static final String PATH = "/routing-repository/responsibility/api/v1";

    private final ResponsibilityService service;

    public ExternResponsibilityController(ResponsibilityService service) {
        this.service = service;
    }


    @Operation(
            summary = "Der Service gibt die auf Basis der Anfrage die gefundene Zuständigkeit zurück")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Die auf Basis der Anfrage gefundene Zuständigkeit", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsibilityLookupResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Return-Code beim Auftreten eines technischen Fehlers", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})
    })
    @RequestMapping(method = RequestMethod.GET, path = "/byAgsAndServiceIdAsJSON")
    public ResponseEntity<ResponsibilityLookupResponse> getAsJSON(
            @RequestParam @Schema(description = "Der allgemeine Gemeindeschlüssel")
            @NotNull String ags,
            @RequestParam @Schema(description = "Die ID der Dienstleitung aus civento")
            @NotNull String serviceId) {

        ResponsibilityLookupRequest request = new ResponsibilityLookupRequest(ags, serviceId);
        ResponsibilityLookupResponse response = this.service.processLookupRequest(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Der Service gibt die auf Basis der Anfrage die gefundene Zuständigkeit zurück")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Die auf Basis der Anfrage gefundene Zuständigkeit", content = {
                    @Content(mediaType = APPLICATION_XML_VALUE, schema = @Schema(implementation = ResponsibilityLookupResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Return-Code beim Auftreten eines technischen Fehlers", content = {
                    @Content(mediaType = APPLICATION_XML_VALUE, schema = @Schema(implementation = ErrorResponse.class))})
    })
    @RequestMapping(method = RequestMethod.GET, path = "/byAgsAndServiceIdAsXML",
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ResponsibilityLookupResponse> getAsXML(
            @RequestParam @Schema(description = "Der allgemeine Gemeindeschlüssel")
            @NotNull String ags,
            @RequestParam @Schema(description = "Die ID der Dienstleistung aus")
            @NotNull String serviceId) {

        ResponsibilityLookupRequest request = new ResponsibilityLookupRequest(ags, serviceId);
        ResponsibilityLookupResponse response = this.service.processLookupRequest(request);
        return ResponseEntity.ok(response);
    }


}
