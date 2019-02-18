package quwen.core.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"quwen.core.config", "quwen.db.repository"})
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
