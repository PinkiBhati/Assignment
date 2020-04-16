package com.project.Ecommerce.Repos;


import com.project.Ecommerce.Entities.CategoryMetadataField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMetadataFieldRepository extends PagingAndSortingRepository<CategoryMetadataField, Long> {

    @Query(value = "select * from category_metadata_field",nativeQuery = true)
    public List<CategoryMetadataField> getAllCategoryMetadataField();


    @Query(value = "select name from category_metadata_field where id = :id",nativeQuery = true)
    List<Object[]> getMetadataField(@Param("id") Long id);

    @Query(value = "select name from category_metadata_field where id = :id",nativeQuery = true)
    public String getNameOfMetadata(@Param("id") Long id);

}
