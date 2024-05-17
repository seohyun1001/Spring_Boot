package org.zerock.springboot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.springboot.domain.Member;
import org.zerock.springboot.domain.MemberRole;
import org.zerock.springboot.dto.MemberJoinDTO;
import org.zerock.springboot.repository.MemberRepository;


@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MIdExistException {

        String mid = memberJoinDTO.getMid();
        boolean exist = memberRepository.existsById(mid);

        if (exist) {
            throw new MIdExistException();
        }

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        member.addRole(MemberRole.USER);

        log.info("==========================================");
        log.info(member);
        log.info(member.getRoleSet());

        memberRepository.save(member);
    }
}
