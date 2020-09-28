package datajpacom.example.proyectoconjpa.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import datajpacom.example.proyectoconjpa.entities.Cliente;
import datajpacom.example.proyectoconjpa.entities.Factura;
import datajpacom.example.proyectoconjpa.entities.ItemFactura;
import datajpacom.example.proyectoconjpa.entities.Producto;
import datajpacom.example.proyectoconjpa.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    private IClienteService clienteServiceImp;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
            RedirectAttributes flash) {
        Cliente cliente = clienteServiceImp.findOne(clienteId);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }
        Factura factura = new Factura();
        factura.setCliente(cliente);
        model.put("factura", factura);
        model.put("titulo", "Crear factura");
        return "factura/form";

    }

    @GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
        return clienteServiceImp.findByNombre(term);
    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura, BindingResult result, Model model,
            @RequestParam(name = "item_id[]", required = false) Long[] itemId,
            @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
            SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "crear Factura");
            return "factura/form";
        }
        if (itemId == null || itemId.length == 0) {
            model.addAttribute("titulo", "crear Factura");
            model.addAttribute("error", "Error: la factura no puede no tener items");
            return "factura/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Producto producto = clienteServiceImp.findProductoById(itemId[i]);
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            factura.addItemFactura(linea);
            log.info("ID: " + itemId[i].toString() + ", cantidad; " + cantidad[i].toString());
        }
        clienteServiceImp.saveFactura(factura);
        status.setComplete();
        flash.addFlashAttribute("success", "Factura creada con exito");
        return "redirect:/ver/" + factura.getCliente().getId();
    }

    @GetMapping("ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes flash){
        //Factura factura = clienteServiceImp.findFacturaById(id);
        Factura factura = clienteServiceImp.fetchByIdWithClienteWithItemFacturaWithProducto(id);
        if(factura==null){
            flash.addFlashAttribute("error", "la factura no existe en la base de datos");
            return "redirect:/listar";
        }
        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Factura: " + factura.getDescripcion());
        return "factura/ver";

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash){
        Factura factura = clienteServiceImp.findFacturaById(id);
        if(factura!= null){
            clienteServiceImp.deleteFactura(id);
            flash.addFlashAttribute("success", "Factura eliminada exitosamente");
            return "redirect:/ver/" + factura.getCliente().getId();
        }else{
            flash.addFlashAttribute("error", "La factura no existe en la base de datos");
            return "redirect:/listar";
        }
        
    }
}
