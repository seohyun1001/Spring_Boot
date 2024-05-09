package org.zerock.springboot.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.springboot.domain.Board;
import org.zerock.springboot.domain.QBoard;
import org.zerock.springboot.domain.QReply;
import org.zerock.springboot.dto.BoardListReplyCountDTO;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable){
        // queryDSL을 이용한 객체 생성
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        // JPQL을 사용한 WHERE 메서드로 조건식 추가
        query.where(board.title.contains("1"));

        // pageable 설정
        this.getQuerydsl().applyPagination(pageable, query);
        // 위에 설정한 조건대로 데이터를 조회
        List<Board> list = query.fetch();
        // 페이지수, 총 행수, 총 페이지수 데이터 조회
        long count = query.fetchCount();
        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable){
        // queryDSL을 이용한 객체 생성
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        // 검색 조건인 types와 keyword가 존재하는지 확인하는 if문
        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
                    case "t":
                        // OR title LIKE '%keyword%' : SQL 작성
                        booleanBuilder.or(board.title.contains(keyword));
                        break;

                        // OR content LIKE '%keyword%'
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;

                        // OR writer LIKE '%keyword%'
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            // 실행할 쿼리문에 types, keyword 조건절 추가
            query.where(booleanBuilder);
        }
        // gt : ~보다 크다
        // AND bno > 0 : SQL 추가
        query.where(board.bno.gt(0L));
        // ORDER BY bno DESC limit 0,10 : 정렬 및 리미트 SQL 추가
        this.getQuerydsl().applyPagination(pageable, query);

        // SQL 조회 및 실행
        List<Board> list = query.fetch();
        // count 관련 SQL 실행
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        // QueryDSL 의 QBoard 사용하기
        // Query 애너테이션 안에 문자열, sql 문법을 사용시, 컴파일 체크가 안됨
        // 따라서  QueryDSL 동적으로 사용하면 자바 문법 형식으로 데이터 베이스 타입 변환이 쉬움
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        // left out join 처리
        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));

        query.groupBy(board);

        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;

                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;

                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        query.where(board.bno.gt(0L));

        // projection이라는 도구를 이용해서 엔티티 클래스를 DTO 타입으로 자동 형변환해줌
        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.bean(BoardListReplyCountDTO.class,
                board.bno,
                board.title,
                board.writer,
                board.regDate,
                reply.count().as("replyCount")
        ));

        this.getQuerydsl().applyPagination(pageable, dtoQuery);
        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();
        return new PageImpl<>(dtoList, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> boardJPQLQuery = from(board);
        boardJPQLQuery.leftJoin(reply).on(reply.board.eq(board)); // Left Join

        getQuerydsl().applyPagination(pageable, boardJPQLQuery); // paging

        List<Board> boardList = boardJPQLQuery.fetch();

        boardList.forEach(board1 -> {
            System.out.println(board1.getBno());
            System.out.println(board1.getImageSet());
            System.out.println("---------------------------------------------");
        });
        return null;
    }


}
