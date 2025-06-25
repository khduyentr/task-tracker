import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TaskRepository {
    private final Path FILE_PATH = Path.of("tasks.json");
    private List<Task> tasks;
    private static TaskRepository _instance;

    private TaskRepository() {
        tasks = loadSavedTasks();
    }

    public static TaskRepository getInstance() {
        if (_instance == null) {
            _instance = new TaskRepository();
        }
        return _instance;
    }

    private List<Task> loadSavedTasks() {
        List<Task> tasks = new ArrayList<>();

        if (!Files.exists(FILE_PATH)) {
            return tasks;
        }

        try {
            String content = Files.readString(FILE_PATH);

            if (content.isEmpty()) {
                return tasks;
            }

            String[] taskList = content.replace("[", "").replace("]", "").split("},");

            for (String taskJson: taskList) {
                String json = taskJson.endsWith("}") ? taskJson : taskJson + "}";
                Task task = Task.fromJson(json);
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public void listTasks(String status) {
        // only show not deleted item
        List<Task> filtered = tasks.stream().filter((item) -> !item.isDeleted()).toList();;
        if (!Objects.equals(status, "All")) {
            filtered = filtered.stream().filter((item) -> Objects.equals(item.getStatus().getLabel(), status)).toList();
        }


        if (filtered.isEmpty()) {
            System.out.println("Nothing has been found!");
        }

        for (Task task: filtered) {
            System.out.println(task.toString());
        }
    }

    public void saveTasks() {
        StringBuilder builder = new StringBuilder();

        builder.append("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            builder.append(tasks.get(i).toJson());
            if (i < tasks.size() - 1) {
                builder.append(",\n");
            }
        }
        builder.append("\n]");

        String content = builder.toString();
        try {
            Files.writeString(FILE_PATH, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewTask(String description) {
        Task task = new Task(description);
        tasks.add(task);

        System.out.println("Task added successfully (id: " + task.getId() + ")");
    }

    public void updateTaskDescription(Integer id, String description) {
        Task task = getTaskById(id).orElseThrow(() -> new IllegalArgumentException("Task @" + id + " not found!"));
        task.setDescription(description);
        System.out.println("Task updated description successfully (id: " + id + ")");
    }

    public void deleteTask(Integer id) {
        Task task = getTaskById(id).orElseThrow(() -> new IllegalArgumentException("Task @" + id + " not found!"));
        if (task.isDeleted()) {
            System.out.println("Task @" + id + " is already deleted");
        }
        task.setDeleted(true);
        System.out.println("Task deleted successfully (id: " + id + ")");
    }

    public void markDone(Integer id) {
        Task task = getTaskById(id).orElseThrow(() -> new IllegalArgumentException("Task @" + id + " not found!"));
        task.setStatus(TaskStatus.DONE);
        System.out.println("Task updated status successfully (id: " + id + ")");
    }

    public void markInProgress(Integer id) {
        Task task = getTaskById(id).orElseThrow(() -> new IllegalArgumentException("Task @" + id + " not found!"));
        task.setStatus(TaskStatus.IN_PROGRESS);
        System.out.println("Task updated status successfully (id: " + id + ")");
    }

    public Optional<Task> getTaskById(Integer id) {
        // convert list<task> into Stream
        // TODO: learn about stream please
        return tasks.stream().filter((task) -> Objects.equals(task.getId(), id)).findFirst();
    }
}
