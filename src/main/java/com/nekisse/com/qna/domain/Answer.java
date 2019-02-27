package com.nekisse.com.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Answer extends AbstractEntity {


    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;


    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String contents;


    public User getWriter() {
        return writer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }


    public Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }


    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }


    @Override
    public String toString() {
        return "Answer{" +
            super.toString() +
             "writer=" + writer +
            ", question=" + question +
            ", contents='" + contents + '\'' +
            '}';
    }
}
