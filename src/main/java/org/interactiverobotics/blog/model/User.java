package org.interactiverobotics.blog.model;

import lombok.*;

import java.util.List;

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
}
