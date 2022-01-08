package br.sales.estante.resources;

import br.sales.estante.business.ArtistBusiness;
import br.sales.estante.dto.ArtistRequest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/artists")
public class ArtistResource {

    @Inject
    private ArtistBusiness artistBusiness;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ListAll() {
        return Response.ok(artistBusiness.listAllArtists()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        var artist = artistBusiness.getById(id);
        return Response.ok(artist).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(final ArtistRequest artistRequest) {
        var artist = artistBusiness.create(artistRequest);
        return Response.ok(artist).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, final ArtistRequest artistRequest) {
        var artist = artistBusiness.update(id, artistRequest);
        return Response.ok(artist).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        artistBusiness.delete(id);
        return Response.noContent().build();
    }
}
