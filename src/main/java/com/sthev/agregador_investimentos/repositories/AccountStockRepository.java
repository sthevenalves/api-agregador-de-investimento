package com.sthev.agregador_investimentos.repositories;

import com.sthev.agregador_investimentos.domain.AccountStock;
import com.sthev.agregador_investimentos.domain.AccountStockId;
import com.sthev.agregador_investimentos.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
