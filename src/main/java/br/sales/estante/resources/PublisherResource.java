package br.sales.estante.resources;

import br.sales.estante.business.PublisherBusiness;
import br.sales.estante.dto.PublisherDto;
import br.sales.estante.model.Publisher;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/v1/publisher")
public class PublisherResource {

    @Inject
    private PublisherBusiness business;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response savePublisher(@Context UriInfo uriInfo, final PublisherDto publisherDto) {

        var publisher = this.business.createPublisher(publisherDto);

        var uri = uriInfo.getAbsolutePathBuilder().path("/v1/publisher").build(publisher);
        return Response.created(uri).build();
    }
}
