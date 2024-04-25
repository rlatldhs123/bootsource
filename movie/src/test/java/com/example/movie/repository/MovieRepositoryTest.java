package com.example.movie.repository;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.movie.constant.MemberRole;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;

@SpringBootTest

public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Test
    @DisplayName("영화랑 영화 이미지 샘플 데이터 추가 100개")
    public void MovieInsertTest() {
        // 영화 / 영화 이미지 샘플 데이터 추가

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("Movie" + i)
                    .build();
            movieRepository.save(movie);

            int count = (int) (Math.random() * 5) + 1;

            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("img" + j + "jpg")
                        .build();

                movieImageRepository.save(movieImage);

            }
        });

    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("멤버100명 밀어넣기")
    @Test
    public void MemberInsetTest() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("mem" + i + "@naver.com")
                    .password(passwordEncoder.encode("1111"))
                    .nickname("reviewer" + i)
                    .role(MemberRole.MEMBER)
                    .build();
            memberRepository.save(member);

        });

    }

    @DisplayName("리뷰200개 밀어넣기")
    @Test
    public void reviewInsetTest() {

        IntStream.rangeClosed(1, 200).forEach(i -> {

            Long mno = (long) (Math.random() * 100) + 1;
            Movie movie = Movie.builder().mno(mno).build();

            Long mid = (long) (Math.random() * 100) + 1;
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .movie(movie)
                    .member(member)
                    .grade((int) (Math.random() * 5) + 1)
                    .text("이영화에 대한 " + i)
                    .build();

            reviewRepository.save(review);

        });

    }

    @Test
    public void movieListTest() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Object[]> list = movieRepository.getListPage(pageRequest);

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    // @Test
    // public void movieListTest2() {

    // PageRequest pageRequest = PageRequest.of(0, 10);
    // Page<Object[]> list = movieRepository.getListPage2(pageRequest);

    // for (Object[] objects : list) {
    // System.out.println(Arrays.toString(objects));
    // }
    // }

    @Test
    public void movieImageListTest() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Object[]> list = movieImageRepository.getTotalList(pageRequest);

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }

    }
}
