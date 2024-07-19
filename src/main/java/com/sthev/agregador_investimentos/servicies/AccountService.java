package com.sthev.agregador_investimentos.servicies;

import com.sthev.agregador_investimentos.domain.AccountStock;
import com.sthev.agregador_investimentos.domain.AccountStockId;
import com.sthev.agregador_investimentos.domain.dto.AccountStockResponseDto;
import com.sthev.agregador_investimentos.domain.dto.AssociateAccountStockDto;
import com.sthev.agregador_investimentos.repositories.AccountRepository;
import com.sthev.agregador_investimentos.repositories.AccountStockRepository;
import com.sthev.agregador_investimentos.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private AccountStockRepository accountStockRepository;

    public void associateStock(String accountId, AssociateAccountStockDto dto){
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //dto to entity
        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );
        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDto> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(as -> new AccountStockResponseDto(as.getStock().getStockId(), as.getQuantity(), 0.0))
                .toList();
    }
}
