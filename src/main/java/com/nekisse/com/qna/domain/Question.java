package com.nekisse.com.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question  extends AbstractEntity{


    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @JsonProperty
    private String title;


    @Lob
    @JsonProperty
    private String contents;

    @JsonProperty
    private Integer countOfAnswer = 0;




    @OneToMany(mappedBy = "question")
    @OrderBy("id desc")
    @JsonIgnore
    private List<Answer> answers;

    public void addAnswer() {
        this.countOfAnswer+=1;
    }

    public void deleteAnswer() {
        this.countOfAnswer -= 1;
    }

    public List<Answer> getAnswers() {
        return answers;
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



    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }


    public void update(String newTitle, String newContents) {
        this.title = newTitle;
        this.contents = newContents;

    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

}
