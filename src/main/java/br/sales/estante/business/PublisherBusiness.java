package br.sales.estante.business;

import br.sales.estante.dto.PublisherRequest;
import br.sales.estante.model.Book;
import br.sales.estante.model.Publisher;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class PublisherBusiness {

    private static final Logger LOG = Logger.getLogger(PublisherBusiness.class);

    @Transactional
    public Publisher create(PublisherRequest publisherRequest) {
        this.validatePublisherRequest(publisherRequest);
        LOG.info("[create] Cria objeto Publisher");
        var publisher = Publisher.builder()
                                            .name(publisherRequest.getName())
                                            .country(publisherRequest.getCountry())
                                            .date(LocalDate.now())
                                            .build();
        LOG.info("[create] Persiste objeto Publisher");
        PanacheEntityBase.persist(publisher);
        return publisher;
    }

    @Transactional
    public List<Publisher> listAll() {
        LOG.info("[listAll] pesquisando a lista de total de editoras");
        return Publisher.listAll();
    }

    @Transactional
    public Publisher getById(Long id) {
        LOG.info("[getById] pesquisa uma editora com id " + id);
        Optional<Publisher> optionalPublisher = Publisher.findByIdOptional(id);
        if (optionalPublisher.isEmpty()) {
            LOG.error("[getById] editora não encontrada");
            throw  new NotFoundException("Editora não encontrada");
        }
        LOG.info("[getById] editora encontrada");
        return optionalPublisher.get();
    }

    @Transactional
    public Publisher update(Long id, PublisherRequest publisherRequest) {
        LOG.info("[update] validando parâmetros");
        this.validatePublisherRequest(publisherRequest);
        LOG.info("[update] pesquisando editora");
        var publisher =  getById(id);
        publisher.setCountry(publisherRequest.getCountry());
        publisher.setName(publisherRequest.getName());
        publisher.setDate(LocalDate.now());
        LOG.info("[update] atualizando editora                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  ");
        PanacheEntityBase.persist(publisher);
        return publisher;
    }

    @Transactional
    public void delete(Long id) {
        LOG.info("[delete] pesquisando editora");
        var publisher = getById(id);
        LOG.info("[delete] pesquisando se existe algum livro cadastrado dessa editora");
        var countLicencingPublisher = Book.count("licencingPublisher", publisher);
        var countOriginalPublisher = Book.count("originalPublisher", publisher);

        if( countLicencingPublisher > 0 || countOriginalPublisher > 0) {
            LOG.error("[delete] Editora cadastrada em livro em uso");
            throw new WebApplicationException("Editora cadastrada em livro em uso", Response.Status.CONFLICT);
        }
        LOG.info("[delete] deletando editora");
        publisher.delete();
    }

    private void validatePublisherRequest(PublisherRequest publisherRequest) {
        LOG.info("[validatePublisherRequest] Validando parâmetros do publisher");
        if (Objects.isNull(publisherRequest.getName()) || publisherRequest.getName().isEmpty()) {
            LOG.error("[validatePublisherRequest] Nome inválido");
            throw new BadRequestException("Nome inválido");
        }
        if (Objects.isNull(publisherRequest.getCountry())) {
            LOG.error("[validatePublisherRequest] País inválido");
            throw new BadRequestException("País inválido");
        }
    }
}
