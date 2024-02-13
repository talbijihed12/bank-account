package com.techbank.account.query.infrastructure.handlers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositEvent;
import com.techbank.account.common.events.FundsWithdrawEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositEvent event);
    void on(FundsWithdrawEvent event);
    void on(AccountClosedEvent event);
}
