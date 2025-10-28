package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.UserIsActiveException;
import bci.app.exceptions.NoSuchUserException;

/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

    DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);
        addIntegerField("userID", bci.app.user.Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException, UserIsActiveException {
        try {
            _receiver.payFine(integerField("userID"));
        }
        catch(bci.exceptions.UserIsNotSuspendedException e) {
            throw new UserIsActiveException(e.getID());
        }
        catch(bci.exceptions.NoSuchUserException e) {throw new NoSuchUserException(integerField("userID"));}
    }

}
