package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.Dao.CategoryMetadataFieldValuesDao;
import com.project.Ecommerce.Entities.CategoryMetadataFieldValues;
import com.project.Ecommerce.Entities.CategoryMetadataFieldValuesId;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.Repos.CategoryMetadataFieldRepository;
import com.project.Ecommerce.Repos.CategoryMetadataFieldValuesRepository;
import com.project.Ecommerce.Repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryMetadataFieldValuesDaoImpl implements CategoryMetadataFieldValuesDao {

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Override
    public void addMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues, Long categoryId, Long metadataId) {

        if (categoryRepository.findById(categoryId).isPresent()) {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent()) {
                CategoryMetadataFieldValuesId categoryMetadataFieldValuesId = new CategoryMetadataFieldValuesId();
                categoryMetadataFieldValuesId.setCid(categoryRepository.findById(categoryId).get().getId());
                categoryMetadataFieldValuesId.setMid(categoryMetadataFieldRepository.findById(metadataId).get().getId());

                categoryMetadataFieldValues.setCategoryMetadataFieldValuesId(categoryMetadataFieldValuesId);
                categoryMetadataFieldValues.setCategory(categoryRepository.findById(categoryId).get());
                categoryMetadataFieldValues.setFieldValues(categoryMetadataFieldValues.getFieldValues());
                categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataFieldRepository.findById(metadataId).get());
                categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
            } else {
                throw new NotFoundException("This metadata ID is wrong because no " +
                        "metadata is present for this ID");
            }
        } else {
            throw new NotFoundException("Category ID is wrong as no data is present for this ID");
        }


    }

    @Override
    public void updateMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues, Long categoryId, Long metadataId) {

        if (categoryMetadataFieldValuesRepository.getFieldValues(categoryId, metadataId) == null) {
            throw new NotFoundException("This combination of category and metadata " +
                    "is wrong as no entry is present for this combination");

        } else {
            CategoryMetadataFieldValues categoryMetadataFieldValues1 = categoryMetadataFieldValuesRepository.getFieldValues(categoryId, metadataId);
            categoryMetadataFieldValues1.setFieldValues(categoryMetadataFieldValues.getFieldValues());
            categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues1);
        }


    }

    @Override
    public List<Object[]> viewAMetadataValue(Long categoryId, Long metadataId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent()) {
                return categoryMetadataFieldValuesRepository.getParticularMetadataValue(categoryId, metadataId);
            } else {
                throw new NotFoundException("This metadata ID is wrong because no " +
                        "metadata is present for this ID");
            }
        } else {
            throw new NotFoundException("Category ID is wrong as no data is present for this ID");
        }

    }

    @Override
    public void deleteAMetadataValue(Long categoryId, Long metadataId) {

        if (categoryRepository.findById(categoryId).isPresent()) {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent()) {
                categoryMetadataFieldValuesRepository.deleteMetadataFieldValues(categoryId, metadataId);
            } else {
                throw new NotFoundException("This metadata ID is wrong because no " +
                        "metadata is present for this ID");
            }
        } else {
            throw new NotFoundException("Category ID is wrong as no data is present for this ID");
        }

    }
}
