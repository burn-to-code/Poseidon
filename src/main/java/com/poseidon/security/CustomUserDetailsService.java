package com.poseidon.security;

import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des détails utilisateur pour Spring Security.
 * <p>
 * Cette classe implémente {@link UserDetailsService} afin de fournir
 * les informations nécessaires à l'authentification des utilisateurs.
 * Elle interagit avec le {@link UserRepository} pour récupérer les utilisateurs
 * stockés en base de données.
 * </p>
 *
 * <p>
 * Le rôle principal de cette classe est de transformer l'entité {@link User}
 * de l'application en objet {@link UserDetails} utilisable par Spring Security.
 * </p>
 *
 * <p>
 * L'annotation {@link Service} permet à Spring de détecter cette classe comme
 * un bean de service et l'annotation {@link AllArgsConstructor} génère automatiquement
 * un constructeur avec tous les champs finaux.
 * </p>
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Charge un utilisateur par son nom d'utilisateur.
     * <p>
     * Cette méthode est appelée par Spring Security lors du processus d'authentification.
     * Elle récupère l'utilisateur correspondant dans la base de données et construit un
     * {@link UserDetails} contenant :
     * <ul>
     *     <li>Le nom d'utilisateur</li>
     *     <li>Le mot de passe haché</li>
     *     <li>Les rôles de l'utilisateur</li>
     * </ul>
     * </p>
     *
     * @param username Le nom d'utilisateur fourni lors de la tentative de connexion.
     * @return Un objet {@link UserDetails} contenant les informations nécessaires à Spring Security.
     * @throws UsernameNotFoundException Si aucun utilisateur avec ce nom n'est trouvé en base.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

    }
}
