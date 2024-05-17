package org.zerock.springboot.service;

import org.zerock.springboot.dto.MemberJoinDTO;

public interface MemberService {

    static class MIdExistException extends Exception {
    }

    void join(MemberJoinDTO memberJoinDTO) throws MIdExistException;

}
