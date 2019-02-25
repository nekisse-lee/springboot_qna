package com.nekisse.com.qna.domain;

import javax.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = )
    private User writer;

    private String title;

    private String contents;


    public Question() {
    }

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }
}
