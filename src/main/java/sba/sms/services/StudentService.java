package sba.sms.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.dao.StudentI;
import sba.sms.utils.HibernateUtil;

public class StudentService  implements StudentI{
	
	@Override
	public void createStudent(Student student) {
	SessionFactory factory = HibernateUtil.getSessionFactory();
	Transaction tx = null;
	try (Session session = factory.openSession()){
		tx = session.beginTransaction();
		session.persist(student);
		tx.commit();
	} catch (Exception e) {
		if (tx != null) {
		tx.rollback();
	}

	}
	}
	
	@Override
	 public List<Student> getAllStudents() {
		  List<Student> studentList = new ArrayList<>();
	        try (Session session = HibernateUtil.getSessionFactory().openSession();){
	            Query<Student> query = session.createQuery("from Student", Student.class);
	            studentList = query.getResultList();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return studentList;
	    }
	
	@Override
	 public Student getStudentByEmail(String email) {
	 Student student = null;

	 try (Session session = HibernateUtil.getSessionFactory().openSession();){
	     Query<Student> query = session.createQuery("FROM Student s WHERE s.email = :email", Student.class);
	     query.setParameter("email", email);
	     student = query.uniqueResult();

	 } catch (Exception  e) {
	     e.printStackTrace();
	 }
	 return student;
	}
	
	
	@Override
	 public boolean validateStudent(String email, String password) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		boolean isValid = false;
		try (Session session = factory.openSession()) {

				 isValid =  getStudentByEmail(email) != null &&
		                getStudentByEmail(email).getPassword().equals(password);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
			 return isValid;
		    }
	    
	
	private static final CourseService courseService = new CourseService();
	 
	@Override
	  public void registerStudentToCourse(String email, int courseId) {
	     Transaction tx = null;   
		 SessionFactory factory = HibernateUtil.getSessionFactory();
	        try (Session ses = factory.openSession()) {
	            tx = ses.beginTransaction();
	            Student student = getStudentByEmail(email);
	            student.addCourse(courseService.getCourseById(courseId));
	            ses.merge(student);
	            tx.commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 @Override
	 public List<Course> getStudentCourses(String email) {
	        SessionFactory factory = HibernateUtil.getSessionFactory();
	        try (Session session = factory.openSession()) {
	            Transaction tx = session.beginTransaction();
	       
	            List<Course> courses = session.createNativeQuery
	            		("SELECT * FROM course c JOIN student_courses sc ON c.id = sc.courses_id WHERE sc.student_email = :email", Course.class)
	                                         .setParameter("email", email)
	                                         .getResultList();
	            tx.commit();
	            return courses;
	        } catch (Exception e) {
	           
	            throw e;
	            
	        }
	        
	    }
	 

	 
	 
}
