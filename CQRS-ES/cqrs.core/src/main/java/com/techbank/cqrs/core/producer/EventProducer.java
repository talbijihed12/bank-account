package com.techbank.cqrs.core.producer;

import com.techbank.cqrs.core.events.BaseEvent;

public interface EventProducer {
    public void produce(String topic, BaseEvent event);
}
