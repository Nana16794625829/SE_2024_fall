package com.isslab.se_form_backend.entity.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormSubmissionEntityId {
    private String submitterId;
    private String week;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormSubmissionEntityId that)) return false;
        return Objects.equals(submitterId, that.submitterId) &&
                Objects.equals(week, that.week);
    }

    @Override
    public int hashCode() {
        return Objects.hash(submitterId, week);
    }
}
