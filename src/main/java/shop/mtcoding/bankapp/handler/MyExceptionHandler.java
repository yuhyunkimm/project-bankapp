package shop.mtcoding.bankapp.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    // 자바스크립트 응답
    // 오류나면 무조건 뒤로 간다
    @ExceptionHandler(RuntimeException.class)
    public String basicException(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + e.getMessage() + "');");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }
    // DTO를 응답
}
