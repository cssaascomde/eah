package de.civento.eahtools.routingrepository.impl.responsibility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ResponsibilityRequest {
    @Getter @Setter
    private String regionalKey;
    @Getter @Setter
    private String civentoKey;
}
