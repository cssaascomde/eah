package de.civento.eahtools.routingrepository.impl.responsibility;

import de.civento.eahtools.routingrepository.base.businessobjects.EntityToBusinessObjectConverter;
import de.civento.eahtools.routingrepository.base.businessobjects.Helper;
import de.civento.eahtools.routingrepository.base.businessobjects.PageOfBusinessObjects;
import de.civento.eahtools.routingrepository.db.ResponsibilityEntity;
import de.civento.eahtools.routingrepository.impl.ou.OuModelMapper;
import de.civento.eahtools.routingrepository.impl.service.ServiceModelMapper;

public class ResponsibilityModelMapper extends EntityToBusinessObjectConverter<Responsibility, ResponsibilityEntity,
        PageOfBusinessObjects<Responsibility>> {
    private static final OuModelMapper OU_MODEL_MAPPER = new OuModelMapper();
    private static final ServiceModelMapper SERVICE_MODEL_MAPPER = new ServiceModelMapper();

    @Override
    public Responsibility convert(ResponsibilityEntity entity) {
        Responsibility responsibility = new Responsibility();
        Helper.mapDefaultFieldsFromEntityToBusinessObject(entity, responsibility);
        responsibility.setDeliveryType(entity.getDeliveryType());
        responsibility.setInterfaceType(entity.getInterfaceType());
        responsibility.setRegionalKey(entity.getRegionalKey());
        responsibility.setOu(OU_MODEL_MAPPER.convert(entity.getOuEntity()));
        responsibility.setService(SERVICE_MODEL_MAPPER.convert(entity.getServiceEntity()));
        return responsibility;
    }
}
