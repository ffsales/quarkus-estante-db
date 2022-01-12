package br.sales.estante.resources;

import br.sales.estante.business.PublisherBusiness;
import br.sales.estante.dto.PublisherRequest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Objects;

@Path("/v1/publisher")
public class PublisherResource {

    @Inject
    private PublisherBusiness business;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response savePublisher(@Context UriInfo uriInfo, final PublisherRequest publisherRequest) {

        var publisher = this.business.create(publisherRequest);

        return Response.ok(publisher).status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublisherById(@PathParam("id") Long id) {
        var publisher = this.business.getById(id);
        return Response.ok(publisher).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ListAll() {
        var publishers = this.business.listAll();
        if (Objects.isNull(publishers)) {
            throw new NotFoundException("Não foi possível encontrar a editora");
        }
        return Response.ok(publishers).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, final PublisherRequest publisherRequest) {
        if (Objects.isNull(publisherRequest.getName()) || publisherRequest.getName().isEmpty()) {
            return Response.ok("Nome inválido").status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
        if (Objects.isNull(publisherRequest.getCountry())) {
            return Response.ok("País inválido").status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
        var publisher = this.business.update(id, publisherRequest);
        return Response.ok(publisher).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        this.business.delete(id);
        return Response.noContent().build();
    }
}
