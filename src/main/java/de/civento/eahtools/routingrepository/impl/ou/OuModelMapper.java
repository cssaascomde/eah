package de.civento.eahtools.routingrepository.impl.ou;

import de.civento.eahtools.routingrepository.base.businessobjects.EntityToBusinessObjectConverter;
import de.civento.eahtools.routingrepository.base.businessobjects.Helper;
import de.civento.eahtools.routingrepository.base.businessobjects.PageOfBusinessObjects;
import de.civento.eahtools.routingrepository.db.OuEntity;

public class OuModelMapper extends EntityToBusinessObjectConverter<Ou, OuEntity, PageOfBusinessObjects<Ou>> {
    @Override
    public Ou convert(OuEntity entity) {
        Ou ou = new Ou();
        ou.setName(entity.getName());
        ou.setType(entity.getType());
        ou.setCiventoKey(entity.getCiventoKey());
        ou.setRegionalKey(entity.getRegionalKey());
        ou.setAddress(entity.getAddress());
        ou.setZipCode(entity.getZipCode());
        ou.setCity(entity.getCity());
        Helper.mapDefaultFieldsFromEntityToBusinessObject(entity, ou);
        return ou;
    }
}
