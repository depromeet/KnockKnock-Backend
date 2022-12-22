package io.github.depromeet.knockknockbackend.infrastructor.slack;


import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackConfig {

    @Value("${slack.token}")
    private String SLACK_TOKEN;

    @Bean
    public MethodsClient getClient() {
        Slack instance = Slack.getInstance();
        return instance.methods(SLACK_TOKEN);
    }
}
