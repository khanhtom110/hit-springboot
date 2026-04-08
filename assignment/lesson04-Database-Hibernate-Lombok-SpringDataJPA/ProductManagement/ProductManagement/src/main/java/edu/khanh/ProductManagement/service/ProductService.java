package edu.khanh.ProductManagement.service;

import edu.khanh.ProductManagement.dto.request.CreateProductRequest;
import edu.khanh.ProductManagement.dto.request.UpdateProductRequest;
import edu.khanh.ProductManagement.entity.Category;
import edu.khanh.ProductManagement.entity.Product;
import edu.khanh.ProductManagement.exception.DuplicateResourceException;
import edu.khanh.ProductManagement.exception.ResourceNotFoundException;
import edu.khanh.ProductManagement.repository.CategoryRepository;
import edu.khanh.ProductManagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id)
                .orElseThrow((() ->new ResourceNotFoundException("Sản phẩm","ID",id)));
    }

    public Product create(CreateProductRequest request){
        Category category=categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->  new ResourceNotFoundException("Danh mục","ID",request.getCategoryId()));

        if(productRepository.existsByNameAndCategory_Id(request.getName(), request.getCategoryId())){
            throw new DuplicateResourceException("Tên sản phẩm","mã danh mục",request.getCategoryId());
        }
        Product product=new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setDescription(request.getDescription());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product update(Long id,UpdateProductRequest request){
        Product product= findById(id);

        Category category=categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->  new ResourceNotFoundException("Danh mục","ID",request.getCategoryId()));

        //Kiem tra ten da ton tai (Voi cac product khac) trong category
        boolean isNameExisted=productRepository.findAll().stream()
                .anyMatch(p -> p.getName().equals(request.getName())
                        && p.getCategory().getId().equals(request.getCategoryId())
                        && !p.getId().equals(id));
        if(isNameExisted){
            throw new DuplicateResourceException("Tên sản phẩm","mã danh mục",request.getCategoryId());
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        product.setActive(request.isActive());

        return productRepository.save(product);
    }

    public void delete(Long id){
        Product product=findById(id);
        productRepository.delete(product);
    }

    public List<Product> findByName(String name){
        return productRepository.findByNameContaining(name);
    }

    public List<Product> findByCategoryId(Long id){
        return productRepository.findByCategory_Id(id);
    }

    public List<Product> findByPrice(Double min, Double max){
        return productRepository.findByPriceBetween(min,max);
    }
}
