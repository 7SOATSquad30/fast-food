package br.com.fiap.grupo30.fastfood;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "My API", version = "v1"),
        servers = {
            @Server(url = "http://localhost:8080", description = "Local Development"),
            @Server(
                    url = "https://29glms05ff.execute-api.us-east-1.amazonaws.com",
                    description = "Production Server")
        })
public class FastfoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastfoodApplication.class, args);
    }
}
