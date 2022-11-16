package io.github.depromeet.knockknockbackend.domain.group.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;


@Getter
@Table(name = "tbl_group_category")
@Entity
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emoji;

    private String content;

    @OneToMany(mappedBy = "category")
    private List<Group> groups = new ArrayList<>();

}


