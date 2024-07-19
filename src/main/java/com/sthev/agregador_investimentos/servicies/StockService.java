package com.sthev.agregador_investimentos.servicies;

import com.sthev.agregador_investimentos.domain.Stock;
import com.sthev.agregador_investimentos.domain.dto.StockDto;
import com.sthev.agregador_investimentos.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public void createStock(StockDto dto){
        var stock = new Stock(
                dto.stockId(),
                dto.description()
        );
        stockRepository.save(stock);
    }
}
