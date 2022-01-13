package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    /**
     * 주문
     */

    @Transactional
    public Long order(Long memberId, Long itemId, int count){


        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOnd(itemId);


        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성

        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice() , count);

        //주문 생성

        Order order = Order.createOrder(member,delivery , orderItem);

        //주문 저장

        orderRepository.save(order); //OrderItem / address cacade때문에 같이 퍼시스트됨

        return order.getId();// 원래 대로라면 딜리버지 리파지토리 세이브 해주고.. orderItem도 세이브하고 해줘야된다 하지만 Order Cascade 때문에해결
                             // 케스케이드 덕분에 퍼시스트 다해줌. 케스케이드는 어떤 개념에서 쓰면 좋을까? 하나의 엔티티? 오더에만 쓰일때 케스케이드...를 쓰면된다. 다른데 사용을 안한다 할때
    }


    /**
     * 주문 취소
     */

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel(); // 데이터만 바꿔주면 더티체킹이라고해서 변경내역 감지를해서 변경 내역을 업데이트 쿼리를 넣어줌  ex) order 의 update 쿼리
    }

//    //검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
}
