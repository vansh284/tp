package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class AddShortcutCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddShortcutCommand(null, null));
    }

    @Test
    public void execute_newMapping_success() {
        AddShortcutCommand addShortcutCommand= new AddShortcutCommand(new ShortcutAlias("del"),
                new CommandWord(DeleteCommand.COMMAND_WORD));

        String expectedMessage = String.format(AddShortcutCommand.MESSAGE_SUCCESS, "del --> delete");

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.getShortcutSettings().registerShortcut(new ShortcutAlias("del"),
                new CommandWord(DeleteCommand.COMMAND_WORD));

        assertCommandSuccess(addShortcutCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMapping_success() {
        ModelManager modelWithExistingMapping = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelWithExistingMapping.getShortcutSettings().registerShortcut(new ShortcutAlias("del"),
                new CommandWord(DeleteCommand.COMMAND_WORD));

        AddShortcutCommand addShortcutCommand= new AddShortcutCommand(new ShortcutAlias("del"),
                new CommandWord(ListCommand.COMMAND_WORD));

        String expectedMessage = String.format(AddShortcutCommand.MESSAGE_SUCCESS,  "del --> list")
                + "\n"
                + String.format(AddShortcutCommand.MESSAGE_REPLACED, "del --> delete");

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.getShortcutSettings().registerShortcut(new ShortcutAlias("del"),
                new CommandWord(ListCommand.COMMAND_WORD));

        assertCommandSuccess(addShortcutCommand, modelWithExistingMapping, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        AddShortcutCommand addShortcutFirstCommand = new AddShortcutCommand(new ShortcutAlias("del"),
                new CommandWord(ListCommand.COMMAND_WORD));
        AddShortcutCommand addShortcutSecondCommand = new AddShortcutCommand(new ShortcutAlias("del"),
                new CommandWord(DeleteCommand.COMMAND_WORD));
        AddShortcutCommand addShortcutThirdCommand = new AddShortcutCommand(new ShortcutAlias("foo"),
                new CommandWord(DeleteCommand.COMMAND_WORD));
        // same object -> returns true

        assertTrue(addShortcutFirstCommand.equals(addShortcutFirstCommand));

        // same values -> returns true
        AddShortcutCommand test = new AddShortcutCommand(new ShortcutAlias("del"),
                new CommandWord(ListCommand.COMMAND_WORD));
        assertTrue(addShortcutFirstCommand.equals(test));

        // different types -> returns false
        assertFalse(addShortcutFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addShortcutFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(addShortcutFirstCommand.equals(addShortcutSecondCommand));

        // different alias -> returns false
        assertFalse(addShortcutFirstCommand.equals(addShortcutThirdCommand));
    }

    @Test
    public void toStringMethod() {
        AddShortcutCommand addShortcutFirstCommand = new AddShortcutCommand(new ShortcutAlias("del"),
                new CommandWord(ListCommand.COMMAND_WORD));
        String expected = AddShortcutCommand.class.getCanonicalName()
                + "{shortcutAlias=" + "del"
                + ", "
                + "command=" + ListCommand.COMMAND_WORD
                + "}";
        assertEquals(expected, addShortcutFirstCommand.toString());
    }
}
