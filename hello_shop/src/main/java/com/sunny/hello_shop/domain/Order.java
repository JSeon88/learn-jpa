package com.sunny.hello_shop.domain;

import com.sunny.common.LocalDateTimePersistenceConverter;
import com.sunny.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name="ORDER_ID")
    private Long id;

    @Column(name="MEMBER_ID")
    private Long memberId;

    //@Convert(converter = LocalDateTimePersistenceConverter.class)
    @Column(name = "ORDERDATE", columnDefinition = "TIMESTAMP")
    private LocalDateTime oderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
