package com.javaegitimleri.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.dao.PetRepository;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;

@Service
public class PetClinicServiceImpl implements PetClinicService {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	@Secured(value= {"ROLE_USER","ROLE_EDITOR"})
	public List<Owner> findOwners() {
		return ownerRepository.findAll();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Owner> findOwners(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Owner findOwner(Long id) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(id);
		if(owner == null)
			throw new OwnerNotFoundException("Owner not found with id :"+id);
		return owner;
	}

	@Override
	public void createOwner(Owner owner) {
		ownerRepository.create(owner);
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("k@s");
		msg.setTo("m@y");
		msg.setSubject("Owner created!");
		msg.setText("Owner entity with id :" + owner.getId() + " created successfully.");

		mailSender.send(msg);

	}

	@Override
	public void updateOwner(Owner owner) {
		ownerRepository.update(owner);

	}

	@Override
	public void deleteOwner(Long id) {
		petRepository.deleteByOwnerId(id);
		ownerRepository.delete(id);
		//if(true) throw new RuntimeException("testing rollback...");

	}

}
