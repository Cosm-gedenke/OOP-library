package bci.app.work;

import bci.LibraryManager;
import bci.app.StringConverter;
import pt.tecnico.uilib.menus.Command;

/**
 * 4.3.2. Display all works.
 */
class DoDisplayWorks extends Command<LibraryManager> {

    DoDisplayWorks(LibraryManager receiver) {
        super(Label.SHOW_WORKS, receiver);
    }

    @Override
    protected final void execute() {
        StringConverter visitor = new StringConverter();
        _receiver.getAllWorks()
            .stream()
            .map(work -> work.accept(visitor))
            .forEach(_display::popup);
    }
}
