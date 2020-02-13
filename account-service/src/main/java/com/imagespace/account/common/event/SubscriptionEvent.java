package com.imagespace.account.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.account.common.dto.SubscriptionDto;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionEvent extends BaseEvent {

    @JsonProperty("payload")
    SubscriptionDto payload;

    public SubscriptionEvent(String eventName, SubscriptionDto payload) {
        super(eventName);
        this.payload = payload;
    }

}
