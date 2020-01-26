package com.imagespace.source.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "sources")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SourceDocument {

    @Id
    String id;

    @Field("image_data")
    byte[] imageData;

    @Field("created_at")
    LocalDateTime createdAt;

    @Field("deleted")
    boolean deleted;

    public SourceDocument(String id, byte[] imageData) {
        this.id = id;
        this.imageData = imageData;
        this.createdAt = LocalDateTime.now();
    }

}
