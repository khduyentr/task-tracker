# ✅ TaskCLI — Simple Java Task Manager

This project is inspired by the [Task Tracker project from roadmap.sh](https://roadmap.sh/projects/task-tracker).
A command-line based task manager written in **pure Java** that:

- Stores tasks in a `tasks.json` file  
- Uses **no external libraries**  
- Uses the **Singleton Pattern** for data management  
- Supports **soft delete** (deleted tasks are just marked, not removed)

---

## 📦 Features

- ✅ Add a new task  
- ✏️ Update task description  
- 🔄 Mark task as **in progress** or **done**  
- ❌ Soft delete a task (not removed from file, just marked)  
- 📃 List tasks by status:  
  - All  
  - To-do  
  - In-progress  
  - Done

## 🛠️ Compile & Run

You only need `javac` and `java`. No Maven or Gradle!

### 🔹 Compile All Classes (Inside src folder)

```bash
javac *.java
```
This will compile everything and place .class files inside src/

🔹 Run the App
```bash
java  TaskCLI <command> [arguments...]
```
📖 Command Reference
🟢 Add Task
```bash
java TaskCLI add "Learn Java Streams"
```
✏️ Update Task Description
```bash
java TaskCLI update "Master Streams" 1
```
🔄 Mark Task as In-Progress
```bash
java TaskCLI mark-in-progress 1
```
✅ Mark Task as Done
```bash
java TaskCLI mark-done 1
```
❌ Soft Delete Task
```bash
java TaskCLI delete 1
```
⚠️ This will not remove the task from the file, it sets a deleted: true flag. Deleted tasks will not appear in normal listings.

📋 List Tasks
All Tasks (default)
```bash
java TaskCLI list
```
Only To-do Tasks
```bash
java TaskCLI list todo
```
Only In-progress Tasks
```bash
java TaskCLI list in_progress
```
Only Done Tasks
```bash
java -cp src TaskCLI list done
```
