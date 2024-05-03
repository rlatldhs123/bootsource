package com.example.movie.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.example.movie.constant.MemberRole;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;

import groovyjarjarantlr4.v4.parse.ANTLRParser.blockSuffix_return;
import jakarta.transaction.Transactional;

@SpringBootTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieImageRepository movieImageRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void movieInsertTest() {
        // 영화 / 영화이미지 샘플 데이터 추가
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("Movie" + i)
                    .build();
            movieRepository.save(movie);

            int count = (int) (Math.random() * 5) + 1;

            for (int j = 0; j < count; j++) {
                MovieImage mImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("img" + j + ".jpg")
                        .build();
                movieImageRepository.save(mImage);
            }
        });
    }

    @Test
    public void memberInsertTest() {
        // 멤버 샘플 데이터 추가
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("mem" + i + "@naver.com")
                    .password(passwordEncoder.encode("1111"))
                    .role(MemberRole.MEMBER)
                    .nickname("reviewer" + i)
                    .build();
            memberRepository.save(member);
        });

        // Member member = Member.builder()
        // .email("admin1@naver.com")
        // .password(passwordEncoder.encode("1111"))
        // .role(MemberRole.ADMIN)
        // .nickname("admin1")
        // .build();
        // memberRepository.save(member);
    }

    @Test
    public void reviewInsertTest() {
        // 리뷰 샘플 데이터 추가
        IntStream.rangeClosed(1, 200).forEach(i -> {

            Long mno = (long) (Math.random() * 100) + 1;
            Movie movie = Movie.builder().mno(mno).build();

            Long mid = (long) (Math.random() * 100) + 1;
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .movie(movie)
                    .member(member)
                    .grade((int) (Math.random() * 5) + 1)
                    .text("이 영화에 대한.." + i)
                    .build();
            reviewRepository.save(review);
        });
    }

    @Test
    public void movieListTest() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> list = movieRepository.getListPage(pageRequest);

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void movieImageListTest() {

        PageRequestDto requestDto = PageRequestDto.builder()
                .type("t")
                .keyword("Movie")
                .page(1)
                .size(10)
                .build();

        Page<Object[]> list = movieImageRepository.getTotalList(requestDto.getType(), requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("mno").descending()));

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void movieGetTest() {
        List<Object[]> result = movieImageRepository.getMovieRow(3L);

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Transactional
    @Test
    public void movieRemoveTest() {
        Movie movie = Movie.builder().mno(1L).build();

        // 이미지 삭제
        movieImageRepository.deleteByMovie(movie);

        // 리뷰 삭제
        reviewRepository.deleteByMovie(movie);

        // 영화 삭제
        movieRepository.delete(movie);
    }

    // @Transactional
    @Test
    public void testFindReviews() {
        Movie movie = Movie.builder().mno(3L).build();
        List<Review> reviews = reviewRepository.findByMovie(movie);

        // LazyInitializationException: could not initialize proxy
        // fetch = FetchType.LAZY : select review table 만 실행
        // 3번 영화에 리뷰4개라면
        // select 구문이 4번 각각 실행됨

        reviews.forEach(review -> {
            System.out.println(review);
            System.out.println(review.getMember().getEmail());
            System.out.println(review.getMember().getNickname());
        });
    }

    @Commit // 여기서 테스트하지만 반영까지 해달라는 어노테이션
    @Transactional
    @Test
    public void deleteByMemberTest() {
        Member member = Member.builder()
                .mid(6L).build();

        reviewRepository.deleteByMember(member);

        // 회원삭제

        memberRepository.delete(member);
    }
}
