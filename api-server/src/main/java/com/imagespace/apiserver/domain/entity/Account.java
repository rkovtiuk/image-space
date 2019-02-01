package com.imagespace.apiserver.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)


@Entity
@Table(name = "account")

public class Account extends BaseEntity {

    @Id
    String id;

    @Column
    String password;

    @Column
    String name;

    @Column
    String info;

}

