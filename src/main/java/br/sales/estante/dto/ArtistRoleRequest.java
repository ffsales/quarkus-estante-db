package br.sales.estante.dto;

import br.sales.estante.model.ArtistType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArtistRoleRequest {

    @JsonProperty("artist_id")
    private Long artistId;
    @JsonProperty("artist_type")
    private ArtistType artistType;
}
