package com.durval.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.durval.cursomc.domain.Cliente;
import com.durval.cursomc.dto.ClienteDto;
import com.durval.cursomc.repositories.ClienteRepository;
import com.durval.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDto> {	
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	ClienteRepository repo;
		
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}	

	@Override
	public boolean isValid(ClienteDto objDto, ConstraintValidatorContext context) {
		
		
		
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);		
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();			
		
		Cliente aux = repo.findByEmail(objDto.getEmail());		
		if(aux != null && !aux.getId().equals(uriId)) {			
			list.add(new FieldMessage("email", "Email já existente"));			
		}		

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}













