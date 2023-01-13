package io.github.depromeet.knockknockbackend.infrastructor.fcm;


import com.google.api.core.ApiFuture;
import com.google.firebase.ErrorCode;
import com.google.firebase.messaging.*;
import io.github.depromeet.knockknockbackend.domain.notification.exception.FcmServerException;
import io.github.depromeet.knockknockbackend.domain.notification.exception.FcmTokenInvalidException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmService {

    public ApiFuture<BatchResponse> sendGroupMessageAsync(
            List<String> tokenList, String title, String content, String imageUrl) {
        MulticastMessage multicast =
                MulticastMessage.builder()
                        .addAllTokens(tokenList)
                        .setNotification(
                                Notification.builder()
                                        .setTitle(title)
                                        .setBody(content)
                                        .setImage(imageUrl)
                                        .build())
                        .setApnsConfig(
                                ApnsConfig.builder()
                                        .setAps(Aps.builder().setSound("default").build())
                                        .build())
                        .build();

        return FirebaseMessaging.getInstance().sendMulticastAsync(multicast);
    }

    public void sendMessageSync(String token, String content) {
        Message message =
                Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder().setBody(content).build())
                        .setApnsConfig(
                                ApnsConfig.builder()
                                        .setAps(Aps.builder().setSound("default").build())
                                        .build())
                        .build();
        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            ErrorCode errorCode = e.getErrorCode();
            log.error(
                    "**[sendMessageSync] errorCode: {}, errorMessage: {}, messagingErrorCode: {}",
                    errorCode,
                    e.getMessage());
            if (errorCode.equals(ErrorCode.INVALID_ARGUMENT)) {
                throw FcmTokenInvalidException.EXCEPTION;
            }
            throw FcmServerException.EXCEPTION;
        }
    }
}
