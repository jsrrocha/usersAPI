package com.challenge.usersAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.challenge.usersAPI.util.PessoaUtil;

@RestController
@RequestMapping("pessoa")
public class PessoaController {
	
	@Autowired
	public PessoaUtil pessoaUtil;
	
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
}
