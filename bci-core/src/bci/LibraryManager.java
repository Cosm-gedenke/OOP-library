package bci;

import bci.creators.Creator;
import bci.users.User;
import bci.works.Work;
import bci.requests.Rules;
import bci.exceptions.*;
import bci.notifications.Notification;
import java.io.*;
import java.util.Collection;


/**
 * The façade class.
 */
public class LibraryManager {

    private Rules _defaultRules = new Rules();

    /** The object doing all the actual work. */
    private Library _library = new Library(_defaultRules);

    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        String filename = _library.getFilename();
        if (filename == null) throw new MissingFileAssociationException();

        if (wasChanged()) {
            try (var oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
                oos.writeObject(_library);
                _library.setChanged(false);
            }
        }
    }

    public void saveAs(String filename) throws MissingFileAssociationException, IOException {
        _library.setFilename(filename);
        save();
    }

    public boolean wasChanged() {
      return _library.wasChanged();
    }
    
    public void load(String filename) throws UnavailableFileException {
      try (var ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
          _library = (Library) ois.readObject();
          _library.setFilename(filename);
          _library.setChanged(false);
        } catch (IOException | ClassNotFoundException e) {
            throw new UnavailableFileException(filename);
        }
    }

    /**
     * Gets the current date
     * @return Current date
     */
    public Library getLibrary() {
      return _library;
    }

    /**
     * Gets the current date
     * @return Current date
     */
    public int getDate() {
      return _library.getDate();
    }

    /**
     * Gets a user by its ID
     * @param id ID of the user to get
     * @return the User that was found
     * @throws NoSuchUserException if no user with the given ID exists
     */
    public User getUser(int id) throws NoSuchUserException {
      return _library.getUser(id);
    }

    /**
     * Get the creator of a specific name
     * @param name name of the creator to get
     * @return creator or null if one does not exist
     * @throws NoSuchCreatorException if no creator with the given name exists
     */
    public Creator getCreator(String name) throws NoSuchCreatorException {
        return _library.getCreator(name);
    }

    /**
     * Gets a work by its ID
     * @param id ID of the work to get
     * @return the Work that was found
     * @throws NoSuchWorkException if no work with the given ID exists
     */
    public Work getWork(int id) throws NoSuchWorkException{
      return _library.getWork(id);
    }
    
    /**
     * Gets a collection of all the users of the library in the required order
     * @return Collection of users
     */
    public Collection<? extends Visitable> getAllUsers() {
      return _library.getAllUsers();
    }

    /**
     * Gets a collectio  of all the works in the library sorted by their ID
     * @return Collection of works
     */
    public Collection<? extends Visitable> getAllWorks() {
        return _library.getAllWorks();
    }

    public Collection<? extends Visitable> search(String searchterm) {
        return _library.search(searchterm);
    }

    /**
     * Advances the date by a specific number of days
     * @param days Number of days to advance the date by
     */
    public void advanceDate(int days) {
      _library.advanceDate(days);
    }

    /**
     * Adds a new user to the library's database
     * @param name Name of the user to add
     * @param email Email of the user to add
     * @return ID of the user that was registered
     * @throws UserRegistrationFailedException TODO
     */
    public int registerUser(String name, String email)
        throws UserRegistrationFailedException {
      return _library.registerUser(name, email);
    }

    /**
     * Makes a request with the given data
     * @param userID id of the user that is requesting
     * @param workID id of the work to be requested
     * @throws NoSuchUserException if the user does not exist
     * @throws NoSuchWorkException if the work does not exist
     * @throws BorrowingRuleFailedException if some rule was not followed
     */
    public int makeRequest(int userID, int workID)
        throws NoSuchUserException, NoSuchWorkException, BorrowingRuleFailedException {
        return _library.makeRequest(userID, workID);
    }

    /**
     * Read text input file and initializes the current library (which should be empty)
     * with the domain entities representeed in the import file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException if some error happens during the processing of the
     * import file.
     */
    public void importFile(String filename) throws ImportFileException {
      try {
        if (filename != null && !filename.isEmpty())
          _library.importFile(filename);
      } catch (IOException | UnrecognizedEntryException /* FIXME maybe other exceptions */ e) {
        throw new ImportFileException(filename, e);
      }
    }

    /**
     * updates the inventory of the given work identificator with the given amount
     *
     * @param id workid of the work in the system
     * @param amount amount of stock to be added
     * @throws NoSuchWorkException if the workid doesn't correspond an existing work
     * @throws NotEnoughInventoryException if the amount blows over the total stock
     */
    public void updateInventory(int id, int amount) throws NoSuchWorkException, NotEnoughInventoryException  {
      _library.updateInventory(id, amount);
    }

    public Collection<Notification> getUserNotifications(int userID) throws NoSuchUserException {
        return _library.throwUserNotifications(userID);
    }

    /**
     * adds a availability notification of a specific work to a given user
     * @param userID userid of the user in the system
     * @param workID workid of the work in the system
     */
    public void addAvailabilityNotification(int userID, int workID) {
        _library.addAvailabilityNotification(userID, workID);
    }

    public void returnWork(int userid, int workid)
        throws NoSuchWorkException, NoSuchUserException, WorkNotBorrowedByUserException {
            _library.returnWork(userid, workid);
    }

    public int getFine(int userID) throws NoSuchUserException {
        return _library.getFine(userID);
    }

    public int payFine(int userID) throws UserIsNotSuspendedException, NoSuchUserException {
        return _library.payFine(userID);
    }
}
