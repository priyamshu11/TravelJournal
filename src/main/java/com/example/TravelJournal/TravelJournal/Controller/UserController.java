package com.example.TravelJournal.TravelJournal.Controller;

import com.example.TravelJournal.TravelJournal.Configuration.CustomUserDetailsService;
import com.example.TravelJournal.TravelJournal.Configuration.JwtUtil;
import com.example.TravelJournal.TravelJournal.Model.Users;
import com.example.TravelJournal.TravelJournal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public UserController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")

    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        try {
            Users newUser = userService.saveUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Users user) {
        logger.info("Login initialized");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {

//        return userService.findByUsername(username);
        logger.info("Fetching user by username: {}", username);

        Users user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            logger.warn("User with username {} not found", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable String id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.findAllUsers();
    }

    @PutMapping("/{id}")
    public Users updateUser(@PathVariable String id, @RequestBody Users user) {
        return userService.updateUser(id, user.getUsername(), user.getPassword());
    }

    @DeleteMapping("/{id}")
    public boolean deleteUserById(@PathVariable String id) {
        return userService.deleteUserById(id);
    }

    @DeleteMapping("/username/{username}")
    public boolean deleteUserByUsername(@PathVariable String username) {
        return userService.deleteUserByUsername(username);
    }
}
