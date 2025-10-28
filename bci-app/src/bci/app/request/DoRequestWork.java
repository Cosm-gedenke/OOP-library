package bci.app.request;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.BorrowingRuleFailedException;

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = integerField("userId");
        int workId = integerField("workId");
        
        try {
            int returnDay = _receiver.makeRequest(userId, workId);
            _display.popup(Message.workReturnDay(workId, returnDay));
        }
        catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(e.getID());
        }
        catch (bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(e.getID());
        }
        catch (bci.exceptions.NoStockRuleFailedException e) {
            if (Form.confirm(Prompt.returnNotificationPreference())) {
                _receiver.addAvailabilityNotification(userId, workId);
            }
        }
        catch (bci.exceptions.BorrowingRuleFailedException e) {
            throw new BorrowingRuleFailedException(userId, workId, e.getRuleID());
        }
    }
}

