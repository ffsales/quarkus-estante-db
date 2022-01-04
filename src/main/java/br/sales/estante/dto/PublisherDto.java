package br.sales.estante.dto;

import br.sales.estante.model.Country;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
public class PublisherDto {

    @NotNull(message = "Nome não pode ser nulo")
    private String name;
    private Country country;
}
