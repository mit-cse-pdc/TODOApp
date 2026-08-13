package in.manipal.mit.ccd.lab4.dto;

public class TodoCreateRequest {

    private String taskDescription;
    private String userId;

    public TodoCreateRequest() {
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
