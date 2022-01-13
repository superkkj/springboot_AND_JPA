package jpabook.jpashop.domain.Item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    //== 비즈니스 로직==//
    //보통 개발할 때 set 함수써서 결과를 만든다 . 객체지향 적으로 하려면 데이터를 가지고 있는 쪽이 비즈니스 매소드 있는게 좋다. 반복


    /**
     * stock 증가
     */

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */

    public void removeStock(int quantity) {

        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;

    }
}
