package br.sales.estante.business;

import br.sales.estante.dto.ArtistRequest;
import br.sales.estante.model.Artist;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ArtistBusiness {

    public List<Artist> listAllArtists() {
        return Artist.listAll();
    }

    public Artist getById(Long id) {
        Optional<Artist> optionalArtist = Artist.findByIdOptional(id);
        if (optionalArtist.isEmpty()) {
            throw new NotFoundException("Artista não encontrado");
        }
        return optionalArtist.get();
    }

    @Transactional
    public Artist create(ArtistRequest artistRequest) {
        var artist = Artist.builder()
                .name(artistRequest.getName())
                .date(LocalDate.now())
                .build();

        PanacheEntityBase.persist(artist);
        return artist;
    }

    @Transactional
    public Artist update(Long id, ArtistRequest artistRequest) {
        var artist = getById(id);
        artist.setName(artistRequest.getName());
        artist.setDate(LocalDate.now());
        artist.persist();
        return artist;
    }

    @Transactional
    public void delete(Long id) {
        //TODO verificar uso do artista em algum relacionamento
        var artist = getById(id);
        artist.delete();
    }
}
