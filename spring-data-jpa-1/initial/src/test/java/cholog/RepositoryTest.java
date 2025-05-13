package cholog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class RepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void save() {
        customerRepository.save(new Customer("Jack", "Bauer"));

        Iterable<Customer> customers = customerRepository.findAll();
        assertThat(customers).extracting(Customer::getFirstName).containsOnly("Jack");
    }

    @Test
    void findAll() {
        entityManager.persist(new Customer("Jack", "Bauer"));
        entityManager.persist(new Customer("Chloe", "O'Brian"));

        Iterable<Customer> customers = customerRepository.findAll();
        assertThat(customers).extracting(Customer::getFirstName).containsOnly("Jack", "Chloe");
    }

    @Test
    void findById() {
        entityManager.persist(new Customer("Jack", "Bauer"));
        entityManager.persist(new Customer("Chloe", "O'Brian"));

        Customer customer = customerRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
        assertThat(customer.getFirstName()).isEqualTo("Jack");
    }

    @Test
    void count() {
        entityManager.persist(new Customer("Jack", "Bauer"));
        entityManager.persist(new Customer("Chloe", "O'Brian"));

        long count = customerRepository.count();
        assertThat(count).isEqualTo(2);
    }

    @Test
    void delete() {
        entityManager.persist(new Customer("Jack", "Bauer"));
        entityManager.persist(new Customer("Chloe", "O'Brian"));

        customerRepository.deleteById(1L);
        assertThat(customerRepository.count()).isEqualTo(1);
    }
}
