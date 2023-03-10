package shop.mtcoding.bankapp.controller;

import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.mtcoding.bankapp.dto.account.AccountDepositReqDto;
import shop.mtcoding.bankapp.dto.account.AccountSaveReqDto;
import shop.mtcoding.bankapp.dto.account.AccountTransferReqDto;
import shop.mtcoding.bankapp.dto.account.AccountWithdrawReqDto;
import shop.mtcoding.bankapp.handler.ex.CustomException;
import shop.mtcoding.bankapp.model.account.Account;
import shop.mtcoding.bankapp.model.account.AccountRepository;
import shop.mtcoding.bankapp.model.user.User;
import shop.mtcoding.bankapp.service.AccountService;

@Controller
public class AccountController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/acount/transfer")
    public String transfer(AccountTransferReqDto accountTransferReqDto) { // 이체(인증필요)
        // 1. 인증
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인을 먼저 해주세요", HttpStatus.UNAUTHORIZED);
        }

        // 2. 유효성 검사
        // 동일한 계좌 비교
        if (accountTransferReqDto.getWAccountNumber().equals(accountTransferReqDto.getDAccountNumber())) {
            throw new CustomException("출금계좌와 입금계좌가 동일할 수 없습니다", HttpStatus.BAD_REQUEST);
        }
        if (accountTransferReqDto.getAmount() == null) {
            throw new CustomException("금액을 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (accountTransferReqDto.getAmount().longValue() <= 0) {
            throw new CustomException("이체액이 0원 이하를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (accountTransferReqDto.getWAccountNumber() == null || accountTransferReqDto.getWAccountNumber().isEmpty()) {
            throw new CustomException("출금계좌번호를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (accountTransferReqDto.getDAccountNumber() == null || accountTransferReqDto.getDAccountNumber().isEmpty()) {
            throw new CustomException("입금계좌번호를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (accountTransferReqDto.getWAccountPassword() == null
                || accountTransferReqDto.getWAccountPassword().isEmpty()) {
            throw new CustomException("출금계좌 비밀번호를 입력해주세요", HttpStatus.BAD_REQUEST);
        }

        // 3. 서비스 호출
        int accountId = accountService.계좌이체(accountTransferReqDto, principal.getId());
        return "redirect:/account/" + accountId;
    }

    @PostMapping("/account/deposit") // 입금(atm)
    public String deposit(AccountDepositReqDto accountDepositReqDto) {
        // 인증이 필요없다
        // 1. 유효성 검사
        if (accountDepositReqDto.getAmount() == null) { // 숫자 0 일 수는 있어도 isEmpty(공백) 일 수는 없다
            throw new CustomException("금액을 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (accountDepositReqDto.getAmount().longValue() <= 0) {
            throw new CustomException("출금액이 0원 이하를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (accountDepositReqDto.getDAccountNumber() == null || accountDepositReqDto.getDAccountNumber().isEmpty()) {
            throw new CustomException("계좌번호를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        accountService.계좌입금(accountDepositReqDto);
        return "redirect:/";
    }

    @PostMapping("/account/withdraw") // 출금
    public String withdraw(AccountWithdrawReqDto accountWithdrawReqDto) {
        // 1. atm에서 출금 하기 때문에 인증 필요 없이 유효성 검사만하면 된다
        if (accountWithdrawReqDto.getAmount() == null) { // 숫자 0 일 수는 있어도 isEmpty(공백) 일 수는 없다
            throw new CustomException("금액을 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (accountWithdrawReqDto.getAmount().longValue() <= 0) { // 숫자 0 일 수는 있어도 isEmpty(공백) 일 수는 없다
            throw new CustomException("출금액이 0원 이하를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (accountWithdrawReqDto.getWAccountNumber() == null || accountWithdrawReqDto.getWAccountNumber().isEmpty()) {
            throw new CustomException("계좌번호를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (accountWithdrawReqDto.getWAccountPassword() == null
                || accountWithdrawReqDto.getWAccountPassword().isEmpty()) {
            throw new CustomException("계좌비밀번호를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        int accountId = accountService.계좌출금(accountWithdrawReqDto);

        return "redirect:/account/" + accountId;
        // return "redirect:/account/1";
    }

    @PostMapping("/account")
    public String save(AccountSaveReqDto accountSaveReqDto, int principalId) {
        // 1. 인증
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인을 먼저 해주세요", HttpStatus.UNAUTHORIZED);
        }
        // 2. 유효성 검사
        if (accountSaveReqDto.getNumber() == null || accountSaveReqDto.getNumber().isEmpty()) {
            throw new CustomException("number를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (accountSaveReqDto.getPassword() == null || accountSaveReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        // 3. 서비스에 계좌생성() 호출
        accountService.계좌생성(accountSaveReqDto, principalId);

        return "redirect:/";
    }

    @GetMapping({ "/", "/account" })
    public String main(Model model) { // model에 값을 추가하면 request에 저장된다
        // 1. 인증
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/loginForm";
        }
        List<Account> accountList = accountRepository.findByUserId(principal.getId());
        model.addAttribute("accountList", accountList);
        return "account/main";
    }

    @GetMapping("/account/{id}") // get요청은 body가 없다 => 쿼리스트링
    public String detail(@PathVariable int id, @RequestParam(name = "gubun", defaultValue = "all") String gubun) {
        // 1.인증 (get이라 유효성 검사가 필요없다)

        // 2. 레파지토리 호출 (메서드를 3개 || 마이바티스 동적쿼리 중 선택 )
        return "account/detail";
    }

    @GetMapping("/account/saveForm")
    public String saveForm() {
        // throw new CustomException("인증되지않았습니다", HttpStatus.UNAUTHORIZED);
        return "account/saveForm";
    }

    @GetMapping("/account/withdrawForm")
    public String withdrawForm() {
        return "account/withdrawForm";
    }

    @GetMapping("/account/depositForm")
    public String depositForm() {
        return "account/depositForm";
    }

    @GetMapping("/account/transferForm")
    public String transferForm() {
        throw new CustomException("인증되지않았습니다", HttpStatus.UNAUTHORIZED);
        // return "account/transferForm";
    }

}
