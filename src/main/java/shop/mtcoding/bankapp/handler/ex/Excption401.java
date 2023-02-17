package shop.mtcoding.bankapp.handler.ex;

import org.springframework.http.HttpStatus;

public class Excption401 extends RuntimeException {

    private HttpStatus status;

}
