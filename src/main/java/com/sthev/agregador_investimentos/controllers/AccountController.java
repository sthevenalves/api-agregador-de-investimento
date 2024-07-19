package com.sthev.agregador_investimentos.controllers;

import com.sthev.agregador_investimentos.domain.dto.AccountDto;
import com.sthev.agregador_investimentos.domain.dto.AccountStockResponseDto;
import com.sthev.agregador_investimentos.domain.dto.AssociateAccountStockDto;
import com.sthev.agregador_investimentos.servicies.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("/{accountId}/accounts")
    public ResponseEntity<Void> createAccount (@PathVariable("accountId") String accountId,
                                               @RequestBody AssociateAccountStockDto dto){
        service.associateStock(accountId, dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> associateStock(@PathVariable("accountId") String accountId){
    var stocks = service.listStocks(accountId);
    return ResponseEntity.ok(stocks);
    }
}
