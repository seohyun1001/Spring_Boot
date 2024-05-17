package org.zerock.springboot.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.springboot.domain.Member;
import org.zerock.springboot.repository.MemberRepository;
import org.zerock.springboot.security.dto.MemberSecurityDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

//    private PasswordEncoder passwordEncoder;
//    public CustomUserDetailsService() {
//        this.passwordEncoder = new BCryptPasswordEncoder();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load User By Username : " + username);

        // 반환할 UserDetails 생성하기
//        UserDetails userDetails = User.builder()
//                .username(username)
////                .password("1111")
//                .password(passwordEncoder.encode("1111")) // 패스워드 인코딩 필요
//                .authorities("ROLE_USER")
//                .build();
//
//        return userDetails;
        // -> 이 코드만 추가한 후 실행하면 password encoder가 없어서 오류가 남
        // 240517 위의 코드 주석처리



        // 데이터베이스에 username으로 검색한 회원 정보를 취득
        Optional<Member> result = memberRepository.getWithRoles(username);

        // 데이터가 있는지 확인하는 if문
        if (result.isEmpty()) { // 해당 아이디를 가진 사용자가 없다면
            throw  new UsernameNotFoundException(("user name not found------------"));
        }

        Member member = result.get();

        // Member 객체를 MemberSecurityDTO 객체로 변환 
        MemberSecurityDTO memberSecurityDTO =
                new MemberSecurityDTO(
                        member.getMid(),
                        member.getMpw(),
                        member.getEmail(),
                        member.isDel(),
                        false,
                        // 결과물 : ROLE_USER , ROLE_ADMIN
                        member.getRoleSet()
                                .stream()
                                .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                        .collect(Collectors.toList())
                );

        log.info("memberSecurityDTO");
        log.info(memberSecurityDTO);

        return  memberSecurityDTO;
    }
}
