package datajpacom.example.proyectoconjpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import datajpacom.example.proyectoconjpa.dao.IClienteDao;
import datajpacom.example.proyectoconjpa.dao.IFacturaDao;
import datajpacom.example.proyectoconjpa.dao.IProductoDao;
import datajpacom.example.proyectoconjpa.entities.Cliente;
import datajpacom.example.proyectoconjpa.entities.Factura;
import datajpacom.example.proyectoconjpa.entities.Producto;

@Service
public class ClienteServiceImp implements IClienteService {

    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IProductoDao productoDao;

    @Autowired
    private IFacturaDao facturaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        // TODO Auto-generated method stub
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional
    public void save(final Cliente cliente) {
        // TODO Auto-generated method stub
        clienteDao.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(final Long id) {
        // TODO Auto-generated method stub
        return clienteDao.findById(id).get();
    }

    @Override
    @Transactional
    public void delete(final Long id) {
        // TODO Auto-generated method stub
        clienteDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(final Pageable pageable) {
        // TODO Auto-generated method stub
        return clienteDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(final String term) {
        return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        // TODO Auto-generated method stub
        facturaDao.save(factura);
    }

    @Override
    @Transactional(readOnly=true)
    public Producto findProductoById(Long id) {
        // TODO Auto-generated method stub
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findFacturaById(Long id) {
        // TODO Auto-generated method stub
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    public void deleteFactura(Long id) {
        // TODO Auto-generated method stub
            facturaDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id) {
        // TODO Auto-generated method stub
        return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente fetchByIdWithFacturas(Long id) {
        // TODO Auto-generated method stub
        return clienteDao.fetchByIdWithFacturas(id);
    }

    
    
}
