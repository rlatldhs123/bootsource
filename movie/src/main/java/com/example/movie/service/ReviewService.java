package com.example.movie.service;

import java.util.List;

import com.example.movie.dto.ReviewDto;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewService {
    // 특정 영화의 모든 리뷰 가져오기
    List<ReviewDto> getListOfMovie(Long mno);

    // 특정 영화의 리뷰 등록
    Long addReview(ReviewDto reviewDto);

    void removeReview(Long reviewNo);

    ReviewDto getReview(Long reviewNo);

    Long updateReview(ReviewDto reviewDto);

    public default ReviewDto entityToDto(Review review) {
        return ReviewDto.builder()
                .reviewNo(review.getReviewNo())
                .text(review.getText())
                .grade(review.getGrade())
                .createdDate(review.getCreatedDate())
                .lastModifiedDate(review.getLastModifiedDate())
                .mid(review.getMember().getMid())
                .nickname(review.getMember().getNickname())
                .email(review.getMember().getEmail())
                .mno(review.getMovie().getMno())
                .build();
    }

    public default Review dtoToEntity(ReviewDto reviewDto) {
        return Review.builder()
                .reviewNo(reviewDto.getReviewNo())
                .text(reviewDto.getText())
                .grade(reviewDto.getGrade())
                .member(Member.builder().mid(reviewDto.getMid()).build())
                .movie(Movie.builder().mno(reviewDto.getMno()).build())
                .build();
    }
}
