package bci.exceptions;

public class UserRegistrationFailedException extends Exception {
    private String name;

    private String email;

    public UserRegistrationFailedException(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }
}
