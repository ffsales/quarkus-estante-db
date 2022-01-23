package br.sales.estante.business;

import br.sales.estante.dto.ArtistRequest;
import br.sales.estante.model.ArtistRole;
import br.sales.estante.model.ArtistType;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import java.time.LocalDate;

@QuarkusTest
@TestTransaction
public class ArtistsBusinessTest {

    @Inject
    private ArtistBusiness artistBusiness;

    @Test
    public void shouldCreateArtistWithSucess() {
        var artistRequest = new ArtistRequest();
        artistRequest.setName("Novo artista");

        var artistPersist = artistBusiness.create(artistRequest);

        Assertions.assertNotNull(artistPersist.id);
        Assertions.assertEquals(LocalDate.now(), artistPersist.getDate());
    }

    @Test
    public void shouldCreateArtistWithNameNull() {
        var artistRequest = new ArtistRequest();
        var badRequestException = Assertions.assertThrows(BadRequestException.class, () -> artistBusiness.create(artistRequest));
        Assertions.assertEquals("O nome está nulo ou vazio", badRequestException.getMessage());
    }

    @Test
    public void shouldCreateAndGetWithSucess() {
        var artistRequest = new ArtistRequest();
        artistRequest.setName("Novo artista");

        var artistPersist = artistBusiness.create(artistRequest);
        var artistFound = artistBusiness.getById(artistPersist.id);

        Assertions.assertNotNull(artistPersist);
        Assertions.assertEquals("Novo artista", artistFound.getName());
    }

    @Test
    public void shouldUpdateWithSucess() {
        var artistRequest = new ArtistRequest();
        artistRequest.setName("Novo artista");

        var artistPersist = artistBusiness.create(artistRequest);
        artistRequest.setName("Novo artista atualizado");
        var artistUpdate = artistBusiness.update(artistPersist.id, artistRequest);

        Assertions.assertEquals(artistPersist.id, artistUpdate.id);
        Assertions.assertEquals("Novo artista atualizado", artistUpdate.getName());
    }

    @Test
    public void shouldUpdateArtistWithNameNull() {
        var artistRequest = new ArtistRequest();
        artistRequest.setName("Novo artista");
        var artistPersist = artistBusiness.create(artistRequest);

        var newArtistRequest = new ArtistRequest();
        var badRequestException = Assertions.assertThrows(BadRequestException.class, () -> artistBusiness.update(artistPersist.id, newArtistRequest));
        Assertions.assertEquals("O nome está nulo ou vazio", badRequestException.getMessage());
    }

    @Test
    public void shouldDeleteWithSucess() {
        var artistRequest = new ArtistRequest();
        artistRequest.setName("Novo artista");

        var artistPersist = artistBusiness.create(artistRequest);
        artistBusiness.delete(artistPersist.id);

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class,
                                                                        () -> artistBusiness.getById(artistPersist.id));
        Assertions.assertEquals("Artista não encontrado", notFoundException.getMessage());
    }

    @Test
    public void shouldDeleteWithConflict() {
        var artistRequest = new ArtistRequest();
        artistRequest.setName("Novo artista");
        var artistPersist = artistBusiness.create(artistRequest);

        var artistRole = new ArtistRole();
        artistRole.setArtist(artistPersist);
        artistRole.setType(ArtistType.WRITER_ARTIST);
        artistRole.setDate(LocalDate.now());
        artistRole.persist();

        WebApplicationException webApplicationException = Assertions.assertThrows(WebApplicationException.class,
                                                                        () -> artistBusiness.delete(artistPersist.id));
        Assertions.assertEquals("Artista cadastrado em livro em uso.", webApplicationException.getMessage());
        Assertions.assertEquals(409, webApplicationException.getResponse().getStatus());
    }

    @Test
    public void shouldDeleteWithNotFound() {
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class,
                () -> artistBusiness.delete(1L));
        Assertions.assertEquals("Artista não encontrado", notFoundException.getMessage());
    }
}
