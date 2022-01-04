package br.sales.estante.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    @OneToMany(mappedBy = "originalPublisher", orphanRemoval = true)
    private List<Book> books;
}
