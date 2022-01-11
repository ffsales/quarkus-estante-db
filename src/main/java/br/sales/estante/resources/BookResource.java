package br.sales.estante.resources;

import br.sales.estante.business.BookBusiness;
import br.sales.estante.dto.BookRequest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/v1/books")
public class BookResource {

    @Inject
    private BookBusiness bookBusiness;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ListAll() {
        return Response.ok(this.bookBusiness.listAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") Long id) {
        return Response.ok(this.bookBusiness.getById(id)).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(@Context UriInfo uriInfo, final BookRequest bookRequest) {
        var book = bookBusiness.saveBook(bookRequest);
        return Response.ok(book).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, final BookRequest bookRequest) {
        var artist = bookBusiness.update(id, bookRequest);
        return Response.ok(artist).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        this.bookBusiness.delete(id);
        return Response.noContent().build();
    }
}
