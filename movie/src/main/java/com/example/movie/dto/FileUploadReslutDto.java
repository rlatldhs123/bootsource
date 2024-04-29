package com.example.movie.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

// Serializable : 객체 상대로 입출력 하기위해 필요함
public class FileUploadReslutDto implements Serializable {

    // v폴더, uuid ,실제 파일 명

    private String folderPath;
    private String uuid;
    private String fileName;

    public String getImageURL() {
        String fullPath = "";

        try {
            fullPath = URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return fullPath;

    }

    public String getthumbImageURL() {
        String fullPath = "";

        try {
            fullPath = URLEncoder.encode(folderPath + "/small_" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return fullPath;

    }

}
