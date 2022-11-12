package io.github.depromeet.knockknockbackend.domain.option.domain;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Table(name = "tbl_option")
@Entity
public class Option {

    @Id
    private Long userId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean newOption;

    private boolean reactionOption;

    private boolean nightOption;

    public void setNewOption(boolean newOption) {
        this.newOption = newOption;
    }

    public void setReactionOption(boolean reactionOption) {
        this.reactionOption = reactionOption;
    }

    public void setNightOption(boolean nightOption) {
        this.nightOption = nightOption;
    }

}
