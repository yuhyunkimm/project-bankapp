package shop.mtcoding.bankapp.model.account;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bankapp.handler.ex.CustomException;

@Getter
@Setter
public class Account {
    private Integer id;
    private String number;
    private String password;
    private Long balance;
    private Integer userId;
    private Timestamp createdAt;

    public void withdraw(Long amount) {
        this.balance = this.balance - amount;
    }

    public void deposit(Long amount) {
        this.balance = this.balance + amount;
    }

    public void checkPassword(String passoword) {
        if (!this.password.equals(passoword)) {
            throw new CustomException("계좌비밀번호를 확인 해주세요", HttpStatus.BAD_REQUEST);
        }
    }

    public void checkBalance(Long amount) {
        if (this.balance < amount) {
            throw new CustomException("잔액이 부족합니다", HttpStatus.BAD_REQUEST);
        }
    }
}
