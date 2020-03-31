package com.challenge.usersAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.challenge.usersAPI.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	public Endereco findByRuaAndNumero(String street, String number); 
}
