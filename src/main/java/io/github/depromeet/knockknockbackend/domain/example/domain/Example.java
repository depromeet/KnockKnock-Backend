package io.github.depromeet.knockknockbackend.domain.example.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "tbl_example")
@Entity
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exampleValue;

    @Builder
    public Example(String exampleValue) {
        this.exampleValue = exampleValue;
    }
}
