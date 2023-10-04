package SpringSecurity.security;


import SpringSecurity.model.PRole;
import SpringSecurity.model.Person;
import SpringSecurity.model.PersonPrimaryKey;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@AllArgsConstructor

/*
@EnableGlobalMethodSecurity в спрингБуте с 3 версии устаревший!

@EnableMethodSecurity на замену
Обратите внимание, что вы можете избежать использования prePostEnabled = true, поскольку по умолчанию используется true.

boolean prePostEnabled() default true;
boolean jsr250Enabled() default false;
boolean proxyTargetClass() default false;
 */

@EnableMethodSecurity(proxyTargetClass = true, jsr250Enabled = true, securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.builder()
                .username("Nikola")
                .password(encoder().encode("password"))
                .roles("READ", "WRITE", "DELETE")
                .build();
        UserDetails firstUser = User.builder()
                .username("First")
                .password(encoder().encode("passwordFirst"))
                .roles("WRITE")
                .build();
        UserDetails secondUser = User.builder()
                .username("Second")
                .password(encoder().encode("passwordSecond"))
                .roles("DELETE")
                .build();

        return new InMemoryUserDetailsManager(admin, firstUser, secondUser);
    }


}

