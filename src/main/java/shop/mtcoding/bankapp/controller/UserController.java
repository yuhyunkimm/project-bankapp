package shop.mtcoding.bankapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.bankapp.dto.user.JoinReqDto;
import shop.mtcoding.bankapp.dto.user.loginReqDto;
import shop.mtcoding.bankapp.handler.ex.CustomException;
import shop.mtcoding.bankapp.model.user.User;
import shop.mtcoding.bankapp.model.user.UserRepository;
import shop.mtcoding.bankapp.service.UserService;

@Controller
public class UserController {
    /*
     * post요청과 put 요청시에만 body 데이터가 있다
     * 해당 body데이터는 컨트롤러 메서드의 매개변수에 주입된다(DS)
     * 스프링은 x-www-form-urlencoded가 기본 파싱전략
     * key=value&key=value (form태그의 기본 전송 전략 / key= form태그의 name과 동일)
     * 컨트롤러의 메서드는 매개변수에서 두가지 방식으로 데이터를 받는다
     * 1. 그냥 변수 / 2. DTO(Object)
     * 주의 : key이름과 변수이름이 동일해야 한다
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    // select 요청이지만 로그인만 post로 한다(예외)
    @PostMapping("/login")
    public String login(loginReqDto loginReqDto) {
        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요", HttpStatus.BAD_REQUEST);
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password 작성해주세요", HttpStatus.BAD_REQUEST);
        }
        // 레파지토리 호출(조회)
        User principal = userRepository.findByUsernameAndPassword(loginReqDto);
        // controller 기능확인
        // User principal = new User();
        // principal.setId(1);
        // principal.setUsername("ssar");
        if (principal == null) {
            throw new CustomException("아이디 혹은 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
        }

        session.setAttribute("principal", principal);
        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) { // DTO로 받는 것이 좋다
        // 1. post / put 일 때만 유효성 검사(이것보다 우선되는 것이 인증 검사이다)
        // null과 공백처리만 진행
        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요", HttpStatus.BAD_REQUEST);
        }
        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password 작성해주세요", HttpStatus.BAD_REQUEST);
        }
        if (joinReqDto.getFullname() == null || joinReqDto.getFullname().isEmpty()) {
            throw new CustomException("fullname을 작성해주세요", HttpStatus.BAD_REQUEST);
        }
        // 컨벤션 : post / put / delete 할 때만 하기
        // 서비스 호출 => 회원가입();
        userService.회원가입(joinReqDto);

        return "redirect:/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
