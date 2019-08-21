package net.lelyak.edu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Nazar Lelyak.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class BlogUser {

    @Id
    @NotEmpty(message = "*Please provide a user name")
    private String userName;

    @Length(min = 4, message = "*Your password must have at least 4 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled;

}
