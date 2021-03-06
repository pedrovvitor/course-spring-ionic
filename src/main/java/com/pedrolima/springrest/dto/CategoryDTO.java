package com.pedrolima.springrest.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.pedrolima.springrest.entities.Category;

public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotEmpty(message = "Mandatory filling")
	@Length(min = 5, max = 80, message = "Must contain between 5 and 80 characters")
	private String name;

	public CategoryDTO() {
	}
	
	public CategoryDTO(Category obj) {
		id = obj.getId();
		name = obj.getName();
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

}
