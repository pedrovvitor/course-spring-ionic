package com.pedrolima.springrest.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.pedrolima.springrest.entities.Customer;
import com.pedrolima.springrest.services.validation.CustomerUpdate;

@CustomerUpdate
public class CustomerDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	@NotEmpty(message = "Mandatory filling")
	@Length(min = 5, max = 120, message = "Must contain between 5 and 120 characters")
	private String name;
	
	@NotEmpty(message = "Mandatory filling")
	@Email(message = "Invalid Email")
	private String email;

	public CustomerDTO() {
	}
	
	public CustomerDTO(Customer obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
