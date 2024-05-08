package org.zerock.springboot.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.springboot.dto.upload.UploadFileDTO;

@RestController
@Log4j2
public class UpDownController {

//    @Tag(name = "Upload POST", description = "POST 방식으로 파일 등록")
//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public String upload(UploadFileDTO uploadFileDTO) {
//        log.info(uploadFileDTO);
//        return null;
//    }
    // 파일 여러개는 swagger 3버전에서 불가능 하여 하나만 저장할 수 있는 컨트롤러 만듦

    @Tag(name = "Upload POST", description = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(MultipartFile file) {
        log.info(file);
        return null;
    }



}
