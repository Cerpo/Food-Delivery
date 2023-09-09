package com.cerpo.fd.repository;

import com.cerpo.fd.model.user.Role;
import com.cerpo.fd.model.user.User;
import com.cerpo.fd.model.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Date;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TestUserRepository {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void itShouldSelectUserByEmail() {
        User user = new User("Test@test.com", new BCryptPasswordEncoder().encode("TestPW"), new Date(), Role.ROLE_CUSTOMER);

        userRepository.save(user);
        Optional<User> dbUser = userRepository.findByEmail("Test@test.com");

        assertThat(dbUser).isNotNull().get().isEqualTo(user);
    }
}
