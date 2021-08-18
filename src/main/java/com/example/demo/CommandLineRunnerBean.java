package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CartRepository cartRepository;

    public void run(String...args){
        User admin = new User("admin", "admin", true);
        Role adminRole = new Role("admin", "ROLE_ADMIN");

        User user = new User("user", "user", true);
        Role userRole = new Role("user", "ROLE_USER");
        user.setAddress("1234 xxxxx st");
        user.setEmail("user@domain.com");
        user.setState("MD");
        user.setCity("Montgomery");
        user.setPhone("555-555-5555");

        userRepository.save(admin);
        roleRepository.save(adminRole);

        userRepository.save(user);
        roleRepository.save(userRole);

        Category birthday = new Category();
        birthday.setName("Birthday");
        Set<Product> birthdayProducts = new HashSet<>();
        birthday.setProducts(birthdayProducts);

        Category house = new Category();
        house.setName("Housewarming");
        Set<Product> houseProducts = new HashSet<>();
        house.setProducts(houseProducts);

        Category wedding = new Category();
        wedding.setName("Wedding Bouquet");
        Set<Product> weddingProducts = new HashSet<>();
        wedding.setProducts(weddingProducts);

        Product flutter = new Product();
        flutter.setName("Flutter");
        flutter.setPrice(58);
        flutter.setDescription("This is a great bouquet for birthdays.");
        flutter.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139581/hannah-olinger-CNgHaJwT5Pk-unsplash_fi2p0h.jpg");
        flutter.setCategory(birthday);
        Set<CartsAndProducts> flutterOrders = new HashSet<>();
        flutter.setOrders(flutterOrders);

        Product spirit = new Product();
        spirit.setName("Spirit Basket");
        spirit.setPrice(75);
        spirit.setDescription("Gorgeous bouquet that features carnations, stock, roses, lilies and Fuji mums.");
        spirit.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139580/leonardo-wong-7pGehyH7o64-unsplash_vqydow.jpg");
        spirit.setCategory(birthday);
        Set<CartsAndProducts> spiritOrders = new HashSet<>();
        spirit.setOrders(spiritOrders);

        Product eternal = new Product();
        eternal.setName("Eternal");
        eternal.setPrice(75);
        eternal.setDescription("An exuberance of bright and beautiful white blossoms provides an exquisite way to deliver your expressions of sympathy and comfort.");
        eternal.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139570/nathan-dumlao-gXVwCdhqwp4-unsplash_l4s1fb.jpg");
        eternal.setCategory(birthday);
        Set<CartsAndProducts> eternalOrders = new HashSet<>();
        eternal.setOrders(eternalOrders);

        Product belle = new Product();
        belle.setName("Belle of the Ball");
        belle.setPrice(45);
        belle.setDescription("Arranged with elegant florals and fragrance, this show-stopping collection of lilies and irises shares the perfect expression for any reason or occasion.");
        belle.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139567/carrie-beth-williams-s3AFTBZ3cnc-unsplash_aqu4xc.jpg");
        belle.setCategory(house);
        Set<CartsAndProducts> belleOrders = new HashSet<>();
        belle.setOrders(belleOrders);

        Product garden = new Product();
        garden.setName("Garden of Life");
        garden.setPrice(120);
        garden.setDescription("A gorgeous mix of bold, bright summer flowers is the best way to boost any mood.");
        garden.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139567/europeana-6c43FgRt0Dw-unsplash_tvxwae.jpg");
        garden.setCategory(house);
        Set<CartsAndProducts> gardenOrders = new HashSet<>();
        garden.setOrders(gardenOrders);

        Product light = new Product();
        light.setName("Light of my Life");
        light.setPrice(80);
        light.setDescription("The Light of My Life Bouquet blossoms with brilliant color and a sweet sophistication to create the perfect impression.");
        light.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139564/visual-stories-micheile-A5W9yhQBb6A-unsplash_pnjp3f.jpg");
        light.setCategory(house);
        Set<CartsAndProducts> lightOrders = new HashSet<>();
        light.setOrders(lightOrders);

        Product simple = new Product();
        simple.setName("Simple Charm");
        simple.setPrice(86);
        simple.setDescription("Capture the beauty of the seasons in bloom with our Simple Charm Bouquet.");
        simple.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139562/brigitte-tohm-buUFEtmNnjc-unsplash_cxhafa.jpg");
        simple.setCategory(house);
        Set<CartsAndProducts> simpleOrders = new HashSet<>();
        simple.setOrders(simpleOrders);

        Product sorbet = new Product();
        sorbet.setName("Sorbet");
        sorbet.setPrice(86);
        sorbet.setDescription("Sorbet Bouquet is curated with a full serving of freshness and fragrance by our local florists to make anyone's day sweeter.");
        sorbet.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139557/tai-s-captures-FlVYEh0aJcM-unsplash_ztdynf.jpg");
        sorbet.setCategory(wedding);
        Set<CartsAndProducts> sorbetOrders = new HashSet<>();
        sorbet.setOrders(sorbetOrders);

        Product bliss = new Product();
        bliss.setName("Blissful");
        bliss.setPrice(60);
        bliss.setDescription("This dreamy jewel toned bouquet combines bold color and eye catching texture to make a statement.");
        bliss.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139556/annie-spratt-WBpr_yH0Frg-unsplash_d0hgrr.jpg");
        bliss.setCategory(house);
        Set<CartsAndProducts> blissOrders = new HashSet<>();
        bliss.setOrders(blissOrders);

        Product peaceful = new Product();
        peaceful.setName("Peaceful Garden");
        peaceful.setPrice(65);
        peaceful.setDescription("Express warmth to your loved ones with this peaceful garden.");
        peaceful.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139557/evie-s-XjX3Ju1jFfU-unsplash_rnukfe.jpg");
        peaceful.setCategory(wedding);
        Set<CartsAndProducts> peacefulOrders = new HashSet<>();
        peaceful.setOrders(peacefulOrders);

        Product comfort = new Product();
        comfort.setName("Comfort and Grace");
        comfort.setPrice(95);
        comfort.setDescription("Our Comfort and Grace is elegantly crafted by our passionate and dedicated local florists to compliment your heartfelt sympathies.");
        comfort.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629139547/taya-iv-n0dWXoatfSc-unsplash_erbfre.jpg");
        comfort.setCategory(wedding);
        Set<CartsAndProducts> comfortOrders = new HashSet<>();
        comfort.setOrders(comfortOrders);

        Product sprinkle = new Product();
        sprinkle.setName("Sprinkles");
        sprinkle.setPrice(85);
        sprinkle.setDescription("Sprinkles make everything better. Overflowing with roses, carnations, gerbera daisies.");
        sprinkle.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629144980/niklas-ohlrogge-74QmIJDTD-c-unsplash_nicosu.jpg");
        sprinkle.setCategory(birthday);
        Set<CartsAndProducts> sprinkleOrders = new HashSet<>();
        sprinkle.setOrders(sprinkleOrders);

        Product peach = new Product();
        peach.setName("Peach Bellini");
        peach.setPrice(60);
        peach.setDescription("In the spirit of the season, this lively arrangement comes right from the fields to bring freshness to any space.");
        peach.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629144978/markus-clemens-mibjbNoS1XA-unsplash_ruog0s.jpg");
        peach.setCategory(house);
        Set<CartsAndProducts> peachOrders = new HashSet<>();
        peach.setOrders(peachOrders);

        Product summer = new Product();
        summer.setName("Summer Lovin'");
        summer.setPrice(60);
        summer.setDescription("With soft colors and rich texture, this bouquet elegantly expresses your appreciation for any reason.");
        summer.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629144976/annie-spratt-x4YEK7o8L0c-unsplash_hkubce.jpg");
        summer.setCategory(wedding);
        Set<CartsAndProducts> summerOrders = new HashSet<>();
        summer.setOrders(summerOrders);

        Product sweet = new Product();
        sweet.setName("Sweet Surprise");
        sweet.setPrice(76);
        sweet.setDescription("Whether it's for an occasion or a pick–me–up for yourself, this sweet arrangement is ready to delight you or your loved ones.");
        sweet.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629144971/christie-kim-0IsBu45B3T8-unsplash_lhb1iy.jpg");
        sweet.setCategory(wedding);
        Set<CartsAndProducts> sweetOrders = new HashSet<>();
        sweet.setOrders(sweetOrders);

        Product elegance = new Product();
        elegance.setName("Alluring Elegance");
        elegance.setPrice(105);
        elegance.setDescription("An illuminating array of florals brings an air of elegance to any room it's placed. ");
        elegance.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629144967/annie-spratt-01Wa3tPoQQ8-unsplash_trisvo.jpg");
        elegance.setCategory(wedding);
        Set<CartsAndProducts> eleganceOrders = new HashSet<>();
        elegance.setOrders(eleganceOrders);

        Product cherish = new Product();
        cherish.setName("Cherished Friend");
        cherish.setPrice(90);
        cherish.setDescription("For the friends who truly feel like family, share your thoughts and love with timeless flowers.");
        cherish.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629144967/annie-spratt-tODXQIEAh9M-unsplash_o7bwcl.jpg");
        cherish.setCategory(birthday);
        Set<CartsAndProducts> cherishOrders = new HashSet<>();
        cherish.setOrders(cherishOrders);

        Product precious = new Product();
        precious.setName("You're Precious");
        precious.setPrice(80);
        precious.setDescription("Handcrafted and inspired by the gorgeous hues of the season, the You're Precious Bouquet is full of sweet sentiment for your favorite person.");
        precious.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629144966/ananthu-selvam-62saZ_T5AP0-unsplash_ysq2je.jpg");
        precious.setCategory(birthday);
        Set<CartsAndProducts> preciousOrders = new HashSet<>();
        precious.setOrders(preciousOrders);

        Product champagne = new Product();
        champagne.setName("Pink Champagne");
        champagne.setPrice(65);
        champagne.setDescription("Our Pink Champagne Roses bring a pop of bubbly color to any home.");
        champagne.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629144963/jessie-daniella-QLuleNy8LMM-unsplash_znnx4s.jpg");
        champagne.setCategory(house);
        Set<CartsAndProducts> champagneOrders = new HashSet<>();
        champagne.setOrders(champagneOrders);

        Product joy = new Product();
        joy.setName("Joyful White Orchid");
        joy.setPrice(45);
        joy.setDescription("The most popular variety of this plant, the Phalaenopsis orchid makes a great gift for plant lovers and plant beginners alike");
        joy.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1629144960/zoe-schaeffer-V_MV68oW3FE-unsplash_qhzlti.jpg");
        joy.setCategory(birthday);
        Set<CartsAndProducts> joyOrders = new HashSet<>();
        joy.setOrders(joyOrders);

        weddingProducts.add(sorbet);
        weddingProducts.add(peaceful);
        weddingProducts.add(comfort);
        weddingProducts.add(summer);
        weddingProducts.add(sweet);
        weddingProducts.add(elegance);

        houseProducts.add(belle);
        houseProducts.add(garden);
        houseProducts.add(light);
        houseProducts.add(simple);
        houseProducts.add(bliss);
        houseProducts.add(peach);
        houseProducts.add(champagne);

        birthdayProducts.add(flutter);
        birthdayProducts.add(spirit);
        birthdayProducts.add(eternal);
        birthdayProducts.add(sprinkle);
        birthdayProducts.add(cherish);
        birthdayProducts.add(joy);
        birthdayProducts.add(precious);


        categoryRepository.save(wedding);
        categoryRepository.save(house);
        categoryRepository.save(birthday);

    }

}
