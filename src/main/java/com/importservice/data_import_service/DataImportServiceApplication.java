package com.importservice.data_import_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication

public class DataImportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataImportServiceApplication.class, args);
	}

}

// ✅ Controlador temporal para validar que el microservicio responde
@RestController
@RequestMapping("/api/test")
class TestController {

    @GetMapping
    public String check() {
        return "✅ Microservicio funcionando correctamente";
    }
}