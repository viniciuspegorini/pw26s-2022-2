package br.edu.utfpr.pb.pw26s.server.service;

import br.edu.utfpr.pb.pw26s.server.model.AuthProvider;
import br.edu.utfpr.pb.pw26s.server.model.Authority;
import br.edu.utfpr.pb.pw26s.server.model.User;
import br.edu.utfpr.pb.pw26s.server.repository.AuthorityRepository;
import br.edu.utfpr.pb.pw26s.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserService {

    UserRepository userRepository;

    AuthorityRepository authorityRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService (UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.authorityRepository = authorityRepository;
    }

    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProvider(AuthProvider.local);
        user.setUserAuthorities(new HashSet<>());
        user.getUserAuthorities().add(authorityRepository.findById(1L).orElse(new Authority()));
        return userRepository.save(user);
    }

}
