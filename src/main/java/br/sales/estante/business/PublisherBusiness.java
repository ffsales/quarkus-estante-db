package br.sales.estante.business;

import br.sales.estante.dto.PublisherDto;
import br.sales.estante.model.Publisher;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDate;

@ApplicationScoped
public class PublisherBusiness {

    @Transactional
    public Publisher createPublisher(PublisherDto publisherDto) {

        var publisher = Publisher.builder()
                                            .name(publisherDto.getName())
                                            .country(publisherDto.getCountry())
                                            .date(LocalDate.now())
                                            .build();

        PanacheEntityBase.persist(publisher);

        return publisher;
    }
}
