package com.springBootEdu.petclinic.dao;

import java.util.List;

import com.springBootEdu.petclinic.model.Owner;

public interface OwnerRepository {
	List<Owner> findAll();
	Owner findById(Long id);
	List<Owner> findByLastName(String lastName);
	void create(Owner owner);
	void update(Owner owner);
	void delete(Long id);

}
