package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.DTO.ProductDTO;
import com.project.Ecommerce.DTO.ViewProductDTO;
import com.project.Ecommerce.Dao.CategoryDao;
import com.project.Ecommerce.Dao.ProductDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductDaoImpl implements ProductDao {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryDao categoryDao;

    private JavaMailSender javaMailSender;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    public ProductDaoImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    GetCurrentDetails getCurrentDetails;
    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public long getId(String productName) {
        long id = productRepository.getProductVariationId(productName);
        return id;
    }


    @Override
    public void addProduct(Product product, Long categoryId) {
        String sellerName = getCurrentDetails.getUser();
        Seller seller = sellerRepository.findByUsername(sellerName);
        Set<Product> productSet = new HashSet<>();
        Product product1 = new Product();
        product1.setBrand(product.getBrand());
        product1.setActive(false);
        product1.setCancellable(product.getIsCancellable());
        product1.setDescription(product.getDescription());
        product1.setName(product.getName());
        product1.setSellers(seller);
        product1.setCategoriesInProduct(categoryRepository.findById(categoryId).get());
        productSet.add(product1);
        productRepository.save(product1);
    }

    @Override
    public List<Object[]> getProductDetails() {
        String username = getCurrentDetails.getUser();
        Seller seller = sellerRepository.findByUsername(username);
        List<Object[]> objects = productRepository.getProducts(seller.getId());
        return objects;
    }

    @Override
    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        String seller = getCurrentDetails.getUser();
        Seller seller1 = sellerRepository.findByUsername(seller);
        if (product.isPresent()) {
            Product product1 = product.get();
            if ((product1.getSellers().getUsername()).equals(seller1.getUsername())) {
                productRepository.deleteProductVariation(product1.getId());
                productRepository.deleteProduct(productId);

            } else {
                throw new NullException("You cant delete this product");
            }

        } else {
            throw new NotFoundException("This product name is wrong");
        }
    }

    @Override
    public void editProduct(ProductDTO product, Long productId) {
        String seller = getCurrentDetails.getUser();
        Seller seller1 = sellerRepository.findByUsername(seller);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product1 = productOptional.get();
            if ((product1.getSellers().getUsername()).equals(seller1.getUsername())) {
                if (product.getName().equals(product1.getBrand()) || product.getName().equals(product1.
                        getCategoriesInProduct().getName()) || product.getName().equals(product1.getSellers()
                        .getFirstName()) || product.getName().equals(product.getBrand())) {
                    throw new NullException("Product name should be unique with respect to seller,brand,category");
                }
                if (product.getName() != null) {
                    product1.setName(product.getName());
                }

                if (product.getIsActive() != null) {
                    product1.setActive(product.getIsActive());
                }

                if (product.getIsCancellable() != null) {
                    product1.setCancellable(product.getIsCancellable());
                }

                if (product.getIsReturnable() != null) {
                    product1.setReturnable(product.getIsReturnable());
                }

                if (product.getBrand() != null) {
                    product1.setBrand(product.getBrand());
                }

                if (product.getDescription() != null) {
                    product1.setDescription(product.getDescription());
                }

                productRepository.save(product1);


            } else {
                throw new NullException("You cannot edit this product");
            }
        } else {
            throw new NotFoundException("This product ID is wrong");
        }

    }

    @Override
    public void deactivate(Long productId) {

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getIsActive() == true) {
                String seller = productRepository.getThatSeller(productId);
                System.out.println(seller);
                Seller seller1 = sellerRepository.findByUsername(seller);
                System.out.println(product.getBrand());
                productRepository.setActiveStatusOfProduct(productId);
                productRepository.setActiveStatusOfProductAndProductVariation(product.getId());
                System.out.println(seller1.getUsername());
                String subject = "Regarding deactivation";
                String text = product.getName() + "  got deactivated by admin";
                notificationService.sendToSeller(seller1, subject, text);
            } else {
                throw new NotFoundException("This product is already de-activated");
            }
        } else {
            throw new NotFoundException("This product is not found");
        }


    }

    @Override
    public void activateProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            if (product.getIsActive() == false) {
                String seller = productRepository.getThatSeller(productId);
                System.out.println(seller);
                Seller seller1 = sellerRepository.findByUsername(seller);
                System.out.println(product.getBrand());
                productRepository.activateTheProduct(productId);
                System.out.println(seller1.getUsername());
                String subject = "Regarding Activation";
                String text = product.getName() + "  got activated by admin";
                notificationService.sendToSeller(seller1, subject, text);
            } else {
                throw new NotFoundException("This product is already activated");
            }
        } else {
            throw new NotFoundException("This product is not found");
        }

    }

    @Override
    public void addNewProduct(ProductDTO product, Long categoryId) {
        int result = categoryRepository.checkIfLeaf(categoryId);
        if (result == 1) {
            if (product.getName().equals(categoryRepository.findById(categoryId).get().getName()) || product.getName().equals(product.getBrand())) {
                throw new NullException("Name of category and product is cannot be same");
            }

            Product product1 = modelMapper.map(product, Product.class);
            addProduct(product1, categoryId);
        } else {
            throw new NullException("This category is not a leaf category");
        }

        Long productId = productRepository.findProduct(product.getName());
        Long id = userRepository.findAdmin();
        User user = userRepository.findById(id).get();
        String text = "Product Brand: " + product.getBrand() + "\n" + "Product Description: " +
                product.getDescription() + "\n" + "Product ID: " + productId;
        notificationService.sendToAdmin(user, text);
    }


    @Override
    public List<ViewProductDTO> viewSingleProduct(Long productId) {
        String sellerName = getCurrentDetails.getUser();
        Seller seller = sellerRepository.findByUsername(sellerName);
        List<ViewProductDTO> viewProductDTOS= new ArrayList<>();
        List<String> fields= new ArrayList<>();
        List<String> values=new ArrayList<>();
        ViewProductDTO viewProductDTO= new ViewProductDTO();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getIsActive() == true) {
                if ((product.getSellers().getUsername()).equals(seller.getUsername())) {

                    viewProductDTO.setProductName(product.getName());
                    viewProductDTO.setBrand(product.getBrand());
                    viewProductDTO.setCancellable(product.getIsCancellable());
                    viewProductDTO.setActive(product.getIsActive());
                    viewProductDTO.setDescription(product.getDescription());
                    viewProductDTO.setReturnable(product.getIsReturnable());
                    Long categoryId= productRepository.getCategoryId(product.getId());
                    Category category= categoryRepository.findById(categoryId).get();
                    viewProductDTO.setCategoryName(category.getName());
                    List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category.getId());
                    for (Long l : longList) {
                        CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findById(l).get();//Size is added into the list
                        fields.add(categoryMetadataField.getName());
                        values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.getId(), l));
                        viewProductDTO.setValues(values);
                        viewProductDTO.setFieldName(fields);

                    }

                    viewProductDTOS.add(viewProductDTO);
                    return viewProductDTOS;

                } else {
                    throw new NotFoundException("You cannot view this product");
                }

            } else {
                throw new NullException("This product is not active");
            }

        } else {
            throw new NotFoundException("This product is not present");
        }

    }

    @Override
    public List<ViewProductDTO> viewSingleProductForAdmin(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        List<ViewProductDTO> viewProductDTOS= new ArrayList<>();
        List<String> fields= new ArrayList<>();
        List<String> values=new ArrayList<>();

        if (productOptional.isPresent())
        {
            ViewProductDTO viewProductDTO= new ViewProductDTO();
            Product product= productOptional.get();
            viewProductDTO.setProductName(product.getName());
            viewProductDTO.setBrand(product.getBrand());
            viewProductDTO.setCancellable(product.getIsCancellable());
            viewProductDTO.setActive(product.getIsActive());
            viewProductDTO.setDescription(product.getDescription());
            viewProductDTO.setReturnable(product.getIsReturnable());
            Long categoryId= productRepository.getCategoryId(product.getId());
            Category category= categoryRepository.findById(categoryId).get();
            viewProductDTO.setCategoryName(category.getName());
            List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category.getId());
            for (Long l : longList) {
                CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findById(l).get();//Size is added into the list
                fields.add(categoryMetadataField.getName());
                values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.getId(), l));
                viewProductDTO.setValues(values);
                viewProductDTO.setFieldName(fields);

            }

            viewProductDTOS.add(viewProductDTO);
            return viewProductDTOS;

        }

        else {
            throw new NotFoundException("This product ID is wrong");
        }
    }

    @Override
    public List<Object[]> viewProduct(Long id) {
        List<Object[]> list = new ArrayList<>();
        Optional<Product> product1 = productRepository.findById(id);
        if (product1.isPresent()) {
            if (product1.get().isActive() == true) {
                if (productVariationRepository.getProductVariation(id).isEmpty()) {
                    throw new NotFoundException("product variations not available for this product");
                } else {
                    list.addAll(productRepository.getSingleProduct(id));
                    list.addAll(productVariationRepository.getProductVariation(id));
                    return list;
                }
            } else {
                throw new NotFoundException("product is not present or is currently not active");
            }
        } else {
            throw new NotFoundException("prooduct with this id is not present");
        }

    }

    @Override
    public List<Object[]> viewProducts(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        List<Object[]> list1 = new ArrayList<>();
        if (category.isPresent()) {
            int result = categoryRepository.checkIfLeaf(categoryId);
            if (result == 1) {
                List<Product> list = productRepository.getProductsForCategory(categoryId);
                for (Product product : list) {
                    if (!productVariationRepository.getProductVariations(product.getId()).isEmpty() && product.isActive() ==true) {
                        list1.addAll(productRepository.getSingleProduct(product.getId()));

                    }

                }
                return list1;
            } else {
                throw new NotFoundException("Category is not a leaf category");
            }
        } else {
            throw new NotFoundException("category with this id is not present");

        }
    }

    @Override
    public List<Object[]> viewSimilarProducts(Long productId)
    {
        List<Object[]> list= new ArrayList<Object[]>();
        Long categoryId= productRepository.getCategoryId(productId);
        Optional<Product> productOptional= productRepository.findById(productId);
        List<Product> productList= productRepository.getProductsForCategory(categoryId);
        if (productOptional.isPresent()&&productOptional.get().getIsActive()==true) //product passed in header should be active
        {
            Product product= productOptional.get();
            for (Product product1:productList)
            {
                if(product1.getBrand().equals(product.getBrand())&&product1.getIsActive()==true) //similiar product's isActive should be true
                {
                    list.addAll(productRepository.getSingleProduct(product1.getId()));
                }
            }
            return list;
        }

        else {
            throw new NotFoundException("This ID iw wrong");
        }
    }

    @Override
    public List<Object[]> getAllProducts() {
        return productRepository.getAllProducts();
    }
}
