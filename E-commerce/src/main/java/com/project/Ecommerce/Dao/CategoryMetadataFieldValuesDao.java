package com.project.Ecommerce.Dao;

import com.project.Ecommerce.Entities.CategoryMetadataFieldValues;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Repository
public interface CategoryMetadataFieldValuesDao {
    public void addMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues,
                                  Long categoryId, Long metadataId);
    public void updateMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues,
                                   Long categoryId, Long metadataId);

    public List<Object[]> viewAMetadataValue(Long categoryId,Long metadataId);
    public void deleteAMetadataValue (Long categoryId,Long metadataId);

}
