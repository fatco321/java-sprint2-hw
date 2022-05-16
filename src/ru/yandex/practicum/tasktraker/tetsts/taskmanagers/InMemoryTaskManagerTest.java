package taskmanagers;

import ru.yandex.practicum.tasktraker.controller.InMemoryTaskManager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    public InMemoryTaskManagerTest() {
        setManager(new InMemoryTaskManager());
    }
}