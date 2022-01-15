package br.sales.estante.business;

import br.sales.estante.dto.ArtistRoleRequest;
import br.sales.estante.dto.BookRequest;
import br.sales.estante.model.ArtistRole;
import br.sales.estante.model.Book;
import br.sales.estante.model.Publisher;
import br.sales.estante.repository.BookRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookBusiness {

    private static final Logger LOG = Logger.getLogger(BookBusiness.class);

    @Inject
    BookRepository bookRepository;

    @Inject
    PublisherBusiness publisherBusiness;

    @Inject
    ArtistBusiness artistBusiness;

    @Transactional
    public Book findByTitle(final String title) {
        return bookRepository.findByTitle(title).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
    }

    @Transactional
    public Book getById(Long id) {
        LOG.info("[getById] pesquisando livro com o id " + id);
        Optional<Book> bookOptional = Book.findByIdOptional(id);
        if (bookOptional.isEmpty()) {
            LOG.error("[getById] livro não encontrado");
            throw new NotFoundException("Não foi possível encontrar nenhum livro");
        }
        LOG.info("[getById] livro encontrado");
        return bookOptional.get();
    }

    @Transactional
    public List<Book> listAll() {
        LOG.info("[listAll] pesquisando a lista de total de livros");
        return Book.listAll();
    }

    @Transactional
    public Book saveBook(final BookRequest bookRequest) {
        LOG.info("[saveBook] montando objeto Book");
        var book = Book.builder()
                .title(bookRequest.getTitle())
                .genre(bookRequest.getGenre())
                .type(bookRequest.getType())
                .date(LocalDate.now())
                .build();
        LOG.info("[saveBook] validando parâmetros do livro");
        validateBookParameters(bookRequest, book);
        LOG.info("[saveBook] persistindo objeto Book");
        book.persist();
        return book;
    }

    @Transactional
    public void delete(Long id) {
        LOG.info("[delete] pesquisando livro");
        var book = getById(id);
        LOG.info("[delete] livro deletado");
        book.delete();
    }

    @Transactional
    public Book update(Long id, BookRequest bookRequest) {
        LOG.info("[update] pesquisando livro");
        var book = getById(id);
        LOG.info("[update] validando parâmetros do livro");
        validateBookParameters(bookRequest, book);
        book.setType(bookRequest.getType());
        book.setGenre(bookRequest.getGenre());
        book.setTitle(bookRequest.getTitle());
        book.setDate(LocalDate.now());
        LOG.info("[update] persistindo atualizações do livro");
        book.persist();
        return book;
    }

    private Publisher getPublisher(Long id) {
        return publisherBusiness.getById(id);
    }

    private List<ArtistRole> buildArtistRoleList(List<ArtistRoleRequest> artistRoleRequestList) {
        LOG.info("[buildArtistRoleList] montando ArtistRole");
        return artistRoleRequestList.stream()
                .map(arr -> ArtistRole.builder()
                        .artist(artistBusiness.getById(arr.getArtistId()))
                        .type(arr.getArtistType())
                        .date(LocalDate.now())
                        .build())
                .collect(Collectors.toList());
    }

    private void validateBookParameters(BookRequest bookRequest, Book book) {
        if (Objects.isNull(bookRequest.getArtistList()) || bookRequest.getArtistList().isEmpty()) {
            LOG.error("[validateBookParameters] artistas não podem ser nulos");
            throw new BadRequestException("Artistas não podem ser nulos");
        } else {
            book.setArtistsRole(buildArtistRoleList(bookRequest.getArtistList()));
        }
        LOG.info("[validateBookParameters] validando editora original");
        if (Objects.nonNull(bookRequest.getOriginalPublisherId())) {
            var originalPublisher = this.getPublisher(bookRequest.getOriginalPublisherId());
            book.setOriginalPublisher(originalPublisher);
        } else {
            LOG.error("[validateBookParameters] editora original não pode ser nula");
            throw new BadRequestException("Editora original não pode ser nula");
        }
        LOG.info("[validateBookParameters] validando editora licenciante");
        if (Objects.nonNull(bookRequest.getLicencingPublisherId())) {
            var licencingPublisher = this.getPublisher(bookRequest.getLicencingPublisherId());
            book.setLicencingPublisher(licencingPublisher);
        } else {
            LOG.error("[validateBookParameters] editora licenciante não pode ser nula");
            throw new BadRequestException("Editora licenciante não pode ser nula");
        }
    }
}
