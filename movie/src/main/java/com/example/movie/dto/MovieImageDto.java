package com.example.movie.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class MovieImageDto {

    private Long inum;

    //

    private String uuid;

    private String imgName;

    private String path;

    public String getImageURL() {
        String fullPath = "";

        try {
            fullPath = URLEncoder.encode(path + "/" + uuid + "_" + imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return fullPath;

    }

    public String getthumbImageURL() {
        String fullPath = "";

        try {
            fullPath = URLEncoder.encode(path + "/small_" + uuid + "_" + imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return fullPath;

    }

}
