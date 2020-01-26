package com.imagespace.account.domain.entity;


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
public class Subscribe extends BaseEntity {

    @Id @AccessType(PROPERTY) UUID id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    Account subscriber;

    @ManyToOne
    @JoinColumn(name = "subscribing_id")
    Account subscribing;

    public Subscribe(UUID subscriber, UUID subscribing) {
        this.id = UUID.randomUUID();
        this.subscriber = new Account().setId(subscriber);
        this.subscribing = new Account().setId(subscribing);
    }
}
