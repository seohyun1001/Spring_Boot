package org.zerock.springboot.service;

import org.zerock.springboot.domain.Board;
import org.zerock.springboot.dto.*;
import org.zerock.springboot.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // DTO를 엔티티로 변환하기
    // boardDTO를 board 객체로 바꾸기 위해 사용하는 메서드(이미지 때문에)
    default Board dtoToEntity(BoardDTO boardDTO) {

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();

        // boardDRO의 String[] 타입을 Set<BoarImage> 타입으로 바꿈
        if (boardDTO.getFileNames() != null) {
            // 반복문으로 String[]의 문자열을 하나씩 변경
            boardDTO.getFileNames().forEach(fileName -> {
                // uuid_파일이름.확장자
                // uuid와 파일이름을 나누고 있음
                // arr[0] : uuid
                // arr[1] : 파일이름.확장자
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
            });
        }
        return board;
    }

    // Board 엔티티를 BoardDTO 타입으로 변환

    default  BoardDTO entityToDto(Board board) {

        // 단순 데이터 설정
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        // Set<boardImage>를 List<String>으로 변환하기 위한 코드
        List<String> fileNames =
                board.getImageSet().stream().sorted().map(boardImage ->
                        // UUID_파일명.확장자 형식으로 boardImage 테이터를 String 타입으로 바꿈
                        boardImage.getUuid() + "_" + boardImage.getFileName())
                        .collect(Collectors.toList());

        boardDTO.setFileNames(fileNames);

        return boardDTO;

    }

}
