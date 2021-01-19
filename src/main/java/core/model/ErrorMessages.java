package core.model;

public enum ErrorMessages {
    ADD("Failed to insert the %s to database"),
    GET("Failed to get %s by id = %d"),
    GET_ALL("Failed to get all %s from database"),
    UPDATE("Failed to update the %s"),
    DELETE("Failed to delete the %s with id = %s");
    
    private String message;
    
    ErrorMessages(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
