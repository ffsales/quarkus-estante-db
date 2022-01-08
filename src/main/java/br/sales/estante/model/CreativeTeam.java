package br.sales.estante.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "creative_team")
public class CreativeTeam extends PanacheEntity {

    @OneToMany(mappedBy = "creativeTeam", fetch = FetchType.EAGER)
    private List<ArtistRole> artistsRole;
    private LocalDate date;
}
