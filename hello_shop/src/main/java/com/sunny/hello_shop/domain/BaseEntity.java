package com.sunny.hello_shop.domain;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class BaseEntity {

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
