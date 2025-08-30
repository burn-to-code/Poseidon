package com.poseidon.config;

import com.poseidon.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration principale de la sécurité Spring pour l'application Poseidon.
 * <p>
 * Cette classe configure :
 * <ul>
 *     <li>Les règles d'accès aux différentes URLs.</li>
 *     <li>Le formulaire de connexion personnalisé.</li>
 *     <li>Le processus de déconnexion.</li>
 *     <li>Le service de gestion des utilisateurs via {@link CustomUserDetailsService}.</li>
 *     <li>L'encodeur de mots de passe {@link BCryptPasswordEncoder} pour sécuriser les mots de passe.</li>
 * </ul>
 *
 * <p>
 * Les annotations principales utilisées :
 * <ul>
 *     <li>{@link Configuration} : indique que cette classe contient des beans Spring.</li>
 *     <li>{@link EnableWebSecurity} : active la sécurité web de Spring Security.</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructeur de la configuration Spring Security.
     *
     * @param customUserDetailsService Service personnalisé pour récupérer les détails des utilisateurs.
     */

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Configure la chaîne de filtres de sécurité pour HTTP.
     * <p>
     * Cette méthode définit :
     * <ul>
     *     <li>Les URLs publiques : "/user/**", "/css/**" et "/" accessibles sans authentification.</li>
     *     <li>Les autres URLs nécessitent une authentification.</li>
     *     <li>La page de connexion personnalisée à "/app/login" et la redirection après succès vers "/admin/home".</li>
     *     <li>Le mécanisme de déconnexion via "/app-logout" et redirection vers la page de login avec paramètre "logout".</li>
     *     <li>Le service de gestion des utilisateurs {@link CustomUserDetailsService} pour l'authentification.</li>
     * </ul>
     *
     * @param http L'objet {@link HttpSecurity} utilisé pour configurer la sécurité web.
     * @return La chaîne de filtres de sécurité {@link SecurityFilterChain} construite.
     * @throws Exception Si une erreur survient lors de la configuration de la sécurité.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**", "/css/**", "/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .defaultSuccessUrl("/admin/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/app-logout")
                        .logoutSuccessUrl("/app/login?logout")
                        .permitAll()
                )
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    /**
     * Bean pour encoder les mots de passe utilisateurs avec l'algorithme BCrypt.
     * <p>
     * Cet encodeur est utilisé pour :
     * <ul>
     *     <li>Hasher les mots de passe lors de l'enregistrement d'un utilisateur.</li>
     *     <li>Vérifier les mots de passe lors de l'authentification.</li>
     * </ul>
     *
     * @return Une instance de {@link BCryptPasswordEncoder}.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
