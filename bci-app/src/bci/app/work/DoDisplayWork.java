package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.StringConverter;
import bci.app.exceptions.NoSuchWorkException;

/**
 * 4.3.1. Display work.
 */
class DoDisplayWork extends Command<LibraryManager> {

    DoDisplayWork(LibraryManager receiver) {
        super(Label.SHOW_WORK, receiver);
        addIntegerField("id", Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _display.popup(_receiver.getWork(integerField("id"))
                                    .accept(new StringConverter()));
        }
        catch (bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(e.getID());
        }
    }

}
