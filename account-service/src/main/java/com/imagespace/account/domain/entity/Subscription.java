package com.imagespace.account.domain.entity;

import com.imagespace.account.common.util.Constants;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.util.UUID;

import static org.springframework.data.annotation.AccessType.Type.PROPERTY;

@Entity
@Table(name = "subscription")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Subscription extends BaseEntity {

    @Id @AccessType(PROPERTY) UUID id;

    @Column int priority;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    Account follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    Account following;

    public Subscription(UUID follower, UUID following) {
        this.id = UUID.randomUUID();
        this.priority = Constants.DEFAULT_SUBSCRIPTION_PRIORITY;
        this.follower = new Account().setId(follower);
        this.following = new Account().setId(following);
    }
}
