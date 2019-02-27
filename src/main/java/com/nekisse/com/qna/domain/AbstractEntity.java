package com.nekisse.com.qna.domain;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

public class AbstractEntity {
    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyedDate;


    public Long getId() {
        return id;
    }





    public String getFormattedCreateDate() {
        return getFormattedDate(createDate, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public String getFormattedModifyedDate() {
        return getFormattedDate(modifyedDate, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    private String getFormattedDate(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(formatter);
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "AbstractEntity{" +
            "id=" + id +
            ", createDate=" + createDate +
            ", modifyedDate=" + modifyedDate +
            '}';
    }
}
