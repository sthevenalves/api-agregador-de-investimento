package com.sthev.agregador_investimentos.controllers;

import com.sthev.agregador_investimentos.domain.User;
import com.sthev.agregador_investimentos.domain.dto.StockDto;
import com.sthev.agregador_investimentos.domain.dto.UserDTO;
import com.sthev.agregador_investimentos.servicies.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    @Autowired
    private StockService service;


    @PostMapping
    public ResponseEntity<Void> createStock (@RequestBody StockDto dto){
        this.service.createStock(dto);
        return ResponseEntity.ok().build();

}
}