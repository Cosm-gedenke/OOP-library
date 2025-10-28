package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
        addIntegerField("id", Prompt.workId());
        addIntegerField("amount", Prompt.amountToUpdate());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.updateInventory(integerField("id"),integerField("amount"));
        }
        catch (bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(e.getID());
        }
        catch (bci.exceptions.NotEnoughInventoryException e) {
            _display.popup(Message.notEnoughInventory(e.getID(), e.getAmount()));
        }
    }
}
