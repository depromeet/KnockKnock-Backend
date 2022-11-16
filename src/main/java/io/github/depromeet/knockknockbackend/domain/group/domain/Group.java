package io.github.depromeet.knockknockbackend.domain.group.domain;


import io.github.depromeet.knockknockbackend.domain.group.domain.vo.GroupBaseInfoVo;
import java.util.ArrayList;
import java.util.List;
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

@Getter
@Table(name = "tbl_group")
@Entity
@NoArgsConstructor
public class Group {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String thumbnailPath;

    private String backgroundImagePath;

    private Boolean publicAccess;

    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @OneToMany(mappedBy = "group")
    private List<Member> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


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

    public void setMembers(List<Member> memberList){
        members = memberList;
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
            .groupType(groupType).build();
    }
}
