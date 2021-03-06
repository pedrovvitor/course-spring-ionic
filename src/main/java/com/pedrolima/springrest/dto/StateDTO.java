package com.pedrolima.springrest.dto;

import java.io.Serializable;

import com.pedrolima.springrest.entities.State;

public class StateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	
	public StateDTO() {
	}
	
	public StateDTO(State obj) {
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
