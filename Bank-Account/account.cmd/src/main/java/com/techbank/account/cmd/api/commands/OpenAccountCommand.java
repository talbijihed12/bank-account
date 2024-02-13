package com.techbank.account.cmd.api.commands;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.commands.BaseCommand;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private Double openingBalance;
}
