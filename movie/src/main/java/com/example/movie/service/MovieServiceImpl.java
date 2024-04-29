package com.example.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;
import com.example.movie.repository.MovieImageRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;
    private final ReviewRepository reviewRepository;

    // Movie(mno=100, title=Movie100), MovieImage(inum=295,
    // uuid=6444a6d2-77e1-44d5-b8d9-1959f7e438a6, imgName=img0jpg, path=null), 1,
    // 3.0
    //

    // 해당방식은 한번에 처리했다 변수 지정 없이
    @Override
    public PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto) {

        Pageable pageable = pageRequestDto.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieImageRepository.getTotalList(pageable);

        Function<Object[], MovieDto> function = (en -> entityToDto((Movie) en[0],
                (List<MovieImage>) Arrays.asList((MovieImage) en[1]),
                (Long) en[2],
                (Double) en[3]));

        return new PageResultDto<>(result, function);

    }

    // [Movie(mno=3, title=Movie3), MovieImage(inum=12,
    // uuid=92814136-b13f-48ce-9e90-22cc1dccf0b2, imgName=img4jpg, path=null), 2,
    // 5.0]

    @Override
    public MovieDto getRow(Long mno) {

        List<Object[]> result = movieImageRepository.getMovieRow(mno);
        // 0번 인덱스에 위치한 정보를 가지고 오라고 하면 [Movie(mno=3, title=Movie3), MovieImage(inum=10,
        // uuid=f7dae041-d4a8-4d1e-bfeb-35fd21c9b8ea, imgName=img2jpg, path=null), 2,
        // 5.0] 이런식으로 나올거다
        // 무비 이미지만 계속 달라지고 있다

        // 이렇게 어차피 반복되는 정보들은 일단 따로 담아준다

        // 근대 문제는 이미지다
        Movie movie = (Movie) result.get(0)[0];

        // result 에 사이즈만큼 반복이 돌아야 한다

        List<MovieImage> movieImages = new ArrayList<>();

        result.forEach(arr -> {
            // arr[1] 은 인덱스 위치에 의거하여 무비이미지가 나온다
            MovieImage movieImage = (MovieImage) arr[1];
            movieImages.add(movieImage);
        });

        Long reviewCnt = (Long) result.get(0)[2];
        Double avg = (Double) result.get(0)[3];

        return entityToDto(movie, movieImages, reviewCnt, avg);

    }

    @Transactional
    @Override
    public void movieRemove(Long mno) {

        Movie movie = Movie.builder().mno(mno).build();

        // 이미지 삭제
        movieImageRepository.deleteMovie(movie);

        // 리뷰 삭제
        reviewRepository.deleteMovie(movie);

        // 영화삭제
        movieRepository.delete(movie);

    }

    @Transactional
    @Override
    public Long movieInsert(MovieDto movieDto) {
        // 영화정보 : title => Movie Entity
        // 이미지 : MovieImage Entity

        Map<String, Object> entityMap = dtoToEntity(movieDto);

        // 무비 삽입
        Movie movie = (Movie) entityMap.get("movie");
        movieRepository.save(movie);

        // 무비 이미지 삽입
        List<MovieImage> movieImages = (List<MovieImage>) entityMap.get("imgList");
        movieImages.forEach(image -> movieImageRepository.save(image));

        // dto ==> entity

        return movie.getMno();

    }

}
