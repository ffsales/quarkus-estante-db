package br.sales.estante.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Artist extends PanacheEntity {

    private String name;
    private LocalDate date;
}
