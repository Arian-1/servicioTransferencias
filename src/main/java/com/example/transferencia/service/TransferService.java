package com.example.transferencia.service;

import com.example.transferencia.model.Cuenta;
import com.example.transferencia.repository.CuentaRepository;
import com.example.transferencia.service.exception.AccountNotFoundException;
import com.example.transferencia.service.exception.InsufficientFundsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final CuentaRepository cuentaRepo;

    public TransferService(CuentaRepository cuentaRepo) {
        this.cuentaRepo = cuentaRepo;
    }

    @Transactional
    public void transferir(Long origenId, Long destinoId, BigDecimal monto) {
        Cuenta origen = cuentaRepo.findById(origenId)
                .orElseThrow(() -> new AccountNotFoundException("Cuenta origen no encontrada: " + origenId));
        Cuenta destino = cuentaRepo.findById(destinoId)
                .orElseThrow(() -> new AccountNotFoundException("Cuenta destino no encontrada: " + destinoId));

        if (origen.getSaldo().compareTo(monto) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente en cuenta origen");
        }

        origen.retirar(monto);
        destino.depositar(monto);

        cuentaRepo.save(origen);
        cuentaRepo.save(destino);
    }
}

