package com.imagespace.post.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.post.common.dto.PostDto;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostEvent extends BaseEvent {

    @JsonProperty("payload")
    PostDto payload;

    public PostEvent(String eventName, PostDto payload) {
        super(eventName);
        this.payload = payload;
    }

}

