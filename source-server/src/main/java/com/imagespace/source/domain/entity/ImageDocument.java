package com.imagespace.source.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(of = "id")
@Document(collection = "images")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageDocument {

    @Id
    String id;

    @Field("image_data")
    byte[] imageData;

    @Field("created_at")
    LocalDateTime createdAt;

    @Field("deleted")
    boolean deleted;

}
