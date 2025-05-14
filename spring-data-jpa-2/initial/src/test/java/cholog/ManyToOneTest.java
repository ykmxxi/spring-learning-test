package cholog;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
class ManyToOneTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    void uniDirection() {
        Publisher publisher = new Publisher("출판사");
        entityManager.persist(publisher); // 1차 캐시에 저장 -> 영속성 컨텍스트에 저장하는 것

        Book book = new Book("책", publisher);
        entityManager.persist(book); // 1차 캐시에 저장

        Book persistBook = entityManager.find(Book.class, book.getId());

        assertThat(persistBook).isNotNull();
        assertThat(persistBook.getPublisher()).isNotNull();
    }

    @Test
    void biDirection() {
        Publisher publisher = new Publisher("출판사");
        entityManager.persist(publisher);

        Book book = new Book("책", publisher);
        entityManager.persist(book);
        publisher.addBook(book);

        entityManager.flush();
        entityManager.clear();

        Publisher persistPublisher = entityManager.find(Publisher.class, publisher.getId());
        assertThat(persistPublisher).isNotNull();
        assertThat(persistPublisher.getBooks()).isNotEmpty();
    }

    @Test
    void findByIdForBook() {
        Publisher publisher = new Publisher("출판사");
        entityManager.persist(publisher);

        Book book = new Book("책", publisher);
        entityManager.persist(book);

        entityManager.flush();
        entityManager.clear();

        Optional<Book> persistBook = bookRepository.findById(book.getId());
        assertThat(persistBook).isPresent();
        assertThat(persistBook.get().getPublisher()).isNotNull();
    }

    @Test
    void findByIdForPublisher() {
        Publisher publisher = new Publisher("출판사");
        entityManager.persist(publisher);

        Book book = new Book("책", publisher);
        entityManager.persist(book);

        entityManager.flush();
        entityManager.clear();

        Optional<Publisher> persistPublisher = publisherRepository.findById(publisher.getId());
        assertThat(persistPublisher).isPresent();
        assertThat(persistPublisher.get().getBooks()).isNotNull();
    }
}
