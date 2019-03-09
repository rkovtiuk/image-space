package com.imagespace.core.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "post")
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Post extends BaseEntity {

    @Id UUID id;

    @Column UUID source;

    @Column int likes;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

}
