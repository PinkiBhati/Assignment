package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.DTO.ProductDTO;
import com.project.Ecommerce.DTO.ViewProductDTO;
import com.project.Ecommerce.DTO.ViewProductForCustomerDTO;
import com.project.Ecommerce.Dao.CategoryDao;
import com.project.Ecommerce.Dao.ProductDao;
import com.project.Ecommerce.Dao.UploadDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.ExceptionHandling.ProductNotFoundException;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentlyLoggedInUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
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
    GetCurrentlyLoggedInUser getCurrentlyLoggedInUser;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UploadDao uploadDao;
    Long[] params={};

    @Override
    public long getId(String productName) {
        long id = productRepository.getProductVariationId(productName);
        return id;
    }


    @Override
    public void addProduct(Product product, Long categoryId) {
        String sellerName = getCurrentlyLoggedInUser.getCurrentUser();
        Seller seller = sellerRepository.findByUsername(sellerName);
        Set<Product> productSet = new HashSet<>();
        Product product1 = new Product();
        product1.setBrand(product.getBrand());
        product1.setActive(false);
        product1.setCancellable(product.isCancellable());
        product1.setReturnable(product.isReturnable());
        product1.setDescription(product.getDescription());
        product1.setName(product.getName());
        product1.setCreatedBy(seller.getUsername());
        product1.setCreatedOn(LocalDateTime.now());
        product1.setSellers(seller);
        product1.setCategoriesInProduct(categoryRepository.findById(categoryId).get());
        productSet.add(product1);
        productRepository.save(product1);
    }

    @Override
    public List<ViewProductDTO> getProductDetails(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Seller seller = sellerRepository.findByUsername(username);
        List<ViewProductDTO> list= new ArrayList<>();

        for (Long l : productRepository.getAllProductsOfSeller(seller.getId(),paging))
        {
            Optional<Product> productOptional = productRepository.findById(l);
            Product product1 = productOptional.get();
            if ((product1.getSellers().getUsername()).equals(seller.getUsername())) {

                 list.add(viewSingleProduct(product1.getId()));
            }

        }
       return list;
    }

    @Override
    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        String seller = getCurrentlyLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(seller);

        if (product.isPresent()) {
            Product product1 = product.get();
            if ((product1.getSellers().getUsername()).equals(seller1.getUsername())) {
                productRepository.deleteProductVariation(product1.getId());
                productRepository.deleteProduct(productId);

            } else {
                throw new NullException(messageSource.getMessage("message",params ,LocaleContextHolder.getLocale()));
            }

        } else {
            throw new ProductNotFoundException(messageSource.getMessage("message1",params,LocaleContextHolder.getLocale()));
        }
    }



    @Override
    public void updateProduct(ProductDTO product, Long productId) {
        String seller = getCurrentlyLoggedInUser.getCurrentUser();
        Seller seller1 = sellerRepository.findByUsername(seller);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product1 = productOptional.get();
            if ((product1.getSellers().getUsername()).equals(seller1.getUsername())) {
                if (product.getName().equals(product1.getBrand()) || product.getName().equals(product1.
                        getCategoriesInProduct().getName()) || product.getName().equals(product1.getSellers()
                        .getFirstName()) || product.getName().equals(product.getBrand())) {
                    throw new NullException(messageSource.getMessage("message2",params,LocaleContextHolder.getLocale()));
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

                product1.setModifiedBy(seller1.getUsername());
                productRepository.save(product1);


            } else {
                throw new NullException(messageSource.getMessage("message3",params,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new ProductNotFoundException(messageSource.getMessage("message1",params,LocaleContextHolder.getLocale()));
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
                notificationService.sendMailToUser(seller1, subject, text);
            } else {
                throw new NotFoundException(messageSource.getMessage("message7",params,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new ProductNotFoundException(messageSource.getMessage("message1",params,LocaleContextHolder.getLocale()));
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
                notificationService.sendMailToUser(seller1, subject, text);
            } else {
                throw new NotFoundException(messageSource.getMessage("message8",params,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new ProductNotFoundException(messageSource.getMessage("message1",params,LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public void addNewProduct(ProductDTO product, Long categoryId) {
        int result = categoryRepository.checkIfLeaf(categoryId);
        if (result == 0) {
            if (product.getName().equals(categoryRepository.findById(categoryId).get().getName()) || product.getName().equals(product.getBrand())) {
                throw new NullException(messageSource.getMessage("message9",params,LocaleContextHolder.getLocale()));
            }

            Product product1 = modelMapper.map(product, Product.class);
            addProduct(product1, categoryId);
        } else {
            throw new NullException(messageSource.getMessage("message4",params,LocaleContextHolder.getLocale()));
        }

        Long productId = productRepository.findProduct(product.getName());
        Long id = userRepository.findAdmin();
        User user = userRepository.findById(id).get();
        String text = "Product Brand: " + product.getBrand() + "\n" + "Product Description: " +
                product.getDescription() + "\n" + "Product ID: " + productId;
        notificationService.sendToAdmin(user, text);
    }


    @Override
    public ViewProductDTO viewSingleProduct(Long productId) {
        String sellerName = getCurrentlyLoggedInUser.getCurrentUser();
        Seller seller = sellerRepository.findByUsername(sellerName);
        List<String> fields= new ArrayList<>();
        List<String> values=new ArrayList<>();
        ViewProductDTO viewProductDTO= new ViewProductDTO();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

                if ((product.getSellers().getUsername()).equals(seller.getUsername())) {

                    viewProductDTO.setProductName(product.getName());
                    viewProductDTO.setProductId(product.getId());
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

                    return viewProductDTO;

                } else {
                    throw new NotFoundException(messageSource.getMessage("message10",params,LocaleContextHolder.getLocale()));
                }


        } else {
            throw new ProductNotFoundException(messageSource.getMessage("message1",params,LocaleContextHolder.getLocale()));
        }

    }


    @Override
    public ViewProductForCustomerDTO viewSingleProductForAdmin(Long productId) {
        String currentPath = System.getProperty("user.dir");
        String fileBasePath = currentPath +"/src/main/resources/productVariation/";
        File dir = new File(fileBasePath);
        Optional<Product> productOptional = productRepository.findById(productId);

        List<String> fields= new ArrayList<>();
        List<String> values=new ArrayList<>();
        List<String > links=new ArrayList<>();
        ViewProductForCustomerDTO viewProductDTO= new ViewProductForCustomerDTO();


        if (productOptional.isPresent()) {
            Product product= productOptional.get();
                Set<ProductVariation> productVariationSet= product.getProductVariations();

                viewProductDTO.setProductName(product.getName());
                viewProductDTO.setBrand(product.getBrand());
                viewProductDTO.setProductId(product.getId());
                viewProductDTO.setCancellable(product.getIsCancellable());
                viewProductDTO.setActive(product.getIsActive());
                viewProductDTO.setDescription(product.getDescription());
                viewProductDTO.setReturnable(product.getIsReturnable());
                Long categoryId = productRepository.getCategoryId(product.getId());
                Category category = categoryRepository.findById(categoryId).get();
                viewProductDTO.setCategoryName(category.getName());
                List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category.getId());
                for (Long l : longList) {
                    CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findById(l).get();//Size is added into the list
                    fields.add(categoryMetadataField.getName());
                    values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.getId(), l));
                    viewProductDTO.setValues(values);
                    viewProductDTO.setFieldName(fields);

                }

                for (ProductVariation productVariation : productVariationSet) {
                    if (dir.isDirectory()) {
                        File arr[] = dir.listFiles();
                        for (File file : arr) {
                            if (file.getName().startsWith(productVariation.getId().toString() + "_0")) {
                                links.add("http://localhost:8080/downloadProductVariationImage/"+file.getName());
                            }
                        }
                    }


                    viewProductDTO.setLinks(links);


                }

        }//

        else {
            throw new ProductNotFoundException(messageSource.getMessage("message1",params,LocaleContextHolder.getLocale()));
        }

        return viewProductDTO;
    }

    @Override
    public ViewProductForCustomerDTO viewProduct(Long id) {

        String currentPath = System.getProperty("user.dir");
        String fileBasePath = currentPath +"/src/main/resources/productVariation/";
        File dir = new File(fileBasePath);
        Optional<Product> productOptional = productRepository.findById(id);

        List<String> fields= new ArrayList<>();
        List<String> values=new ArrayList<>();
        List<String > links=new ArrayList<>();
        ViewProductForCustomerDTO viewProductDTO= new ViewProductForCustomerDTO();
        if (productOptional.isPresent()) {

            Product product= productOptional.get();
            Set<ProductVariation> productVariationSet= product.getProductVariations();
            viewProductDTO.setProductName(product.getName());
            viewProductDTO.setProductId(product.getId());
            viewProductDTO.setBrand(product.getBrand());
            viewProductDTO.setCancellable(product.getIsCancellable());
            viewProductDTO.setActive(product.getIsActive());
            viewProductDTO.setDescription(product.getDescription());
            viewProductDTO.setReturnable(product.getIsReturnable());
            Long categoryId = productRepository.getCategoryId(product.getId());
            Category category = categoryRepository.findById(categoryId).get();
            viewProductDTO.setCategoryName(category.getName());
            List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category.getId());
            for (Long l : longList) {
                CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findById(l).get();//Size is added into the list
                fields.add(categoryMetadataField.getName());
                values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.getId(), l));
                viewProductDTO.setValues(values);
                viewProductDTO.setFieldName(fields);

            }

            for (ProductVariation productVariation : productVariationSet) {
                if (dir.isDirectory()) {
                    File arr[] = dir.listFiles();
                    for (File file : arr) {
                        if (file.getName().startsWith(productVariation.getId().toString() + "_0")) {
                            links.add("http://localhost:8080/downloadProductVariationImage/"+file.getName());
                        }
                    }
                }


                viewProductDTO.setLinks(links);


            }
            return viewProductDTO;
        }

        else {
            throw new NotFoundException(messageSource.getMessage("message6",params,LocaleContextHolder.getLocale()));
        }


    }

    @Override
    public List<ViewProductForCustomerDTO> viewProducts(Long categoryId) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        List<ViewProductForCustomerDTO> viewProductDTOList= new ArrayList<>();
        String currentPath = System.getProperty("user.dir");
        String fileBasePath = currentPath +"/src/main/resources/productVariation/";
        File dir = new File(fileBasePath);
        ViewProductForCustomerDTO viewProductDTO= new ViewProductForCustomerDTO();
        if (categoryOptional.isPresent()) {
            if (categoryRepository.checkIfLeaf(categoryId) == 0) {
                Category category= categoryOptional.get();

                for (Long l: productRepository.getAllProductsOfCategory(category.getId()))
                {

                        viewProductDTOList.add(viewProduct(l));

                }

                return viewProductDTOList;
            } else {
                throw new NotFoundException(messageSource.getMessage("message4",params,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("message5",params,LocaleContextHolder.getLocale()));

        }
    }
    @Override
    public List<ViewProductForCustomerDTO> viewSimilarProducts(Long productId,Integer pageNo, Integer pageSize,String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<ViewProductForCustomerDTO> list= new ArrayList<>();
        ViewProductForCustomerDTO viewProductDTO= new ViewProductForCustomerDTO();
        Long categoryId= productRepository.getCategoryId(productId);
        Category category= categoryRepository.findById(categoryId).get();
        Optional<Product> productOptional= productRepository.findById(productId);
        List<Product> productList= productRepository.getProductsForCategory(categoryId);
        if (productOptional.isPresent()&&productOptional.get().getIsActive()==true) //product passed in header should be active
        {
            Product product= productOptional.get();
            for (Long l: productRepository.getAllProductOfCategoryAndSameBrand(categoryId,product.getBrand(),paging))
            {
                if(productRepository.findById(l).get().getIsActive()==true) //similiar product's isActive should be true
                {
                    Product product1= productRepository.findById(l).get();
                    list.add(viewProduct(product1.getId())) ;
                }
            }
            return list;
        }

        else {
            throw new ProductNotFoundException(messageSource.getMessage("message1",params,LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public List<ViewProductForCustomerDTO> getAllProducts() {
//        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<ViewProductForCustomerDTO> viewProductForCustomerDTOS = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        List<String> links = new ArrayList<>();

        for (Long l : productRepository.getAllProductsId()) {

            viewProductForCustomerDTOS.add(viewSingleProductForAdmin(l)) ;

        }

        return viewProductForCustomerDTOS;
    }

}
