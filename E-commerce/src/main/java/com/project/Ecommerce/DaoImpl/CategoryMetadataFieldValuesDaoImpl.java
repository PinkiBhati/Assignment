package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.Dao.CategoryMetadataFieldValuesDao;
import com.project.Ecommerce.Entities.CategoryMetadataFieldValues;
import com.project.Ecommerce.Entities.CategoryMetadataFieldValuesId;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.Repos.CategoryMetadataFieldRepository;
import com.project.Ecommerce.Repos.CategoryMetadataFieldValuesRepository;
import com.project.Ecommerce.Repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryMetadataFieldValuesDaoImpl implements CategoryMetadataFieldValuesDao {

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    private MessageSource messageSource;
    Long[] params={};

    @Override
    public void addMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues, Long categoryId, Long metadataId) {

        if (categoryRepository.findById(categoryId).isPresent() && categoryRepository.checkIfLeaf(categoryId)==0) {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent()) {
                CategoryMetadataFieldValuesId categoryMetadataFieldValuesId = new CategoryMetadataFieldValuesId();
                categoryMetadataFieldValuesId.setCid(categoryRepository.findById(categoryId).get().getId());
                categoryMetadataFieldValuesId.setMid(categoryMetadataFieldRepository.findById(metadataId).get().getId());

                categoryMetadataFieldValues.setCategoryMetadataFieldValuesId(categoryMetadataFieldValuesId);
                categoryMetadataFieldValues.setCategory(categoryRepository.findById(categoryId).get());

                String[] valuesArray = categoryMetadataFieldValues.getFieldValues().split(",");
                Set<String> s = new HashSet<>(Arrays.asList(valuesArray));
                if (s.size()==valuesArray.length&&s.size()>=1&&valuesArray[0]!="") // null nhi hona chahye aur unique value qki HashSet unique value leta h
                {
                    categoryMetadataFieldValues.setFieldValues(categoryMetadataFieldValues.getFieldValues());

                }
                else {
                    throw new NullException(messageSource.getMessage("message24",params, LocaleContextHolder.getLocale()));
                }
                categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataFieldRepository.findById(metadataId).get());
                categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
            } else {
                throw new NotFoundException(messageSource.getMessage("message25",params, LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("message5",params, LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public void updateMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues, Long categoryId, Long metadataId) {

        if (categoryRepository.findById(categoryId).isPresent())
        {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent())
            {
                if (categoryMetadataFieldValuesRepository.getFieldValues(categoryId, metadataId) != null) {
                    CategoryMetadataFieldValues categoryMetadataFieldValues1 = categoryMetadataFieldValuesRepository.getFieldValues(categoryId, metadataId);
                    categoryMetadataFieldValues1.setFieldValues(categoryMetadataFieldValues.getFieldValues());
                    categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues1);
                } else {
                    throw new NotFoundException(messageSource.getMessage("message27",params, LocaleContextHolder.getLocale()));
                }
            }
            else {
                throw new NotFoundException(messageSource.getMessage("message26",params, LocaleContextHolder.getLocale()));
            }
        }

        else {
            throw new NotFoundException(messageSource.getMessage("message5",params, LocaleContextHolder.getLocale()));
        }



    }

    @Override
    public List<Object[]> viewAMetadataValue(Long categoryId, Long metadataId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent()) {
                return categoryMetadataFieldValuesRepository.getParticularMetadataValue(categoryId, metadataId);
            } else {
                throw new NotFoundException(messageSource.getMessage("message26",params, LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("message5",params, LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public void deleteAMetadataValue(Long categoryId, Long metadataId) {

        if (categoryRepository.findById(categoryId).isPresent()) {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent()) {
                categoryMetadataFieldValuesRepository.deleteMetadataFieldValues(categoryId, metadataId);
            } else {
                throw new NotFoundException(messageSource.getMessage("message26",params, LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("message5",params, LocaleContextHolder.getLocale()));
        }

    }
}
