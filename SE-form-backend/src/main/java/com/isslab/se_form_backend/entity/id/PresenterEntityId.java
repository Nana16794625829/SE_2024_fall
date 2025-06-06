package com.isslab.se_form_backend.entity.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/*
* 用於 PresenterEntity 的複合主鍵
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresenterEntityId {
    private String presenterId;
    private String week;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PresenterEntityId that)) return false;
        return Objects.equals(presenterId, that.presenterId) &&
                Objects.equals(week, that.week);
    }

    @Override
    public int hashCode() {
        return Objects.hash(presenterId, week);
    }
}
