package org.zerock.springboot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.springboot.domain.Reply;
import org.zerock.springboot.dto.ReplyDTO;
import org.zerock.springboot.repository.ReplyRepository;

import java.util.Optional;

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
}
