package com.example.typeface.controller;



import com.example.typeface.config.JwtUtils;
import com.example.typeface.dto.JwtResponse;
import com.example.typeface.dto.LoginRequest;
import com.example.typeface.dto.RegisterRequest;
import com.example.typeface.entity.User;
import com.example.typeface.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository repo, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {

        if (repo.existsByEmail(req.getEmail()))
            return "Email already exists";

        if (repo.existsByUsername(req.getUsername()))
            return "Username already exists";

        User u=new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));

        repo.save(u);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest req) {
        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        return new JwtResponse(token, user.getEmail(), user.getUsername());
    }
}

