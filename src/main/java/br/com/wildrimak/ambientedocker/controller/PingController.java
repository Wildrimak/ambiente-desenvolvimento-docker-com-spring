package br.com.wildrimak.ambientedocker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public String ping() {

		String text = "Wildrimak!\nAll Right! \nAdded new informations\nWith live reload too! And only me!";
		System.out.println(text);
		return text;

	}

}
