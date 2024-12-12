package road_to_employment.interview_helper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class InterviewHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewHelperApplication.class, args);
	}

}
