package br.sales.estante.resources;

import br.sales.estante.business.BookBusiness;
import br.sales.estante.dto.BookRequest;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/v1/books")
@Tag(name = "/v1/books", description = "API de livros")
public class BookResource {

    private static final Logger LOG = Logger.getLogger(BookResource.class);

    @Inject
    private BookBusiness bookBusiness;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "API que retorna um livro")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 200 para sucesso", responseCode = "200"),
            @APIResponse(description = "retorna 404 se não encontrar", responseCode = "404")
    })
    public Response getBookById(@PathParam("id") Long id) {
        LOG.info("[getBookById] pesquisando book com id " + id);
        var book = this.bookBusiness.getById(id);
        LOG.info("[getBookById] livro encontrado");
        return Response.ok(book).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "API que retorna uma lista de todos os livros")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 200 para sucesso", responseCode = "200"),
            @APIResponse(description = "retorna 404 se não encontrar nenhum", responseCode = "404")
    })
    public Response listAll() {
        LOG.info("[listAll] pesquisando a lista de total de livros");
        return Response.ok(this.bookBusiness.listAll()).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "API que cadastra um livro")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 201 para sucesso", responseCode = "201"),
            @APIResponse(description = "retorna 400 caso algum parâmetro esteja inválido", responseCode = "400")
    })
    public Response createBook(@Context UriInfo uriInfo, final BookRequest bookRequest) {
        LOG.info("[createBook] cadastrando livro");
        var book = bookBusiness.saveBook(bookRequest);
        LOG.info("[createBook] livro cadastrado");
        return Response.ok(book).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "API que atualiza um livro")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 200 para sucesso", responseCode = "200"),
            @APIResponse(description = "retorna 400 caso algum parâmetro esteja inválido", responseCode = "400"),
            @APIResponse(description = "retorna 404 se não encontrar", responseCode = "404")
    })
    public Response update(@PathParam("id") Long id, final BookRequest bookRequest) {
        LOG.info("[update] atualizando livro com id " + id);
        var artist = bookBusiness.update(id, bookRequest);
        LOG.info("[update] livro atualizado");
        return Response.ok(artist).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "API que deleta um livro")
    @APIResponses(value = {
            @APIResponse(description = "Retorna 204 para sucesso", responseCode = "204"),
            @APIResponse(description = "retorna 404 se não encontrar", responseCode = "404")
    })
    public Response delete(@PathParam("id") Long id) {
        LOG.info("[delete] deletando livro com id " + id);
        this.bookBusiness.delete(id);
        LOG.info("[delete] livro deletado");
        return Response.noContent().build();
    }
}
