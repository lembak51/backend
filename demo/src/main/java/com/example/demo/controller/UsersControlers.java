package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Role;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UsersControlers {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public void AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping("/get")
    public List<UserEntity> list() {
        List<UserEntity> userEntityList =userRepository.findAll();
        userEntityList.stream().forEach(x -> x.setPassword(null));
        return userEntityList;

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserEntity> get(@PathVariable Integer id) {
        try {
            UserEntity user = userRepository.findById(id).get();
            user.setPassword(null);
            return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<UserEntity>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getCurrentUser")
    public ResponseEntity<UserEntity> get() {
        try {
           // UserEntity user = userRepository.findById(id).get();
          //  user.setPassword(null);

            UserEntity user = userRepository.findByUsername(getContext().getAuthentication().getName()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
            user.setPassword(null);
            return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<UserEntity>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getRoleByID/{id}")
    public ResponseEntity<String> getTypeOfUser(@PathVariable Integer id) {
        try {
           Optional<UserEntity> type = userRepository.findById(id);
            //String name = SecurityContextHolder.getContext().getAuthentication().getName();

            //System.out.println(type);
           return new ResponseEntity< String>(type.get().getRoles().get(0).getName(), HttpStatus.OK);
          //  return new ResponseEntity<>(userRepository.findRoleNamesByLoginId(id).get(0), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity< String>("User not exists ",HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode((userDTO.getPassword())));

        Role roles = roleRepository.findById(userDTO.getIdRole()).get();
        user.setRoles(singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("New user added successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO, @PathVariable Integer id) {
        try {
            userRepository.findById(id).ifPresent(user1->{user1.setUsername(userDTO.getUsername());
            user1.setPassword(passwordEncoder.encode((userDTO.getPassword())));
                userRepository.save(user1);});
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }


}
