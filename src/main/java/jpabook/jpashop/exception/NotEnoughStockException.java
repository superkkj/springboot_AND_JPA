package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {

    //오버라이드 한 이유? 메세지를 넘겨주려고 예외가 발생하는 근원적인 이유를 보여 줄 수 있

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

}
