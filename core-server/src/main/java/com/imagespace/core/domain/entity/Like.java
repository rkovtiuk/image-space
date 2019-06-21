package com.imagespace.core.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.util.UUID;

import static org.springframework.data.annotation.AccessType.Type.PROPERTY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "like")
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Like {

    @Id @AccessType(PROPERTY) UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

}
