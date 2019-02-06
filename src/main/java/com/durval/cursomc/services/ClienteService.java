package com.durval.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.durval.cursomc.domain.Cliente;
import com.durval.cursomc.repositories.ClienteRepository;
import com.durval.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id) {		
		   Optional<Cliente> obj = repo.findById(id); 		
		   return obj.orElseThrow(() -> new ObjectNotFoundException(   
				   		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 	
	}
		
}
