package com.imagespace.post.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.io.Serializable;
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
@NamedEntityGraph(name = "Like.post", attributeNodes = @NamedAttributeNode("post"))
public class Like implements Serializable {

    @Id
    @AccessType(PROPERTY)
    UUID id;

    @Column
    UUID accountId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

}
