package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.entity.Child;
import com.example.jpa.entity.Parent;

@SpringBootTest
public class ParentRepositoryTest {
    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Test
    public void insertTest() {
        // 부모 한명에 자식 2명

        Parent parent = Parent.builder().name("부모").build();
        parentRepository.save(parent);

        Child child = Child.builder().name("자식 1").build();
        childRepository.save(child);

        Child child1 = Child.builder().name("자식 2").build();
        childRepository.save(child1);

    }

    @Test
    public void insertCascafdeTest() {
        // 부모 한명에 자식 2명
        // Casecade 개념

        Parent parent = Parent.builder().name("부모").build();

        Child child = Child.builder().name("자식 1").build();
        parent.getChildList().add(child);
        // 부모의 리스트에 추가만 해놓았는데
        // 디버그 콘솔에서는 자식의 insert 구문이 등장 했다
        // Cascade 로 인해 영속 상태를 전이 해서 그렇다

        // Cascade : 부모가 영속 상태시 자식도 같이 영속 상대로 감

        // 따로따로 저장을 하지 않더라도 부모의 자식 이 저장된다

        // 이 개념을 알면 부모를 날릴때 자식도 같이 함꼐 날릴 수 있다

        Child child1 = Child.builder().name("자식 2").build();
        parent.getChildList().add(child1);

        parentRepository.save(parent);
        // 리스트 형태로 만들어서 add 한다고 해서 자식이 삽입되지는 않는다

        // childRepository.save(child);
        // childRepository.save(child1);
    }

    @Test
    public void DeleteTest() {
        // 원래 개념은 부모가 자식을 가지고 있을 경우 삭제시 자식의 부모 id 를 변경 후 부모를 삭제를 했었어야 했다
        //

        Parent parent = Parent.builder().id(1L).build();

        // 부모를 null 로 지정해서 날려버리기 첫번쨰 방법

        // Child child = Child.builder().id(1L).parent(null).build();
        // Child child1 = Child.builder().id(2L).parent(null).build();

        // 두번째 방법은 자식을 날리고 부모를 날린다

        Child child = Child.builder().id(52L).build();
        childRepository.delete(child);

        Child child1 = Child.builder().id(53L).build();
        childRepository.delete(child1);

        parentRepository.delete(parent);

    }

    @Test
    public void deleteCascafdeTest() {
        // 부모가 날라갈떄 자식 2명도 같이 날아가는 형태로 만들기

        Parent parent = Parent.builder().id(52L).build();

        Child child = Child.builder().id(102L).build();
        parent.getChildList().add(child);

        Child child1 = Child.builder().id(103L).build();
        parent.getChildList().add(child1);

        parentRepository.delete(parent);
        // 리스트 형태로 만들어서 add 한다고 해서 자식이 삽입되지는 않는다

        // childRepository.save(child);
        // childRepository.save(child1);
    }

    // @Transactional // 이걸로 묶어주면 LAZY 관련 에러가 사라짐 이유는?
    @Test
    public void deleteOrphanTest() {

        // 기존 자식 여부 확인
        Parent parent = parentRepository.findById(102L).get();
        // FetchType 이 LAZY 인 경우 오류 발생

        System.out.println("기존 자식 " + parent.getChildList());

        parent.getChildList().remove(0); // 객체 입장에서보면
        // 인덱스 가 제거가 되면 childList 에서 제거 => 엔티티 제거

        parentRepository.save(parent);

    }

}
