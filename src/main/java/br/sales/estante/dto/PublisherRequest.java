package br.sales.estante.dto;

import br.sales.estante.model.Country;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PublisherRequest {

    @NotNull(message = "Nome não pode ser nulo")
    private String name;
    private Country country;
}
