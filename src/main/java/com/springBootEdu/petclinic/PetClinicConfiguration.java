package com.springBootEdu.petclinic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetClinicConfiguration {
	@Autowired
	private PetClinicProperties petClinicProperties;
	
	@PostConstruct
	public void init() {
		petClinicProperties.setDisplayOwnersWithPets(false);
		System.out.println("Display owners with pets :"+ petClinicProperties.isDisplayOwnersWithPets());
	}

}
