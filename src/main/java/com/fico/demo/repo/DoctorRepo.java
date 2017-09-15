package com.fico.demo.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fico.demo.model.Doctor;


@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

	List<Doctor> findByCategoryCategoryID(int categoryID);

	//void deleteByCategoryCategoryID(int categoryID);

	Doctor findByDoctorName(String doctorName);
}