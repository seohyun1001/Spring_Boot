package org.zerock.springboot.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.springboot.domain.Board;
import org.zerock.springboot.domain.Reply;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testinsert() {

        // 더미 데이터 예제
        Long bno = 100L;
        // Reply 클래스에 멤버로 사용될 더미 예제
        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyText("test comment---")
                .replyer("user1")
                .build();

        // 저장하기
        replyRepository.save(reply);

    }

    // 페이징 처리 확인
    @Test
    public void testBoardReplies() {
        // 100번 게시들의 댓글 목록 조회를 페이징 처리하여 조회
        Long bno = 100L;

        Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);
        result.getContent().forEach(reply -> {
            log.info(reply);
        });

    }

}
