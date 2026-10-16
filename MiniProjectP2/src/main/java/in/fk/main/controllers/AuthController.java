package in.fk.main.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.fk.main.dto.LoginRequest;
import in.fk.main.security.JwtUtil;
import in.fk.main.user.User;
import in.fk.main.user.UserService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest dto) {

	    if (dto.getEmail() == null || dto.getPassword() == null) {
	        return ResponseEntity.badRequest().body("Email or password missing");
	    }

	    User user = userService.findByEmail(dto.getEmail());

	    if (user == null || !user.getPassword().equals(dto.getPassword())) {
	        return ResponseEntity.status(401).body("Invalid credentials");
	    }

	    String token = JwtUtil.generateToken(user.getEmail(), user.getRole());
	    
	    System.out.println("TOKEN ROLE: " + user.getRole());

	    // ✅ RETURN JSON INSTEAD OF RAW STRING
	    return ResponseEntity.ok(
	            Map.of(
	                "status", "success",
	                "token", token,
	                "role", user.getRole()
	            )
	    );
	}
	
}