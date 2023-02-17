package shop.mtcoding.bankapp.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountTransferReqDto {
    private Long amount; // 이체금액
    private String wAccountNumber; // 출금계좌번호
    private String dAccountNumber; // 입금계좌번호
    private String wAccountPassword; // 출금계좌 비밀번호
}
