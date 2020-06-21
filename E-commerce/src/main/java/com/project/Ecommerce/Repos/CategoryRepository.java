package com.project.Ecommerce.Repos;

import com.project.Ecommerce.Entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    @Query(value = "select name,id from category where parent_id is null",nativeQuery = true)
    List<Object[]> getMainCategory();

    @Query(value = "select exists(select * from category where parent_id=:parent_id)",nativeQuery = true)
    int checkIfLeaf(@Param("parent_id") Long parent_id);

    @Query(value = "select name,id from category where parent_id in(select id from category where name=:name)",nativeQuery = true)
    List<Object[]> getSubCategory(@Param("name") String name);

    @Query(value = "select name,id from category where parent_id in(select id from category where id=:id)",nativeQuery = true)
    List<Object[]> getSubCategoryWithId(@Param("id") Long id);

    @Query(value = "select id from category where name=:name",nativeQuery = true)
    Long getIdOfParentCategory(@Param("name") String name);

    @Query(value = "select name,id from category where parent_id not in (select id from category where parent_id is null)", nativeQuery = true)
    public List<Object[]> getSubcategory();

    @Query(value = "select parent_id from category where id=:id",nativeQuery = true)
    public Long getCategoryParent(@Param("id") Long id);

    @Query(value = "select name from category where id=:id",nativeQuery = true)
    public Object[] getCategoryName(@Param("id") Long id);

    @Query(value = "select * from category",nativeQuery = true)
    public List<Object[]> getAllCategories(Pageable pageable);

}
