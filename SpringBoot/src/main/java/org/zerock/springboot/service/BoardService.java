package org.zerock.springboot.service;

import org.zerock.springboot.dto.BoardDTO;

public interface BoardService {
    Long register(BoardDTO boardDTO);
}
