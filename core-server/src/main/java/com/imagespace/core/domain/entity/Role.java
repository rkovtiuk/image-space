package com.imagespace.core.domain.entity;

import com.imagespace.core.domain.entity.converter.RoleTypeConverter;
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

    @Column
    @Convert(converter = RoleTypeConverter.class)
    RoleType name;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    public enum RoleType {
        USER, ADMIN
    }

}
