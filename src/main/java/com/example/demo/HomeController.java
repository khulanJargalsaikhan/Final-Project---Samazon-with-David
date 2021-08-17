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
    CloudinaryConfig cloudc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @RequestMapping("/")
    public String homePage(){
        return "home";
    }

    @RequestMapping("/flowers")
    public String showAllFlowers(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("carts", cartRepository.findAll());
        return "flowers";
    }

    @RequestMapping("/product/{id}")
    public String showShoppingCart(@PathVariable("id") long id, Model model){
        model.addAttribute("product", productRepository.findById(id).get());
        model.addAttribute("carts", cartRepository.findAll());
        return "shoppingcart";
    }







    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }
}
