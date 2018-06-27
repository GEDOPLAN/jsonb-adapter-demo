package de.gedoplan.jsonb.mapping.resource;

import de.gedoplan.jsonb.mapping.adapter.EntityAdapter;
import de.gedoplan.jsonb.mapping.model.Speaker;
import de.gedoplan.jsonb.mapping.model.Talk;
import de.gedoplan.jsonb.mapping.persistence.TalkRepository;
import java.util.List;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author GEDOPLAN, Dominik Mathmann
 */
@Path("talk")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TalkResource {

    @Inject
    TalkRepository talkRepository;

    private final Jsonb jsonAdapted_Speaker;

    public TalkResource() {
        JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter<Speaker>() {});
        this.jsonAdapted_Speaker = JsonbBuilder.create(config);
    }

    @GET
    public Response getTalks() {
        List<Talk> allTalks = this.talkRepository.findAll();
        return Response.ok(jsonAdapted_Speaker.toJson(allTalks)).build();
    }
    
    @GET
    @Path("{id}")
    public Response getTalk(@PathParam("id") Integer id){
        return Response.ok(this.talkRepository.findById(id)).build();
    }

    @PUT
    @Path("{id}")
    public void updateTalk(@PathParam("id") Integer id, String talkJson) {
        Talk talk = jsonAdapted_Speaker.fromJson(talkJson, Talk.class);
        if (!id.equals(talk.getId())) {
            throw new BadRequestException("id of updated object must be unchanged");
        }

        this.talkRepository.merge(talk);
    }
}
