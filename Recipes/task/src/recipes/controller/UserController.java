package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.entity.User;
import recipes.repository.UserRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("register")
    public void saveUser(@Valid @RequestBody User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

}
