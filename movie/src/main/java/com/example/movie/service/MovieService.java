package com.example.movie.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.MovieImageDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;

public interface MovieService {

    PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto);

    // 엔티티 ==> DTO

    // [Movie(mno=100, title=Movie100), MovieImage(inum=295,
    // uuid=6444a6d2-77e1-44d5-b8d9-1959f7e438a6, imgName=img0jpg, path=null), 1,
    // 3.0]

    public default MovieDto entityToDto(List<MovieImage> movieImages, Review review, Movie movie, Long reviewCnt,
            Double avg) {
        MovieDto movieDto = MovieDto.builder()
                .mno(movie.getMno())
                .createdDate(movie.getCreatedDate())
                .lastModifiedDate(movie.getLastModifiedDate())
                .title(movie.getTitle())
                .avg(avg)
                .reviewCnt(reviewCnt)
                .build();

        // 영화 상세 조회 => 이미지를 모두 보여주기

        List<MovieImageDto> movieImageDtos = movieImages.stream().map(movieImage -> {
            return MovieImageDto.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath()).build();
        }).collect(Collectors.toList());

        movieDto.setMovieImageDtos(movieImageDtos);

        return movieDto;

    }

    // dto 를 엔티티로

    public default Map<String, Object> dtoToEntity(MovieDto dto) {

        return null;

    }

}
