package taskmanagers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktraker.controller.FileBackedTasksManager;
import ru.yandex.practicum.tasktraker.tasks.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();

    public FileBackedTasksManagerTest(FileBackedTasksManager fileBackedTasksManager) {
        setManager(new FileBackedTasksManager());
    }

    @Test
    void test1_ShouldLoadWithEmptyListExceptions() throws IOException {
        File file = new File("tasksTest.csv");
        PrintWriter pw = new PrintWriter(file);
        pw.write("ID;TYPE;NAME;STATUS;DESCRIPTION;EPIC" + "\n");
        pw.write("\n" + "HISTORY");
        pw.close();
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> {
                    pw.write("ID;TYPE;NAME;STATUS;DESCRIPTION;EPIC" + "\n");
                    pw.write("\n" + "HISTORY");
                    pw.close();
                    FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(new File("tasksTest.csv"));
                }
        );
        assertNull(exception.getMessage());
    }

    @Test
    void test2_ShouldLoadWithOutSubtask() {
        Task task = new Task("Task_Test", "Test", TaskStatus.NEW);
        Epic epic = new Epic("Epic_Test", "Test");
        fileBackedTasksManager.addTask(task);
        fileBackedTasksManager.addEpic(epic);
        fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File("tasks.csv"));
        assertEquals(epic.getTaskName(), fileBackedTasksManager.getEpic(2).getTaskName());
        assertEquals(task.getTaskName(), fileBackedTasksManager.getTask(1).getTaskName());
    }
}
