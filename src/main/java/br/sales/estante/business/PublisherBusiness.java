package br.sales.estante.business;

import br.sales.estante.dto.PublisherDto;
import br.sales.estante.model.Publisher;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PublisherBusiness {

    @Transactional
    public Publisher create(PublisherDto publisherDto) {

        var publisher = Publisher.builder()
                                            .name(publisherDto.getName())
                                            .country(publisherDto.getCountry())
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
    public Optional<Publisher> getById(Long id) {
        return Optional.ofNullable(Publisher.findById(id));
    }

    @Transactional
    public Publisher update(Long id, PublisherDto publisherDto) {
        var optPublisher = getById(id);
        if (optPublisher.isEmpty()) {
            throw  new NotFoundException("Editora não encontrada");
        }

        var publisher = optPublisher.get();
        publisher.setCountry(publisherDto.getCountry());
        publisher.setName(publisherDto.getName());
        publisher.setDate(LocalDate.now());

        PanacheEntityBase.persist(publisher);

        return publisher;
    }

    @Transactional
    public void delete(Long id) {
        //TODO criar regra

        var optPublisher = getById(id);

        if (optPublisher.isEmpty()) {
            throw new NotFoundException("Editora não encontrada");
        }
        var publisher = optPublisher.get();
        publisher.delete();
    }
}
