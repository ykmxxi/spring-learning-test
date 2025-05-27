package cholog;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class TodoClientWithRestClient {

    private final RestClient restClient;

    public TodoClientWithRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Todo> getTodos() {
        // restClient의 get 메서드를 사용하여 요청을 보내고 결과를 To-do 리스트로 변환하여 반환
        Todo[] todoBody = restClient.get()
                .uri("/todos")
                .retrieve()
                .body(Todo[].class);

        return Arrays.asList(todoBody);
    }

    public Todo getTodoById(Long id) {
        // restClient의 get 메서드를 사용하여 요청을 보내고 결과를 To-do로 변환하여 반환
        // 존재하지 않는 id로 요청을 보낼 경우 TodoException.NotFound 예외를 던짐
        return restClient.get() // GET 요청 호출
                .uri("/todos/{id}", id) // URI 지정
                .retrieve() // 요청을 실제로 실행, 응답 본문을 추출하기 위한 ResponseSpec 빌더로 진입하는 메서드
                .onStatus(// 커스텀 에러 처리 로직을 등록할 때 사용
                        status -> status.value() == 404,
                        (request, response) -> {
                            throw new TodoException.NotFound(id);
                        })
                .body(Todo.class);
    }

    public ResponseEntity<Todo> postTodo(Todo todo) {
        return restClient.post()
                .uri("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(todo)
                .retrieve()
                .toEntity(Todo.class);
    }

    public ResponseEntity<Todo> updateTodo(Todo todo) {
        return restClient.put()
                .uri("/todos/{id}", todo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(todo)
                .retrieve()
                .toEntity(Todo.class);
    }
}
