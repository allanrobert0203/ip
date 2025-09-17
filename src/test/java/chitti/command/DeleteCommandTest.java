package chitti.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import chitti.exception.DuplicateTaskException;
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
    void testDeleteInvalidTaskNumber() throws DuplicateTaskException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");

        tasks.add(new ToDo("Test task"));
        DeleteCommand command = new DeleteCommand("2");

        Exception exception = assertThrows(ChittiException.class, () -> {
            command.execute(tasks, ui, storage);
        });

        assertEquals("Task 2 doesn't exist! You have 1 tasks.", exception.getMessage());
        assertEquals(1, tasks.size()); // Task should still be there
    }

    @Test
    void testDeleteNegativeTaskNumber() throws DuplicateTaskException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");

        tasks.add(new ToDo("Test task"));
        DeleteCommand command = new DeleteCommand("0");

        Exception exception = assertThrows(ChittiException.class, () -> {
            command.execute(tasks, ui, storage);
        });

        assertEquals("Task 0 doesn't exist! You have 1 tasks.", exception.getMessage());
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

    @Test
    void testDeleteNonNumericInput() throws DuplicateTaskException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");

        tasks.add(new ToDo("Test task"));
        DeleteCommand command = new DeleteCommand("abc");

        Exception exception = assertThrows(ChittiException.class, () -> {
            command.execute(tasks, ui, storage);
        });

        assertEquals("Please provide a valid task number!", exception.getMessage());
        assertEquals(1, tasks.size());
    }

    @Test
    void testDeleteEmptyInput() throws DuplicateTaskException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");

        tasks.add(new ToDo("Test task"));
        DeleteCommand command = new DeleteCommand("");

        Exception exception = assertThrows(ChittiException.class, () -> {
            command.execute(tasks, ui, storage);
        });

        assertEquals("Please specify which task to delete! Usage: delete <task number>", exception.getMessage());
        assertEquals(1, tasks.size());
    }
}
