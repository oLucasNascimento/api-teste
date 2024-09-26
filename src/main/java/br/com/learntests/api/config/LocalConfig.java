package br.com.learntests.api.config;

import br.com.learntests.api.domain.User;
import br.com.learntests.api.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void startDb() {
        User u1 = new User(null, "Lucas", "lucas@mail", "123");
        User u2 = new User(null, "Luska", "luska@mail", "123");

        this.userRepository.saveAll(List.of(u1, u2));
    }

}
