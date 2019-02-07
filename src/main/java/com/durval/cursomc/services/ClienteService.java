package com.durval.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.durval.cursomc.domain.Cliente;
import com.durval.cursomc.dto.ClienteDto;
import com.durval.cursomc.repositories.ClienteRepository;
import com.durval.cursomc.services.exception.DateIntegrityException;
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
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());		
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {

			throw new DateIntegrityException("Não é possivel excluir poque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {

		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction ){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}	
	
	
	public Cliente fromDto(ClienteDto objDto ) {
		
		 return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);		
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
		
}
