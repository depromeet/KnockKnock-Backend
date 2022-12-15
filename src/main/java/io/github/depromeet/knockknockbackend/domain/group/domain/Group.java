package io.github.depromeet.knockknockbackend.domain.group.domain;


import io.github.depromeet.knockknockbackend.domain.group.domain.vo.GroupBaseInfoVo;
import io.github.depromeet.knockknockbackend.domain.group.exception.AlreadyGroupEnterException;
import io.github.depromeet.knockknockbackend.domain.group.exception.HostCanNotLeaveGroupException;
import io.github.depromeet.knockknockbackend.domain.group.exception.NotHostException;
import io.github.depromeet.knockknockbackend.domain.group.exception.NotMemberException;
import io.github.depromeet.knockknockbackend.domain.group.service.dto.UpdateGroupDto;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupUser> groupUsers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "group")
    private List<Notification> notifications = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private User host;
    @Builder
    public Group(Long id, String title, String description, String thumbnailPath, String backgroundImagePath,
        Boolean publicAccess , Category category ,GroupType groupType ,User host) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailPath = thumbnailPath;
        this.backgroundImagePath = backgroundImagePath;
        this.publicAccess = publicAccess;
        this.category = category;
        this.groupType = groupType;
        this.host = host;
        this.groupUsers.add(new GroupUser(this,host));
    }

    public static String generateGroupTitle(String reqUserName, Integer memberCount){
        if(memberCount <= 1){
            return reqUserName + "님의 방";
        }
        return reqUserName + "님 외 " + (memberCount - 1) + "명";
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
            .groupId(id)
            .hostInfoVO(host.getUserInfo())
            .build();
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
        return this.groupUsers.size();
    }

    public static Group of(Long id) {
        return Group.builder()
            .id(id)
            .build();
    }

    public void validUserIsHost(Long userId){
        if(!checkUserIsHost(userId)){
            throw NotHostException.EXCEPTION;
        }
    }
    public Boolean checkUserIsHost(Long userId){
        return host.getId().equals(userId);
    }

    public void validUserIsAlreadyEnterGroup(Long userId){
        if(checkUserIsMemberOfGroup(userId))
            throw AlreadyGroupEnterException.EXCEPTION;
    }

    public void validUserIsMemberOfGroup(Long userId){
        if(!checkUserIsMemberOfGroup(userId)){
            throw NotMemberException.EXCEPTION;
        }
    }

    public boolean checkUserIsMemberOfGroup(Long userId) {
        return groupUsers.stream()
            .anyMatch(groupUser ->
                groupUser.getUserId().equals(userId));
    }

    public List<UserInfoVO> getMemberInfoVOs(){
        return groupUsers.stream()
            .map(GroupUser::getMemberUserInfo)
            .collect(Collectors.toList());
    }

    public void memberInviteNewUsers(Long reqUserId, List<User> newMembers){
        newMembers.forEach(user -> memberInviteNewUser(reqUserId, user));
    }

    public void removeMemberByUserId(Long userId){
        if(checkUserIsHost(userId)){
            throw HostCanNotLeaveGroupException.EXCEPTION;
        }
        groupUsers.removeIf(groupUser -> groupUser.getUserId().equals(userId));
    }

    public void memberInviteNewUser(Long reqUserId, User newUser) {
        validUserIsMemberOfGroup(reqUserId);
        addMember(newUser);
    }

    private void addMember(User newUser) {
        validUserIsAlreadyEnterGroup(newUser.getId());
        GroupUser groupUser = new GroupUser(this, newUser);
        groupUsers.add(groupUser);
    }

    public void hostAcceptMember(Long reqUserId ,User newUser){
        validUserIsHost(reqUserId);
        addMember(newUser);
    }

    public void enterGroup(User newUser){
        addMember(newUser);
    }


}
