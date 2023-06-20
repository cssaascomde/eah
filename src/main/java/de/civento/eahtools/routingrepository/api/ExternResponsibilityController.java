package de.civento.eahtools.routingrepository.api;

import de.civento.eahtools.routingrepository.impl.responsibility.ResponsibilityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


//    @RequestMapping(method = RequestMethod.GET, path = "/")
//    public String hello() {return "Hallo " + SecurityContextHolder.getContext().getAuthentication().getName();}


    /*
    @Operation(
            summary = "Der Service gibt die auf Basis der Anfrage die gefundene Zuständigkeit zurück")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Die auf Basis der Anfrage gefundene Zuständigkeit", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsibilityResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Return-Code beim Auftreten eines technischen Fehlers", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})
    })
    @RequestMapping(method = RequestMethod.GET, path = "/byAgsAndLeikaIdAsJSON")
    public ResponseEntity<ResponsibilityResponse> getAsJSON(
            @RequestParam @Schema(description = "Der allgemeine Gemeindeschlüssel")
            @NotNull String ags,
            @RequestParam @Schema(description = "Die ID aus dem Leistungskatalog")
            @NotNull String leikaId) {

        ResponsibilityRequest request = new ResponsibilityRequest(ags, leikaId);
        ResponsibilityResponse response = this.service.get(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Der Service gibt die auf Basis der Anfrage die gefundene Zuständigkeit zurück")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Die auf Basis der Anfrage gefundene Zuständigkeit", content = {
                    @Content(mediaType = APPLICATION_XML_VALUE, schema = @Schema(implementation = ResponsibilityResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Return-Code beim Auftreten eines technischen Fehlers", content = {
                    @Content(mediaType = APPLICATION_XML_VALUE, schema = @Schema(implementation = ErrorResponse.class))})
    })
    @RequestMapping(method = RequestMethod.GET, path = "/byAgsAndLeikaIdAsXML",
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ResponsibilityResponse> getAsXML(
            @RequestParam @Schema(description = "Der allgemeine Gemeindeschlüssel")
            @NotNull String ags,
            @RequestParam @Schema(description = "Die ID aus dem Leistungskatalog")
            @NotNull String leikaId) {

        ResponsibilityRequest request = new ResponsibilityRequest(ags, leikaId);
        ResponsibilityResponse response = this.service.get(request);
        return ResponseEntity.ok(response);
    }

     */
}
