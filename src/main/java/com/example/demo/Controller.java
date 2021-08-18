package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartsAndProductsRepository cartsAndProductsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping("/userInfo")
    public String userInfomration(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "userinformation";
    }


}
