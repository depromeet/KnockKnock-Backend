package io.github.depromeet.knockknockbackend.domain.group.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Table(name = "tbl_group_category")
@Entity
@NoArgsConstructor(access=AccessLevel.PROTECTED )
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emoji;

    private String content;

    private Long listOrder;

    @OneToMany(mappedBy = "category")
    private List<Group> groups = new ArrayList<>();

    public static final Long defaultEmptyCategoryId = 1L;


    @Builder
    public Category(Long id, String emoji, String content) {
        this.id = id;
        this.emoji = emoji;
        this.content = content;
    }
}


