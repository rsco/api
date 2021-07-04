package com.ebanx.api.controller;

import com.ebanx.api.service.OperationsServices;
import com.ebanx.api.entity.*;
import com.ebanx.api.dto.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class OperationsController {

    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        String resetAck = OperationsServices.resetAccount();
        return ResponseEntity.ok().body(resetAck);
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@RequestParam(value = "account_id") String accountId) {
        BigDecimal balance = OperationsServices.getAccountBalance(accountId);
        if (!balance.equals(BigDecimal.ZERO)) {
            return ResponseEntity.ok().body(balance);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(balance);
        }
    }

    @PostMapping("/event")
    public ResponseEntity<?> createAccount(@RequestBody Event event) {
        DestinationDTO accountDTO;
        OriginDTO originDTO;
        TransferDTO transferDTO;
        switch (event.type) {
            case "deposit":
                accountDTO = OperationsServices.createOrUpdateAccount(event);
                if (Optional.ofNullable(accountDTO).isPresent())
                    return new ResponseEntity<IAccount>(accountDTO, HttpStatus.CREATED);
            case "withdraw":
                originDTO = OperationsServices.withdrawAccount(event);
                if (Optional.ofNullable(originDTO).isPresent()) {
                    return new ResponseEntity<IAccount>(originDTO, HttpStatus.CREATED);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
                }
            case "transfer":
                transferDTO = OperationsServices.transferFromExistingAccount(event);
                if (Optional.ofNullable(transferDTO).isPresent()) {
                    return new ResponseEntity<>((IAccount) transferDTO, HttpStatus.CREATED);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
                }
        }
        return null;
    }


}
