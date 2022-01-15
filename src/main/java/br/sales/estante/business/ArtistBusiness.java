package br.sales.estante.business;

import br.sales.estante.dto.ArtistRequest;
import br.sales.estante.model.Artist;
import br.sales.estante.model.ArtistRole;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ArtistBusiness {

    private static final Logger LOG = Logger.getLogger(ArtistBusiness.class);

    public List<Artist> listAllArtists() {
        LOG.info("[listAllArtists] pesquisando a lista de total de artistas");
        return Artist.listAll();
    }

    public Artist getById(Long id) {
        LOG.info("[getById] pesquisando artista com o id " + id);
        Optional<Artist> optionalArtist = Artist.findByIdOptional(id);
        if (optionalArtist.isEmpty()) {
            LOG.error("[getById] artista não encontrado");
            throw new NotFoundException("Artista não encontrado");
        }
        LOG.info("[getById] artista encontrado");
        return optionalArtist.get();
    }

    @Transactional
    public Artist create(ArtistRequest artistRequest) {
        LOG.info("[create] montando objeto Artist");
        var artist = Artist.builder()
                .name(artistRequest.getName())
                .date(LocalDate.now())
                .build();
        LOG.info("[create] persistindo objeto Artist");
        PanacheEntityBase.persist(artist);
        return artist;
    }

    @Transactional
    public Artist update(Long id, ArtistRequest artistRequest) {
        LOG.info("[update] pesquisando artista");
        var artist = getById(id);
        artist.setName(artistRequest.getName());
        artist.setDate(LocalDate.now());
        LOG.info("[update] persistindo atualizações de artista");
        artist.persist();
        return artist;
    }

    @Transactional
    public void delete(Long id) {
        LOG.info("[delete] pesquisando artista");
        var artist = getById(id);
        LOG.info("[delete] verificando se existe algum livro cadastrdo com esse artista");
        long countRoles = ArtistRole.count("artist", artist);
        if (countRoles > 0) {
            LOG.error("[delete] Artista cadastrado em livro em uso");
            throw new WebApplicationException("Artista cadastrado em livro em uso.", Response.Status.CONFLICT);
        }
        LOG.info("[delete] deletando artista");
        artist.delete();
    }
}
