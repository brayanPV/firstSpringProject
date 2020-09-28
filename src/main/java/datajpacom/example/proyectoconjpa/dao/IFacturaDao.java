package datajpacom.example.proyectoconjpa.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import datajpacom.example.proyectoconjpa.entities.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Serializable>{
    
    @Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
