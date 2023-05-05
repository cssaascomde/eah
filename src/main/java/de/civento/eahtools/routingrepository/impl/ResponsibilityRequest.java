package de.civento.eahtools.routingrepository.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ResponsibilityRequest {
    @Getter @Setter
    private String ags;
    @Getter @Setter
    private String leikaId;
}
