package chitti.command;

import chitti.exception.ChittiException;
import chitti.storage.Storage;
import chitti.task.TaskList;
import chitti.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddTodoCommandTest {

    @Test
    void testAddTodoWithValidDescription() throws Exception {

        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");
        AddTodoCommand command = new AddTodoCommand("Read book");


        command.execute(tasks, ui, storage);


        assertEquals(1, tasks.size());
    }

    @Test
    void testAddTodoWithEmptyDescription() {

        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");
        AddTodoCommand command = new AddTodoCommand("");


        Exception exception = assertThrows(ChittiException.class, () -> {
            command.execute(tasks, ui, storage);
        });

        assertEquals("The description of a todo cannot be empty. Use the following format: todo <description>",
                exception.getMessage());
        assertEquals(0, tasks.size());
    }

    @Test
    void testAddTodoWithSpacesOnly() {

        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");
        AddTodoCommand command = new AddTodoCommand("   ");

        Exception exception = assertThrows(ChittiException.class, () -> {
            command.execute(tasks, ui, storage);
        });

        assertEquals("The description of a todo cannot be empty. Use the following format: todo <description>",
                exception.getMessage());
        assertEquals(0, tasks.size());
    }

    @Test
    void testAddMultipleTodos() throws Exception {
        // Setup
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");
        AddTodoCommand command1 = new AddTodoCommand("Task 1");
        AddTodoCommand command2 = new AddTodoCommand("Task 2");

        command1.execute(tasks, ui, storage);
        command2.execute(tasks, ui, storage);

        assertEquals(2, tasks.size());
    }
}