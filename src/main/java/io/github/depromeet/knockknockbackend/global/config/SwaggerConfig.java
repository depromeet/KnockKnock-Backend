package io.github.depromeet.knockknockbackend.global.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.depromeet.knockknockbackend.global.annotation.DisableSecurity;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.method.HandlerMethod;
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

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }


    @Bean
    public OperationCustomizer customize() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            DisableSecurity methodAnnotation = handlerMethod.getMethodAnnotation(
                DisableSecurity.class);
            // DisableSecurity 어노테이션있을시 스웨거 시큐리티 설정 삭제
            if(methodAnnotation != null){
                operation.setSecurity(Collections.emptyList());
            }
            //태그 중복 설정시 제일 구체적인 값만 태그로 설정
            List<String> tags = operation.getTags();
            if(tags != null && !tags.isEmpty() ){
                operation.setTags(Collections.singletonList(tags.get(0)));
            }
            return operation;
        };
    }

}