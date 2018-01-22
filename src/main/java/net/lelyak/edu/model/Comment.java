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
@Entity @Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentText;

    @ManyToOne
    private BlogUser user;
    @ManyToOne
    private Post post;

    private LocalDateTime createdDate;
}
