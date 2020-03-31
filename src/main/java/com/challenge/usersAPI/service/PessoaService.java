package com.challenge.usersAPI.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.usersAPI.model.Pessoa;
import com.challenge.usersAPI.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	public PessoaRepository pessoaRepositoryository;
	
	public void savePerson(Pessoa pessoa) {
		pessoaRepositoryository.saveAndFlush(pessoa);
	}
	
	public List<Pessoa> findAll(){
		Iterable<Pessoa> peopleInterable = pessoaRepositoryository.findAll();
		
		List<Pessoa> people = StreamSupport.stream(peopleInterable.spliterator(), false)
		    .collect(Collectors.toList());
		return people;
	}
	

	public Pessoa findPessoaByUsername(String username) {
		return pessoaRepositoryository.findByUsername(username);
	}

}
