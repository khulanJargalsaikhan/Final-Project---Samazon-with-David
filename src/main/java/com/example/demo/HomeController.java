package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
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

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    ProductHistoryRepository productHistoryRepository;

    ArrayList<Product> products = new ArrayList<>();
    private double total = 0;
    private double shipping = 0;
    private double tax = 0;
    private double subtotal = 0;

    @RequestMapping("/")
    public String homePage(Model model){
        model.addAttribute("cartCount", products.size());
        model.addAttribute("products", productRepository.findAll());
        return "home";
    }

    @RequestMapping("/flowers")
    public String showAllFlowers(Model model){
        model.addAttribute("cartCount", products.size());
        model.addAttribute("products", productRepository.findAll());
        return "flowers";
    }

    @RequestMapping("/birthday")
    public String showAllBirthdayCategory(Model model){
        model.addAttribute("cartCount", products.size());
        model.addAttribute("category", categoryRepository.findByName("Birthday"));
        return "birthday";
    }

    @RequestMapping("/wedding")
    public String showAllWeddingCategory(Model model){
        model.addAttribute("cartCount", products.size());
        model.addAttribute("category", categoryRepository.findByName("Wedding Bouquet"));
        return "wedding";
    }

    @RequestMapping("/house")
    public String showAllHousewarmingCategory(Model model){
        model.addAttribute("cartCount", products.size());
        model.addAttribute("category", categoryRepository.findByName("Housewarming"));
        return "house";
    }


    @RequestMapping("/product/{id}")
    public String showShoppingCart(@PathVariable("id") long id, Model model){
        model.addAttribute("cartCount", products.size());
        model.addAttribute("product", productRepository.findById(id).get());
        model.addAttribute("carts", cartRepository.findAll());
        return "shoppingcart";
    }


    @RequestMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") long id, Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        products.add(productRepository.findById(id).get());
        subtotal += productRepository.findById(id).get().getPrice();
        return "redirect:/shoppingCart";
    }

    @RequestMapping("/shoppingCart")
    public String shoppingCart(Model model){
        model.addAttribute("cartCount", products.size());
        model.addAttribute("products", products);
        if (subtotal <= 50 && !products.isEmpty()){
            shipping = 5.99;
        }
        else {
            shipping = 0;
        }
        tax = Math.round((subtotal * 0.06) * 100.0) /100.0;
        total = Math.round((tax + shipping + subtotal) * 100.0) / 100.0;

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
        LocalDate date = LocalDate.now();
        cart.setDate(date);
        cartRepository.save(cart);
        for (Product product : products){
            CartsAndProducts order = new CartsAndProducts();
            order.setProduct(product);
            order.setCart(cart);
            cartsAndProductsRepository.save(order);
            ProductHistory productHistory = new ProductHistory();
            productHistory.createProductHistory(product);
            productHistory.setCart(cart);
            productHistoryRepository.save(productHistory);
        }


        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        cart.setUser(user);
        user.getCarts().add(cart);
        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("samazon12345@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Samazon Order Confirmation");

        String emailMessage = "Products \n===========================================================================\n\n";
        for (Product product : products){
            emailMessage += String.format("%-120s $%.2f\n", product.getName(), product.getPrice()).replace("  ", "..");
        }
        emailMessage += "\n===========================================================================\n";
        emailMessage += String.format("%120s: $%.2f \n%120s: $%.2f \n%120s: $%.2f", "Subtotal", subtotal, "Tax", tax, "Total", total);
        message.setText(emailMessage);

        try {
            mailSender.send(message);
        } catch(MailException e) {
            e.printStackTrace();
        }
        model.addAttribute("user", user);
        tax = 0;
        total = 0;
        shipping = 0;
        subtotal = 0;
        products.clear();
        return "confirmationpage";
    }

    @RequestMapping("/removeItem/{id}")
    public String removeFromCart(@PathVariable("id") long id){
        for(Product product : products){
            if (product.getId() == id){
                products.remove(product);
                subtotal -= product.getPrice();
                tax = 0;
                break;
            }
        }
        return "redirect:/shoppingCart";
    }


    @RequestMapping("/login")
    public String login(Model model){
        tax = 0;
        subtotal = 0;
        total = 0;
        shipping = 0;
        products.clear();
        model.addAttribute("cartCount", products.size());
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(Model model) {
        return "redirect:/login?logout=true";
    }

    @GetMapping("/search")
    public String searchProduct(Model model){
        model.addAttribute("cartCount", products.size());
        return "home";
    }

    @PostMapping("/search")
    public String searchProduct(Model model, @RequestParam(name="name") String name){
        model.addAttribute("cartCount", products.size());
        ArrayList<Product> results = (ArrayList<Product>) productRepository.findAllByNameContainingIgnoreCase(name);
        model.addAttribute("results", results);
        model.addAttribute("carts", cartRepository.findAll());
        return "result";
    }





    @RequestMapping("/userInfo")
    public String userInfomration(Model model, Principal principal){
        model.addAttribute("cartCount", products.size());
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "userinformation";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model){
        model.addAttribute("cartCount", products.size());
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "productform";
    }

    @PostMapping("/processProduct")
    public String processProduct(@Valid Product product, BindingResult result, @RequestParam("file") MultipartFile file, Model model){
        model.addAttribute("cartCount", products.size());
        if (result.hasErrors()){
            return "productform";
        }
        if(file.isEmpty() && (product.getPhoto() == null || product.getPhoto().isEmpty())){
            product.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629329688/default_product_lq4klk.png");
        }
        else if(!file.isEmpty()){
            try{
                Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                product.setPhoto(uploadResult.get("url").toString());
            } catch (IOException e){
                e.printStackTrace();
                return "redirect:/addProduct";
            }
        }
        productRepository.save(product);
        return "redirect:/flowers";
    }

    @RequestMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable("id") long id, Model model){
        model.addAttribute("cartCount", products.size());
        model.addAttribute("product", productRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        return "productform";
    }

    @RequestMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        productRepository.deleteById(id);
        return "redirect:/flowers";
    }

    @RequestMapping("/productDetails/{id}")
    public String productDetails(@PathVariable("id") long id, Model model){
        model.addAttribute("cartCount", products.size());
        model.addAttribute("product", productRepository.findById(id).get());
        return "productdetails";
    }

}
