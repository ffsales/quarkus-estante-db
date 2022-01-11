package br.sales.estante.dto;

import br.sales.estante.model.Genre;
import br.sales.estante.model.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookRequest {

    private String title;
    private Genre genre;
    private Type type;
    @JsonProperty("original_publisher_id")
    private Long originalPublisherId;
    @JsonProperty("lincencing_publisher_id")
    private Long licencingPublisherId;
    @JsonProperty("artist_list")
    private List<ArtistRoleRequest> artistList;
}
