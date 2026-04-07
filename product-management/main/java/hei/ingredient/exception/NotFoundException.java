package hei.ingredient.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String resource ,Object id){
        super( resource + "with id" +id+ " not found");
    }
}
