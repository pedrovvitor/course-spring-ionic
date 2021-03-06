package com.pedrolima.springrest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pedrolima.springrest.dto.CategoryDTO;
import com.pedrolima.springrest.entities.Category;
import com.pedrolima.springrest.repositories.CategoryRepository;
import com.pedrolima.springrest.services.exceptions.DataIntegrityException;
import com.pedrolima.springrest.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;


	public Category findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Category> findAll() {
		return repository.findAll();
	}
	public Category insert(Category obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Category update(CategoryDTO objDto) {
		Category newObj = findById(objDto.getId());
		updateData(newObj, objDto);
		return repository.save(newObj);
	}

	public void deleteById(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Unable to delete Category wich contains Products");
		}
	}

	public Page<Category> findPage( Integer page, Integer size, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Category fromDTO(CategoryDTO objDto) {
		return new Category(objDto.getId(), objDto.getName());
	}
	
	private void updateData(Category newObj, CategoryDTO objDto) {
		newObj.setId(objDto.getId());
		newObj.setName(objDto.getName());
	}
	
}
