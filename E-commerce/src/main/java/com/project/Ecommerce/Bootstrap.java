package com.project.Ecommerce;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    @Lazy
    public void run(ApplicationArguments args) throws Exception {


            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Set<User> users = new HashSet<>();
            Set<Role> roles = new HashSet<>();
            Set<Address> addresses = new HashSet<>();
            Customer user = new Customer();
            user.setFirstName("pinki");
            user.setLastName("bhati");
            user.setEnabled(true);
            user.setActive(true);
            user.setCreatedBy("pinki");
            user.setContact("+917564389210");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setUsername("bhatipinki0@gmail.com");
            Role role = new Role();
            role.setRoleName("ROLE_ADMIN");
            users.add(user);
            role.setUsers(users);
            roles.add(role);
            user.setRoles(roles);
            Address address = new Address();
            address.setCity("delhi");
            address.setCountry("india");
            address.setAddressLine("sadar");
            address.setLabel("office");
            address.setZipCode(110095);
            address.setState("delhi");
            address.setUser(user);
            addresses.add(address);
            user.setAddresses(addresses);
            userRepository.save(user);

            Set<User> userSet = new HashSet<>();
            Set<Role> roleSet = new HashSet<>();
            Set<Address> addresses5 = new HashSet<>();
            Customer user1 = new Customer();
            user1.setFirstName("DemoCustomer");
            user1.setLastName("singh");
            user1.setEnabled(true);
            user1.setActive(true);
            user1.setContact("09711247133");
            user1.setPassword(passwordEncoder.encode("customer"));
            user1.setUsername("pinki6@gmail.com");
            Role role5 = new Role();
            role5.setRoleName("ROLE_CUSTOMER");
            userSet.add(user1);
            role5.setUsers(userSet);
            roleSet.add(role5);
            user1.setRoles(roleSet);
            Address address5 = new Address();
            address5.setCity("delhi");
            address5.setCountry("india");
            address5.setLabel("office");
            address5.setZipCode(110095);
            address5.setState("delhi");
            address5.setAddressLine("sadar colony");
            addresses5.add(address5);
            address5.setUser(user1);
            user1.setAddresses(addresses5);
            userRepository.save(user1);
            Category category = new Category("Clothing");

            Set<Category> categories = new HashSet<>();

            Category category1 = new Category("Men");
            Category category2 = new Category("Women");
            //men
            Category category3= new Category("Jeans");
            Category category4= new Category("Shirts");
            Category category5= new Category("Winter Wear");
            //women
            Category category6= new Category("Western Wear");
            Category category7= new Category("Ethnic Wear");
            Category category8= new Category("Accessories");

            //add to set of categories
            categories.add(category1);
            categories.add(category2);
            categories.add(category3);
            categories.add(category4);
            categories.add(category5);
            categories.add(category6);
            categories.add(category7);
            categories.add(category8);


            category1.setCategory(category);
            category2.setCategory(category);

            category3.setCategory(category1);
            category4.setCategory(category1);
            category5.setCategory(category1);

            category6.setCategory(category2);
            category7.setCategory(category2);
            category8.setCategory(category2);

            category.setCategories(categories);
            category1.setCategories(categories);
            category2.setCategories(categories);

            Set<Product> products = new HashSet<>();

            Product product = new Product("Casual Shirts","Tommy Hilfiger");
            product.setDescription("Sunset pocket shirt in Yellow ang grey checks colour." +
                    " Featuring the same 1902 sunset pocket shape as shirts from the levi's archives; ");
            product.setActive(true);
            product.setCancellable(true);
            product.setReturnable(true);

            Product product1= new Product("Formal Shirts","Van Heusen");
            product1.setDescription("This is Asia size,please confirm you size before purchase." +
                    "Please allow 1-2cm measuring deviation due to manual measurement.");
            product1.setActive(false);
            product1.setCancellable(true);
            product1.setReturnable(true);

            Product product2= new Product("Slim Fit","Calvin Klein");
            product2.setDescription("This is a very nice slim fit jeans");
            product2.setActive(true);
            product2.setCancellable(true);
            product2.setReturnable(true);

            Product product3= new Product("Regular Fit","Calvin Klein");
            product3.setDescription("This is a very nice regular fit jeans");
            product3.setActive(true);
            product3.setCancellable(true);
            product3.setReturnable(true);

            Product product4= new Product("Joggers","Wrangler");
            product4.setDescription("Sport these jogger jeans and show off your casual side.");
            product4.setActive(true);
            product4.setCancellable(true);
            product4.setReturnable(true);

            Product product5= new Product("Jackets","Belstaff");
            product5.setDescription("This is a very nice jacket");
            product5.setActive(true);
            product5.setCancellable(true);
            product5.setReturnable(true);

            Product product6= new Product("Sweaters","Roadster");
            product6.setDescription("This is a very nice Sweater");
            product6.setActive(false);
            product6.setCancellable(true);
            product6.setReturnable(true);

            Product product7= new Product("Tops","Polo");
            product7.setDescription("Aubergine solid blouson top," +
                    " has a mandarin collar with button closure, " +
                    "three-quarter puff sleeves, elasticated hem");
            product7.setActive(true);
            product7.setCancellable(true);
            product7.setReturnable(true);

            Product product8= new Product("Jumpsuits","DressBerry");
            product8.setDescription("FotoableArc is an online fashion label which believes" +
                    " in dressing the Indian woman for both her perfect and not-so-perfect moments. ");
            product8.setActive(true);
            product8.setCancellable(true);
            product8.setReturnable(true);

            Product product9= new Product("Sarees","Satya paul");
            product9.setDescription("ANNI DESIGNER women's Raniyal georgette saree with blouse.");
            product9.setActive(true);
            product9.setCancellable(true);
            product9.setReturnable(true);

            Product product10= new Product("Kurta","Biba");
            product10.setDescription("Here is the kurti by Vaamsi. Ideal for parties and festivals.");
            product10.setActive(true);
            product10.setCancellable(true);
            product10.setReturnable(true);

            Product product11= new Product("Gloves","Adidas");
            product11.setDescription("Diya Home hand gloves are Cotton made Thin, Soft to touch and comfy for wearing.");
            product11.setActive(true);
            product11.setCancellable(true);
            product11.setReturnable(true);

            Product product12= new Product("Stoles","Anekaant");
            product12.setDescription("When you're looking for that perfect " +
                    "finishing touch to add-on your sweet sense of style, simply toss on the fashion scarf");
            product12.setActive(true);
            product12.setCancellable(true);
            product12.setReturnable(true);


            product.setCategoriesInProduct(category4);
            product1.setCategoriesInProduct(category4);

            product2.setCategoriesInProduct(category3);
            product3.setCategoriesInProduct(category3);
            product4.setCategoriesInProduct(category3);

            product5.setCategoriesInProduct(category5);
            product6.setCategoriesInProduct(category5);

            product7.setCategoriesInProduct(category6);
            product8.setCategoriesInProduct(category6);

            product9.setCategoriesInProduct(category7);
            product10.setCategoriesInProduct(category7);

            product11.setCategoriesInProduct(category8);
            product12.setCategoriesInProduct(category8);

            products.add(product);
            products.add(product1);
            products.add(product2);
            products.add(product3);
            products.add(product4);
            products.add(product5);
            products.add(product6);
            products.add(product7);
            products.add(product8);
            products.add(product9);
            products.add(product10);
            products.add(product11);
            products.add(product12);

            category.setProducts(products);

            Seller seller = new Seller();
            Seller seller1= new Seller();
            Seller seller2= new Seller();
            Seller seller3= new Seller();


            product.setSellers(seller);
            product1.setSellers(seller);
            product2.setSellers(seller);
            product3.setSellers(seller1);
            product4.setSellers(seller1);
            product5.setSellers(seller1);
            product6.setSellers(seller2);
            product7.setSellers(seller2);
            product8.setSellers(seller2);
            product9.setSellers(seller3);
            product10.setSellers(seller3);
            product11.setSellers(seller3);
            product12.setSellers(seller3);


            Role role1= new Role("ROLE_SELLER");

            seller.setProducts(products);
            Set<Address> addressSet= new HashSet<>();
            Address address1= new Address();
            address1.setAddressLine("sadar");
            address1.setState("delhi");
            address1.setZipCode(110010);
            address1.setCountry("India");
            address1.setCity("delhi");
            address1.setLabel("HOME");
            address1.setUser(seller);
            addressSet.add(address1);

            seller.setFirstName("Priti");
            seller.setLastName("Bhati");
            seller.addRoles(role1);
            seller.setCreatedBy("Priti");
            seller.setEnabled(true);
            seller.setActive(true);
            seller.setAddresses(addressSet);
            seller.setCompanyContact("+919843928689");
            seller.setCompanyName("Tommy Hilfiger");
            seller.setGst("27AAACS8577K2ZO");
            seller.setPassword(passwordEncoder.encode("admins"));
            seller.setUsername("bhatipinki056@gmail.com");

            seller1.setFirstName("Arti");
            seller1.setLastName("Sharma");
            seller1.setCreatedBy("Arti");
            seller1.setUsername("arti123@gmail.com");
            seller1.addRoles(role1);
            seller1.setPassword(passwordEncoder.encode("Seller@1"));
            seller1.setCompanyContact("+917564392867");
            seller1.setGst("27ABACS8577K2ZO");
            seller1.setCompanyName("Zara");
            seller1.setEnabled(true);
            seller1.setActive(true);


            seller2.setFirstName("Ankit");
            seller2.setLastName("Sagar");
            seller2.setUsername("ankit@gmail.com");
            seller2.setCreatedBy("Ankit");
            seller2.addRoles(role1);
            seller2.setPassword(passwordEncoder.encode("Seller@2"));
            seller2.setCompanyContact("+919876543210");
            seller2.setGst("27ACACS8577K2ZO");
            seller2.setCompanyName("Armani");
            seller2.setEnabled(true);
            seller2.setActive(true);

            seller3.setFirstName("Shivam");
            seller3.setLastName("Sharma");
            seller3.setUsername("shivam@gmail.com");
            seller3.setCreatedBy("Shivam");
            seller3.addRoles(role1);
            seller3.setPassword(passwordEncoder.encode("Seller@3"));
            seller3.setCompanyContact("+918964392869");
            seller3.setGst("27AVACS8577K2ZO");
            seller3.setCompanyName("Prada");
            seller3.setEnabled(true);
            seller3.setActive(true);


            ProductVariation productVariation = new ProductVariation();
            Set<ProductVariation> productVariationSet = new HashSet<>();
            productVariation.setPrice(1234d);
            productVariation.setQuantityAvailable(3);
            productVariation.setProduct(product);
            productVariation.setActive(true);
            Map<String,Object> attributes = new HashMap<>();
            attributes.put("SIZE","L");
            productVariation.setInfoAttributes(attributes);
            String  info = objectMapper.writeValueAsString(attributes);
            productVariation.setMetadata(info);
            System.out.println(objectMapper.readValue(info,HashMap.class));
            productVariationSet.add(productVariation);
            product.setProductVariations(productVariationSet);

            ProductVariation productVariation1 = new ProductVariation();
            productVariation1.setPrice(1500.12);
            productVariation1.setQuantityAvailable(4);
            productVariation1.setActive(true);
            Map<String,Object> attributes1 = new HashMap<>();
            attributes1.put("SIZE","M");
            productVariation1.setInfoAttributes(attributes);
            String  info2 = objectMapper.writeValueAsString(attributes);
            productVariation1.setMetadata(info2);
            System.out.println(objectMapper.readValue(info,HashMap.class));
            productVariation1.setProduct(product);
            productVariationSet.add(productVariation1);
            ProductVariation productVariation2 = new ProductVariation();
            productVariation2.setPrice(1000.00);
            productVariation2.setQuantityAvailable(10);
            productVariation2.setProduct(product1);
            productVariation2.setActive(true);
            productVariationSet.add(productVariation2);
            product1.setProductVariations(productVariationSet);


            ProductVariation productVariation3 = new ProductVariation();
            productVariation3.setPrice(700.00);
            productVariation3.setQuantityAvailable(20);
            productVariation3.setProduct(product1);
            productVariation3.setActive(true);
            productVariationSet.add(productVariation3);
            product1.setProductVariations(productVariationSet);


            ProductVariation productVariation4 = new ProductVariation();
            productVariation4.setPrice(1500.00);
            productVariation4.setQuantityAvailable(3);
            productVariation4.setProduct(product2);
            productVariation4.setActive(true);
            productVariationSet.add(productVariation4);
            product2.setProductVariations(productVariationSet);


            ProductVariation productVariation5 = new ProductVariation();
            productVariation5.setPrice(1475.00);
            productVariation5.setQuantityAvailable(5);
            productVariation5.setActive(true);
            productVariation5.setProduct(product2);
            productVariationSet.add(productVariation5);
            product2.setProductVariations(productVariationSet);



            ProductVariation productVariation6 = new ProductVariation();
            productVariation6.setPrice(1700.00);
            productVariation6.setQuantityAvailable(10);
            productVariation6.setProduct(product3);
            productVariation6.setActive(true);
            productVariationSet.add(productVariation6);
            product3.setProductVariations(productVariationSet);


            ProductVariation productVariation7 = new ProductVariation();
            productVariation7.setPrice(800.00);
            productVariation7.setQuantityAvailable(30);
            productVariation7.setProduct(product4);
            productVariation7.setActive(true);
            productVariationSet.add(productVariation7);
            product4.setProductVariations(productVariationSet);

            ProductVariation productVariation8 = new ProductVariation();
            productVariation8.setPrice(1900.00);
            productVariation8.setQuantityAvailable(4);
            productVariation8.setProduct(product5);
            productVariation8.setActive(true);
            productVariationSet.add(productVariation8);
            product5.setProductVariations(productVariationSet);

            ProductVariation productVariation9 = new ProductVariation();
            productVariation9.setPrice(1800.00);
            productVariation9.setQuantityAvailable(5);
            productVariation9.setProduct(product6);
            productVariation9.setActive(true);
            productVariationSet.add(productVariation9);
            product6.setProductVariations(productVariationSet);

            ProductVariation productVariation10 = new ProductVariation();
            productVariation10.setPrice(1185.00);
            productVariation10.setQuantityAvailable(9);
            productVariation10.setProduct(product7);
            productVariation10.setActive(true);
            productVariationSet.add(productVariation10);
            product7.setProductVariations(productVariationSet);



            ProductVariation productVariation11 = new ProductVariation();
            productVariation11.setPrice(1700.00);
            productVariation11.setQuantityAvailable(4);
            productVariation11.setProduct(product8);
            productVariation11.setActive(true);
            productVariationSet.add(productVariation11);
            product8.setProductVariations(productVariationSet);


            productRepository.save(product);
            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
            productRepository.save(product5);
            productRepository.save(product6);
            productRepository.save(product7);
            productRepository.save(product8);
            productRepository.save(product9);
            productRepository.save(product10);
            productRepository.save(product11);
            productRepository.save(product12);

            CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
            categoryMetadataField.setName("SIZE");
            categoryMetadataFieldRepository.save(categoryMetadataField);

            CategoryMetadataFieldValuesId categoryMetadataFieldValuesId= new CategoryMetadataFieldValuesId();
            categoryMetadataFieldValuesId.setCid(category4.getId());
            categoryMetadataFieldValuesId.setMid(categoryMetadataField.getId());

            CategoryMetadataFieldValues categoryMetadataFieldValues= new CategoryMetadataFieldValues();
            categoryMetadataFieldValues.setFieldValues("L,M,S");
            categoryMetadataFieldValues.setCategoryMetadataFieldValuesId(categoryMetadataFieldValuesId);
            categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataField);
            categoryMetadataFieldValues.setCategory(category4);

            Set<CategoryMetadataFieldValues> categoryMetadataFieldValues1 = new HashSet<>();
            categoryMetadataFieldValues1.add(categoryMetadataFieldValues);
            category4.setCategoryMetadataFieldValues(categoryMetadataFieldValues1);

            categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);


        }
    }

