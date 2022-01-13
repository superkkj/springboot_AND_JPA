package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") // 외래키
    private Member member;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //order 저장하면 orderItems도 저장한다
    private List<OrderItem> orderItems = new ArrayList<>();

    // ex persist(orderItemA) , orderItemB , orderItemC
    // persist(order) 만 해주면됨 각각 됨 해줘야되는데 케스케이드 해주면 해결

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //하이버네이트가 지원하는 로컬데이트타임
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER , CANCEL]

    //양방향 관계 셋팅하려면

    //== 연관관계 메서드 ==//
    //원래대로 라면
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드 ==//

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { //생성하는 지점 변경할때 여기만 바꿈
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직 ==//

    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");

        }
        this.setStatus(OrderStatus.CANCEL);

        for (OrderItem orderItem : this.orderItems) { // this 안써도 색칠해줘서 안해도됨
            orderItem.cancel();
        }

    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice = orderItem.getTotalPrice();
//
//        }
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();

    }


}
