package shop.mtcoding.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.bankapp.dto.account.AccountDepositReqDto;
import shop.mtcoding.bankapp.dto.account.AccountSaveReqDto;
import shop.mtcoding.bankapp.dto.account.AccountWithdrawReqDto;
import shop.mtcoding.bankapp.handler.ex.CustomException;
import shop.mtcoding.bankapp.model.account.Account;
import shop.mtcoding.bankapp.model.account.AccountRepository;
import shop.mtcoding.bankapp.model.history.History;
import shop.mtcoding.bankapp.model.history.HistoryRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Transactional
    public void 계좌생성(AccountSaveReqDto accountSaveReqDto, int principalId) {
        Account account = accountSaveReqDto.toModel(principalId);
        accountRepository.insert(account);
    }

    @Transactional
    public int 계좌출금(AccountWithdrawReqDto accountWithdrawReqDto) {
        // atm 계좌출금
        // 1. 계좌 존재 유무
        Account accountPS = accountRepository.findByNumber(accountWithdrawReqDto.getWAccountNumber());
        if (accountPS == null) {
            throw new CustomException("계좌를 확인 해주세요", HttpStatus.BAD_REQUEST);
        }

        // 유저 인증(atm으로 출금하는 경우로 인증이 필요없다)
        // 2. 계좌패스워드 확인
        // if
        // (!accountPS.getNumber().equals(accountWithdrawReqDto.getWAccountPassword()))
        // {
        // throw new CustomException("계좌비밀번호를 확인 해주세요", HttpStatus.BAD_REQUEST);
        // }
        accountPS.checkPassword(accountWithdrawReqDto.getWAccountPassword());

        // 3. 계좌잔액
        // if (accountPS.getBalance() < accountWithdrawReqDto.getAmount()) {
        // throw new CustomException("잔액이 부족합니다", HttpStatus.BAD_REQUEST);
        // }
        accountPS.checkBalance(accountWithdrawReqDto.getAmount());

        // 4. 잔액변경 => 출금(balance -(마이너스))
        // 기존 값을 select해서 다들고 와서 전체를 덮어씌우기
        accountPS.setBalance(accountWithdrawReqDto.getAmount());
        accountRepository.updateById(accountPS);

        // 5. 히스토리(거래내역)
        History history = new History();
        history.setAmount(accountWithdrawReqDto.getAmount());
        history.setWAccountId(accountPS.getId());
        history.setDAccountId(null);
        history.setWBalance(accountPS.getBalance());
        history.setDBalance(null);

        // 6. 해당 계좌의 id를 return
        return accountPS.getId();
    }

    public void 계좌입금(AccountDepositReqDto accountDepositReqDto, int principalId) {
        // atm 계좌입금 (인증은 컨트롤러에서 확인)
        // 내 비밀번호 확인 ?
        // 1. 내 계좌 잔액
        // 2. 금액 입력
        // 3. 잔액 변경
        // 4. 잔액 변경

    }
}