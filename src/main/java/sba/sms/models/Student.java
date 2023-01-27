package sba.sms.models;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "student")
public class Student {
	
	
	@Id
	@NonNull
	@Column(name = "email", unique = true, length = 50)
	String email;
	@NonNull
	@Column(name = "name", nullable = false, length = 50)
	String name;
	@NonNull
	@Column(name = "password",  length = 50)
	String password;
	
	@ToString.Exclude
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email", referencedColumnName = "email"),
            inverseJoinColumns = @JoinColumn(name = "courses_id"))
	Set<Course> courses = new HashSet<>();
	
	 public void addCourse(Course course){
	        courses.add(course);
	        course.getStudents().add(this);
	    }

	    
	    @Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Student other = (Student) obj;
			return Objects.equals(email, other.email)
					&& Objects.equals(name, other.name) && Objects.equals(password, other.password);
		}
	    @Override
	    public int hashCode() {
	        return Objects.hash(email, name, password);
	    }
	}


