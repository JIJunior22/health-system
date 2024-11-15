package group.nine.healthsystem.exceptions;

public class UserException extends RuntimeException {
    public UserException() {
        super("Error: User not found!");
    }

}
