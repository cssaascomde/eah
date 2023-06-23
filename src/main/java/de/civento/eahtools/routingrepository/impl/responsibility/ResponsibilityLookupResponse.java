package de.civento.eahtools.routingrepository.impl.responsibility;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsibilityLookupResponse {
    @Builder.Default
    @Getter @Setter
    boolean successful = false;
    @Getter @Setter
    private String msg;
    @Getter @Setter
    private Responsibility responsibility;
}
