package edu.khanh.ProductManagement.service;

import edu.khanh.ProductManagement.dto.request.CreateCategoryRequest;
import edu.khanh.ProductManagement.dto.request.UpdateCategoryRequest;
import edu.khanh.ProductManagement.entity.Category;
import edu.khanh.ProductManagement.exception.BadException;
import edu.khanh.ProductManagement.exception.DuplicateResourceException;
import edu.khanh.ProductManagement.exception.ResourceNotFoundException;
import edu.khanh.ProductManagement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Danh mục","ID",id));
    }

    public Category create(CreateCategoryRequest request){
        if(categoryRepository.existsByName(request.getName())){
            throw new DuplicateResourceException("Danh mục","tên", request.getName());
        }

        Category category=new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    public Category update(Long id,UpdateCategoryRequest request){
        //Kiem tra ten da ton tai voi category khac
        boolean isNameExisted=categoryRepository.findAll().stream()
                .anyMatch(category -> category.getName().equals(request.getName())
                        && !category.getId().equals(id));
        if (isNameExisted) {
            throw new DuplicateResourceException("Danh mục","tên",request.getName());
        }

        Category category=findById(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    public void delete(Long id ){
        Category category=findById(id);

        //Kiem tra product trong category co ton tai khong
        boolean existedProductInside= !category.getProducts().isEmpty();
        if(existedProductInside){
            throw new BadException("Không thể xóa Category đang chứa Product");
        }
        categoryRepository.delete(category);
    }
}
