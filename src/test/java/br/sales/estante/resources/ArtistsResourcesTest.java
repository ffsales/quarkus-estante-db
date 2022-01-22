package br.sales.estante.resources;

import br.sales.estante.business.ArtistBusiness;
import br.sales.estante.dto.ArtistRequest;
import br.sales.estante.model.Artist;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestTransaction
public class ArtistsResourcesTest {

    @InjectMock
    ArtistBusiness artistBusiness;

    @Test
    public void shouldCreateArtistWithCreateReturn() {
        Mockito.when(artistBusiness.create(any())).thenReturn(mockArtist());
        given()
                .when()
                .spec(new RequestSpecBuilder().build())
                .contentType(ContentType.JSON)
                .post("/v1/artists")
                .then()
                .statusCode(201);
    }

    @Test
    public void shouldCreateArtistWithBadRequestReturn() {
        var artistRequest = new ArtistRequest();
        Mockito.when(artistBusiness.create(artistRequest)).thenThrow(new BadRequestException());
        given()
                .when()
                .spec(new RequestSpecBuilder().build())
                .contentType(ContentType.JSON)
                .body(artistRequest)
                .post("/v1/artists")
                .then()
                .statusCode(400);
    }

    @Test
    public void shouldUpdateWithSucess() {
        Mockito.when(artistBusiness.update(any(), any())).thenReturn(mockArtist());
        given()
                .when()
                .spec(new RequestSpecBuilder().build())
                .contentType(ContentType.JSON)
                .body(new ArtistRequest())
                .put("/v1/artists/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldUpdateWithBadRequestReturn() {
        Mockito.when(artistBusiness.update(any(), any())).thenThrow(new BadRequestException());
        given()
                .when()
                .spec(new RequestSpecBuilder().build())
                .contentType(ContentType.JSON)
                .body(new ArtistRequest())
                .put("/v1/artists/1")
                .then()
                .statusCode(400);
    }

    @Test
    public void shouldUpdateWithNotFoundReturn() {
        Mockito.when(artistBusiness.update(any(), any())).thenThrow(new NotFoundException());
        given()
                .when()
                .spec(new RequestSpecBuilder().build())
                .contentType(ContentType.JSON)
                .body(new ArtistRequest())
                .put("/v1/artists/1")
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldGetArtistWithSucess() {
        Mockito.when(artistBusiness.getById(any())).thenReturn(mockArtist());
        given()
                .when()
                .spec(new RequestSpecBuilder().build())
                .contentType(ContentType.JSON)
                .body(new ArtistRequest())
                .get("/v1/artists/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldGetArtistWithNotFoundReturn() {
        Mockito.when(artistBusiness.getById(any())).thenThrow(new NotFoundException());
        given()
                .when()
                .spec(new RequestSpecBuilder().build())
                .contentType(ContentType.JSON)
                .body(new ArtistRequest())
                .get("/v1/artists/1")
                .then()
                .statusCode(404);
    }

    private Artist mockArtist() {
        return Artist.builder()
                .name("Artista Teste")
                .date(LocalDate.now())
                .build();
    }
}
