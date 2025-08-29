package com.poseidon.IT;

import com.poseidon.domain.*;
import com.poseidon.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class SetUpIT {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected BidListRepository bidListRepository;
    @Autowired
    protected CurvePointRepository curvePointRepository;
    @Autowired
    protected RatingRepository ratingRepository;
    @Autowired
    protected RuleNameRepository ruleNameRepository;
    @Autowired
    protected TradeRepository tradeRepository;
    @Autowired
    protected UserRepository userRepository;

    protected PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        // Clean all repos
        bidListRepository.deleteAll();
        curvePointRepository.deleteAll();
        ratingRepository.deleteAll();
        ruleNameRepository.deleteAll();
        tradeRepository.deleteAll();
        userRepository.deleteAll();

        // Insert base entities
        bidListRepository.save(new BidList("acc1", "type1", 10.0));
        bidListRepository.save(new BidList("acc2", "type2", 20.0));

        curvePointRepository.save(new CurvePoint(10, 10.0, 10.0));
        curvePointRepository.save(new CurvePoint(20, 10.0, 20.0));

        ratingRepository.save(new Rating("Aaa", "AA+", "AAA", 1));
        ratingRepository.save(new Rating("Baa", "BB+", "BBB", 2));

        ruleNameRepository.save(new RuleName("Aaa", "AA", "AAA", "AaA, AAa", "aAA", "aaA"));
        ruleNameRepository.save(new RuleName("Baa", "BB", "BBB", "BbB, Bbb", "bBB", "bbB"));

        tradeRepository.save(new Trade("acc1", "type1", 10.0, 20.0));
        tradeRepository.save(new Trade("acc2", "type2", 20.0, 10.0));

        // Users
        User admin = new User();
        admin.setUsername("admin");
        admin.setFullname("Admin");
        admin.setPassword(passwordEncoder.encode("motDePasse123"));
        admin.setRole("ADMIN");

        User user1 = new User();
        user1.setUsername("user1");
        user1.setFullname("User1");
        user1.setPassword(passwordEncoder.encode("motDePasse123"));
        user1.setRole("USER");

        userRepository.save(admin);
        userRepository.save(user1);

        // Setup Spring Security context
        UserDetails userConnected = org.springframework.security.core.userdetails.User
                .withUsername("testuser")
                .password("pass")
                .roles("USER")
                .build();

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userConnected, userConnected.getUsername(), userConnected.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
