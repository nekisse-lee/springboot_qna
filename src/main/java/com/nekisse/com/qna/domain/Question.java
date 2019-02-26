package com.nekisse.com.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;


    @Lob
    private String contents;

    private LocalDateTime createDate;


    @OneToMany(mappedBy = "question")
    @OrderBy("id asc")
    private List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public void update(String newTitle, String newContents) {
        this.title = newTitle;
        this.contents = newContents;

    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

}
