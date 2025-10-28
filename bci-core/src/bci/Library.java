package bci;

import bci.exceptions.*;
import bci.users.User;
import bci.creators.Creator;
import bci.works.*;
import bci.requests.*;
import bci.notifications.*;

import java.io.*;

import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;

/** Class that represents the library as a whole. */
class Library implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /**
     * Name of the file to use when importing data
     */
    private String filename = null;
    
    /**
     * Wether the library has been changed 
     */
    private transient boolean changed = false;

    /**
     * Current date, in days since the start of the program
     */
    private int date = 1;

    /**
     * ID of the most recent user in the library
     */
    private int currentUserID = 1;

    /**
     * ID of the most recent work in the library
     */
    private int currentWorkID = 1;
    
    /**
     * Map of all the existing creators
     */
    private Map<String, Creator> creators = new HashMap<String, Creator>();

    /**
     * Set of users for efficient sorting
     */
    private Set<User> usersSet = new TreeSet<User>();

    /**
     * Map of users for efficient look-up
     */
    private Map<Integer, User> usersMap = new HashMap<Integer, User>();

    /**
     * Map of all the works in the library
     */
    private Map<Integer, Work> works = new TreeMap<Integer, Work>();

    /**
     * Rules that the library must follow for requests
     */
    private Rules rules;

    /**
     * Constructs a library that will follow a set of rules for requests
     * @param rules rules to follow
     */
    public Library(Rules rules) {
        this.rules = rules;
    }

    /**
     * Gets the current date
     * @return Current date
     */
    public int getDate() {
        return this.date;
    }

    /**
     * Check wether the library has been changed
     * @return library change status
     */
    public boolean wasChanged() {
        return this.changed;
    }

    /**
     * Gets the current file name
     * @return the file's name
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Gets a user by its ID
     * @param id ID of the user to get
     * @return the User that was found
     * @throws NoSuchUserException if no user with the given ID exists
     */
    public User getUser(int id) throws NoSuchUserException {
        User user = this.usersMap.get(id);

        if (user == null) {
            throw new NoSuchUserException(id);
        }

        return user;
    }

    /**
     * Get the creator of a specific name
     * @param name name of the creator to get
     * @return creator or null if one does not exist
     * @throws NoSuchCreatorException if no creator with the given name exists
     */
    public Creator getCreator(String name) throws NoSuchCreatorException {
        Creator creator = creators.get(name);
        if (creator == null) {
            throw new NoSuchCreatorException(name);
        }
        return creator;
    }

    /**
     * Gets a work by its ID
     * @param id ID of the work to get
     * @return the Work that was found
     * @throws NoSuchWorkException if no user with the given ID exists
     */
    public Work getWork(int id) throws NoSuchWorkException {
        Work work = this.works.get(id);

        if (work == null) {
            throw new NoSuchWorkException(id);
        }

        return work;
    }

    /**
     * Gets a collection of all the users of the library in the required order
     * @return Collection of users
     */
    public Collection<? extends Visitable> getAllUsers() {
        return this.usersSet;
    }

    /**
     * Gets a collectio  of all the works in the library sorted by their ID
     * @return Collection of works
     */
    public Collection<? extends Visitable> getAllWorks() {
        return this.works.values();
    }

    public Collection<? extends Visitable> search(String searchterm) {
        List<Work> foundworks = new ArrayList<Work>();
        for(Work work : works.values()) {
            if (work.containsTerm(searchterm)) {foundworks.add(work);}
        }
        return foundworks;
    }
    /**
     * Sets the "changed" status of the library
     * @param value status of the library
     */
    public void setChanged(boolean value) {
        this.changed = value;
    }

    /**
     * Set the file to use when importing data
     * @param filename name of the file to use
     */
    public void setFilename(String filename) {
        this.filename = filename;
        setChanged(true);
    }

    /**
     * Checks if a User name is valid
     * @param name name to validate
     * @return True or false based on the name
     */
    public boolean isValidUserName(String name) {
        return !name.isEmpty();
    }

    /**
     * Checks if an email is valid
     * @param email email to validate
     * @return True or false based on the name
     */
    public boolean isValidEmail(String email) {
        return !email.isEmpty();
    }

    /**
     * Checks if a user's attributes are valid
     * @param name name of the user
     * @param email email of the user
     * @return True or false based on the attributes
     */
    public boolean isValidUser(String name, String email) {
        return isValidUserName(name) && isValidEmail(email);
    }

    /**
     * Checks if a string is an integer
     * @param string string to check
     * @return True or false based on the string
     */
    public boolean isInteger(String string) {
        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks the validaty of a DVD's fields
     * Only checks what is not already checked elsewhere
     * @param stock stock of the DVD
     * @param price price of the DVD
     * @param igac IGAC registration number
     * @return True or false based on the fields
     */
    public boolean isValidDVD(int stock, int price, String igac) {
        return stock >= 0 && price >= 0 && isInteger(igac);
    }

    /**
     * Checks the validaty of a Book's fields
     * Only checks what is not already checked elsewhere
     * @param stock stock of the Book
     * @param price price of the Book
     * @param isbn ISBN registration number
     * @return True or false based on the fields
     */
    public boolean isValidBook(int stock, int price, String isbn) {
        return stock >= 0 && price >= 0 && isInteger(isbn) &&
            10 <= isbn.length() && isbn.length() <= 13;
    }

    /**
     * Converts a string into a category
     * @param category string to convert
     * @return Category converted from the string
     * @throws UnrecognizedEntryException if an invalid entry is provided
     */
    public Category categoryFromString(String category) throws UnrecognizedEntryException {
        switch (category) {
            case ("FICTION"): return Category.FICTION;
            case ("REFERENCE"): return Category.REFERENCE;
            case ("SCITECH"): return Category.SCITECH;
            default: throw new UnrecognizedEntryException(category);
        }
    }

    /**
     * Advances the date by a specific number of days
     * @param days Number of days to advance the date by
     */
    public void advanceDate(int days) {
        if (days > 0) {
            this.date += days;
            setChanged(true);
            updateUsersState(date);
        }
    }

    /**
     * Adds a new user to the library's database
     * @param name Name of the user to add
     * @param email Email of the user to add
     * @return ID of the user that was registered
     * @throws UserRegistrationFailedException if the user fields are invalid
     */
    public int registerUser(String name, String email)
            throws UserRegistrationFailedException {
        if (!isValidUser(name, email)) {
            throw new UserRegistrationFailedException(name, email);
        }

        User user = new User(name, email, this.currentUserID);
        usersSet.add(user);
        usersMap.put(this.currentUserID++, user);
        setChanged(true);
        return this.currentUserID - 1;
    }

    /**
     * Adds a new creator to the library's database
     * Does not check if a creator of the same name already exists
     * @param name Name of the creator to register
     * @return Creator that was registered
     */
    public Creator registerCreator(String name) {
        Creator creator = new Creator(name);
        creators.put(name, creator);
        return creator;
    }

    public void registerDVD(int id, int stock, int price, String title,
        Category category, Creator director, String igac) throws UnrecognizedEntryException {
            if (!isValidDVD(stock, price, igac)) {
                throw new UnrecognizedEntryException(stock + ":" + price + ":" +  igac);
            }

            DVD dvd = new DVD(currentWorkID, stock, price, title, category, director, igac);
            director.addWork(dvd);
            addWork(currentWorkID++, dvd);
    }

    public void registerBook(int id, int stock, int price, String title,
        Category category, Collection<Creator> authorList, String isbn)
            throws UnrecognizedEntryException {

            if (!isValidBook(stock, price, isbn)) {
                throw new UnrecognizedEntryException(stock + ":" + price + ":" + isbn);
            }
            
            Book book = new Book(currentWorkID, stock, price, title, category, authorList, isbn);

            for (Creator author : authorList) {
                author.addWork(book);
            }
            
            addWork(currentWorkID++, book);
    }

    /**
     * Adds a work to the database
     * @param id id of the work
     * @param work work to add
     */
    public void addWork(int id, Work work) {
        works.put(id, work);
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
            User user = getUser(userID);
            Work work = getWork(workID);

            rules.validateAll(user, work);
            int returnDate = returnDate(user, work) + date;
            Request request = new Request(work, returnDate);
            user.addRequest(request);
            work.decrementAvailableStock();;
            
            return returnDate;
    }
    public int returnDate(User user, Work work) {
        return user.getBehaviour().returnDate(work.getTotalStock());
    }
    /**
     * Read the text input file at the beginning of the program and populates the
     * instances of the various possible types (books, DVDs, users).
     *
     * @param filename name of the file to load
     * @throws UnrecognizedEntryException if an entry is invalid
     * @throws IOException if a standard IO error occured
     */
    public void importFile(String filename) throws UnrecognizedEntryException, IOException {
        var reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            var fields = line.split(":");
            process(fields);
        }

        setChanged(true);
        reader.close();
    }

    /**
     * Processes the given fields and populates the library with them.
     *
     * @param fields fields read from the file
     * @throws UnrecognizedEntryException if an entry is invalid
     */
    public void process(String[] fields) throws UnrecognizedEntryException {
        switch (fields[0]) {
            case "DVD" -> processDvd(fields);
            case "BOOK" -> processBook(fields);
            case "USER"  -> processUser(fields);
            default -> throw new UnrecognizedEntryException(fields[0]);
        }
    }
    
    /**
     * Processes the given fields and creates a DVD
     * @param fields fields read from the file
     * @throws UnrecognizedEntryException if an entry is invalid
     */
    public void processDvd(String[] fields) throws UnrecognizedEntryException {
        String title = fields[1];
        String directorName = fields[2];
        int price, stock;

        try {
            price = Integer.parseInt(fields[3]);
            stock = Integer.parseInt(fields[6]);
        }
        catch (NumberFormatException e) {
            throw new UnrecognizedEntryException(fields[3] + ":" + fields[6]);
        }

        Category category = categoryFromString(fields[4]);

        String igac = fields[5];
        
        Creator director;
        try {
            director = getCreator(directorName);
        } catch (NoSuchCreatorException e) {
            director = registerCreator(directorName);
        }
        
        registerDVD(currentWorkID, stock, price, title, category, director, igac);
        setChanged(true);
    }

    /**
     * Processes the given fields and creates a User
     *
     * @param fields fields read from the file
     */
    public void processUser(String[] fields) throws UnrecognizedEntryException {
        String name = fields[1];
        String email = fields[2];
        try {
            registerUser(name, email);
            setChanged(true);
        }
        catch (UserRegistrationFailedException e) {
            throw new UnrecognizedEntryException(e.getName() + ":" + e.getEmail());
        }
    }

    /**
     * Processes the given fields and creates a Book
     *
     * @param fields read from the file
     * @throws UnrecognizedEntryException if an entry is invalid
     */
    public void processBook(String[] fields) throws UnrecognizedEntryException {
        String title = fields[1];
        String authorsStr = fields[2];
        int price, stock;
        Category category = categoryFromString(fields[4]);
        String isbn = fields[5];

        try {
            price = Integer.parseInt(fields[3]);
            stock = Integer.parseInt(fields[6]);
        }
        catch (NumberFormatException e) {
            throw new UnrecognizedEntryException(fields[3] + ":" + fields[6]);
        }

        String[] authorsSplit = authorsStr.split(",");

        List<Creator> authorList = new ArrayList<Creator>();

        for (String authorName : authorsSplit) {
            Creator author;
            try {
                author = getCreator(authorName);
            } catch (NoSuchCreatorException e) {
                author = registerCreator(authorName);
            }

            authorList.add(author);
        }

        registerBook(currentWorkID, stock, price, title, category, authorList, isbn);
        setChanged(true);
    }
    
    public void updateInventory(int id, int amount) throws NoSuchWorkException, NotEnoughInventoryException  {
        Work work = getWork(id);
        work.updateInventory(amount);
        
        if (work.getTotalStock() == 0) {
            works.remove(id);
            if (work instanceof Book) {
                removeBookFromAuthors((Book) work);
            }
            else if (work instanceof DVD) {
                removeDVDFromDirector((DVD) work);
            }
        }
    }

    public Collection<Notification> throwUserNotifications(int userID) throws NoSuchUserException {
        User user = getUser(userID);
        List<Notification> returnNotifications = new ArrayList<Notification>();
        List<Notification> userNotifications = user.getNotifications(); // You'll need to add this getter
        
        for(int i = 0; i < userNotifications.size(); i++) {
            Notification notification = userNotifications.get(i);
            // Check against the CURRENT work state, not the copied one
            Work currentWork = this.works.get(notification.getWork().getID());
            if(currentWork != null) {
                returnNotifications.add(notification);
                userNotifications.remove(i);
                i--;
            }
        }
        return returnNotifications;
    }

    private void removeBookFromAuthors(Book book) {
        Collection<Creator> authors = book.getAuthors();

        for (Creator author : authors) {
            removeWorkFromCreator(book, author);
        }
    }

    private void removeDVDFromDirector(DVD dvd) {
        removeWorkFromCreator(dvd, dvd.getDirector());
    }

    private void removeWorkFromCreator(Work work, Creator creator) {
        creator.removeWork(work);

        if (!creator.hasWorks()) {
            creators.remove(creator.getName());
        }
    }
    
    public void addAvailabilityNotification(int userID, int workID) {
        try {
            getUser(userID).addInterestAvailable(new AvailabilityNotification(getWork(workID)));
        }
        catch (NoSuchUserException e) {
            // Do nothing
        }
        catch (NoSuchWorkException e) {
            // Do nothing
        }
    }

    private void updateUsersState(int date) {
        for(User user : this.usersSet) {
            user.updateState(date);
        }
    }

    private void updateUsersInterest(int workID) {
        for (User user : this.usersSet) {
            user.checkInterest(workID);
            
        }
    }

    public void returnWork(int userID, int workID)
        throws NoSuchWorkException, NoSuchUserException, WorkNotBorrowedByUserException {
            User user = getUser(userID);
            Work work = getWork(workID);
            boolean wasOutOfStock = user.returnWork(work, this.date);
            if (wasOutOfStock) {
                updateUsersInterest(workID);
            }
    }

    public int getFine(int userID) throws NoSuchUserException {
        return getUser(userID).getFine(date);
    }

    public int payFine(int userID) throws NoSuchUserException, UserIsNotSuspendedException {
        return getUser(userID).payFine(date);
    }
}
