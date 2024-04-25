package org.zerock.springboot.service;

import org.zerock.springboot.dto.BoardDTO;
import org.zerock.springboot.dto.PageRequestDTO;
import org.zerock.springboot.dto.PageResponseDTO;

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
}