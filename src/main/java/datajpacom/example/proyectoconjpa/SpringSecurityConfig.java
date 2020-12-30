package datajpacom.example.proyectoconjpa;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import datajpacom.example.proyectoconjpa.auth.handler.LoginSuccessHandler;
import datajpacom.example.proyectoconjpa.service.JpaUserDetailsService;
//Se puede usarprePostEnable
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   @Autowired
   private JpaUserDetailsService userDetailsService;

    @Autowired
    public void configureglobal(AuthenticationManagerBuilder builder) throws Exception {
       /* PasswordEncoder encoder = this.passwordEncoder;
        UserBuilder users = User.builder().passwordEncoder(password -> encoder.encode(password));
        builder.inMemoryAuthentication().withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
                .withUser(users.username("brayan").password("12345").roles("USER"));
    */
       builder.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
        
    }

    @Autowired
    private LoginSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
        /*.antMatchers("/uploads/**").hasAnyRole("USER")
        .antMatchers("/ver/**").hasAnyRole("USER")
        .antMatchers("/form/**").hasAnyRole("ADMIN")
        .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
        .antMatchers("/factura/**").hasAnyRole("ADMIN")*/
        .anyRequest().authenticated()
        .and()
            .formLogin()
            .successHandler(successHandler)
            .loginPage("/login")
            .permitAll()
        .and()
        .logout().permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/error_403");
    }

}
