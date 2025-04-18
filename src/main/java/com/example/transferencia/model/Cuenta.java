package com.example.transferencia.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String propietario;

    private BigDecimal saldo;



    public Cuenta() { }

    public Cuenta(String propietario, BigDecimal saldo) {
        this.propietario = propietario;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void retirar(BigDecimal monto) {
        this.saldo = this.saldo.subtract(monto);
    }

    public void depositar(BigDecimal monto) {
        this.saldo = this.saldo.add(monto);
    }
}

