package datajpacom.example.proyectoconjpa.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import datajpacom.example.proyectoconjpa.dao.IUsuarioDao;
import datajpacom.example.proyectoconjpa.entities.Role;
import datajpacom.example.proyectoconjpa.entities.Usuario;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Usuario usuario = usuarioDao.findByUsername(username);

        if(usuario==null){
            logger.error("Error login: no existe el usuario " +username);
            throw new UsernameNotFoundException("username " + username + " no existe en el sistema");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Role role: usuario.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getAuthoritY()));
        }
        if(authorities.isEmpty()){
            logger.error("El usuario " + username + " no tienen rol asignado");
            throw new UsernameNotFoundException("El usuario " + username + " no tienen rol asignado");
        }
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }
    
}
