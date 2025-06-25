public class TaskCLI {
    public static void main(String[] args) {
        TaskRepository repository = new TaskRepository();

        if (args.length < 1) {
            System.out.println("Usage: <command> [arguments]");
            System.out.println(" - Add: <add> [description]");
            System.out.println(" - Update description: <update> [new description] [id]");
            System.out.println(" - Mark in-progress: <mark-in-progress> [id]");
            System.out.println(" - Mark done: <mark-done> [id]");
            System.out.println(" - Delete: <delete> [id]");
            System.out.println(" - List: <list> [status: todo in_progress done]");
            return;
        }

        String command = args[0];

        switch (command) {
            case "add":
                if (args.length < 2) {
                    System.out.println(" - Add: <add> [description]");
                    return;
                }
                repository.addNewTask(args[1]);
                break;
            case "delete":
                if (args.length < 2) {
                    System.out.println(" - Delete: <delete> [id]");
                    return;
                }
                repository.deleteTask(Integer.parseInt(args[1]));
                break;
            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println(" - Mark in-progress: <mark-in-progress> [id]");
                    return;
                }
                repository.markInProgress(Integer.parseInt(args[1]));
                break;
            case "mark-done":
                if (args.length < 2) {
                    System.out.println(" - Mark done: <mark-done> [id]");
                    return;
                }
                repository.markDone(Integer.parseInt(args[1]));
                break;
            case "update":
                if (args.length < 3) {
                    System.out.println(" - Update description: <update> [new description] [id]");
                    return;
                }
                String description = args[1];
                Integer id = Integer.parseInt(args[2]);
                repository.updateTaskDescription(id, description);
                break;

            case "list":
                if (args.length < 2) {
                  repository.listTasks("All");
                  return;
                } else {
                    String status = args[1];
                    String label = switch (status) {
                        case "in_progress" -> "In-progress";
                        case "done" -> "Done";
                        default -> "To-do";
                    };
                    repository.listTasks(label);
                }
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
        repository.saveTasks();
    }
}