package bci.app.work;

import bci.LibraryManager;
import bci.app.StringConverter;
import bci.app.exceptions.NoSuchCreatorException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.3.3. Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

    DoDisplayWorksByCreator(LibraryManager receiver) {
        super(Label.SHOW_WORKS_BY_CREATOR, receiver);
        addStringField("name", Prompt.creatorId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            StringConverter visitor = new StringConverter();
            _receiver.getCreator(stringField("name"))
                                    .getWorks()
                                    .stream()
                                    .map(work -> work.accept(visitor))
                                    .forEach(_display::popup);
        }
        catch (bci.exceptions.NoSuchCreatorException e) {
            throw new NoSuchCreatorException(e.getName());
        }
    }

}
