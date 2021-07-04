package com.ebanx.api.service;

import com.ebanx.api.entity.*;
import com.ebanx.api.dto.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OperationsServices {
    public static final String OK = "OK";
    private static Map<String, Account> accounts = new HashMap<>();

    public static BigDecimal getAccountBalance(String accountId) {
        Optional<Account> accountOpt = Optional.ofNullable(accounts.get(accountId));
        if (accountOpt.isPresent()) {
            return accounts.get(accountId).getBalance();
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static DestinationDTO createOrUpdateAccount(Event event) {
        String destinationID = event.destination;
        BigDecimal amount = event.amount;
        Account account = accounts.get(destinationID);
        if (isEventNotNull(event)) {
            if (isAnExistingAccount(destinationID)) {
                depositAmount(account, amount);
            } else {
                account = new Account(destinationID, amount);
                createAccount(destinationID, amount, account);
            }
            return new DestinationDTO(new Destination(destinationID, account.getBalance()));
        }
        return null;
    }

    private static void createAccount(String destinationID, BigDecimal amount, Account account) {
        account.setAccountId(destinationID);
        account.setBalance(amount);
        accounts.put(destinationID, account);
    }

    private static void depositAmount(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
    }

    private static boolean isAnExistingAccount(String destination) {
        return accounts.containsKey(destination);
    }

    private static boolean isEventNotNull(Event event) {
        return Optional.ofNullable(event).isPresent();
    }

    public static OriginDTO withdrawAccount(Event event) {
        if (isEventNotNull(event)) {
            String accountID = event.origin;
            Account account = accounts.get(accountID);
            if (isAnExistingAccount(accountID)) {
                BigDecimal amount = event.amount;
                account.setBalance(account.getBalance().subtract(amount));
                return new OriginDTO(new Origin(accountID, account.getBalance()));
            } else {
                return null;
            }
        }
        return null;
    }


    public static TransferDTO transferFromExistingAccount(Event event) {
        Account accountOrigin;
        Account accountDestination;
        if (isEventNotNull(event)) {
            boolean isAnExistingOriginAccount = isAnExistingAccount(event.origin);
            boolean isAnExistingDestinationAccount = isAnExistingAccount(event.destination);
            if (isAnExistingOriginAccount && isAnExistingDestinationAccount) {
                accountOrigin = debitOriginAccount(event);
                accountDestination = creditDestinationAccount(event);
                return new TransferDTO(new Origin(event.origin, accountOrigin.getBalance()), new Destination(event.destination, accountDestination.getBalance()));
            } else return null;
        }
        return null;

    }

    private static Account creditDestinationAccount(Event event) {
        Account accountDestination;
        accountDestination = accounts.get(event.destination);
        depositAmount(accountDestination, event.getAmount());
        return accountDestination;
    }

    private static Account debitOriginAccount(Event event) {
        Account accountOrigin;
        accountOrigin = accounts.get(event.origin);
        accountOrigin.setBalance(accountOrigin.getBalance().subtract(event.getAmount()));
        return accountOrigin;
    }

    public static String resetAccount() {
        if (!accounts.isEmpty()) {
            accounts.clear();
        }
        return OK;
    }
}
