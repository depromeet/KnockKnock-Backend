package io.github.depromeet.knockknockbackend.infrastructor.fcm;


import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void sendMessage(String token, String content) {
        Message message =
                Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder().setBody(content).build())
                        .setApnsConfig(
                                ApnsConfig.builder()
                                        .setAps(Aps.builder().setSound("default").build())
                                        .build())
                        .build();
        FirebaseMessaging.getInstance().sendAsync(message);
    }
}
