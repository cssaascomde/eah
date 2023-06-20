package de.civento.eahtools.routingrepository.impl.service;

import de.civento.eahtools.routingrepository.base.businessobjects.EntityToBusinessObjectConverter;
import de.civento.eahtools.routingrepository.base.businessobjects.Helper;
import de.civento.eahtools.routingrepository.base.businessobjects.PageOfBusinessObjects;
import de.civento.eahtools.routingrepository.db.ServiceEntity;

public class ServiceModelMapper extends EntityToBusinessObjectConverter<Service, ServiceEntity,
        PageOfBusinessObjects<Service>> {
    @Override
    public Service convert(ServiceEntity entity) {
        Service s = new Service();
        s.setLeikaKey(entity.getLeikaKey());
        s.setCiventoKey(entity.getCiventoKey());
        s.setDeliveryType(entity.getDeliveryType());
        s.setResponsibilityType(entity.getResponsibilityType());
        s.setName(entity.getName());
        Helper.mapDefaultFieldsFromEntityToBusinessObject(entity, s);
        return s;
    }
}
