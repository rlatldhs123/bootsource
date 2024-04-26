package com.example.movie.repository.total;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.QMovie;
import com.example.movie.entity.QMovieImage;
import com.example.movie.entity.QReview;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2

public class MovieImageReviewRepositoryImpl extends QuerydslRepositorySupport implements MovieImageReviewRepository {

    public MovieImageReviewRepositoryImpl() {
        super(MovieImage.class);

    }

    @Override
    public Page<Object[]> getTotalList(Pageable pageable) {
        log.info("=======  Querydsl getTotalList ====== ");

        // Q 클래스 가져오기

        QMovie movie = QMovie.movie;
        QReview review = QReview.review;
        QMovieImage movieImage = QMovieImage.movieImage;

        // 기준이 되는 클래그 from

        JPQLQuery<MovieImage> query = from(movieImage);
        // 무비 이미지와 무비 가 같으면 조인
        query.leftJoin(movie).on(movieImage.movie.eq(movie));

        // 서브 쿼리는 from 절 사용을 못함

        JPQLQuery<Tuple> tuple = query.select(movie, movieImage,
                JPAExpressions.select(review.countDistinct()).from(review).where(review.movie.eq(movieImage.movie)),
                JPAExpressions.select(review.grade.avg().round()).from(review)
                        .where(review.movie.eq(movieImage.movie)))
                .where(movieImage.inum
                        .in(JPAExpressions.select(movieImage.inum.min()).from(movieImage).groupBy(movieImage.movie)));
        // .orderBy(movie.mno.desc());

        // .orderBy(movie.mno.desc()); == 동일 한 역할 코드를 유연하게 하기 위해
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Movie> orderbyExpression = new PathBuilder<>(Movie.class, "movie");
            tuple.orderBy(new OrderSpecifier(direction, orderbyExpression.get(prop)));

        });

        // 페이지 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        long count = tuple.fetchCount();

        return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);

    }

    @Override
    public List<Object[]> getMovieRow(Long mno) {

        QMovie movie = QMovie.movie;
        QReview review = QReview.review;
        QMovieImage movieImage = QMovieImage.movieImage;

        // 기준이 되는 클래그 from

        JPQLQuery<MovieImage> query = from(movieImage);
        // 무비 이미지와 무비 가 같으면 조인
        query.leftJoin(movie).on(movieImage.movie.eq(movie));

        // 서브 쿼리는 from 절 사용을 못함

        JPQLQuery<Tuple> tuple = query.select(movie, movieImage,
                JPAExpressions.select(review.countDistinct()).from(review).where(review.movie.eq(movieImage.movie)),
                JPAExpressions.select(review.grade.avg().round()).from(review)
                        .where(review.movie.eq(movieImage.movie)))
                .where(movieImage.movie.mno.eq(mno))
                .orderBy(movieImage.inum.desc());

        List<Tuple> result = tuple.fetch();

        return result.stream().map(t -> t.toArray()).collect(Collectors.toList());

    }

}
