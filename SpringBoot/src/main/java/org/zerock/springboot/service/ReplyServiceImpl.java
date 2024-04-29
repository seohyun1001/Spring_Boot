package org.zerock.springboot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.springboot.domain.Reply;
import org.zerock.springboot.dto.PageRequestDTO;
import org.zerock.springboot.dto.PageResponseDTO;
import org.zerock.springboot.dto.ReplyDTO;
import org.zerock.springboot.repository.ReplyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{
    // 의존성 주입
    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDTO replyDTO) {
        // replyDTO -> entity 클래스 타입으로 변환
        Reply reply = modelMapper.map(replyDTO, Reply.class);
        Long rno = replyRepository.save(reply).getRno();

        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {
        // 댓글 번호로 찾기, 반환 타입은 optional
        Optional<Reply> replyOptional = replyRepository.findById(rno);
        // optional 타입에서 entity 클래스 타입으로 변환
        Reply reply = replyOptional.orElseThrow();
        // entity 클래스 타입을 DTO 데이터 전달용으로 사용하는 타입으로 변환
        return modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {

        Optional<Reply> replyOptional = replyRepository.findById(replyDTO.getRno());
        Reply reply = replyOptional.orElseThrow();
        reply.changeText(replyDTO.getReplyText());
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {
        // PageRequestDTO : 화면에서 백엔드로 보내는 페이징 정보가 들어있다. 현재 페이지, 크기 등
        // 페이징 하기 위한 준비물
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        // 페이징 처리가 된 댓글 목록 조회
        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);
        // 스트림 병렬 처리하여, 여러개 댓글의 내용을 타입 변환하기 엔티티 -> DTO -> list
        List<ReplyDTO> dtoList = result.getContent().stream().map(reply -> modelMapper.map(reply, ReplyDTO.class)).collect(Collectors.toList());

        // 서버에서 페이지 처리하고 화면으로 전달 해주기
        // PageResponseDTO로 응답
        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
