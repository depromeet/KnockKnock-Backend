package io.github.depromeet.knockknockbackend.global.slack;

import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.ContextBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.element.ImageElement;
import io.github.depromeet.knockknockbackend.domain.report.domain.Report;
import io.github.depromeet.knockknockbackend.domain.report.event.NewReportEvent;
import io.github.depromeet.knockknockbackend.domain.report.service.ReportUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        // 신고자 정보
        Long reporterId = report.getReporter().getId();
        String reporterName = report.getReporter().getNickname();
        String reporterInfo = reporterId + " : " + reporterName;
        // 피신고자 정보
        Long defendantId = report.getDefendant().getId();
        String defendantName = report.getDefendant().getNickname();
        String defendantInfo = defendantId + " : " + defendantName;

        // 신고사유
        String description = report.getDescription();

        // 알림 관련 내용
        Long notificationId = report.getNotificationId();
        String title = report.getTitle();
        String imageUrl = report.getImageUrl();
        String content = report.getContent();

        List<LayoutBlock> layoutBlocks = new ArrayList<>();
        layoutBlocks.add(
                Blocks.header(headerBlockBuilder -> headerBlockBuilder.text(plainText("신고 알림"))));

        MarkdownTextObject reporterMarkDown =
                MarkdownTextObject.builder().text("*신고자:* " + reporterInfo).build();
        MarkdownTextObject defendantMarkDown =
                MarkdownTextObject.builder().text("*피신고자:* " + defendantInfo).build();

        ContextBlock reporterBlock =
                ContextBlock.builder()
                        .elements(List.of(reporterMarkDown, defendantMarkDown))
                        .build();
        layoutBlocks.add(reporterBlock);

        layoutBlocks.add(divider());

        MarkdownTextObject reason =
                MarkdownTextObject.builder()
                        .text(
                                "*신고사유:* "
                                        + description
                                        + "\n*알림 아이디:* "
                                        + notificationId
                                        + "\n*타이틀:* "
                                        + title
                                        + "\n*내용:* "
                                        + content)
                        .build();
        ImageElement imageElement =
                ImageElement.builder().imageUrl(imageUrl).altText(imageUrl).build();
        layoutBlocks.add(
                section(section -> section.fields(List.of(reason)).accessory(imageElement)));

        ChatPostMessageRequest chatPostMessageRequest =
                ChatPostMessageRequest.builder()
                        .channel(SERVICE_CHANNEL)
                        .text("")
                        .blocks(layoutBlocks)
                        .build();

        try {
            methodsClient.chatPostMessage(chatPostMessageRequest);
        } catch (SlackApiException | IOException slackApiException) {
            log.error(slackApiException.toString());
        }
    }
}
