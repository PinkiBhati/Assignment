
package com.project.Ecommerce.Repos;

import com.project.Ecommerce.Entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {


    @Query(value =  "select product.name as pname,category.name as cname,description,brand" +
            " from product join category on product.category_id= category.id  where seller_user_id=:seller_user_id and is_active=true",nativeQuery = true)
    List<Object[]> getProducts(@Param(value = "seller_user_id") long id);

    @Query(value = "select * from product where category_id=:category_id",nativeQuery = true)
    List<Product> getProductsForCategory(@Param("category_id") Long category_id);

    @Query(value = "select product.id as pid ,product.name as pname, brand, description from " +
            "product join category on category.id= product.category_id where product.is_active= true and category.name=?1",nativeQuery = true)
    List<Object[]> getAllProductsForCustomer(String categoryName);

    @Transactional
    @Modifying
    @Query(value = "update Product set isActive= false where id =:id")
    public void setActiveStatusOfProduct(@Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update product_variation set is_active = false where product_id =:product_id",nativeQuery = true)
    public void setActiveStatusOfProductAndProductVariation(@Param(value = "product_id") long product_id);

    @Query("select id from Product where name =:name")
    public long findProduct(@Param(value = "name") String name);

    @Transactional
    @Modifying
    @Query(value = "update product set is_active=true where id=:id",nativeQuery = true)
    public void activateTheProduct(@Param(value = "id") Long id);

    @Query(value = "select username from user join  product on user.id=product.seller_user_id where product.id= ?1",nativeQuery = true)
    public String getThatSeller(Long productId);

    @Transactional
    @Modifying
    @Query(value = "delete from product  where id=:id ",nativeQuery = true)
    public void deleteProduct(@Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(value = "delete product_variation from product_variation inner join" +
            " product on product.id= product_variation.product_id where product_id =:id  ",nativeQuery = true)
    public void deleteProductVariation(@Param(value = "id") long id);


    @Query(value = "select id from product where category_id =:category_id",nativeQuery = true)
    public Long findProductOfCategory(@Param(value = "category_id") long category_id);


    @Query(value = "select brand,description,is_cancellable,product.name as pname, " +
            "category.name as cname from product join category on product.category_id= category.id  where product.is_active=true",nativeQuery = true)
    public List<Object[]> getAllProducts();

    @Query(value = "select brand,description,name from product where category_id in (select id from category where name=:name)",nativeQuery = true)
    List<Object[]> getProductss(@Param("name") String name );

/*
    @Query(value = "select brand,name,description from product where name=:name",nativeQuery = true)*/
    List<Object[]> findByName(String categoryName);


    @Query(value = "select id from product where name=:name",nativeQuery = true)
    long getProductVariationId(@Param("name") String name);

    @Query(value = "select category_id from product where id=:id",nativeQuery = true)
    long getCategoryId(@Param("id")long id);

    @Query(value = "select product.name as productName,category.name as categoryName,product.brand,product.description from product inner join category  on" +
            " product.category_id = category.id where product.id = ?1",nativeQuery = true)
    List<Object[]> getSingleProduct(Long id);


}
