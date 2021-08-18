package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

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
    CategoryRepository categoryRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartsAndProductsRepository cartsAndProductsRepository;

    ArrayList<Product> products = new ArrayList<>();

    @RequestMapping("/")
    public String homePage(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("carts", cartRepository.findAll());
        return "home";
    }

    @RequestMapping("/flowers")
    public String showAllFlowers(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("carts", cartRepository.findAll());
        return "flowers";
    }

    @RequestMapping("/birthday")
    public String showAllBirthdayCategory(Model model){
        model.addAttribute("category", categoryRepository.findByName("Birthday"));
        model.addAttribute("carts", cartRepository.findAll());
        return "birthday";
    }

    @RequestMapping("/wedding")
    public String showAllWeddingCategory(Model model){
        model.addAttribute("category", categoryRepository.findByName("Wedding Bouquet"));
        model.addAttribute("carts", cartRepository.findAll());
        return "wedding";
    }

    @RequestMapping("/house")
    public String showAllHousewarmingCategory(Model model){
        model.addAttribute("category", categoryRepository.findByName("Housewarming"));
        model.addAttribute("carts", cartRepository.findAll());
        return "house";
    }


    @RequestMapping("/product/{id}")
    public String showShoppingCart(@PathVariable("id") long id, Model model){
        model.addAttribute("product", productRepository.findById(id).get());
        model.addAttribute("carts", cartRepository.findAll());
        return "shoppingcart";
    }


    @RequestMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") long id, Model model){
        products.add(productRepository.findById(id).get());
        model.addAttribute("products", products);
        return "shoppingcart";
    }

    @RequestMapping("/placeOrder")
    public String placeOrder(Principal principal, Model model){
        Cart cart = new Cart();
        cartRepository.save(cart);
        for (Product product : products){
            CartsAndProducts order = new CartsAndProducts();
            order.setProduct(product);
            order.setCart(cart);
            cartsAndProductsRepository.save(order);
        }


        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        cart.setUser(user);
        user.getCarts().add(cart);
        userRepository.save(user);

        products.clear();
        model.addAttribute("user", user);
        return "test";
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        products.clear();
        return "redirect:/login?logout=true";
    }













    @GetMapping("/search")
    public String searchProduct(Model model){
        return "home";
    }

    @PostMapping("/search")
    public String searchProduct(Model model, @RequestParam(name="name") String name){
        ArrayList<Product> results = (ArrayList<Product>) productRepository.findAllByNameContaining(name);
        model.addAttribute("results", results);
        model.addAttribute("carts", cartRepository.findAll());
        return "result";
    }





}
