package io.github.depromeet.knockknockbackend.global.config;


import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import java.util.*;


/**
 * Swagger 사용 환경을 위한 설정 파일
 * */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(authSetting())
            .info(swaggerInfo());
    }

    private  Info swaggerInfo(){
        License license = new License();
        license.setUrl("https://github.com/depromeet/KnockKnock-Backend");
        license.setName("디프만 7팀 낙낙");

        return new Info()
            .version("v0.0.1")
            .title("\"낙낙 서버 API문서\"")
            .description("낙낙 서버의 API 문서 입니다.")
            .license(license);
    }

    private Components authSetting(){
        return new Components().addSecuritySchemes("access-token",
            new SecurityScheme()
                .type(Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(In.HEADER).name("Authorization"));
    }

    //    static {
    //        SpringDocUtils.getConfig().addAnnotationsToIgnore(MemberId.class);
    //    }

}