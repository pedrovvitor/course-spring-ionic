package com.pedrolima.springrest.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pedrolima.springrest.dto.CustomerNewDTO;
import com.pedrolima.springrest.entities.Customer;
import com.pedrolima.springrest.entities.enums.CustomerType;
import com.pedrolima.springrest.repositories.CustomerRepository;
import com.pedrolima.springrest.resources.exceptions.FieldMessage;
import com.pedrolima.springrest.services.validation.utils.BR;

public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, CustomerNewDTO> {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public void initialize(CustomerInsert ann) {
	}

	@Override
	public boolean isValid(CustomerNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getType() == null) {
			list.add(new FieldMessage("type", "Type can't be null"));
		}

		if (objDto.getType().equals(CustomerType.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getType().equals(CustomerType.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		//validator customizado de email unico no banco
		Customer aux = customerRepository.findByEmail(objDto.getEmail());
		if(aux != null) {
			list.add((new FieldMessage("email", "Email already registered!")));
		}
		// inclua os testes aqui, inserindo erros na lista

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}