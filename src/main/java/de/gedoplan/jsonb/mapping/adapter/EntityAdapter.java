package de.gedoplan.jsonb.mapping.adapter;

import de.gedoplan.jsonb.mapping.model.JPAEntity;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.bind.adapter.JsonbAdapter;
import javax.persistence.EntityManager;

/**
 *
 * @author GEDOPLAN, Dominik Mathmann
 */
public abstract class EntityAdapter<T extends JPAEntity> implements JsonbAdapter<List<T>, JsonArray>{

    @Inject
    private EntityManager em;

    private Class<T> getTargetClass() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public JsonArray adaptToJson(List<T> orgnl) throws Exception {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        orgnl.stream().map(JPAEntity::getId).forEach(arrayBuilder::add);
        return arrayBuilder.build();
    }

    public List<T> adaptFromJson(JsonArray adptd) throws Exception {
        List<T> list = new ArrayList<>();

        adptd.stream().forEach(val -> {
            Integer id = ((JsonNumber) val).intValue();
            list.add(em.getReference(getTargetClass(), id));
        });

        return list;
    }

}
