package com.sunny.hello_shop.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class Book extends Item{

    private String auther;

    private String isbn;
}
