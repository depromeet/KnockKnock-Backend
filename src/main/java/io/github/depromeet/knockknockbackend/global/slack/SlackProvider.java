package io.github.depromeet.knockknockbackend.global.slack;

import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.TextObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackProvider {

    private final MethodsClient methodsClient;
    private final ObjectMapper objectMapper;

    private final String BACKEND_ERROR_CHANNEL = "C04G9V2QVRS";
    private final int MaxLength = 500;

    @Async
    public void sendError(String url, ContentCachingRequestWrapper cachingRequest, Exception e)
            throws IOException {
        String method = cachingRequest.getMethod();
        String body = objectMapper.readTree(cachingRequest.getContentAsByteArray()).toString();
        String exceptionAsString = Throwables.getStackTraceAsString(e);
        int cutLength = Math.min(exceptionAsString.length(), MaxLength);

        String errorMessage = e.getMessage();
        String errorStack = exceptionAsString.substring(0, cutLength);

        List<LayoutBlock> layoutBlocks = new ArrayList<>();
        layoutBlocks.add(
                Blocks.header(
                        headerBlockBuilder -> headerBlockBuilder.text(plainText("백엔드 에러 로그"))));

        layoutBlocks.add(divider());

        MarkdownTextObject methodMarkdown =
                MarkdownTextObject.builder().text("*요청주소:*\n" + method + " : " + url).build();
        MarkdownTextObject bodyMarkdown =
                MarkdownTextObject.builder().text("*요청바디:*\n" + body).build();
        List<TextObject> fields = List.of(methodMarkdown, bodyMarkdown);
        layoutBlocks.add(section(section -> section.fields(fields)));

        layoutBlocks.add(divider());

        MarkdownTextObject errorNameMarkdown =
                MarkdownTextObject.builder().text("*에러 메시지:*\n" + errorMessage).build();
        MarkdownTextObject errorStackMarkdown =
                MarkdownTextObject.builder().text("*에러 스택:*\n" + errorStack).build();
        layoutBlocks.add(
                section(section -> section.fields(List.of(errorNameMarkdown, errorStackMarkdown))));

        ChatPostMessageRequest chatPostMessageRequest =
                ChatPostMessageRequest.builder()
                        .channel(BACKEND_ERROR_CHANNEL)
                        .text("")
                        .blocks(layoutBlocks)
                        .build();
        try {
            methodsClient.chatPostMessage(chatPostMessageRequest);
        } catch (SlackApiException slackApiException) {
            log.error(slackApiException.toString());
        }
    }
}
