package in.manipal.mit.ccd.lab4.model;

import java.time.LocalDateTime;

public class Todo {

    private Long todoId;
    private String taskDescription;
    private String status;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeUpdated;
    private String userId;

    public Todo() {
    }

    public Todo(Long todoId, String taskDescription, String status, LocalDateTime dateTimeCreated,
                LocalDateTime dateTimeUpdated, String userId) {
        this.todoId = todoId;
        this.taskDescription = taskDescription;
        this.status = status;
        this.dateTimeCreated = dateTimeCreated;
        this.dateTimeUpdated = dateTimeUpdated;
        this.userId = userId;
    }

    public Long getTodoId() {
        return todoId;
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public LocalDateTime getDateTimeUpdated() {
        return dateTimeUpdated;
    }

    public void setDateTimeUpdated(LocalDateTime dateTimeUpdated) {
        this.dateTimeUpdated = dateTimeUpdated;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
