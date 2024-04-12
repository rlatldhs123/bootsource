package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Adderess;
import com.example.jpa.entity.Member;
import com.example.jpa.entity.Member2;
import com.example.jpa.entity.Order;
import com.example.jpa.entity.Product;
import com.example.jpa.entity.Team2;

@SpringBootTest
public class JpqlRepositoryTest {

    @Autowired
    private Team2Repository team2Repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Member2Repository member2Repository;

    @Test
    void insertTest() {

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Team2 team2 = Team2.builder().name("team" + i).build();
            team2Repository.save(team2);

            Product product = Product.builder().name("product" + i).price(i * 10000).stockAmount(i * 5).build();
            productRepository.save(product);

            Member2 member2 = Member2.builder().userName("user" + i).age(i).team2(team2).build();
            member2Repository.save(member2);

        });
    }

    @Test
    public void orderInsertTest() {
        Adderess address = new Adderess();
        address.setCity("서울시 종로구");
        address.setStreet("151-11");
        address.setZipcode("11017");

        IntStream.rangeClosed(1, 3).forEach(i -> {
            Member2 member2 = Member2.builder().id(1L).build();
            Product product = Product.builder().id(2L).build();

            Order order = Order.builder()

                    .member2(member2)
                    .product(product)
                    .homeAddress(address)
                    .build();
            orderRepository.save(order);

        });
    }

    @Test
    public void findMembersTest() {
        // member2Repository.findbyMembers().forEach(member -> {
        // System.out.println(member);
        // });

        List<Object[]> list = member2Repository.findbyMembers2();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects)); // [User1, 1]
            System.out.printf("username=$s, age = %d\n", objects[0], objects[1]);

        }

    }

    @Test
    public void findByHomeAddressTest() {
        System.out.println(orderRepository.findByHomeAddress());

    }

    @Test
    public void findByOrdersTest() {
        List<Object[]> list = orderRepository.findByOrders();

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));

            // [Member2(id=1, userName=user1, age=1),
            // com.example.jpa.entity.Product@5cbb2b1d, 1]

            Member2 member2 = (Member2) objects[0];
            Product product = (Product) objects[1];
            Long id = (Long) objects[2];

            // 위와 같이 형변환을 해서 가져 올 수 있다

            System.out.println(member2);
            System.out.println(product);
            System.out.println(id);

        }

    }

    @Test
    public void findByAgeListTest() {
        System.out.println(member2Repository.findByAgeList(5));

    }

    // @Test
    // public void findByTeamEqualTest() {
    // System.out.println(member2Repository.findByTeamEqual(Team2.builder().id(2L).build()));

    @Test
    public void aggregateTEST() {
        List<Object[]> list = member2Repository.aggregate();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
            System.out.println("회원수" + objects[0]);
            System.out.println("나이 합" + objects[1]);
            System.out.println("나이 평균" + objects[2]);
            System.out.println("최대 나이" + objects[3]);
            System.out.println("최소 나이" + objects[4]);

            // 회원수10
            // 나이 합55
            // 나이 평균5.5
            // 최대 나이10
            // 최소 나이1

        }

    }

    @Test
    public void findByTeamMemberTEST() {
        // 조인 후 멤버 결과만 가져오는 메소드
        System.out.println(member2Repository.findByTeamMember("team1"));
        // 조인후 Member2, Team2 결과 가져오기
        List<Object[]> list = member2Repository.findByTeamMember2("team2");

        for (Object[] objects : list) {
            Member2 member2 = (Member2) objects[0];
            Team2 team2 = (Team2) objects[1];
            System.out.println(member2);
            System.out.println(team2);

        }

    }

}
