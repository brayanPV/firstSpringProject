package datajpacom.example.proyectoconjpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import datajpacom.example.proyectoconjpa.entities.Cliente;
import datajpacom.example.proyectoconjpa.service.IClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping(value = {"/listar"})
    public List<Cliente> listar(){
        return clienteService.findAll();
    }
}
