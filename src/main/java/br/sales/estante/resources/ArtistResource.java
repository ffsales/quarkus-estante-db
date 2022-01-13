package br.sales.estante.resources;

import br.sales.estante.business.ArtistBusiness;
import br.sales.estante.dto.ArtistRequest;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/artists")
@Tag(name = "/v1/artists", description = "API de Artistas")
public class ArtistResource {

    @Inject
    private ArtistBusiness artistBusiness;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "API que retorna uma lista de todos os artistas")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 200 para sucesso", responseCode = "200"),
            @APIResponse(description = "retorna 404 se não encontrar nenhum", responseCode = "404")
    })
    public Response ListAll() {
        return Response.ok(artistBusiness.listAllArtists()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "API que retorna um artista")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 200 para sucesso", responseCode = "200"),
            @APIResponse(description = "retorna 404 se não encontrar", responseCode = "404")
    })
    public Response findById(@PathParam("id") Long id) {
        var artist = artistBusiness.getById(id);
        return Response.ok(artist).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "API que cadastra um artista")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 201 para sucesso", responseCode = "201"),
            @APIResponse(description = "retorna 400 caso algum parâmetro esteja inválido", responseCode = "400")
    })
    public Response save(final ArtistRequest artistRequest) {
        var artist = artistBusiness.create(artistRequest);
        return Response.ok(artist).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "API que atualiza um artista")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 200 para sucesso", responseCode = "200"),
            @APIResponse(description = "retorna 400 caso algum parâmetro esteja inválido", responseCode = "400"),
            @APIResponse(description = "retorna 404 se não encontrar", responseCode = "404")
    })
    public Response update(@PathParam("id") Long id, final ArtistRequest artistRequest) {
        var artist = artistBusiness.update(id, artistRequest);
        return Response.ok(artist).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(description = "API que deleta um artista")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 204 para sucesso", responseCode = "204"),
            @APIResponse(description = "retorna 404 se não encontrar", responseCode = "404")
    })
    public Response delete(@PathParam("id") Long id) {
        artistBusiness.delete(id);
        return Response.noContent().build();
    }
}
