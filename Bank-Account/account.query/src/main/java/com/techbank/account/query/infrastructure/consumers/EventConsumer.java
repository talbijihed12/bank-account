package com.techbank.account.query.infrastructure.consumers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositEvent;
import com.techbank.account.common.events.FundsWithdrawEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume (@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume (@Payload FundsDepositEvent event, Acknowledgment ack);
    void consume (@Payload FundsWithdrawEvent event, Acknowledgment ack);
    void consume (@Payload AccountClosedEvent event, Acknowledgment ack);
}
