package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Memo;

// JUnit : 테스트 라이브러리

@SpringBootTest // 테스트 전용 클래스임을 알려준다
public class MemoRepositoryTest {
    @Autowired // 자바에서는 객체 생성을 안하고는 쓸 수가 없다
    // MemoRepository memoRepository = new MemoRepositoryImpl() 개념이다
    private MemoRepository memoRepository;

    @Test // 테스트 메소드(리턴 타입은 void)임
    public void insertMemo() {
        // 100 개의 메모를 입력
        for (int i = 1; i < 101; i++) {
            Memo memo = new Memo();

            memo.setMemoText("momoText" + i);

            memoRepository.save(memo); // == dao.insert 호출하는 개념
            // .save() 호출은 insert, update 작업을 할때 쓴다

        }

    }

    @Test
    public void getMemo() {
        // 특정 아이디 조회
        Memo memo = new Memo();

        // Optional : null 을 체크 할수 있는 메소드 보유
        // Optional 를 쓰게되면 .get() 으로 가져와야 한다

        Optional<Memo> result = memoRepository.findById(21L);

        System.out.println(result.get());

    }

    @Test
    public void getMemoList() {
        // 특정 아이디 조회
        Memo memo = new Memo();

        // Optional : null 을 체크 할수 있는 메소드 보유
        // Optional 를 쓰게되면 .get() 으로 가져와야 한다

        List<Memo> result = memoRepository.findAll();

        for (Memo memo2 : result) {
            System.out.println(memo2);

        }

    }

    @Test
    public void updateMemo() {

        // 업데이트 할 entity 먼저 찾기
        Optional<Memo> result = memoRepository.findById(25L);

        // 찾은 것을 옵셔널이기에 Memo 객체에 담는다
        Memo memo = result.get();
        // 텍스트 업데이트 셋팅
        memo.setMemoText("Update title");
        // 담고난 것을 저장
        memoRepository.save(memo);
    }

    @Test
    public void deleteMemo() {

        // 엔티티 찾기
        Optional<Memo> result = memoRepository.findById(24L);

        Memo memo = result.get();
        memoRepository.delete(memo);

        System.out.println("삭제 memo " + memoRepository.findById(24L));

        // 삭제 memoOptional.empty 정상처리 되었을떄 나오는 디버그 콘솔 텍스트
        // Optional 형태이기에 정상 사제 진행시 empty로 나온다

    }

    @Test
    public void testJpql() {
        List<Memo> list = memoRepository.findByMnoLessThan(5L);
        list.forEach(System.out::println);

        list = memoRepository.findByMnoLessThanOrderByMnoDesc(10L);
        list.forEach(System.out::println);

        list = memoRepository.findByMnoBetween(50L, 70L);
        list.forEach(System.out::println);

    }

}
