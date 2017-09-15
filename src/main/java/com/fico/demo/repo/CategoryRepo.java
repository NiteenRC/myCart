package com.fico.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fico.demo.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

	Category findByCategoryName(String categoryName);

	List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);
}
