package com.project.Ecommerce.Repos;

import com.project.Ecommerce.Entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    @Query(value = "select name from category where parent_id is null",nativeQuery = true)
    List<Object[]> getMainCategory();

    @Query(value = "select exists(select * from product where category_id=:category_id)",nativeQuery = true)
    int checkIfLeaf(@Param("category_id") Long category_id);

    @Query(value = "select name from category where parent_id in(select id from category where name=:name)",nativeQuery = true)
    List<Object[]> getSubCategory(@Param("name") String name);

    @Query(value = "select id from category where name=:name",nativeQuery = true)
    Long getIdOfParentCategory(@Param("name") String name);

    @Query(value = "select name from category where parent_id not in (select id from category where parent_id is null)", nativeQuery = true)
    public List<Object[]> getSubcategory();
}
