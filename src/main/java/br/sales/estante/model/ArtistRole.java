package br.sales.estante.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "artist_role")
public class ArtistRole extends PanacheEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creative_team_id", referencedColumnName = "id", nullable=false)
    private CreativeTeam creativeTeam;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id", referencedColumnName = "id", nullable=false)
    private Artist artist;
    private ArtistType type;
    private LocalDate date;
}
