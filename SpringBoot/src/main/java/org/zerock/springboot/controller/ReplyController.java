package org.zerock.springboot.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.springboot.dto.ReplyDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
public class ReplyController {

    @Tag(name = "Replies POST", description = "POST 방식으로 댓글 등록")
    // @ApiOperation(value, notes) 대신에 Tag 씀
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    // ResponseEntity : 데이터를 http 상태 코드와 같이 앞단에 전달하기
    public ResponseEntity<Map<String, Long>> register(@Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult) throws BindException {
        log.info(replyDTO);

        // 오류 및 유효성 체크 등, 오류 발생시 에러 표시
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", 123L);

        return ResponseEntity.ok(resultMap);
    }

}
