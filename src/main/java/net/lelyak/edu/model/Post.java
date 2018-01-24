package net.lelyak.edu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Nazar Lelyak.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity @Table(name = "posts")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postText;

    @ManyToOne
    private BlogUser user;

    private LocalDateTime createdDate;
}
