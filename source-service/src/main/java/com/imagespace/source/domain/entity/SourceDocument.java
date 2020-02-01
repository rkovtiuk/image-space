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
@Document(collection = "source")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SourceDocument {

    @Id
    @Field("_id")
    String id;

    @Field("source_id")
    String sourceId;

    @Field("image_data")
    byte[] imageData;

    @Field("created_at")
    LocalDateTime createdAt;

    @Field("deleted")
    boolean deleted;

    public SourceDocument(String sourceId, byte[] imageData) {
        this.sourceId = sourceId;
        this.imageData = imageData;
        this.createdAt = LocalDateTime.now();
    }

}
