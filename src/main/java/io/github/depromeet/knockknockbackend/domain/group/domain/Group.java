package io.github.depromeet.knockknockbackend.domain.group.domain;


import io.github.depromeet.knockknockbackend.domain.group.domain.vo.GroupBaseInfoVo;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.UpdateGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.service.dto.UpdateGroupDto;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Table(name = "tbl_group")
@Entity
@NoArgsConstructor
public class Group extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String thumbnailPath;

    private String backgroundImagePath;

    private Boolean publicAccess;

    @Enumerated(EnumType.STRING)
    private GroupType groupType;


    @Embedded
    private GroupUsers groupUsers = new GroupUsers();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "group")
    private List<Notification> notifications = new ArrayList<>();
    @Builder
    public Group(String title, String description, String thumbnailPath, String backgroundImagePath,
        Boolean publicAccess , Category category ,GroupType groupType) {
        this.title = title;
        this.description = description;
        this.thumbnailPath = thumbnailPath;
        this.backgroundImagePath = backgroundImagePath;
        this.publicAccess = publicAccess;
        this.category = category;
        this.groupType = groupType;
    }

    public static String generateGroupTitle(){
        // TODO : 방이름 자동 설정 로직 정책에 따르기
        return "방이름";
    }

    public GroupBaseInfoVo getGroupBaseInfoVo(){
        return GroupBaseInfoVo.builder()
            .title(title)
            .description(description)
            .thumbnailPath(thumbnailPath)
            .backgroundImagePath(backgroundImagePath)
            .publicAccess(publicAccess)
            .category(category)
            .groupType(groupType)
            .groupId(id).build();
    }

    public void updateGroup(UpdateGroupDto updateGroupDto,Category category) {
        this.title = updateGroupDto.getTitle();
        this.description = updateGroupDto.getDescription();
        this.thumbnailPath = updateGroupDto.getThumbnailPath();
        this.backgroundImagePath = updateGroupDto.getBackgroundImagePath();
        this.publicAccess = updateGroupDto.getPublicAccess();
        this.category = category;
    }

    public int getMemberCount(){
        return this.groupUsers.getMemberCount();
    }
}
