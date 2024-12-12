package acc.br.desafioSiteUniv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DesafioSiteUnivApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioSiteUnivApplication.class, args);
	}

}
