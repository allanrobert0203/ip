package chitti.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.task.ToDo;
import chitti.ui.Ui;

class DeleteCommandTest {

    @Test
    void testDeleteValidTask() throws Exception {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");

        tasks.add(new ToDo("Test task"));
        DeleteCommand command = new DeleteCommand("1");

        command.execute(tasks, ui, storage);

        assertEquals(0, tasks.size());
    }

    @Test
    void testDeleteInvalidTaskNumber() {

        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");

        tasks.add(new ToDo("Test task"));
        DeleteCommand command = new DeleteCommand("2");

        Exception exception = assertThrows(ChittiException.class, () -> {
            command.execute(tasks, ui, storage);
        });

        assertEquals("chitti.task.Task 2 doesn't exist! You have 1 tasks.", exception.getMessage());
        assertEquals(1, tasks.size()); // Task should still be there
    }

    @Test
    void testDeleteNegativeTaskNumber() {

        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");

        tasks.add(new ToDo("Test task"));
        DeleteCommand command = new DeleteCommand("0");

        Exception exception = assertThrows(ChittiException.class, () -> {
            command.execute(tasks, ui, storage);
        });

        assertEquals("chitti.task.Task 0 doesn't exist! You have 1 tasks.", exception.getMessage());
        assertEquals(1, tasks.size());
    }

    @Test
    void testDeleteTaskWithSpacesInInput() throws Exception {

        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");

        tasks.add(new ToDo("Test task"));
        DeleteCommand command = new DeleteCommand("  1  ");

        command.execute(tasks, ui, storage);

        assertEquals(0, tasks.size());
    }
}
