package org.zerock.springboot.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    // 멤버 변수 설정
    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;

    // 생성자
    public MemberSecurityDTO(String username, String password, String email,
                             boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {
        // 부모 클래스의 생성자를 부름
        super(username, password, authorities);

        // 객체 안의 멤버 변수에 각각의 데이터 설정
        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.del = del;
        this.social = social;

    }

}
