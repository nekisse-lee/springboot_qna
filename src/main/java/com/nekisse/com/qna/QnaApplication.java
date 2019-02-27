package com.nekisse.com.qna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
public class QnaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QnaApplication.class, args);
    }



    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("qna")
            .apiInfo(apiInfo())
            .select()
            .paths(PathSelectors.ant("/api/**"))
            .build()
            ;

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("My Qna Api")
            .description("My Qna Api")
            .termsOfServiceUrl("termsOfServiceUrl")
            .contact("contact")
            .license("license")
            .licenseUrl("licenseUrl")
            .version("2.0")
            .build();
    }
}
