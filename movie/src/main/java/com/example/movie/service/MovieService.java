package com.example.movie.service;

import java.util.ArrayList;
import java.util.HashMap;
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
    // 리드
    MovieDto getRow(Long mno);

    // 삭제

    void movieRemove(Long mno);

    // 영화 추가

    Long movieInsert(MovieDto movieDto);

    PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto);

    // 엔티티 ==> DTO

    // [Movie(mno=100, title=Movie100), MovieImage(inum=295,
    // uuid=6444a6d2-77e1-44d5-b8d9-1959f7e438a6, imgName=img0jpg, path=null), 1,
    // 3.0]

    public default MovieDto entityToDto(Movie movie, List<MovieImage> movieImages, Long reviewCnt,
            Double avg) {
        MovieDto movieDto = MovieDto.builder()
                .mno(movie.getMno())
                .createdDate(movie.getCreatedDate())
                .lastModifiedDate(movie.getLastModifiedDate())
                .title(movie.getTitle())
                .avg(avg != null ? avg : 0.0d)
                .reviewCnt(reviewCnt)
                .build();

        // 영화 상세 조회 => 이미지를 모두 보여주기

        // 엔티티 => DTO

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

    // dto => 엔티티로

    public default Map<String, Object> dtoToEntity(MovieDto dto) {
        Map<String, Object> entitMap = new HashMap<>();
        // Movie 엔티티 생성
        Movie movie = Movie.builder()
                .mno(dto.getMno())
                .title(dto.getTitle())
                .build();

        // 생성된 무비 엔티티를 MAP 에 담기 : put()

        entitMap.put("movie", movie);

        // List<MovieImageDto> MovieImageDtos 를

        // List<MOvie > 변환
        List<MovieImageDto> movieImageDtos = dto.getMovieImageDtos();

        if (movieImageDtos != null && movieImageDtos.size() > 0) {

            List<MovieImage> movieImages = movieImageDtos.stream().map(mDto -> {

                MovieImage movieImage = MovieImage.builder()
                        .imgName(mDto.getImgName())
                        .uuid(mDto.getUuid())
                        .path(mDto.getPath())
                        .movie(movie)
                        .build();
                return movieImage;

            }).collect(Collectors.toList());

            entitMap.put("imgList", movieImages);

        }

        // 변환이 끝난 entity list 를 map 담기 : put()

        return entitMap;

    }

}
