package cholog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class RestClientTest {

    @Autowired
    private TodoClientWithRestClient todoClient;

    @Test
    void testGetTodos() {
        List<Todo> todos = todoClient.getTodos();
        assertThat(todos).isNotEmpty();
    }

    @Test
    void testGetTodoWithId() {
        Todo todo = todoClient.getTodoById(1L);
        assertThat(todo.getTitle()).isNotEmpty();
    }

    @Test
    void testGetTodoWithNonExistentId() {
        Long nonExistentId = 9999L;

        assertThatThrownBy(() -> todoClient.getTodoById(nonExistentId))
                .isInstanceOf(TodoException.NotFound.class);
    }

    @Test
    void testPostTodos() {
        Todo todo = new Todo(3L, 6L, "hello", false);

        ResponseEntity<Todo> response = todoClient.postTodo(todo);
        Todo created = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertAll(() -> {
            assertThat(created.getUserId()).isEqualTo(3L);
            assertThat(created.getTitle()).isEqualTo("hello");
            assertThat(created.isCompleted()).isEqualTo(false);
        });
    }

    @DisplayName("PUT 요청 테스트")
    @Test
    void testPutTodos() {
        Todo todo = todoClient.getTodoById(1L);
        todo.setTitle("changed");
        todo.setCompleted(true);
        todo.setUserId(100L);

        Todo afterPut = todoClient.updateTodo(todo).getBody();

        assertAll(
                () -> assertThat(afterPut.getTitle()).isEqualTo("changed"),
                () -> assertThat(afterPut.isCompleted()).isEqualTo(true),
                () -> assertThat(afterPut.getUserId()).isEqualTo(100L)
        );

    }
}
