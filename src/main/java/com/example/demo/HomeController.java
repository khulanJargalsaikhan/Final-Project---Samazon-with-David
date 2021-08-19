package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

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
    private double total = 0;
    private double shipping = 0;
    private double tax = 0;

    @RequestMapping("/")
    public String homePage(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("cartItems", products.size());
        return "home";
    }

    @RequestMapping("/flowers")
    public String showAllFlowers(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("cartItems", products.size());
        return "flowers";
    }

    @RequestMapping("/birthday")
    public String showAllBirthdayCategory(Model model){
        model.addAttribute("category", categoryRepository.findByName("Birthday"));
        model.addAttribute("cartItems", products.size());
        return "birthday";
    }

    @RequestMapping("/wedding")
    public String showAllWeddingCategory(Model model){
        model.addAttribute("category", categoryRepository.findByName("Wedding Bouquet"));
        model.addAttribute("cartItems", products.size());
        return "wedding";
    }

    @RequestMapping("/house")
    public String showAllHousewarmingCategory(Model model){
        model.addAttribute("category", categoryRepository.findByName("Housewarming"));
        model.addAttribute("cartItems", products.size());
        return "house";
    }


    @RequestMapping("/product/{id}")
    public String showShoppingCart(@PathVariable("id") long id, Model model){
        model.addAttribute("product", productRepository.findById(id).get());
        model.addAttribute("carts", cartRepository.findAll());
        model.addAttribute("cartItems", products.size());
        return "shoppingcart";
    }


    @RequestMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") long id, Model model){
        products.add(productRepository.findById(id).get());
        total += productRepository.findById(id).get().getPrice();
        return "redirect:/shoppingCart";
    }

    @RequestMapping("/shoppingCart")
    public String shoppingCart(Model model){
        model.addAttribute("cartItems", products.size());
        model.addAttribute("products", products);
        double subtotal = total;
        if (total <= 50 && !products.isEmpty()){
            shipping = 5.99;
        }
        tax = Math.round((total * 0.06) * 100.0) /100.0;
        total = Math.round((tax + shipping + total) * 100.0) / 100.0;

        model.addAttribute("total", total);
        model.addAttribute("shipping", shipping);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("tax", tax);
        return "shoppingcart";
    }

    @RequestMapping("/placeOrder")
    public String placeOrder(Principal principal, Model model){
        model.addAttribute("total", total);
        if (principal == null){
            return "redirect:/login";
        }
        if (products.isEmpty()){
            return "redirect:/shoppingCart";
        }
        Cart cart = new Cart();
        cart.setTax(tax);
        cart.setTotal(total);
        cart.setShipping(shipping);
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
        tax = 0;
        total = 0;
        shipping = 0;
        return "confirmationpage";
    }

    @RequestMapping("/removeItem/{id}")
    public String removeFromCart(@PathVariable("id") long id){
        for(Product product : products){
            if (product.getId() == id){
                products.remove(product);
                break;
            }
        }
        return "redirect:/shoppingCart";
    }



    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("cartItems", products.size());
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        tax = 0;
        total = 0;
        shipping = 0;
        products.clear();
        return "redirect:/login?logout=true";
    }






    @GetMapping("/search")
    public String searchProduct(Model model){
        model.addAttribute("cartItems", products.size());
        return "home";
    }

    @PostMapping("/search")
    public String searchProduct(Model model, @RequestParam(name="name") String name){
        model.addAttribute("cartItems", products.size());
        ArrayList<Product> results = (ArrayList<Product>) productRepository.findAllByNameContainingIgnoreCase(name);
        model.addAttribute("results", results);
        model.addAttribute("carts", cartRepository.findAll());
        return "result";
    }





}
