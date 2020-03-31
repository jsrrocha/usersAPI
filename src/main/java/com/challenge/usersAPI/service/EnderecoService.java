package com.challenge.usersAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.usersAPI.model.Endereco;
import com.challenge.usersAPI.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	public EnderecoRepository enderecoRepository;
	
	public void saveAddress(Endereco endereco) {
		enderecoRepository.save(endereco);
	}
	
	public Endereco findAddressByStreetAndNumber(String street, String number) {
		return enderecoRepository.findByRuaAndNumero(street, number);
	}
}
