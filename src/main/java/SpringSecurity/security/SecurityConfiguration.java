package SpringSecurity.security;


import SpringSecurity.model.PRole;
import SpringSecurity.model.Person;
import SpringSecurity.model.PersonPrimaryKey;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.formLogin(Customizer.withDefaults())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/persons/by-city").permitAll()
                        .requestMatchers("/persons/by-age:{age}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/persons/by-name-and-surname").hasAnyRole("ADMIN")
                        .anyRequest().authenticated());


        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.builder()
                .username("Admin")
                .password(encoder().encode("password"))
                .roles("ADMIN")
                .build();
        UserDetails firstUser = User.builder()
                .username("First")
                .password(encoder().encode("passwordFirst"))
                .roles("USER")
                .build();
        UserDetails secondUser = User.builder()
                .username("Second")
                .password(encoder().encode("passwordSecond"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, firstUser, secondUser);
    }


}

