package br.sales.estante.business;

import br.sales.estante.dto.ArtistRoleRequest;
import br.sales.estante.dto.BookRequest;
import br.sales.estante.model.ArtistRole;
import br.sales.estante.model.Book;
import br.sales.estante.model.Publisher;
import br.sales.estante.repository.BookRepository;

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

        Optional<Book> bookOptional = Book.findByIdOptional(id);

        if (bookOptional.isEmpty()) {
            throw new NotFoundException("Não foi possível encontrar nenhum livro");
        }

        return bookOptional.get();
    }

    @Transactional
    public List<Book> listAll() {
        return Book.listAll();
    }

    @Transactional
    public Book saveBook(final BookRequest bookRequest) {

        var book = Book.builder()
                .title(bookRequest.getTitle())
                .genre(bookRequest.getGenre())
                .type(bookRequest.getType())
                .date(LocalDate.now())
                .build();

        if (Objects.isNull(bookRequest.getArtistList()) || bookRequest.getArtistList().isEmpty()) {
            throw new BadRequestException("Artistas não podem ser nulos");
        } else {
            book.setArtistsRole(buildArtistRoleList(bookRequest.getArtistList()));
        }

        if (Objects.nonNull(bookRequest.getOriginalPublisherId())) {
            var originalPublisher = this.getPublisher(bookRequest.getOriginalPublisherId());
            book.setOriginalPublisher(originalPublisher);
        } else {
            throw new BadRequestException("Editora original não pode ser nula");
        }

        if (Objects.nonNull(bookRequest.getLicencingPublisherId())) {
            var licencingPublisher = this.getPublisher(bookRequest.getLicencingPublisherId());
            book.setLicencingPublisher(licencingPublisher);
        } else {
            throw new BadRequestException("Editora licenciante não pode ser nula");
        }

        book.persist();
        return book;
    }

    @Transactional
    public void delete(Long id) {
        var book = getById(id);
        book.delete();
    }

    @Transactional
    public Book update(Long id, BookRequest bookRequest) {
        var book = getById(id);

        if (Objects.nonNull(bookRequest.getOriginalPublisherId())) {
            var originalPublisher = this.getPublisher(bookRequest.getOriginalPublisherId());
            book.setOriginalPublisher(originalPublisher);
        }

        if (Objects.nonNull(bookRequest.getLicencingPublisherId())) {
            var licencingPublisher = this.getPublisher(bookRequest.getLicencingPublisherId());
            book.setLicencingPublisher(licencingPublisher);
        }

        if (Objects.nonNull(bookRequest.getArtistList()) && !bookRequest.getArtistList().isEmpty()) {
            book.getArtistsRole().clear();
            book.getArtistsRole().addAll(buildArtistRoleList(bookRequest.getArtistList()));
        }

        book.setType(bookRequest.getType());
        book.setGenre(bookRequest.getGenre());
        book.setTitle(bookRequest.getTitle());
        book.setDate(LocalDate.now());
        book.persist();

        return book;
    }

    private Publisher getPublisher(Long id) {
        return publisherBusiness.getById(id);
    }

    private List<ArtistRole> buildArtistRoleList(List<ArtistRoleRequest> artistRoleRequestList) {

        return artistRoleRequestList.stream()
                .map(arr -> ArtistRole.builder()
                        .artist(artistBusiness.getById(arr.getArtistId()))
                        .type(arr.getArtistType())
                        .date(LocalDate.now())
                        .build())
                .collect(Collectors.toList());
    }
}
