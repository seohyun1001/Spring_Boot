package org.zerock.springboot.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.springboot.domain.Member;
import org.zerock.springboot.domain.MemberRole;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 일반 회원 추가 테스트
    @Test
    public void insertMembers() {
        // 1~100 반복문
        IntStream.rangeClosed(1, 100).forEach(i -> {
            // 회원 데이터 생성
            Member member = Member.builder()
                    .mid("member" + i)
                    // 비밀번호 암호화
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email" + i + "@aaa.bbb")
                    .build();

            // 일반 회원의 권한 설정
            member.addRole(MemberRole.USER);

            // 관리자 권한 설정
            if (i >= 90) {
                member.addRole(MemberRole.ADMIN);
            }

            // 데이터가 없으면 insert / 데이터가 있으면 update 역할
            memberRepository.save(member);
        });
    }

}
