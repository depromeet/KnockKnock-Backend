package io.github.depromeet.knockknockbackend.global.slack;

import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.ContextBlock;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.SectionBlock.SectionBlockBuilder;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.element.ImageElement;
import io.github.depromeet.knockknockbackend.domain.report.domain.Report;
import io.github.depromeet.knockknockbackend.domain.report.event.NewReportEvent;
import io.github.depromeet.knockknockbackend.domain.report.service.ReportUtils;
import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import io.github.depromeet.knockknockbackend.domain.user.event.RegisterUserEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackEventHandler {
    private final ReportUtils reportUtils;
    private final MethodsClient methodsClient;

    private final String SERVICE_CHANNEL = "C04G56UU8H3";

    @Async
    @TransactionalEventListener(
            classes = NewReportEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void handleNewReportEvent(NewReportEvent newReportEvent) {
        Long reportId = newReportEvent.getReportId();
        Report report = reportUtils.queryReport(reportId);
        // ????????? ??????
        Long reporterId = report.getReporter().getId();
        String reporterName = report.getReporter().getNickname();
        String reporterInfo = reporterId + " : " + reporterName;
        // ???????????? ??????
        Long defendantId = report.getDefendant().getId();
        String defendantName = report.getDefendant().getNickname();
        String defendantInfo = defendantId + " : " + defendantName;

        // ????????????
        String description = report.getDescription();

        // ?????? ?????? ??????
        Long notificationId = report.getNotificationId();
        String title = report.getTitle();
        String imageUrl = report.getImageUrl();
        String content = report.getContent();

        List<LayoutBlock> layoutBlocks = new ArrayList<>();
        layoutBlocks.add(getHeaderBlock("?????? ??????"));

        MarkdownTextObject reporterMarkDown =
                MarkdownTextObject.builder().text("*?????????:* " + reporterInfo).build();
        MarkdownTextObject defendantMarkDown =
                MarkdownTextObject.builder().text("*????????????:* " + defendantInfo).build();

        ContextBlock reporterBlock =
                ContextBlock.builder()
                        .elements(List.of(reporterMarkDown, defendantMarkDown))
                        .build();
        layoutBlocks.add(reporterBlock);

        layoutBlocks.add(divider());

        MarkdownTextObject reason =
                MarkdownTextObject.builder()
                        .text(
                                "*????????????:* "
                                        + description
                                        + "\n*?????? ?????????:* "
                                        + notificationId
                                        + "\n*?????????:* "
                                        + title
                                        + "\n*??????:* "
                                        + content)
                        .build();

        layoutBlocks.add(getSectionWithImage(reason, imageUrl));

        sendMessage(SERVICE_CHANNEL, layoutBlocks);
    }

    private static SectionBlock getSectionWithImage(
            MarkdownTextObject reason, @Nullable String imageUrl) {
        return section(
                section -> {
                    SectionBlockBuilder fields = section.fields(List.of(reason));
                    if (imageUrl != null) {
                        ImageElement imageElement = getImageElement(imageUrl);
                        fields.accessory(imageElement);
                    }
                    return fields;
                });
    }

    @Async
    @TransactionalEventListener(
            classes = RegisterUserEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void handleNewReportEvent(RegisterUserEvent registerUserEvent) {
        UserInfoVO userInfoVO = registerUserEvent.getUserInfoVO();
        String email = userInfoVO.getEmail();
        Long id = userInfoVO.getId();
        String nickname = userInfoVO.getNickname();
        String profilePath = userInfoVO.getProfilePath();
        String provider = registerUserEvent.getProvider();

        List<LayoutBlock> layoutBlocks = new ArrayList<>();
        layoutBlocks.add(getHeaderBlock("?????? ?????? ??????"));
        layoutBlocks.add(divider());
        MarkdownTextObject registerMD =
                MarkdownTextObject.builder()
                        .text(
                                "*?????? ?????????:* "
                                        + id
                                        + "\n*?????????:* "
                                        + nickname
                                        + "\n*?????????:* "
                                        + email
                                        + "\n*????????????:* "
                                        + provider)
                        .build();
        layoutBlocks.add(getSectionWithImage(registerMD, profilePath));

        sendMessage(SERVICE_CHANNEL, layoutBlocks);
    }

    private static HeaderBlock getHeaderBlock(String headerText) {
        return Blocks.header(headerBlockBuilder -> headerBlockBuilder.text(plainText(headerText)));
    }

    private void sendMessage(String channel, List<LayoutBlock> layoutBlocks) {
        ChatPostMessageRequest chatPostMessageRequest =
                ChatPostMessageRequest.builder()
                        .channel(channel)
                        .text("")
                        .blocks(layoutBlocks)
                        .build();

        try {
            ChatPostMessageResponse chatPostMessageResponse =
                    methodsClient.chatPostMessage(chatPostMessageRequest);
            chatPostMessageResponse.getError();
        } catch (SlackApiException | IOException slackApiException) {
            log.error(slackApiException.toString());
        }
    }

    private static ImageElement getImageElement(String profilePath) {
        return ImageElement.builder().imageUrl(profilePath).altText(profilePath).build();
    }
}
