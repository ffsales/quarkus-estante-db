package br.sales.estante.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArtistRequest {

    @NotNull(message = "Não pode ser nulo")
    private String name;
}
