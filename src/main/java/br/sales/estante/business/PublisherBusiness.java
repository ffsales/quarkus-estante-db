package br.sales.estante.business;

import br.sales.estante.dto.PublisherDto;
import br.sales.estante.model.Publisher;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

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
    public Publisher getById(Long id) {

        return Publisher.findById(id);
    }

    @Transactional
    public Publisher update(Long id, PublisherDto publisherDto) {

        var publisher = getById(id);

        if (publisher == null) {
            throw  new IllegalArgumentException("Editora não encontrada");
        }

        publisher.setCountry(publisherDto.getCountry());
        publisher.setName(publisherDto.getName());
        publisher.setDate(LocalDate.now());

        PanacheEntityBase.persist(publisher);

        return publisher;
    }

    @Transactional
    public void delete(Long id) {
        //TODO criar regra

        var publisher = getById(id);

        publisher.delete();
    }
}
