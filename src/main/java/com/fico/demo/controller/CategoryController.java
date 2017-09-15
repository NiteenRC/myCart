package com.fico.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fico.demo.exception.CustomErrorType;
import com.fico.demo.model.Category;
import com.fico.demo.repo.CategoryRepo;
import com.fico.demo.repo.DoctorRepo;
import com.fico.demo.util.WebUrl;

@RestController
public class CategoryController {

	public static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	public CategoryRepo categoryRepo;

	@Autowired
	public DoctorRepo doctorRepo;

	@RequestMapping(value = WebUrl.CATEGORY, method = RequestMethod.POST)
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		System.out.println("dddddd "+category.getCategoryName());
		Category cat = categoryRepo.save(category);
		if (cat == null) {
			return new ResponseEntity(new CustomErrorType("Category is not saved"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cat, HttpStatus.CREATED);
	}

	@RequestMapping(value = WebUrl.CATEGORY + "/{categoryID}", method = RequestMethod.DELETE)
	public ResponseEntity<Category> deleteCategory(@PathVariable Integer categoryID) {
		Category category = categoryRepo.findOne(categoryID);
		if (category == null) {
			return new ResponseEntity(new CustomErrorType("Category with categoryID " + categoryID + " not found."),
					HttpStatus.NOT_FOUND);
		}
		categoryRepo.delete(categoryID);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@RequestMapping(value = WebUrl.CATEGORY, method = RequestMethod.GET)
	public ResponseEntity<List<Category>> findAllCategoryList() {
		return new ResponseEntity<>(categoryRepo.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = WebUrl.CATEGORY_BY_NAME + "{categoryName}", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> findByCategoryName(@PathVariable String categoryName) {
		if (categoryName == null) {
			return new ResponseEntity(new CustomErrorType("input is empty"), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(categoryRepo.findByCategoryNameContainingIgnoreCase(categoryName), HttpStatus.OK);
	}
}
