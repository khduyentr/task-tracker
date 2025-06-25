import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private final Integer id;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TaskStatus status;
    private boolean softDeleted;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static int lastest_id = 0;


    public Task(String description) {
        this.description = description;

        // TODO: handle auto-increment later
        lastest_id++;
        this.id = lastest_id;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.softDeleted = false;
    }

    public Task(Integer id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, boolean softDeleted) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.softDeleted = softDeleted;
    }

    public Integer getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public String toJson() {

        return "{" +
                "\"id\":" + "\"" +this.id + "\"," +
                "\"description\":" + "\"" + this.description + "\"," +
                "\"status\":" + "\"" +this.status.toString() + "\"," +
                "\"createdAt\":" + "\"" + this.createdAt.format(formatter) + "\"," +
                "\"updatedAt\":" + "\"" + this.updatedAt.format(formatter) + "\"," +
                "\"deleted\":" + "\"" + this.softDeleted + "\"" +
                "}";
    }

    public static Task fromJson(String detail) {
        String taskDetail = detail.replace("{", "").replace("}", "").replace("\"","");
        String[] fields = taskDetail.split(",");

        String id = fields[0].split(":")[1].strip();
        String description = fields[1].split(":")[1].strip();
        String statusLabel = fields[2].split(":")[1].strip();
        String createdAtStr = fields[3].split("[a-z]:")[1].strip();
        String updatedAtStr = fields[4].split("[a-z]:")[1].strip();
        String deletedStatus = fields[5].split(":")[1].strip();

        TaskStatus status = TaskStatus.getStatusByLabel(statusLabel);
        LocalDateTime createdTime = LocalDateTime.parse(createdAtStr, formatter);
        LocalDateTime updatedTime = LocalDateTime.parse(updatedAtStr, formatter);
        boolean softDeleted = !deletedStatus.equals("false");

        if (Integer.parseInt(id) > lastest_id) {
            lastest_id = Integer.parseInt(id);
        }

        return new Task(Integer.parseInt(id), description, status, createdTime, updatedTime, softDeleted);
    }

    @Override
    public String toString() {

        return "Task @" + this.id + "\n" +
                " - Description: " + this.description + "\n" +
                " - Status: " + this.status.getLabel() + "\n" +
                " - Created at: " + this.createdAt.toString() + "\n" +
                " - Updated at: " + this.updatedAt.toString() + "\n";
    }

    public boolean isDeleted() {
        return this.softDeleted;
    }

    public void setDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
        this.updatedAt = LocalDateTime.now();
    }
}
