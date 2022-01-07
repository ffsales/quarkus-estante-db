package br.sales.estante.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publisher extends PanacheEntity {

    private String name;
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Country country;
}
