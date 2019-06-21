package com.imagespace.core.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.util.UUID;

import static org.springframework.data.annotation.AccessType.Type.PROPERTY;

@Entity
@Table(name = "post")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Post extends BaseEntity {

    @Id @AccessType(PROPERTY) UUID id;

    @Column UUID source;

    @Column long likes;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

}
