package de.civento.eahtools.routingrepository.impl.responsibility;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsibilityLookupRequest {
    @Getter @Setter @NotBlank(message = "Das Feld darf nicht leer sein")
    private String ouRegionalKey;
    @Getter @Setter @NotBlank(message = "Das Feld darf nicht leer sein")
    private String serviceCiventoKey;
}
