package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.FileOpenFailedException;
import bci.exceptions.UnavailableFileException;
import pt.tecnico.uilib.forms.Form;

/**
 * §4.1.1 Open and load files.
 */
class DoOpenFile extends Command<LibraryManager> {

    DoOpenFile(LibraryManager receiver) {
        super(Label.OPEN_FILE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
         try {
            if (_receiver.wasChanged() && Form.confirm(Prompt.saveBeforeExit())) {
                var cmd = new DoSaveFile(_receiver);
                cmd.execute();
            }
            _receiver.load(Form.requestString(Prompt.openFile()));
        }
        catch (UnavailableFileException e) {
            throw new FileOpenFailedException(e);
        }

    }

}
