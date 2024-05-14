package org.zerock.springboot.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
//@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;
    public CustomUserDetailsService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load User By Username : " + username);

        // 반환할 UserDetails 생성하기
        UserDetails userDetails = User.builder()
                .username("user1")
//                .password("1111")
                .password(passwordEncoder.encode("1111")) // 패스워드 인코딩 필요
                .authorities("ROLE_USER")
                .build();

        return userDetails;
        // -> 이 코드만 추가한 후 실행하면 password encoder가 없어서 오류가 남
    }
}
