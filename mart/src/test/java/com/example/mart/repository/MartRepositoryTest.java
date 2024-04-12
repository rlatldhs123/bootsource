package com.example.mart.repository;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.mart.entity.Delivery;
import com.example.mart.entity.DeliveryStauts;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.OrderStatus;
import com.example.mart.repository.ItemRepository;
import com.example.mart.repository.MemberRepository;
import com.example.mart.repository.OrderItemRepository;
import com.example.mart.repository.OrderRepository;

@SpringBootTest
public class MartRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void insertTest() {
        // member 3명 추가
        memberRepository.save(Member.builder()
                .name("홍길동")
                .city("서울시")
                .street("중구")
                .zipcode("15102")
                .build());

        memberRepository.save(Member.builder()
                .name("홍길은")
                .city("인천시")
                .street("남구")
                .zipcode("15103")
                .build());

        memberRepository.save(Member.builder()
                .name("홍길금")
                .city("충주시")
                .street("서구")
                .zipcode("15104")
                .build());

        // 아이템 3개 추가
        itemRepository.save(Item.builder()
                .name("티셔츠")
                .price(10000)
                .stockQuantity(500)

                .build());

        itemRepository.save(Item.builder()
                .name("바지")
                .price(20000)
                .stockQuantity(600)
                .build());

        itemRepository.save(Item.builder()
                .name("셔츠")
                .price(30000)
                .stockQuantity(700)
                .build());
    }

    // 아이템 3개 추가

    // 주문
    @Test
    public void orderInsertTest() {

        // 누가 주문하느냐

        Member member = Member.builder().id(1L).build();

        // 어떤 아이템
        Item item = Item.builder().id(1L).build();

        // 주문 + 주문 상품
        //
        Order order = Order.builder().id(1L).member(member).orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER).build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder().item(item).order(order).orderprice(260000).count(2).build();
        orderItemRepository.save(orderItem);

    }

    @Test
    public void readTest() {
        // 전체 회원 조회
        List<Member> members = memberRepository.findAll();

        members.forEach(m -> System.out.println(m));

        // 전체 아이템 조회
        List<Item> items = itemRepository.findAll();
        items.forEach(i -> System.out.println(i));

        // 전체 주문아이템 조회 시 주문 정보도 확인

        // 객체 그래프 탐색이 가능한 이유는 n:1 관계에서 fetchType.EAGER 이기 때문에

        orderItemRepository.findAll().forEach(entity -> {
            System.out.println(entity);
            System.out.println("상품정보 " + entity.getItem());
            System.out.println("구매자 " + entity.getOrder().getMember().getName());
        });

    }

    @Test
    public void updateTest() {
        // 멤버 주소 수정
        Optional<Member> member1 = memberRepository.findById(1L);

        member1.ifPresent(member -> {
            member.setCity("일본");
            member.setStreet("구로구");

            memberRepository.save(member);
        });

        // 아이템 가격 수정
        Optional<Item> item1 = itemRepository.findById(1L);

        item1.ifPresent(item -> {
            item.setPrice(85745);

            itemRepository.save(item);
        });

        // 주문 상태 수정
        Optional<Order> order1 = orderRepository.findById(1L);

        order1.ifPresent(order -> {
            order.setOrderStatus(OrderStatus.CANCEL);

            orderRepository.save(order);
        });
    }

    @Test
    public void deleteTest() {

        // 주문 제거시 주문 아이템 제거 가능?
        // 주문 조회시 주문 아이템 조회 가능?
        // 이렇게만하면 자식이 있기에 한번에 날리지를 못한다
        // orderRepository.deleteById(1L); 주문아이템이 존재하기에 이아템으로는 한번에 삭제가 안된다

        // 주문 아이템 제거 후에 주문 제거
        orderItemRepository.delete(OrderItem.builder().id(1L).build());
        orderRepository.deleteById(1L);

    }

    @Transactional
    @Test
    public void readTest2() {
        // @OneToMany 를 이용해 조회
        // @OneToMany 는 관련있는 엔티티를 처음부터 안가지고 옴
        // 그렇기에 Order 엔티티를 가서
        // @ToString(exclude = "orderItems") 투스트링 메소드에서 exclude 를 준다

        // order : OrderItem
        // Order 를 기준으로 OrderItem 조회
        // 전체 회원 조회

        Order order = orderRepository.findById(3L).get();
        System.out.println(order);

        // Order를 기준으로 OrderItem 을 조회
        // 1. @Transactional 을 걸던가
        // 2.FetchType @OneToMany(mappedBy = "order" ,fetch = FetchType.EAGER)변경

        System.out.println(order.getOrderItems());
        // 배송지 조회
        System.out.println(order.getDelivery().getCity());

    }

    @Transactional
    @Test
    public void readTest3() {
        // @OneToMany 를 이용해 조회
        // @OneToMany 는 관련있는 엔티티를 처음부터 안가지고 옴
        // 그렇기에 Order 엔티티를 가서
        // @ToString(exclude = "orderItems") 투스트링 메소드에서 exclude 를 준다

        // Member : Order
        // Order 를 기준으로 OrderItem 조회
        // 전체 주문내역 조회

        Member member = memberRepository.findById(1L).get();
        System.out.println(member);
        System.out.println(member.getOrders());

    }

    @Test
    public void orderInsertDeliveryTest() {

        // 누가 주문하느냐

        Member member = Member.builder().id(1L).build();

        // 어떤 아이템
        Item item = Item.builder().id(3L).build();

        // 배송지 입력
        Delivery delivery = Delivery.builder()
                .city("서울시")
                .street("123-12")
                .zipCode("11060")
                .deliveryStauts(DeliveryStauts.READY)
                .order(null)
                .build();
        deliveryRepository.save(delivery);

        // 주문 + 주문 상품
        //
        Order order = Order.builder().id(4L).member(member).orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER).delivery(delivery).build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder().item(item).order(order).orderprice(260000).count(2).build();
        orderItemRepository.save(orderItem);

    }

    // 배송지를 통해서 관련있는 order 가져 오기
    @Test
    public void deliveryOrderGet() {
        //

        Delivery delivery = deliveryRepository.findById(1L).get();
        System.out.println(delivery);
        System.out.println("관련 주문 : " + delivery.getOrder());
    }

    @Test
    @Transactional
    public void testJoinTest() {
        List<Object[]> list = orderRepository.joinList();

        for (Object[] objects : list) {
            Order order = (Order) objects[0];
            Member member = (Member) objects[1];
            OrderItem orderItem = (OrderItem) objects[2];
            System.out.println("------------------------ test 메소드");
            System.out.println(order);
            System.out.println(member);
            System.out.println(orderItem);

        }
        // Member
        System.out.println(orderRepository.members());

    }

}