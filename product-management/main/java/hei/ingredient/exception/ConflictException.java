package hei.ingredient.exception;

public class ConflictException extends RuntimeException{
    private ConflictException (String message) {
        super(message);
    }
    public ConflictException(String resource , String name){
        super(resource +" with name " + name +"already exists");
    }
}
