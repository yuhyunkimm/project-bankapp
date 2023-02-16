package shop.mtcoding.bankapp.model.history;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class History {
    private Integer id;
    private Long amount;
    private Long wBalance;
    private Long dBalance;
    private Integer wAccountId; // 출금계좌 잔액
    private Integer dAccountId; // 입금계좌 잔액
    private Timestamp createdAt;
}
