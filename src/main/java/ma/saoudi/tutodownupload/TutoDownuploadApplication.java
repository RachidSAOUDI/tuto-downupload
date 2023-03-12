package ma.saoudi.tutodownupload;

import ma.saoudi.tutodownupload.properties.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileUploadProperties.class})
public class TutoDownuploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutoDownuploadApplication.class, args);
	}

}
