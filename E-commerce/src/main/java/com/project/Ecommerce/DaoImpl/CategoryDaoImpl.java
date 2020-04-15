package com.project.Ecommerce.DaoImpl;


import com.project.Ecommerce.DTO.FilterDTO;
import com.project.Ecommerce.DTO.ViewCategoriesDTO;
import com.project.Ecommerce.Dao.CategoryDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
    GetCurrentDetails getCurrentDetails;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;


    @Override
    public List<Object[]> getSubcategory() {
        List<Object[]> objects = categoryRepository.getSubcategory();
        return objects;
    }

    @Override
    public List<Object[]> getAllMainCategory() {

        List<Object[]> objects = categoryRepository.getMainCategory();
        if (objects.isEmpty()) {
            throw new NullException("No category Found");
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
                categoryRepository.save(category);
            }
            else
            {
                throw new NotFoundException("category with this id is not present");
            }
        }
        else
        {
            throw new NullPointerException("parent category you selected is already a leaf node");
        }
    }

    @Override
    public void updateCategory(Category category, Long categoryId) {

        if (categoryRepository.findById(categoryId).isPresent()) {
            Category category1 = categoryRepository.findById(categoryId).get();
            category1.setName(category.getName());
            categoryRepository.save(category1);

        } else {
            throw new NotFoundException("This category ID is wrong as no entry is present for this ID");

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
            throw new NotFoundException("The categoryId is wrong and this is not a leaf node ");
        }


    }

    @Override
    public ResponseEntity addMainCategory(Category category)
    {
        categoryRepository.save(category);
        return ResponseEntity.ok().body("category added");
    }

    //change
    @Override
    public List<Object[]> viewSingleCategory( Long categoryId)
    {
        List<Object[]> list= new ArrayList<>();
        Optional<Category> categoryOptional= categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent())
        {
            list.add(categoryRepository.getCategoryName(categoryId));
            Long cid = categoryId;
            while(categoryRepository.getCategoryParent(cid)!=null)
            {
                list.add(categoryRepository.getCategoryName(categoryRepository.getCategoryParent(cid)));
                cid= categoryRepository.getCategoryParent(cid);

            }
            if(categoryRepository.checkIfLeaf(categoryId)==0)
            {
                List<Long> longList= categoryMetadataFieldValuesRepository.getMetadataId(categoryId); //metadata id of that category
                for(Long l: longList)
                {
                    list.addAll(categoryMetadataFieldRepository.getMetadataField(l)); //Size is added into the list
                }
                list.addAll(categoryMetadataFieldValuesRepository.getValues(categoryId)); //LMS is added
            }

            else {
                list.addAll(categoryRepository.getSubCategoryWithId(categoryId)); //Shirts jeans winter wear is added
            }

            return list;
        }

        else {
            throw new NotFoundException("This category ID is wrong");
        }

    }

    //change
    @Override
    public List<Object[]> viewAllCategories()
    {
        List<Object[]> list= new ArrayList<>();
        for (Category category: categoryRepository.findAll())
        {
            if (categoryRepository.checkIfLeaf(category.getId())==0)
            {
                list.add(categoryRepository.getCategoryName(category.getId()));
                List<Long> longList= categoryMetadataFieldValuesRepository.getMetadataId(category.getId()); //metadata id of that category
                for(Long l: longList)
                {
                    list.addAll(categoryMetadataFieldRepository.getMetadataField(l)); //Size is added into the list
                }
                list.addAll(categoryMetadataFieldValuesRepository.getValues(category.getId())); //LMS is added
            }

            else {
                list.add(categoryRepository.getCategoryName(category.getId()));
            }
        }
        return list;
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
                throw new NotFoundException("This is a leaf node and doesn't have any sub-category");
            }

        }

        else {
            throw new NotFoundException("This category Id is wrong");

        }
    }
}
