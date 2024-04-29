package org.zerock.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.springboot.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // 기본적인 crud 확인이 가능
    // 페이징 처리를 위해 jpql 문법 사용
    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> listOfBoard(Long bno, Pageable pageable);



}
