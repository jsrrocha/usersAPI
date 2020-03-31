package com.challenge.usersAPI.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.challenge.usersAPI.model.Endereco;
import com.challenge.usersAPI.model.Pessoa;
import com.challenge.usersAPI.service.EnderecoService;
import com.challenge.usersAPI.service.PessoaService;

@Component
@SuppressWarnings("unchecked")
public class PessoaUtil {

	@Autowired
	public PessoaService pessoaService;

	@Autowired
	public EnderecoService enderecoService;

	@Autowired
	public EnderecoUtil enderecoUtil;

	public void callPersistDataEachMinute() {

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		Runnable task = () -> {
			try {
				persistData();

				if (isReachedLimitPeopleInDB()) {
					executor.shutdown();
				}
			} catch (Exception e) {
				e.getStackTrace();
			}
		};
		executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.MINUTES);
	}

	public boolean isReachedLimitPeopleInDB() {
		Boolean reachedLimit = false;
		List<Pessoa> people = pessoaService.findAll();
		if (people.size() > 300) {
			reachedLimit = true;
		}
		return reachedLimit;
	}

	public void persistData() throws IOException {

		Map<String, Object> user = getUserFromAPI();
		Pessoa pessoa = newPerson(user);
		pessoaService.savePerson(pessoa);

		Endereco endereco = enderecoUtil.newAddress(user, pessoa);
		enderecoService.saveAddress(endereco);

	}

	public Map<String, Object> getUserFromAPI() {
		
		// Get response in responseEntity
		RestTemplate restTemplate = new RestTemplate();
		Object response = restTemplate.getForEntity("https://randomuser.me/api/", Object.class);
		ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
		Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

		// Get the user of responseEntity
		List<Map<String, Object>> results = (List<Map<String, Object>>) responseBody.get("results");
		Map<String, Object> user = (Map<String, Object>) results.get(0);
		return user;
	}

	public Pessoa newPerson(Map<String, Object> user) throws IOException {
		String name = getName(user);
		String gender = getGender(user);
		byte[] photo = getPhoto(user);
		String email = user.get("email").toString();

		Map<String, String> loginMap = (Map<String, String>) user.get("login");
		String username = loginMap.get("username");
		String password = loginMap.get("password");

		Pessoa pessoa = new Pessoa(name, email, username, password, gender, photo);
		return pessoa;
	}

	public String getName(Map<String, Object> user) {
		StringBuilder allName = new StringBuilder();
		
		Map<String, String> nameMap = (Map<String, String>) user.get("name");
		allName.append(nameMap.get("first")).append(" ").append(nameMap.get("last"));
		
		return allName.toString();
	}

	public String getGender(Map<String, Object> user) {
		String gender = "";
		if (user.get("gender").toString().equals("female")) {
			gender = "Feminino";
		} else {
			gender = "Masculino";
		}
		return gender;
	}

	public byte[] getPhoto(Map<String, Object> user) throws IOException {
		Map<String, String> photoMap = (Map<String, String>) user.get("picture");
		BufferedImage image = getImageFromURL(photoMap.get("medium"));
		byte[] photo = convertImageToBytes(image);

		return photo;
	}
	
	public BufferedImage getImageFromURL(String src) throws IOException {
		URL url = new URL(src);
		BufferedImage image = ImageIO.read(url);
		return image;
	}

	public byte[] convertImageToBytes(BufferedImage image) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", byteArrayOutputStream);
		byte[] photo = byteArrayOutputStream.toByteArray();

		return photo;
	}

}
