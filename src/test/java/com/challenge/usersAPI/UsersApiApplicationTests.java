package com.challenge.usersAPI; 

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.challenge.usersAPI.model.Endereco;
import com.challenge.usersAPI.model.Pessoa;
import com.challenge.usersAPI.repository.EnderecoRepository;
import com.challenge.usersAPI.repository.PessoaRepository;
import com.challenge.usersAPI.service.EnderecoService;
import com.challenge.usersAPI.service.PessoaService;
import com.challenge.usersAPI.util.EnderecoUtil;
import com.challenge.usersAPI.util.PessoaUtil;

@SpringBootTest
class UsersApiApplicationTests {
	
	@Autowired
	public PessoaRepository pessoaRepositoryository;
	
	@Autowired
	public PessoaUtil pessoaUtil;
	
	@Autowired
	public PessoaService pessoaService;
	
	@Autowired
	public EnderecoRepository enderecoRepository;
	
	@Autowired
	public EnderecoUtil enderecoUtil;

	@Autowired
	public EnderecoService enderecoService;

	
	@Test
	@Transactional
	public void testAddPerson() throws IOException {
		
		//create person
		BufferedImage image = pessoaUtil
				.getImageFromURL("https://randomuser.me/api/portraits/men/64.jpg"); 
		byte[] photo = pessoaUtil.convertImageToBytes(image);
		String name = "Jose Rocha";
		String username = "josero";
		Pessoa pessoa = 
				new Pessoa(name,"jose@example.com",username, "teste123", "Masculino", photo);
		
		//save person	
		pessoaService.savePerson(pessoa); 
		
		Pessoa pessoaFound = pessoaService.findPessoaByUsername("josero"); 
		if(pessoaFound ==null) {
			fail(); 
		}
		assertEquals(pessoaFound.getNomeCompleto(), name);
	} 
	
	@Test
	@Transactional
	public void testAddEndereco() throws IOException {
		BufferedImage image = pessoaUtil
				.getImageFromURL("https://randomuser.me/api/portraits/men/64.jpg"); 
		byte[] photo = pessoaUtil.convertImageToBytes(image);
		
		String name = "Jose Rocha";
		String username = "josero";
		Pessoa pessoa = 
				new Pessoa(name,"jose@example.com",username, "teste123", "Masculino", photo);
		
		//save person	
		pessoaService.savePerson(pessoa); 
		
		Pessoa pessoaFound = pessoaService.findPessoaByUsername("josero"); 
		if(pessoaFound ==null) {
			fail("Pessoa não encontrada"); 
		}
		
		//create address
		String street = "Ipiranga";
		String number = "500";
		Endereco endereco = new Endereco(street, number, "Porto Alegre", "RS", pessoa);
	    
		//save address
		enderecoService.saveAddress(endereco); 
	    
		Endereco addressFound = enderecoService.findAddressByStreetAndNumber(street, number); 
	    if(addressFound == null) {
	    	fail("Endereço não encontrado"); 
	    }
		assertEquals(addressFound.getRua(), street);
		assertEquals(addressFound.getNumero(), number);
	} 

}
