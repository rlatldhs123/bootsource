package com.example.movie.repository.total;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieImageReviewRepository {
    // 전체 리스트 가져오기
    Page<Object[]> getTotalList(Pageable pageable);

    // 특정 영화 조회
    List<Object[]> getMovieRow(Long mno);

}
