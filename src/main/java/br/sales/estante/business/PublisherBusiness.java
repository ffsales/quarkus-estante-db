package br.sales.estante.business;

import br.sales.estante.dto.PublisherRequest;
import br.sales.estante.model.Book;
import br.sales.estante.model.Publisher;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PublisherBusiness {

    @Transactional
    public Publisher create(PublisherRequest publisherRequest) {
        var publisher = Publisher.builder()
                                            .name(publisherRequest.getName())
                                            .country(publisherRequest.getCountry())
                                            .date(LocalDate.now())
                                            .build();

        PanacheEntityBase.persist(publisher);
        return publisher;
    }

    @Transactional
    public List<Publisher> listAll() {
        return Publisher.listAll();
    }

    @Transactional
    public Publisher getById(Long id) {
        Optional<Publisher> optionalPublisher = Publisher.findByIdOptional(id);
        if (optionalPublisher.isEmpty()) {
            throw  new NotFoundException("Editora não encontrada");
        }
        return optionalPublisher.get();
    }

    @Transactional
    public Publisher update(Long id, PublisherRequest publisherRequest) {
        var publisher =  getById(id);
        publisher.setCountry(publisherRequest.getCountry());
        publisher.setName(publisherRequest.getName());
        publisher.setDate(LocalDate.now());

        PanacheEntityBase.persist(publisher);
        return publisher;
    }

    @Transactional
    public void delete(Long id) {
        var publisher = getById(id);

        var countLicencingPublisher = Book.count("licencingPublisher", publisher);
        var countOriginalPublisher = Book.count("originalPublisher", publisher);

        if( countLicencingPublisher > 0 || countOriginalPublisher > 0) {
            throw new WebApplicationException("Editora cadastrada em livro em uso", Response.Status.CONFLICT);
        }
        publisher.delete();
    }
}
