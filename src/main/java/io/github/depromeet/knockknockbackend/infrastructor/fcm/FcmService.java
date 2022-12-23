package io.github.depromeet.knockknockbackend.infrastructor.fcm;


import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FcmService {

    public void sendGroupMessage(
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

        FirebaseMessaging.getInstance().sendMulticastAsync(multicast);
    }

    public void sendMessage(String token, String title, String content) {
        Message message =
                Message.builder()
                        .setToken(token)
                        .setNotification(
                                Notification.builder().setTitle(title).setBody(content).build())
                        .setApnsConfig(
                                ApnsConfig.builder()
                                        .setAps(Aps.builder().setSound("default").build())
                                        .build())
                        .build();
        FirebaseMessaging.getInstance().sendAsync(message);
    }
}
