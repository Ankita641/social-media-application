package com.learning.socialmediablog.social_media_blog_app.service.Impl;

import com.learning.socialmediablog.social_media_blog_app.entity.RoleEntity;
import com.learning.socialmediablog.social_media_blog_app.entity.UserEntity;
import com.learning.socialmediablog.social_media_blog_app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SocialMediaCustomizedUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

   /* @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserEntity user){

        //so password is save in database plain text
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    @Transactional
    public void updatedPasswords(){
        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users){
            if (!user.getPassword().startsWith("$2a$")){ //check if it's already hashed
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                System.out.println("Updated password for : " + user.getUsername());
            }
        }
    }*/

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        //Load the user by username or email
        UserEntity userEntity = this.userRepository
                .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found with Email or Username : " + usernameOrEmail));

        System.out.println("Fetched password before: " + userEntity.getPassword()); // Debugging

     /*   if (!userEntity.getPassword().startsWith("$2a$")){
            throw new IllegalArgumentException("Stored Password is not encoded in BCrypt!");
        }*/

        //Fetch all the roles associated with user
        Set<RoleEntity> userRoles = userEntity.getRoles();

        //Convert or map Roles in to spring understand format (Granted Authority)
        Set<GrantedAuthority> grantedAuthoritySet = userRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" +  role.getName()))
                .collect(Collectors.toSet());

       // Debugging : print password format
        System.out.println("Fetched Password after : " + userEntity.getPassword());


//        User user = new User(userEntity.getEmail(), userEntity.getPassword(), grantedAuthoritySet);
//        return user;

        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), grantedAuthoritySet);
    }
}
