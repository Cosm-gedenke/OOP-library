package bci.app.user;

import bci.LibraryManager;
import bci.app.StringConverter;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.NoSuchUserException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

    DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
        addIntegerField("userid", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            StringConverter visitor = new StringConverter();
            _receiver.getUserNotifications(integerField("userid"))
                                .stream()
                                .map(notification -> notification.accept(visitor))
                                .forEach(_display::popup);
        }
        catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(e.getID());
        }
    }

}
