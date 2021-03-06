package com.springBootEdu.petclinic.web;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.springBootEdu.petclinic.model.Owner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@ActiveProfiles("dev")
public class PetClinicRestControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Before
	public void setUp() {
		restTemplate = restTemplate.withBasicAuth("user2", "furki");
	}
	
	@Test
	public void testGetOwnerById() {
		
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/4", Owner.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Hadi"));
	}
	
	@Test
	public void testGetOwnersByLastName() {
		
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=Aydin", List.class);
		
		List<Map<String,String>> body = response.getBody();
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Furkan","Hasan","Nadire"));
		
	}
	
	@Test
	public void testGetOwners() {
		
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);
		
		List<Map<String,String>> body = response.getBody();
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Furkan","Hasan","Nadire","Fatma"));
		
	}
	
	@Test
	public void testCreateOwner() {
		
		Owner owner = new Owner();
		owner.setFirstName("Seyma");
		owner.setLastName("Yilmaz");
		
		URI location = restTemplate.postForLocation("http://localhost:8080/rest/owner", owner);
		
		Owner owner2 = restTemplate.getForObject(location, Owner.class);
		
		MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo(owner.getFirstName()));
		MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo(owner.getLastName()));
		
	}
	
	@Test
	public void testUpdateOwner() {
		
		Owner owner = restTemplate.getForObject("http://localhost:8080/rest/owner/1", Owner.class);
		
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Furkan"));
		
		owner.setFirstName("Furki");
		restTemplate.put("http://localhost:8080/rest/owner/1", owner);
		
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Furki"));
	}
	
	@Test
	public void testDeleteOwner() {
		
		try {
			restTemplate.delete("http://localhost:8080/rest/owner/1");
			restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
			Assert.fail("Should have not returned owner");
		}catch(RestClientException ex) {
			
		}
	}
	
	

}
