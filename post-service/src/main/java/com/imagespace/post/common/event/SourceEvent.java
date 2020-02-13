package com.imagespace.post.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.post.common.dto.SourceDto;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SourceEvent extends BaseEvent {

    @JsonProperty("payload")
    SourceDto payload;

    public SourceEvent(String eventName, SourceDto payload) {
        super(eventName);
        this.payload = payload;
    }

}
