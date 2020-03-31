package com.challenge.usersAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.challenge.usersAPI.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	public Pessoa findByUsername(String username); 
} 
