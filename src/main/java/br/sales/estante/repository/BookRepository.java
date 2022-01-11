package br.sales.estante.repository;

import br.sales.estante.model.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {

    @PersistenceContext
    EntityManager em;

    public Optional<Book> findByTitle(final String title) {

        TypedQuery<Book> query = em.createNamedQuery("Book.findByTitle", Book.class);
        query.setParameter("title", title);
        return query.getResultStream().findFirst();
    }
}
