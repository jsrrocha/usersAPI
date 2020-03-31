package com.challenge.usersAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.usersAPI.model.Pessoa;
import com.challenge.usersAPI.service.PessoaService;
import com.challenge.usersAPI.util.PessoaUtil;

@RestController
@RequestMapping("pessoa")
public class PessoaController {
	
	@Autowired
	public PessoaUtil pessoaUtil;
	
	@Autowired 
	public PessoaService pessoaService;
	
	@PostMapping("/add")
	public ResponseEntity<String> persistData() {
		try {
			pessoaUtil.persistData();
			return new ResponseEntity<String>("Dados persistidos com sucesso",HttpStatus.OK);
		}catch (Exception e) {
			e.getStackTrace();
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		try {
			List<Pessoa> people = pessoaService.findAll();
			return new ResponseEntity<List<Pessoa>>(people,HttpStatus.OK);
		}catch (Exception e) {
			e.getStackTrace();
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
}
