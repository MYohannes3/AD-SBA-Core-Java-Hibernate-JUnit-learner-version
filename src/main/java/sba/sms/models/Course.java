package sba.sms.models;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    int id;
    @NonNull
    @Column(name = "name", nullable = false,length = 50)
    String name;
    @NonNull
    @Column(name = "instructor", nullable = false, length = 50)
    String instructor;

    
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}, 
    		mappedBy = "courses")
    Set<Student> students = new HashSet<>();
   
    

    @Override
	public int hashCode() {
		return Objects.hash(id, instructor, name);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return id == other.id && Objects.equals(instructor, other.instructor) && Objects.equals(name, other.name);
	}

}