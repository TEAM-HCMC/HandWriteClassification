package ac.kr.inu;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class InuApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,"
            + "./../dev.properties,"
            + "./../../prod.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(InuApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
