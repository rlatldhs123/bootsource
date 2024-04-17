package com.example.board.repository.search;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.entity.Board;
import com.example.board.entity.QBoard;
import com.example.board.entity.QMember;
import com.example.board.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2

public class SearchBoardRepsitoryImpl extends QuerydslRepositorySupport implements SearchBoardRepsitory {

    public SearchBoardRepsitoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pageable) {
        log.info("Board + REply + Member join");

        // Q클래스 사용
        // 일단 부르기
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // @Query("select b,m from board b left join b.writer m")
        JPQLQuery<Board> query = from(board);

        // 조인
        query.leftJoin(board.writer, member);
        // 서브 쿼리 => JPAExpressions
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count().as("replycnt"))
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);

        // 검색
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(board.bno.gt(0L));

        // type , keyword 가져오기

        // gno > 0 좀더 구동을 빨리 하기위해 썼음

        // 검색 타입이 있는 경우
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(board.title.contains(keyword));

        }
        if (type.contains("c")) {
            conditionBuilder.or(board.content.contains(keyword));

        }
        if (type.contains("w")) {
            conditionBuilder.or(member.email.contains(keyword));

        }
        builder.and(conditionBuilder);
        tuple.where(builder);

        // 페이지 나누기 -
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Board> orderbyExpression = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderbyExpression.get(prop)));

        });
        // 페이지 처리 하는 부분

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        // 전체 개수

        long count = tuple.fetchCount();

        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return new PageImpl<>(list, pageable, count);

        // // 튜플 형태의리스트로 바꿈
        // List<Tuple> result = tuple.fetch();
        // // 다시한번 바꿔준다 오브젝트를 담는 리스트의 형태로
        // List<Object[]> list = result.stream().map(t ->
        // t.toArray()).collect(Collectors.toList());

        // return list;

    }

    @Override
    public Object[] getRow(Long bno) {
        // Q클래스 사용
        // 일단 부르기
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // @Query("select b,m from board b left join b.writer m")
        JPQLQuery<Board> query = from(board);

        // 조인
        query.leftJoin(board.writer, member);
        query.where(board.bno.eq(bno));
        // 서브 쿼리 => JPAExpressions
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count().as("replycnt"))
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);
        // 튜플 형태의리스트로 바꿈
        Tuple result = tuple.fetch().get(0);
        // 다시한번 바꿔준다 오브젝트를 담는 리스트의 형태로

        return result.toArray();

    }

}
