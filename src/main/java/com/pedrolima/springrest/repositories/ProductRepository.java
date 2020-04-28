package com.pedrolima.springrest.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pedrolima.springrest.entities.Category;
import com.pedrolima.springrest.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cat WHERE obj.name LIKE '%:name%' AND cat IN :categories")
	Page<Product> search(@Param("name") String name, @Param("categories") List<Category> categories, PageRequest pageRequest);

}
