package com.techbank.account.cmd.api.controllers;

import com.techbank.account.cmd.api.commands.CloseAccountCommand;
import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/CloseAccount")
public class CloseBankAccountController {
    private final Logger logger = Logger.getLogger(CloseBankAccountController.class.getName());
    @Autowired
    private CommandDispatcher commandDispatcher;
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> openAccount(@PathVariable(value = "id")String id){

        try {
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("Bank account delete request completed successfully"), HttpStatus.CREATED);

        }catch (IllegalStateException e){
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}.",e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()),HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            var safeErrorMessage=MessageFormat.format("Error while processing request to close bank account for id - {0}.",id);
            logger.log(Level.SEVERE,safeErrorMessage,e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
