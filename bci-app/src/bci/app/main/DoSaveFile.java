package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form;
import java.io.IOException;

/**
 * §4.1.1 Open and load files.
 */
class DoSaveFile extends Command<LibraryManager> {

    DoSaveFile(LibraryManager receiver) {
        super(Label.SAVE_FILE, receiver, r -> r.getLibrary() != null);
    }

    @Override
    protected final void execute() {
        try {
		    _receiver.save();
		}
		catch(bci.exceptions.MissingFileAssociationException e) {
		    try {
				_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
			}
			catch (bci.exceptions.MissingFileAssociationException | IOException t) {
			    t.printStackTrace();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

    }

}
