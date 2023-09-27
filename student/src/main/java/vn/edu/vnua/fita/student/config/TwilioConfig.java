package vn.edu.vnua.fita.student.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TwilioConfig {
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String trialNumber;

    @Bean
    public void initializeTwilio() {
        Twilio.init(accountSid, authToken);
    }
}