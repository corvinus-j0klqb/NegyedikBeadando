package com.NegyedikBeadando.NegyedikBeadando.Controller;

import com.NegyedikBeadando.NegyedikBeadando.DAO.Entity.User;
import com.NegyedikBeadando.NegyedikBeadando.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }

    @PostMapping("/process_register")
    public String processRegister(Model model, User user) {
        var userList = userRepo.findUserByUserName(user.getUserName());

        boolean containsDigit = false;
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            for (char c : user.getPassword().toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        // Username OK and password OK
        if (userList.size() == 0 && user.getPassword().matches(".*[^a-z].*") && containsDigit) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            userRepo.save(user);

            return "registration_complete";
        }

        // Username Incorrect
        if (userList.size() > 0) {
            model.addAttribute("usernameExists", true);
            return "registration";
        }

        // Password Incorrect
        if (!user.getPassword().matches("[A-Za-z]+") ||
                !user.getPassword().matches("[0-9]+")) {
            model.addAttribute("incorrectPw", true);
            return "registration";
        }

        return "registration";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }
}
