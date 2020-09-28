package datajpacom.example.proyectoconjpa.dao;

import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import datajpacom.example.proyectoconjpa.entities.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{
    
    @Query("select c from Cliente c left join fetch c.facturas f where c.id =?1")
    public Cliente fetchByIdWithFacturas(Long id);
}
