package org.zerock.springboot.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.springboot.domain.Board;
import org.zerock.springboot.dto.BoardListReplyCountDTO;

public interface BoardSearch {
    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    // 댓글 표시해서 목록 출력
    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

    // N + 1 문제
    Page<BoardListReplyCountDTO> searchWithAll(String[] types, String keyword, Pageable pageable);
}
