// package com.example.jpa.repository;

// import java.util.stream.LongStream;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.example.jpa.entity.Locker;
// import com.example.jpa.entity.SportsMember;

// @SpringBootTest
// public class LockRepositoryTest {

//     @Autowired
//     private SportsMemberRepository sportsMemberRepository;
//     @Autowired
//     private LockRepository lockRepository;

//     @Test
//     public void insertTest() {
//         // Locker 삽입

//         LongStream.rangeClosed(1, 4).forEach(i -> {
//             Locker locker = Locker.builder().name("Locker" + i).build();
//             lockRepository.save(locker);
//         });

//         LongStream.rangeClosed(1, 4).forEach(i -> {
//             SportsMember sportsMember = SportsMember.builder().name("user" + i).locker(Locker.builder().id(i).build())
//                     .build();
//             sportsMemberRepository.save(sportsMember);
//         });
//     }

//     // 회원 조회

//     @Test
//     public void readTest() {
//         // 회원 조회후 locker 정보 조회

//         SportsMember sportsMember = sportsMemberRepository.findById(1L).get();

//         System.out.println("락커명 :" + sportsMember.getLocker().getName());
//         System.out.println("라커번호 :" + sportsMember.getLocker().getId());

//     }

//     @Test
//     public void readTest2() {
//         // 회원 조회후 locker 정보 조회

//         Locker locker = lockRepository.findById(2L).get();

//         System.out.println("회원 이름 :" + locker.getSportsMember().getName());
//         System.out.println("회원 아이디 :" + locker.getSportsMember().getId());

//     }

//     @Test
//     public void updateTest() {
//         SportsMember sportsMember = sportsMemberRepository.findById(1L).get();
//         sportsMember.setName("홍길동");
//         sportsMemberRepository.save(sportsMember);
//     }

// }
