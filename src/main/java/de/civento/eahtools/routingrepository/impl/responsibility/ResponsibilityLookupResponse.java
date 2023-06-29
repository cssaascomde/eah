package de.civento.eahtools.routingrepository.impl.responsibility;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
public class ResponsibilityLookupResponse {
    @Builder.Default
    @Getter @Setter
    boolean successful = false;
    @Getter @Setter
    private String msg;
    @Getter @Setter
    private Responsibility responsibility;
}
