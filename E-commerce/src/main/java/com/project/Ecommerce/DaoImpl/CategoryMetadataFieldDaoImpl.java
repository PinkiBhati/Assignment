package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.Dao.CategoryMetadataFieldDao;
import com.project.Ecommerce.Entities.CategoryMetadataField;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.Repos.CategoryMetadataFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryMetadataFieldDaoImpl implements CategoryMetadataFieldDao {

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    private MessageSource messageSource;
    Long[] params={};


    @Override
    public void deleteCategoryMetadataField(Long id) {

        if (categoryMetadataFieldRepository.findById(id).isPresent()) {
            categoryMetadataFieldRepository.deleteById(id);
        } else {
            throw new NotFoundException(messageSource.getMessage("message21",params, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public List<CategoryMetadataField> viewCategoryMetadataField(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));

        if (categoryMetadataFieldRepository.findAll(paging).isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("message22",params, LocaleContextHolder.getLocale()));
        } else {

            Page<CategoryMetadataField> pageResult = categoryMetadataFieldRepository.findAll(paging);
            if (pageResult.hasContent()) {
                return pageResult.getContent();
            } else {
                throw new NotFoundException(messageSource.getMessage("message23",params, LocaleContextHolder.getLocale()));
            }
        }
    }
}
