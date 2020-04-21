package com.project.Ecommerce.DaoImpl;


import com.project.Ecommerce.DTO.FilterDTO;
import com.project.Ecommerce.DTO.ViewCategoriesDTO;
import com.project.Ecommerce.Dao.CategoryDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentlyLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryDaoImpl implements CategoryDao {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    GetCurrentlyLoggedInUser getCurrentlyLoggedInUser;

    @Autowired
    private MessageSource messageSource;
    Long[] params={};


    @Override
    public List<Object[]> getSubcategory() {
        List<Object[]> objects = categoryRepository.getSubcategory();
        return objects;
    }

    @Override
    public List<Object[]> getAllMainCategory() {

        List<Object[]> objects = categoryRepository.getMainCategory();
        if (objects.isEmpty()) {
            throw new NullException(messageSource.getMessage("message16",params, LocaleContextHolder.getLocale()));
        }
        return objects;
    }

    @Override
    public List<Object[]> getAllSubCategory(String mainCategory) {
        return categoryRepository.getSubCategory(mainCategory);
    }

    @Override
    public void addNewSubCategory(Long parentCategoryId,Category category)
    {
        int result = categoryRepository.checkIfLeaf(parentCategoryId);
        if (result==1)
        {
            Optional<Category> category1 = categoryRepository.findById(parentCategoryId);
            if (category1.isPresent()) {
                category.setCategory(category1.get());
                category.setCreatedBy(getCurrentlyLoggedInUser.getCurrentUser());
                categoryRepository.save(category);
            }
            else
            {
                throw new NotFoundException(messageSource.getMessage("message5",params, LocaleContextHolder.getLocale()));
            }
        }
        else
        {
            throw new NullPointerException(messageSource.getMessage("message17",params, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void updateCategory(Category category, Long categoryId) {

        if (categoryRepository.findById(categoryId).isPresent()) {
            Category category1 = categoryRepository.findById(categoryId).get();
            category1.setName(category.getName());
            category1.setModifiedBy(getCurrentlyLoggedInUser.getCurrentUser());
            categoryRepository.save(category1);

        } else {
            throw new NotFoundException(messageSource.getMessage("message5",params, LocaleContextHolder.getLocale()));

        }
    }

    @Override
    public FilterDTO getFilteringDetails(Long categoryId) {
        Optional<Category> categoryOptional= categoryRepository.findById(categoryId);

        if(categoryOptional.isPresent()&& categoryRepository.checkIfLeaf(categoryId)==0)
        {
            Category category= categoryOptional.get();
            List<Long> listOfMetadataIdsForParticularCategory = categoryMetadataFieldValuesRepository.getMetadataId(categoryId);
            FilterDTO filterDTO= new FilterDTO();
            filterDTO.setCategoryName(category.getName());
            List<String> stringListForField= new ArrayList<>();
            List<String> stringListForValues= new ArrayList<>();
            List<String> brands= new ArrayList<>();
            Double minPrice=0D;
            Double maxPrice=0D;
            Set<Double> doubleSet= new TreeSet<>();

            for (Long idOfMetadata : listOfMetadataIdsForParticularCategory) {

                CategoryMetadataField categoryMetadataField= categoryMetadataFieldRepository.findById(idOfMetadata).get();
                stringListForField.add(categoryMetadataField.getName());
                stringListForValues.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(categoryId,idOfMetadata));
                Set<Product> productSet= category.getProducts();
                for (Product product: productSet)
                {
                    brands.add(product.getBrand());
                    Set<ProductVariation> productVariationSet= product.getProductVariations();
                    for (ProductVariation productVariation: productVariationSet)
                    {
                        doubleSet.add(productVariation.getPrice());
                    }

                }
                Double[] doubles= doubleSet.toArray(new Double[doubleSet.size()]);
                filterDTO.setBrands(brands);
                filterDTO.setFields(stringListForField);
                filterDTO.setValues(stringListForValues);
                filterDTO.setMinPrice(doubles[0]);
                filterDTO.setMaxPrice(doubles[doubleSet.size()-1]);

            }
            return filterDTO;
        }
        else {
            throw new NotFoundException(messageSource.getMessage("message18",params, LocaleContextHolder.getLocale()));
        }


    }

    @Override
    public ResponseEntity addMainCategory(Category category)
    {
        categoryRepository.save(category);
        return ResponseEntity.ok().body(messageSource.getMessage("message19",params, LocaleContextHolder.getLocale()));
    }

    /*Long cid = categoryId;

    while (categoryRepository.getCategoryParent(cid) != null) {
                Category category1 = categoryRepository.findById(categoryRepository.getCategoryParent(categoryId)).get();
                ViewCategoriesDTO categoriesDTO1 = new ViewCategoriesDTO();
                categoriesDTO1.setName(category1.getName());
                cid = category1.getId();
                viewCategoriesDTOList.add(categoriesDTO1);
            }*/

    //change
    @Override
    public List<ViewCategoriesDTO> viewSingleCategory( Long categoryId) {
        List<ViewCategoriesDTO> viewCategoriesDTOList = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            ViewCategoriesDTO categoriesDTO = new ViewCategoriesDTO();
            categoriesDTO.setName(category.getName());
            viewCategoriesDTOList.add(categoriesDTO);

            if (categoryRepository.checkIfLeaf(category.getId()) == 0) {
                List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(categoryId);
                for (Long l : longList) {
                    CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findById(l).get();//Size is added into the list
                    fields.add(categoryMetadataField.getName());
                    values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(categoryId, l));

                }

                categoriesDTO.setValues(values);
                categoriesDTO.setFieldName(fields);

            } else {
                ViewCategoriesDTO viewCategoriesDTO2= new ViewCategoriesDTO();
                viewCategoriesDTO2.setName(category.getName());

                List<Object[]> list = categoryRepository.getSubCategory(category.getName());
                for (Object[] objects : list) {

                    ViewCategoriesDTO viewCategoriesDTO= new ViewCategoriesDTO();
                    viewCategoriesDTO.setName(objects[0].toString());
                    viewCategoriesDTOList.add(viewCategoriesDTO);
                }

            }

        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("message49",params, LocaleContextHolder.getLocale()));
        }
            return viewCategoriesDTOList;
        }


    //change

   @Override
    public List<ViewCategoriesDTO> viewAllCategoriesForAdmin(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<ViewCategoriesDTO> viewCategoriesDTOS= new ArrayList<>();
        List<String> fields= new ArrayList<>();
        List<String> values=new ArrayList<>();

        for (Category category1: categoryRepository.findAll(paging))
        {
            ViewCategoriesDTO viewCategoriesDTO = new ViewCategoriesDTO();
            viewCategoriesDTO.setName(category1.getName());
            if(categoryRepository.checkIfLeaf(category1.getId())==0)
            {
                List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category1.getId());
                for (Long l : longList) {
                    CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findById(l).get();//Size is added into the list
                    fields.add(categoryMetadataField.getName());
                    values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category1.getId(), l));
                    viewCategoriesDTO.setValues(values);
                    viewCategoriesDTO.setFieldName(fields);

                }

            }

            viewCategoriesDTOS.add(viewCategoriesDTO);
        }
        return viewCategoriesDTOS;


    }


    @Override
    public List<ViewCategoriesDTO> viewAllCategoriesForSeller()
    {
        List<Object[]> list =  categoryRepository.getSubcategory();
        List<ViewCategoriesDTO> list1 = new ArrayList<>();
        for (Object[] objects:list)
        {
            ViewCategoriesDTO viewCategoriesDTO = new ViewCategoriesDTO();
            viewCategoriesDTO.setName(objects[0].toString());
            Long categoryId = categoryRepository.getIdOfParentCategory(objects[0].toString());
            Optional<Category> category = categoryRepository.findById(categoryId);
            Set<CategoryMetadataFieldValues> set = category.get().getCategoryMetadataFieldValues(); //L,M,S
            List<String> fields = new ArrayList<>();
            for (CategoryMetadataFieldValues categoryMetadataFieldValues:set)
            {
                fields.add(categoryMetadataFieldValues.getFieldValues());
                viewCategoriesDTO.setValues(fields);
            }
            List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
            List<String > fields1 = new ArrayList<>();
            for (Long l : longList)
            {
                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l);
                fields1.add(categoryMetadataField.get().getName());
                viewCategoriesDTO.setFieldName(fields1);
            }
            list1.add(viewCategoriesDTO);

        }
        return list1;
    }

    @Override
    public List<Object[]> getSubCategory(Long categoryId)
    {

        List<Object[]> list= new ArrayList<>();
        if(categoryRepository.findById(categoryId).isPresent())
        {
            if(categoryRepository.checkIfLeaf(categoryId)==1)
            {
                list = categoryRepository.getSubCategoryWithId(categoryId);
                return list;
            }
            else {
                throw new NotFoundException(messageSource.getMessage("message20",params, LocaleContextHolder.getLocale()));
            }

        }

        else {
            throw new NotFoundException(messageSource.getMessage("message5",params, LocaleContextHolder.getLocale()));

        }
    }
}
