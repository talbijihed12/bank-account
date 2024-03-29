package com.techbank.account.cmd.domain;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.cmd.api.commands.WithdrawFundsCommand;
import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositEvent;
import com.techbank.account.common.events.FundsWithdrawEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private double balance;

    public double getBalance(){
        return this.balance;
    }
    public Boolean getActive(){return this.active;}

    public AccountAggregate(OpenAccountCommand command){
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }
    public void apply(AccountOpenedEvent event){
        this.id=event.getId();
        this.active=true;
        this.balance=event.getOpeningBalance();

    }
    public void depositFunds(double amount){
        if (!this.active){
            throw new IllegalStateException("Funds cannot deposited in a closed account");

        }if (amount<=0){
            throw new IllegalStateException("The deposit amount must be greater than 0");

        }
        raiseEvent(FundsDepositEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }
    public void apply(FundsDepositEvent event){
        this.id=event.getId();
        this.balance += event.getAmount();
    }
    public void withdrawFunds(double amount){
        if (!this.active){
            throw new IllegalStateException("Funds cannot withdraw in a closed account");

        }
        raiseEvent(FundsWithdrawEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }
    public void apply(FundsWithdrawEvent event){
        this.id=event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount(){
        if (!this.active){
            throw new IllegalStateException("The account already has been closed");

        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }
    public void apply(AccountClosedEvent event){
        this.id=event.getId();
        this.active= false;
    }

}
