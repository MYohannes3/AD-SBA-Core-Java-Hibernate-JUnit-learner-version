package sba.sms.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {
	
	@Id
	@Column(name = "email", unique = true, nullable = false, length = 50)
	String email;
	
	@Column(name = "name", nullable = false, length = 50)
	String name;
	
	@Column(name = "password", nullable = false, length = 50)
	String Password;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email", referencedColumnName = "email"),
            inverseJoinColumns = @JoinColumn(name = "courses_id"))
    private List<Course> courses;
}
