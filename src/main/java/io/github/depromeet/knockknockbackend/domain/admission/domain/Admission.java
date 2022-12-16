package io.github.depromeet.knockknockbackend.domain.admission.domain;


import io.github.depromeet.knockknockbackend.domain.admission.event.NewAdmissionEvent;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import io.github.depromeet.knockknockbackend.global.event.Events;
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
import javax.persistence.PostPersist;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_group_admission")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admission extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(value = EnumType.STRING)
    private AdmissionState admissionState;


    @Builder
    public Admission(User requester, Group group,
        AdmissionState admissionState) {
        this.requester = requester;
        this.group = group;
        this.admissionState = admissionState;
    }

    public static Admission createAdmission(User requester, Group group){
        return Admission.builder()
            .admissionState(AdmissionState.PENDING)
            .requester(requester)
            .group(group)
            .build();
    }

    @PostPersist
    private void requestAdmission(){
        Group group = getGroup();

        NewAdmissionEvent newAdmissionEvent = NewAdmissionEvent.builder()
            .requesterId(requester.getId())
            .groupId(group.getId())
            .admissionId(getId())
            .hostId(group.getHost().getId())
            .build();

        Events.raise(newAdmissionEvent);
    }

    public void refuseAdmission(){
        admissionState = AdmissionState.REFUSE;
    }
    public void acceptAdmission(){
        admissionState = AdmissionState.ACCEPT;
    }
}
