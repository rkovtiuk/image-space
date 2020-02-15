package com.imagespace.account.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.annotation.AccessType.Type.PROPERTY;

@Entity
@Table(name = "account")
@Data @Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NamedEntityGraph(name = "Account.roles",
    attributeNodes = @NamedAttributeNode(value = "roles", subgraph = "roles"),
    subgraphs = @NamedSubgraph(name = "roles", attributeNodes = @NamedAttributeNode("role"))
)
public class Account extends BaseEntity {

    @Id @AccessType(PROPERTY) UUID id;

    @Column String username;

    @Column UUID avatar;

    @Column String password;

    @Column String name;

    @Column String info;

    @OneToMany(mappedBy = "account")
    List<AccountRole> roles;

}

