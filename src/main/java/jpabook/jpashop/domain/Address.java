package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() { //리플렉션 프록시 같은 기술을 써야될때.. jpa 스펙상 생성
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
