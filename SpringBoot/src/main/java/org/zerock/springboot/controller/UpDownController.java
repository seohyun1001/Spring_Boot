package org.zerock.springboot.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.springboot.dto.upload.UploadFileDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Log4j2
public class UpDownController {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @Tag(name = "Upload POST", description = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(UploadFileDTO uploadFileDTO) {
        log.info(uploadFileDTO);
        // 파일이 있을 경우만 실행
        if (uploadFileDTO.getFiles() != null){
            // 파일의 개수만큼 반복하여 파일 저장
            uploadFileDTO.getFiles().forEach(multipartFile -> {
                // 원본 파일의 이름을 변수에 저장
                String originalName = multipartFile.getOriginalFilename();
                log.info(originalName);
                // UUID(중복되지 않는 아이디)를 변수에 저장
                // -> 파일 이름이 겹치지 않도록 파일이름에 추가하여 설정
                String uuid = UUID.randomUUID().toString();
                // 파일이 저장되는 경로와 파일이름을 함께 설정 하는 코드
                Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

                try {
                    // 파일을 저장하는 transferTo(new File() 이나 Path를 매개변수로 써야 함)
                    multipartFile.transferTo(savePath);
                    // 저장한 파일의 이미지인지 아닌지 확인하는 if문
                    // -> 이미지여야 실행됨
                    if(Files.probeContentType(savePath).startsWith("image")){
                        // 썸네일 파일의 경로 및 파일 이름 설정
                        File thumbFile = new File(uploadPath,"s_" + uuid + "_" + originalName);
                        // 썸네일 파일을 생성하여 저장(원본 위치, 파일, 파일의 크기)
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return null;
    }
    // 파일 여러개는 swagger 3버전에서 불가능 하여 하나만 저장할 수 있는 컨트롤러 만듦
    // -> postman으로 변경



    @Tag(name = "Upload POST2", description = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload2(MultipartFile file) {
        log.info(file);
        if (!file.isEmpty()) {
            log.info(file.getOriginalFilename());
            String originalName = file.getOriginalFilename();
            log.info(originalName);
            String uuid = UUID.randomUUID().toString();
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

            try {
                file.transferTo(savePath);
            }catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }



}
