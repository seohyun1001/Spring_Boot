package org.zerock.springboot.service;

import org.zerock.springboot.dto.BoardDTO;

public interface BoardService {
    // insert
    Long register(BoardDTO boardDTO);

    // select
    BoardDTO readOne(Long bno);

    // update
    void modify(BoardDTO boardDTO);

    // delete
    void remove(Long bno);
}
