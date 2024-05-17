package org.zerock.springboot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springboot.dto.MemberJoinDTO;
import org.zerock.springboot.service.MemberService;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public void loginGET(String error, String logout) {
        log.info("--------------- login GET ---------------");
        log.info("--------------- error : " + error + "---------------");
        if (logout != null){
            log.info("user logout --------------");
        }
    }

    @GetMapping("/join")
    public void joinGET(){
        log.info("join get ---------------------");
    }

    @PostMapping("/join")
    public String joingPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        log.info("join post --------------------");
        log.info(memberJoinDTO);

        try {
            // 회원가입 서비스 실행
            memberService.join(memberJoinDTO);
            // 기존에 아이디가 존재할 경우 에러 발생
        } catch (MemberService.MIdExistException e) {
            // 에러 발생시 redirect 페이지에 error:mid 값을 가지고 ->
            redirectAttributes.addFlashAttribute("error", "mid");
            // -> member join 페이지로 이동
            return "redirect:/member/join";
        }
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }

}
