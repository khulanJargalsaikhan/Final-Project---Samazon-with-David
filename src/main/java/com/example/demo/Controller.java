package com.example.demo;


import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

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

    @GetMapping("/addProduct")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "productform";
    }

    @PostMapping("/processProduct")
    public String processProduct(@Valid Product product, BindingResult result, @RequestParam("file") MultipartFile file){
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
        model.addAttribute("product", productRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        return "productform";
    }

    @RequestMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        productRepository.deleteById(id);
        return "redirect:/flowers";
    }
}
