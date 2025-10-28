package bci.exceptions;

public class NoSuchCreatorException extends Exception {
    private String name;

    public NoSuchCreatorException(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
