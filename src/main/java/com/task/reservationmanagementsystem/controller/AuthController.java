package com.task.reservationmanagementsystem.controller;

import com.task.reservationmanagementsystem.Util.JwtUtil;
import com.task.reservationmanagementsystem.Util.UserDetailsImp;
import com.task.reservationmanagementsystem.entity.Role;
import com.task.reservationmanagementsystem.entity.User;
import com.task.reservationmanagementsystem.model.Roles;
import com.task.reservationmanagementsystem.model.dto.request.LoginRequest;
import com.task.reservationmanagementsystem.model.dto.request.SignUpRequest;
import com.task.reservationmanagementsystem.model.dto.response.JwtResponse;
import com.task.reservationmanagementsystem.model.dto.response.MessageResponse;
import com.task.reservationmanagementsystem.repository.RoleRepository;
import com.task.reservationmanagementsystem.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws MessagingException {
        if (userRepository.existsByUsername(signUpRequest.username())){
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body(new MessageResponse("Error: Username is already taken",HttpStatus.CONFLICT,HttpServletResponse.SC_CONFLICT));
        }

        if (userRepository.existsByEmail(signUpRequest.email()))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use", HttpStatus.CONFLICT, HttpServletResponse.SC_CONFLICT));

        User user = new User(signUpRequest.username(), signUpRequest.name(), signUpRequest.surname(),signUpRequest.email(), passwordEncoder.encode(signUpRequest.password()),false);
        Set<String> strRoles = signUpRequest.role();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null){
            Role userRole = roleRepository.findByName(Roles.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(Roles.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(Roles.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        User user1 =userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered Successfuly", HttpStatus.CREATED,HttpServletResponse.SC_CREATED));

    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(),loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new JwtResponse(jwtCookie.toString(),"Bearer",null,userDetails.getUsername(),userDetails.getEmail(),roles));
    }
}
