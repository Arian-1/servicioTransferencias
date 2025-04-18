package com.example.transferencia.controller;

import com.example.transferencia.dto.TransferRequest;
import com.example.transferencia.service.TransferService;
import com.example.transferencia.service.exception.AccountNotFoundException;
import com.example.transferencia.service.exception.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferir(@RequestBody TransferRequest req) {
        try {
            transferService.transferir(req.getOrigenId(), req.getDestinoId(), req.getMonto());
            return ResponseEntity.ok("Transferencia realizada con éxito");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (InsufficientFundsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + ex.getMessage());
        }
    }
}

