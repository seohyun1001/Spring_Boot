package org.zerock.springboot.service;

import org.zerock.springboot.dto.PageRequestDTO;
import org.zerock.springboot.dto.PageResponseDTO;
import org.zerock.springboot.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    // 페이징 처리
    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);

}
