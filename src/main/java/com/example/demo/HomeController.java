package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

//    @RequestMapping("/addToCart/{id}")
//    public String addToCart(@PathVariable("id") long id){
//       Product product = productRepository.findById(id).get();
//
//    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }
}
