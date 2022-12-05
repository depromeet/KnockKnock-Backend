package io.github.depromeet.knockknockbackend.domain.group.domain;


import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_group_admission")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invite extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(value = EnumType.STRING)
    private InviteState inviteState;


    @Builder
    public Invite(User receiver, Group group,
        InviteState inviteState) {
        this.receiver = receiver;
        this.group = group;
        this.inviteState = inviteState;
    }

    public static Invite createInvite(User receiver, Group group){
        return Invite.builder()
            .inviteState(InviteState.PENDING)
            .receiver(receiver)
            .group(group)
            .build();
    }

    public void refuseInvite(){
        inviteState = InviteState.REFUSE;
    }
    public void acceptInvite(){
        inviteState = InviteState.ACCEPT;
    }
}
