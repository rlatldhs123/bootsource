package com.example.movie.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // DELETE FROM MOVIE_IMAGE mi WHERE movie_mno=1; ==> 메소드 생성필요
    // delele(), deleteById() ==> Review 의 review_no 기준임
    @Modifying
    @Query("delete from Review r where r.movie = :movie")
    void deleteByMovie(Movie movie);

    // movie 번호를 이용해 리뷰 가져오기
    // 이 메소드 실행 시 join 구문으로 처리해줘
    @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    // 회원탈퇴
    // 회원 탈퇴시 이 회원이 작성한 리뷰도 같이 삭제를 원함
    // void deleteByMember(Member member); // 이 방식대로만 놔두면 review_no 를 기준을 동작하기에 리뷰작성
    // 횟수만큼 딜리트 구문이 실행되어 효율이 떨어짐
    @Modifying
    @Query("delete from Review r where r.member =:member") // 리뷰 엔티티와 멤버와 넘겨 받은 멤버가 일치하면 삭제 해줘
    void deleteByMember(Member member);

}
