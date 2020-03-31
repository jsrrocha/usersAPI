package com.challenge.usersAPI.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.challenge.usersAPI.model.Endereco;
import com.challenge.usersAPI.model.Pessoa;

@Component
@SuppressWarnings("unchecked")
public class EnderecoUtil {
	
	public Endereco newAddress(Map<String, Object> user, Pessoa pessoa) {
		
		Map<String, Object> locationMap = (Map<String, Object>) user.get("location");
		String city = locationMap.get("city").toString();
		String state = locationMap.get("state").toString();

		Map<String, Object> streetMap = (Map<String, Object>) locationMap.get("street");
		String street = streetMap.get("name").toString(); 
		String number = streetMap.get("number").toString();

		Endereco endereco = new Endereco(street, number, city, state, pessoa);
		return endereco;
	}
}
