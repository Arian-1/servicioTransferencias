package com.example.transferencia.controller;

import com.example.transferencia.model.Cuenta;
import com.example.transferencia.dto.TransferRequest;
import com.example.transferencia.repository.CuentaRepository;
import com.example.transferencia.service.TransferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final CuentaRepository cuentaRepo;
    private final TransferService transferService;

    public WebController(CuentaRepository cuentaRepo, TransferService ts) {
        this.cuentaRepo = cuentaRepo;
        this.transferService = ts;
    }


    @GetMapping("/accounts")
    public String lista(Model m) {
        m.addAttribute("cuentas", cuentaRepo.findAll());
        m.addAttribute("nueva", new Cuenta());
        return "accounts";
    }


    @PostMapping("/accounts")
    public String crear(@ModelAttribute Cuenta nueva) {
        cuentaRepo.save(nueva);
        return "redirect:/accounts";
    }


    @GetMapping("/transfer")
    public String formTransfer(Model m) {
        m.addAttribute("req", new TransferRequest());
        m.addAttribute("cuentas", cuentaRepo.findAll());
        return "transfer";
    }


    @PostMapping("/transfer")
    public String transferir(@ModelAttribute TransferRequest req, Model m) {
        try {
            transferService.transferir(req.getOrigenId(), req.getDestinoId(), req.getMonto());
            m.addAttribute("msg","¡Transferencia exitosa!");
        } catch (Exception e) {
            m.addAttribute("error", e.getMessage());
        }
        m.addAttribute("cuentas", cuentaRepo.findAll());
        return "transfer-result";
    }
}
