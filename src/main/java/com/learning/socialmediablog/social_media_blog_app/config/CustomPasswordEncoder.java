package com.learning.socialmediablog.social_media_blog_app.config;

import com.learning.socialmediablog.social_media_blog_app.entity.RoleEntity;
import com.learning.socialmediablog.social_media_blog_app.entity.UserEntity;
import com.learning.socialmediablog.social_media_blog_app.repository.RoleRepository;
import com.learning.socialmediablog.social_media_blog_app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();   //store plain text
    }

    @Override
    public boolean matches(CharSequence rawPassword, String storedPassword) {
        if (storedPassword.startsWith("$2a$")){
            return bCryptPasswordEncoder.matches(rawPassword, storedPassword);
        }
        return rawPassword.toString().equals(storedPassword);
    }

  /*  @Bean
    CommandLineRunner initUsers(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()){
                RoleEntity adminRole = new RoleEntity();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            if (userRepository.findByUsername("admin").isEmpty()){
                UserEntity adminUser = new UserEntity();
                adminUser.setName("Admin User");
                adminUser.setUsername("Admin");
                adminUser.setPassword("admin123");
                adminUser.setEmail("Admin@gmail.com");
                adminUser.getRoles().add(roleRepository.findByName("ROLE_ADMIN").get());
                userRepository.save(adminUser);
            }
        };
    }
*/
}
