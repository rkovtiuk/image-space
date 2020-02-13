package com.imagespace.account.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;


@Entity
@Table(name = "role")
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Role extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column String name;

}
