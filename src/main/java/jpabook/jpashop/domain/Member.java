package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //Order 테이블에 있는 member필드에 매핑된당. 거울일 뿐이야!
    private List<Order> orders = new ArrayList<>(); //best  바로 초기화 하는게 Null 문제에서 안전함.

}
