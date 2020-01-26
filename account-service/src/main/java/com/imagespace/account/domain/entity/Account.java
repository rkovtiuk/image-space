package com.imagespace.account.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.AccessType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

import static org.springframework.data.annotation.AccessType.Type.PROPERTY;

@Entity
@Table(name = "account")
@Data @Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Account extends BaseEntity {

    @Id @AccessType(PROPERTY) UUID id;

    @Column String password;

    @Column String name;

    @Column String info;

}

