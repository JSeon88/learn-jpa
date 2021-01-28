package com.sunny.hello_shop.domain;

import com.sunny.common.LocalDateTimePersistenceConverter;
import com.sunny.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name="ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name="ORDER_ID")
    private Long id;

//    @Column(name="MEMBER_ID")
//    private Long memberId;

    //@Convert(converter = LocalDateTimePersistenceConverter.class)
    @Column(name = "ORDERDATE", columnDefinition = "TIMESTAMP")
    private LocalDateTime oderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    //연관관계 메소드
    public void setMember(Member member) {
        // 기존 관계 제거
        if(null != this.member){
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
