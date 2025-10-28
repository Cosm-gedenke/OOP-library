package bci.app.request;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.NoSuchUserException;
import pt.tecnico.uilib.forms.Form;


/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException, WorkNotBorrowedByUserException {
        try {
            int userId = integerField("userId");
            int workId = integerField("workId");

            _receiver.returnWork(userId, workId);
            int debt = _receiver.getFine(userId);
                if (debt != 0) {
                    _display.popup(Message.showFine(userId, debt));

                    if (Form.confirm(Prompt.finePaymentChoice())) {
                        _receiver.payFine(userId);
                    }
            }
        }
        catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(e.getID());
        }
        catch (bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(e.getID());
        }
        catch (bci.exceptions.WorkNotBorrowedByUserException e) {
            throw new WorkNotBorrowedByUserException(e.getWorkID(), e.getUserID());
        }
        catch (bci.exceptions.UserIsNotSuspendedException e) {
            // Do nothing
        }


    }

}
