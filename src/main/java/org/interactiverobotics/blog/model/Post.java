package org.interactiverobotics.blog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Post {
    private long id;
    private User user;
    private String text;

    public static Post copy(Post source) {
        return new Post(source.getId(), User.copy(source.getUser()), source.getText());
    }
}
