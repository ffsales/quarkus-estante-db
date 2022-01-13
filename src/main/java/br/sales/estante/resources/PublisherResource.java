package br.sales.estante.resources;

import br.sales.estante.business.PublisherBusiness;
import br.sales.estante.dto.PublisherRequest;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Objects;

@Path("/v1/publisher")
@Tag(name = "/v1/publishers", description = "API de editoras")
public class PublisherResource {

    @Inject
    private PublisherBusiness business;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "API que cadastra uma editora")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 201 para sucesso", responseCode = "201"),
            @APIResponse(description = "retorna 400 caso algum parâmetro esteja inválido", responseCode = "400")
    })
   public Response savePublisher(@Context UriInfo uriInfo, final PublisherRequest publisherRequest) {

        var publisher = this.business.create(publisherRequest);

        return Response.ok(publisher).status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "API que retorna uma editora")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 200 para sucesso", responseCode = "200"),
            @APIResponse(description = "retorna 404 se não encontrar", responseCode = "404")
    })
    public Response getPublisherById(@PathParam("id") Long id) {
        var publisher = this.business.getById(id);
        return Response.ok(publisher).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "API que retorna uma lista de todas as editoras")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 200 para sucesso", responseCode = "200"),
            @APIResponse(description = "retorna 404 se não encontrar nenhum", responseCode = "404")
    })
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
    @Operation(description = "API que atualiza uma editora")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 200 para sucesso", responseCode = "200"),
            @APIResponse(description = "retorna 400 caso algum parâmetro esteja inválido", responseCode = "400"),
            @APIResponse(description = "retorna 404 se não encontrar", responseCode = "404")
    })
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
    @Operation(description = "API que deleta uma editora")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 204 para sucesso", responseCode = "204"),
            @APIResponse(description = "retorna 404 se não encontrar", responseCode = "404")
    })
    public Response delete(@PathParam("id") Long id) {
        this.business.delete(id);
        return Response.noContent().build();
    }
}
