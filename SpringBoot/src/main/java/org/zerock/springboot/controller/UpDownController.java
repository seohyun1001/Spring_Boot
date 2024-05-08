package org.zerock.springboot.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.springboot.dto.upload.UploadFileDTO;

@RestController
@Log4j2
public class UpDownController {

    @Tag(name = "Upload POST", description = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(UploadFileDTO uploadFileDTO) {
        log.info(uploadFileDTO);
        return null;
    }

}
