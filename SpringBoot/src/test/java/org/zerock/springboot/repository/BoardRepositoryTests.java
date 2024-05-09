package org.zerock.springboot.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.springboot.domain.Board;
import org.zerock.springboot.domain.BoardImage;
import org.zerock.springboot.dto.BoardListReplyCountDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {
        IntStream.range(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer("user" + i)
                    .build();
            Board result = boardRepository.save(board);
            log.info("BNO : " + result.getBno());
        });
    }

    @Test
    public void testSelect() {
        Long bno = 99L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info(board);
    }

    @Test
    public void testUpdate() {
        Long bno = 99L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.change("update title 99", "update content 99");
        boardRepository.save(board);
    }

    @Test
    public void testDelete() {
        Long bno = 1L;
        boardRepository.deleteById(bno);
    }



    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(5, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        log.info("total count : " + result.getTotalElements());
        log.info("total pages : " + result.getTotalPages());
        log.info("page number : " + result.getNumber());
        log.info("page size : " + result.getSize());
        List<Board> todoList = result.getContent();
        todoList.forEach(board -> log.info(board));
    }

    @Test
    public void testSearch1(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        boardRepository.search1(pageable);
    }

    @Test
    public void testSelectAll() {
        // 동적 WHERE절을 위한 조건식 값 설정하기 (title, content, writer)
        String[] types = {"t", "c", "w"};
        // 검색할 문자열 저장
        String keyword = "1";
        // 몇 개의 데이터를 어떤 정렬로 검색할지 설정
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        // 위의 조건식으로 데이터베이스에서 조회하는 레포지토리 실행
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        log.info(result.getTotalPages());
        log.info(result.getSize());
        log.info(result.getNumber());
        // hasPrevious : 이전 페이지가 있는지
        // hasNext : 다음 페이지가 있는지
        log.info(result.hasPrevious() + " : " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    // 게시글 조회시 해당 잿글의 개수도 같이 조회하기
    @Test
    public void testSearchReplyCount() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        log.info(result.getTotalPages());
        log.info(result.getSize());
        log.info(result.getNumber());

        log.info(result.hasPrevious() + " : " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));

    }

    @Test
    public void testInsertWithImages() {
        Board board = Board.builder()
                .title("Image Test")
                .content("첨부파일 테스트")
                .writer("tester")
                .build();

        for (int i = 0; i < 3; i++){
            board.addImage(UUID.randomUUID().toString(), "file" + i + ".jpg");
        }
        boardRepository.save(board);
    }

    @Test
    public void testReadWithImages() {
        // PK를 이용해 게시글 select 실행
        Optional<Board> result = boardRepository.findByIdWithImages(1L);
        // 데이터가 없는 경우 에러발생
        Board board = result.orElseThrow();

        log.info(board);
        log.info("---------------------------------");
//        log.info(board.getImageSet());
        // -> 데이터베이스와 연결이 끝난 상태이므로 'no session'이라는 에러 메세지가 뜬다
        // -> 해결방법 : @Transactional을 추가함 -> 커밋이 되기 전까지 실행한 쿼리를 하나로 인식함

        for (BoardImage boardImage : board.getImageSet()){ // list를 log로 출력
            log.info(boardImage);
        }
    }

    @Transactional
    @Commit
    @Test
    public void testModifyImages() {
        Optional<Board> result = boardRepository.findByIdWithImages(1L);

        Board board = result.orElseThrow();

        // 기존의 첨부파일 삭제
        board.clearImages();

        // 새로운 첨부파일들
        for (int i = 0; i < 2; i++){
            board.addImage(UUID.randomUUID().toString(), "updateFile" + i + ".jpg");
        }
        boardRepository.save(board);
        // 결과 -> 현재 cascade 속성이 all로 지정되었기 때문에 상위 엔티티(board)의 상태 변화가
        // 하위 엔티티(boardImage)까지 영향(bno가 null로 변함)을 주기는 했지만 삭제 되지는 않음
        // 만일 하위 엔티티의 참조가 더 이상 없는 상태가 되면
        // @OneToMany에 orphan-Removal 속성 값을 true로 지정해주어야 실제 삭제가 이루어짐
        // -> Board 클래스의 @OneToMany 속성을 조정 (orphanRemoval = true)
    }

}
