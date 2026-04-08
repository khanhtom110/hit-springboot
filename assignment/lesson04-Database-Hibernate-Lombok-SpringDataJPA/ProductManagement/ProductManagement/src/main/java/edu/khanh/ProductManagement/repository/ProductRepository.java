package edu.khanh.ProductManagement.repository;

import edu.khanh.ProductManagement.entity.Category;
import edu.khanh.ProductManagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContaining(String keyword);

    List<Product> findByCategory_Id(Long categoryId);

    List<Product> findByPriceBetween(Double min, Double max);

    List<Product> findByActiveTrue();

    boolean existsByNameAndCategory_Id(String name, Long categoryId);
}
