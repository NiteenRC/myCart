package com.fico.demo.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fico.demo.exception.CustomErrorType;
import com.fico.demo.model.Category;
import com.fico.demo.model.Doctor;
import com.fico.demo.repo.CategoryRepo;
import com.fico.demo.repo.DoctorRepo;
import com.fico.demo.util.Utility;
import com.fico.demo.util.WebUrl;

@RestController
public class DoctorController {

	public static final Logger log = LoggerFactory.getLogger(DoctorController.class);

	@Autowired
	public DoctorRepo doctorRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@RequestMapping(value = WebUrl.DOCTORS, method = RequestMethod.POST)
	public ResponseEntity<List<Doctor>> addproduct(@RequestBody List<Doctor> productList) {
		if (productList == null) {
			return new ResponseEntity(new CustomErrorType("input is empty"), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(doctorRepo.save(productList), HttpStatus.CREATED);
	}

	@RequestMapping(value = WebUrl.DOCTOR_AND_CATEGORYID, method = RequestMethod.POST)
	public ResponseEntity<Doctor> addproductList(@PathVariable int categoryID,
			@RequestParam("doctorData") String doctorData, @RequestParam("file") MultipartFile file) {

		Doctor prod = new Doctor();
		JsonNode jn = Utility.jsonToObject(doctorData);

		if (jn == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String productName = jn.get("doctorName").asText();
		Doctor p = doctorRepo.findByDoctorName(productName);

		if (p != null) {
			return new ResponseEntity(new CustomErrorType("Doctor is already exist"), HttpStatus.CONFLICT);
		}

		try {
			byte[] bytes = file.getBytes();
			prod.setImage(bytes);
		} catch (IOException e) {
			log.error("File is failed to save ", e);
			return new ResponseEntity(new CustomErrorType("File is failed to save" + e), HttpStatus.BAD_REQUEST);
		}

		Category category = new Category();
		category.setCategoryID(categoryID);

		prod.setDoctorName(productName);
		prod.setMobileNumber(jn.get("mobileNumber").asText());
		prod.setAddress(jn.get("address").asText());
		prod.setEmail(jn.get("email").asText());
		prod.setCategory(category);
		Doctor product = doctorRepo.save(prod);

		if (product == null) {
			return new ResponseEntity(new CustomErrorType("Doctor is not saved"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}

	@RequestMapping(value = WebUrl.DOCTOR_BY_DOCTORID, method = RequestMethod.DELETE)
	public ResponseEntity<Doctor> deleteProduct(@PathVariable int doctorID) {
		Doctor product = doctorRepo.findOne(doctorID);
		if (product != null) {
			doctorRepo.delete(doctorID);
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		return new ResponseEntity(new CustomErrorType("doctorID " + doctorID + " not found."), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = WebUrl.DOCTOR, method = RequestMethod.GET)
	public ResponseEntity<List<Doctor>> productList() {
		return new ResponseEntity<>(doctorRepo.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = WebUrl.DOCTOR_BY_DOCTORID, method = RequestMethod.GET)
	public ResponseEntity<Doctor> getProductsById(@PathVariable int doctorID) {
		Doctor product = doctorRepo.findOne(doctorID);
		if (product == null) {
			return new ResponseEntity(new CustomErrorType("ProductID: " + doctorID + " not found."),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@RequestMapping(value = WebUrl.DOCTOR_BY_CATEGORYID, method = RequestMethod.GET)
	public ResponseEntity<List<Doctor>> getDoctorsByCategoryId(@PathVariable int categoryID) {
		return new ResponseEntity<>(doctorRepo.findByCategoryCategoryID(categoryID), HttpStatus.OK);
	}

	@RequestMapping(value = WebUrl.DOCTORS, method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getProductsForAllCategories() {
		return new ResponseEntity<>(categoryRepo.findAll(), HttpStatus.OK);
	}
}