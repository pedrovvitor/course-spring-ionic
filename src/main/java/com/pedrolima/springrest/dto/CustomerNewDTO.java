package com.pedrolima.springrest.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.pedrolima.springrest.services.validation.CustomerInsert;

@CustomerInsert
public class CustomerNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Mandatory filling")
	@Length(min = 5, max = 120, message = "Must contain between 5 and 120 characters")
	private String name;

	@NotEmpty(message = "Mandatory filling")
	@Email(message = "Invalid Email")
	private String email;

	@NotEmpty(message = "Mandatory filling")
	private String cpfOuCnpj;

	private Integer type;
	
	@NotEmpty(message = "Mandatory filling")
	private String password;

	@NotEmpty(message = "Mandatory filling")
	private String street;

	@NotEmpty(message = "Mandatory filling")
	private String number;

	private String complement;

	private String neighborhood;

	@NotEmpty(message = "Mandatory filling")
	private String zipCode;

	@NotEmpty(message = "Mandatory filling")
	private String phone1;

	private String phone2;

	private String phone3;

	private Long cityId;

	public CustomerNewDTO() {
		super();
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
