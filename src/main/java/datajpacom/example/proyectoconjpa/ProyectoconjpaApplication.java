package datajpacom.example.proyectoconjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import datajpacom.example.proyectoconjpa.service.IUploadFileService;

@SpringBootApplication
public class ProyectoconjpaApplication implements CommandLineRunner {

	@Autowired
	IUploadFileService uploadFileService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(ProyectoconjpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		uploadFileService.deleteAll();
		uploadFileService.init();

		String password = "12345";
		for(int i=0; i<2; i++){
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println("PASSWORD ENCRIPTADO " + bcryptPassword);
		}

	}

}
