package com.example.movie.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.movie.dto.FileUploadReslutDto;

// 일반 컨트롤러 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/upload")
public class UploadController {

    // fetch 함수를 쓸때는 레스트 컨트롤러고 만들었었다

    // 프로포티즈에 설정한 변수 가져오기
    @Value("${com.exaple.upload.path}")
    private String uploadPath;

    @GetMapping("/ex1")
    public void ex1() {

        log.info("업로드 폼 요청");

    }

    // ResponseEntity : 데이터 + 상태코드 데이터 자체를 리턴 하기위한 타입

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<FileUploadReslutDto>> postMethodName(MultipartFile[] uploadFiles) {

        List<FileUploadReslutDto> fileUploadReslutDtos = new ArrayList<>();

        for (MultipartFile multipartFile : uploadFiles) {
            if (!multipartFile.getContentType().startsWith("image")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String oriName = multipartFile.getOriginalFilename();
            String fileName = oriName.substring(oriName.lastIndexOf("\\") + 1);
            log.info("파일 정보 - 전체 경로 : {}", oriName);
            log.info("파일 정보 - 파일명 : {}", fileName);

            // 폴더 생성
            String saveFolderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + saveFolderPath + File.separator + uuid + "_" + fileName;

            // java.nio.path
            Path savePath = Paths.get(saveName);

            try {
                // 이부분이 원본 파일을 저장하는 부분
                multipartFile.transferTo(savePath);

                // 썸네일 파일 저장
                String thSaveName = uploadPath + File.separator + saveFolderPath + File.separator + "small_" + uuid
                        + "_"
                        + fileName;
                File thFile = new File(thSaveName);

                // 썸네일 생성 이미지 크기 지정부분
                Thumbnailator.createThumbnail(savePath.toFile(), thFile, 100, 100);
            } catch (Exception e) {
                e.printStackTrace();

            }

            fileUploadReslutDtos.add(new FileUploadReslutDto(saveFolderPath, uuid, fileName));
        }

        return new ResponseEntity<>(fileUploadReslutDtos, HttpStatus.OK);

    }

    private String makeFolder() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderStr = dateStr.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderStr);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();

        }
        return folderStr;
    }

    // 이미지 전송
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {

        log.info("================= " + fileName);

        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "utf-8");
            // File.separator 는 운영체제에 따라서 붙는 파일 경로 ex) c:\\ or / 같은 구분자를 알아서 넣어준다
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

        String srcFileName = null;

        try {

            srcFileName = URLDecoder.decode(filePath, "utf-8");

            File file = new File(uploadPath + File.separator + srcFileName);
            file.delete(); // 원본 파일 제거

            File thumbFile = new File(file.getParent(), "small_" + file.getName());
            boolean result = thumbFile.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
