package com.techbank.account.query.api.controllers;

import com.techbank.account.query.api.dto.AccountLookupResponse;
import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.api.queries.*;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/accountLookup")
public class AccountLookupController {
    private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());
    @Autowired
    private QueryDispatcher queryDispatcher;
    @GetMapping("/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountQuery());
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response  = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank account(s)",accounts.size()))
                    .build();
            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch (Exception e){
            var safeErrorMessage = "Failed to complete all accounts request";
            logger.log(Level.SEVERE,safeErrorMessage,e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id){
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response  = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank account")
                    .build();
            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch (Exception e){
            var safeErrorMessage = "Failed to complete account by ID request";
            logger.log(Level.SEVERE,safeErrorMessage,e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value = "accountHolder") String accountHolder){
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response  = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank account")
                    .build();
            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch (Exception e){
            var safeErrorMessage = "Failed to complete account by holder request";
            logger.log(Level.SEVERE,safeErrorMessage,e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountByBalance(@PathVariable(value = "equalityType") EqualityType equalityType,
                                                                     @PathVariable(value = "balance") double balance){
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType,balance));
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response  = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank account(s)",accounts.size()))
                    .build();
            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch (Exception e){
            var safeErrorMessage = "Failed to complete account with balance request";
            logger.log(Level.SEVERE,safeErrorMessage,e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
