package shop.mtcoding.bankapp.controller;

import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.bankapp.dto.account.AccountSaveReqDto;
import shop.mtcoding.bankapp.handler.ex.CustomException;

@Controller
public class AccountController {

    @Autowired
    private HttpSession session;

    @PostMapping("/account")
    public String save(AccountSaveReqDto accountSaveReqDto) {
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
        return "redirect:/";
    }

    @GetMapping({ "/", "/account" })
    public String main() {
        // throw new CustomException("인증되지 않았습니다", HttpStatus.UNAUTHORIZED);
        return "account/main";
    }

    @GetMapping("/account/{id}")
    public String detail(@PathVariable int id) {
        return "account/detail";
    }

    @GetMapping("/account/saveForm")
    public String saveForm() {
        throw new CustomException("인증되지않았습니다", HttpStatus.UNAUTHORIZED);
        // return "account/saveForm";
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
