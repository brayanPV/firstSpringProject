package datajpacom.example.proyectoconjpa.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import datajpacom.example.proyectoconjpa.entities.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{
    
    List<Producto> findByNombreLikeIgnoreCase(String nombre);
    
}
