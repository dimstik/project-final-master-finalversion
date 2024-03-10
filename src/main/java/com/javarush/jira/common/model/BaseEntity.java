package com.javarush.jira.common.model;

import com.javarush.jira.common.HasId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.util.Assert;

@MappedSuperclass //  Эта аннотация указывает, что сущность не является сущностью на уровне базы данных, но ее поля должны быть отображены в сущности, наследующей от этого класса.
//  https://stackoverflow.com/a/6084701/548473
@Access(AccessType.FIELD) //  указывает JPA использовать доступ к полям напрямую, обращаясь к ним без использования геттеров и сеттеров.
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity implements Persistable<Long>, HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true) // этот элемент будет скрыт в документации и не будет виден пользователям или разработчикам, которые используют ваше API.
    protected Long id;

    // doesn't work for hibernate lazy proxy
    public long id() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    //    https://stackoverflow.com/questions/1638723
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}