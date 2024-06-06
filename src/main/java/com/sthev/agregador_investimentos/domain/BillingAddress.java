package com.sthev.agregador_investimentos.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_billingaddress")
public class BillingAddress {
    @Id
    @Column(name = "account_id")
    private UUID id;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    @MapsId
    private Account account;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    public BillingAddress(UUID id, Account account, String street, Integer number) {
        this.id = id;
        this.account = account;
        this.street = street;
        this.number = number;
    }

    public BillingAddress() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
