package hei.ingredient.exception;

public class NullPriceException extends RuntimeException {
    public  NullPriceException(String dishName){
        super("selling price for dish " + dishName + " is null . Marge counting impossible");
    }
}
