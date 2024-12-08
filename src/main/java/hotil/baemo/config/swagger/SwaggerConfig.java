package hotil.baemo.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatApi(){
        return GroupedOpenApi.builder()
            .group("Chat API")
            .packagesToScan("hotil.baemo.domains.chat")
            .build();
    }

    @Bean
    public GroupedOpenApi clubApi(){
        return GroupedOpenApi.builder()
            .group("Club API")
            .packagesToScan("hotil.baemo.domains.clubs")
            .build();
    }

    @Bean
    public GroupedOpenApi communityApi(){
        return GroupedOpenApi.builder()
            .group("Community API")
            .packagesToScan("hotil.baemo.domains.comment","hotil.baemo.domains.community")
            .build();
    }

    @Bean
    public GroupedOpenApi exerciseApi(){
        return GroupedOpenApi.builder()
            .group("Exercise API")
            .packagesToScan("hotil.baemo.domains.exercise")
            .build();
    }

    @Bean
    public GroupedOpenApi noticeApi(){
        return GroupedOpenApi.builder()
            .group("Notice API")
            .packagesToScan("hotil.baemo.domains.notice")
            .build();
    }

    @Bean
    public GroupedOpenApi notificationApi(){
        return GroupedOpenApi.builder()
            .group("Notification API")
            .packagesToScan("hotil.baemo.domains.notification")
            .build();
    }

    @Bean
    public GroupedOpenApi relationApi(){
        return GroupedOpenApi.builder()
            .group("Relation API")
            .packagesToScan("hotil.baemo.domains.relation")
            .build();
    }

    @Bean
    public GroupedOpenApi reportApi(){
        return GroupedOpenApi.builder()
            .group("Report API")
            .packagesToScan("hotil.baemo.domains.report")
            .build();
    }

    @Bean
    public GroupedOpenApi searchApi(){
        return GroupedOpenApi.builder()
            .group("Search API")
            .packagesToScan("hotil.baemo.domains.search")
            .build();
    }

    @Bean
    public GroupedOpenApi userApi(){
        return GroupedOpenApi.builder()
            .group("User API")
            .packagesToScan("hotil.baemo.domains.users")
            .build();
    }



    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }
    private Info apiInfo() {
        return new Info()
                .title("BaeMo API") // API의 제목
                .description("일해라. 프론트 노예들아.") // API에 대한 설명
                .version("1.2.2-1"); // API의 버전
    }
}
