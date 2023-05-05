package de.civento.eahtools.routingrepository.impl;

import de.civento.eahtools.routingrepository.impl.objects.Area;
import de.civento.eahtools.routingrepository.impl.objects.Ou;
import de.civento.eahtools.routingrepository.impl.objects.Service;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement
public class ResponsibilityResponse {
    @Getter @Setter
    private Service service;
    @Getter @Setter
    private Area area;
    @Getter @Setter
    private Ou ou;
}
