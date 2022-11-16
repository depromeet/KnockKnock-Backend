package io.github.depromeet.knockknockbackend.domain.group.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;


@Getter
@Table(name = "tbl_group_background_image")
@Entity
public class BackgroundImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String backgroundImageUrl;
}
