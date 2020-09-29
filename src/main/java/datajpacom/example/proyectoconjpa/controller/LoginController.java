package datajpacom.example.proyectoconjpa.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error,
    @RequestParam(value="logout", required=false) String logout,Model model, Principal principal, RedirectAttributes flash){
        if(principal != null){
            flash.addFlashAttribute("info", "Usted ya ha iniciado sesion en el sistema");
            return "redirect:/";
        }
        if(error!=null){
            model.addAttribute("error", "Usuario o contraseña incorrecta");
        }
        if(logout!=null){
            model.addAttribute("success", "Ha cerrado sesión con exito");
        }
        return "login";
    }
}
