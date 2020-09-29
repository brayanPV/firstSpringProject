package datajpacom.example.proyectoconjpa.dao;

import org.springframework.data.repository.CrudRepository;

import datajpacom.example.proyectoconjpa.entities.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
    
    public Usuario findByUsername(String username);
}
