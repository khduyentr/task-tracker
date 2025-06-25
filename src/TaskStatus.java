public enum TaskStatus {
    TODO("To-do"),
    IN_PROGRESS("In-progress"),
    DONE("Done");

    private final String label;

    TaskStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static TaskStatus getStatusByLabel(String label) {
        return switch (label) {
            case "In-progress" -> IN_PROGRESS;
            case "Done" -> DONE;
            default -> TODO;
        };
    }

    @Override
    public String toString() {
        return this.label;
    }
}
