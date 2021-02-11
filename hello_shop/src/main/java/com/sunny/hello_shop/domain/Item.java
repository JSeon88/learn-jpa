package com.sunny.hello_shop.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;

    private Integer price;

    private Integer stockQuantity;

    //연관관계 매핑
//    @OneToMany(mappedBy = "orderItem")
//    private List<OrderItem> orderItems = new ArrayList<>();
// 주문 상품 -> 상품으로 조회할 일은 많지만 상품 -> 주문 상품을 참조 할 일은 거의 없음. 그래서 다대일 단방향 관계로만 설정.

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
