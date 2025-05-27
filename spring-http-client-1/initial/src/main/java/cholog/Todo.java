package cholog;

public class Todo {

    private Long userId;
    private Long id;
    private String title;
    private boolean completed;

    public Todo() {
    }

    public Todo(final Long userId, final Long id, final String title, final boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setCompleted(final boolean completed) {
        this.completed = completed;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }
}
