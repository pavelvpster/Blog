package org.interactiverobotics.blog.model;

import lombok.*;

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
}
