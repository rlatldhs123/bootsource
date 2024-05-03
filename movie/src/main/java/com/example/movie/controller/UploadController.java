package com.example.movie.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.movie.dto.UploadResultDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/upload")
@Log4j2
@Controller
public class UploadController {

    // application.properties 설정한 변수 가져오기
    @Value("${com.example.upload.path}")
    private String uploadPath;

    // fetch ==> @RestController

    @GetMapping("/ex1")
    public void getMethodName() {
        log.info("upload form 요청");
    }

    // ResponseEntity : 데이터 + 상태코드

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDto>> postUpload(MultipartFile[] uploadFiles) {

        List<UploadResultDto> uploadResultDtos = new ArrayList<>();

        for (MultipartFile multipartFile : uploadFiles) {

            if (!multipartFile.getContentType().startsWith("image")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String oriName = multipartFile.getOriginalFilename();
            String fileName = oriName.substring(oriName.lastIndexOf("\\") + 1);
            log.info("파일정보 - 전체경로 : {}", oriName);
            log.info("파일정보 - 파일명 : {}", fileName);

            // 폴더 생성
            String saveFolderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + saveFolderPath + File.separator + uuid + "_" + fileName;
            // java.nio.Path
            Path savePath = Paths.get(saveName);
            try {
                // 원본 파일 저장
                multipartFile.transferTo(savePath);

                // 썸네일 파일 저장
                String thumbSaveName = uploadPath + File.separator + saveFolderPath + File.separator + "s_" + uuid + "_"
                        + fileName;
                File thumbFile = new File(thumbSaveName);
                // 썸네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 100, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 저장된 파일 정보 객체 생성 후 리스트에 추가
            uploadResultDtos.add(new UploadResultDto(saveFolderPath, uuid, fileName));
        }
        return new ResponseEntity<>(uploadResultDtos, HttpStatus.OK);
    }

    private String makeFolder() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderStr = dateStr.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderStr);
        if (!uploadPathFolder.exists())
            uploadPathFolder.mkdirs();
        return folderStr;
    }

    // 이미지 전송
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "utf-8");

            // File.separator : 운영체제에 따라 /, \
            // c:\\upload\\srcFileName
            File file = new File(uploadPath + File.separator + srcFileName);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/remove")
    public ResponseEntity<Boolean> postMethodName(String filePath) {
        log.info("파일 삭제 요청 {}", filePath);

        // 2024%5C04%5C29%2F2238807f-3897-4245-972e-5677cfe30b1e_inception2.jpg

        String srcFileName = null;

        try {
            srcFileName = URLDecoder.decode(filePath, "utf-8");
            // File.separator : 운영체제에 따라 /, \
            // c:\\upload\\srcFileName
            File file = new File(uploadPath + File.separator + srcFileName);
            file.delete(); // 원본 파일 제거

            File thumbFile = new File(file.getParent(), "s_" + file.getName());
            boolean result = thumbFile.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
