package com.springBootEdu.petclinic.exception;

public class OwnerNotFoundException extends RuntimeException {

	public OwnerNotFoundException(String message) {
		super(message);
	}
   
}
