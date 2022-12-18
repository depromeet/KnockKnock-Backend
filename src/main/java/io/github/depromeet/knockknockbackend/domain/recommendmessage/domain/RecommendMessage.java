package io.github.depromeet.knockknockbackend.domain.recommendmessage.domain;


import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_recommend_message")
@Entity
public class RecommendMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean enable;

    @Builder.Default
    @OneToMany(mappedBy = "recommendMessage")
    private List<RecommendMessageContent> recommendMessageContents = new ArrayList<>();
}
