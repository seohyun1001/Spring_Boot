package org.zerock.springboot.service;

import org.zerock.springboot.dto.*;

public interface BoardService {
    // insert
    Long register(BoardDTO boardDTO);

    // select
    BoardDTO readOne(Long bno);

    // update
    void modify(BoardDTO boardDTO);

    // delete
    void remove(Long bno);

    // search list
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    // 게시글 목록에 댓글 개수 표시하기
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

    // 게시글의 이미지와 댓글의 숫자까지 처리
    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

}
