package org.zerock.springboot.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.springboot.domain.Board;
import org.zerock.springboot.dto.BoardDTO;
import org.zerock.springboot.dto.PageRequestDTO;
import org.zerock.springboot.dto.PageResponseDTO;

import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class BoardServiceTests {
    @Autowired
    private BoardService boardService;

    // insert
    @Test
    public void testRegister() {
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample Title------")
                .content("Sample Content------")
                .writer("user00")
                .build();
        Long bno = boardService.register(boardDTO);
        log.info("bno : " + bno);
    }

    // update
    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("update title --- 2")
                .content("update content --- 2")
                .build();
        boardService.modify(boardDTO);
    }

    // delete
    @Test
    public void testRemove() {
        boardService.remove(3L);
    }

    //
    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);

    }

    @Test
    public void testRegisterWithImages() {
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("File --- Sample Title")
                .content("Sample Content")
                .writer("user00")
                .build();

        boardDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID() + "_aaa.jpg",
                        UUID.randomUUID() + "_bbb.jpg",
                        UUID.randomUUID() + "_ccc.jpg"
                ));

        Long bno = boardService.register(boardDTO);
        log.info("bno : " + bno);

    }

    @Test
    public  void testReadAll() {
        Long bno = 101L;
        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        for (String fileName :boardDTO.getFileNames()){
            log.info(fileName);
        }
    }

}
