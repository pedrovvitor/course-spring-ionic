package com.pedrolima.springrest.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pedrolima.springrest.dto.CustomerDTO;
import com.pedrolima.springrest.dto.CustomerNewDTO;
import com.pedrolima.springrest.entities.Address;
import com.pedrolima.springrest.entities.City;
import com.pedrolima.springrest.entities.Customer;
import com.pedrolima.springrest.entities.enums.CustomerType;
import com.pedrolima.springrest.entities.enums.Profile;
import com.pedrolima.springrest.repositories.CustomerRepository;
import com.pedrolima.springrest.security.UserSS;
import com.pedrolima.springrest.services.exceptions.AuthorizationException;
import com.pedrolima.springrest.services.exceptions.DataIntegrityException;
import com.pedrolima.springrest.services.exceptions.ObjectNotFoundException;


@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value
	("${img.prefix.client.profile}")
	private String prefix;
	
	@Value
	("${img.profile.size}")
	private Integer size;
	
	public Customer findById(Long id) {
		
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Access denied.");
		}
		return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id));
	}


	public Customer insert(CustomerNewDTO objDto) {
		Customer obj = fromDTO(objDto);
		return repository.save(obj);
	}

	public Customer update(CustomerDTO objDto) {
		Customer newObj = findById(objDto.getId());
		udpateDate(newObj, objDto);
		return repository.save(newObj);
	}

	public void deleteById(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Unable to delete Customer wich contains Orders");
		}
	}

	public List<Customer> findAll() {
		return repository.findAll();
	}
	
	public Customer findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Access denied.");
		}
		Customer obj = repository.findByEmail(email);
		if(obj == null) {
			throw new ObjectNotFoundException("Object not found! id: " + user.getId() + " , Type: " + Customer.class.getName());
		}
		return obj;
	}
	
	public Page<Customer> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Customer fromDTO(CustomerDTO objDto) {
		return new Customer(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);
	}

	public Customer fromDTO(CustomerNewDTO objDto) {
		Customer obj = new Customer(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				CustomerType.toEnum(objDto.getType()), pe.encode(objDto.getPassword()));
		Address address = new Address(null, objDto.getStreet(), objDto.getNumber(), objDto.getComplement(),
				objDto.getNeighborhood(), objDto.getZipCode(), obj, new City(objDto.getCityId(), null, null));
		obj.getAddresses().add(address);
		obj.getPhones().add(objDto.getPhone1());
		if (objDto.getPhone2() != null) {
			obj.getPhones().add(objDto.getPhone2());
		}
		if (objDto.getPhone3() != null) {
			obj.getPhones().add(objDto.getPhone3());
		}
		return obj;
	}

	private void udpateDate(Customer newObj, CustomerDTO objDto) {
		newObj.setName(objDto.getName());
		newObj.setEmail(objDto.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Access denied.");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
	
	
}
