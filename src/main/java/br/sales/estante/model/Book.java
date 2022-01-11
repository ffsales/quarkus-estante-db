package br.sales.estante.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name = "Book.findByTitle", query = "SELECT bo FROM Book bo WHERE bo.title LIKE '%:title%' ")
public class Book extends PanacheEntity {

    @NotNull(message = "Informar o título")
    private String title;
    @NotNull(message = "Informar a data")
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "original_publisher_id", referencedColumnName = "id", nullable=false)
    private Publisher originalPublisher;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "licencing_publisher_id", referencedColumnName = "id", nullable=false)
    private Publisher licencingPublisher;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private List<ArtistRole> artistsRole;
}
