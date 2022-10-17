package org.interactiverobotics.blog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class User {
    private long id;
    private String name;
    private List<Post> posts;

    public static User copy(User source) {
        return new User(source.getId(), source.getName(), null);
    }
}
