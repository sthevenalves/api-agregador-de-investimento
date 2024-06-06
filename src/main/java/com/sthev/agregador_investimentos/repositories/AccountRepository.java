package com.sthev.agregador_investimentos.repositories;

import com.sthev.agregador_investimentos.domain.Account;
import com.sthev.agregador_investimentos.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
