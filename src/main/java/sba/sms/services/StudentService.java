package sba.sms.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sba.sms.models.Student;
import sba.sms.dao;
import sba.sms.dao.StudentI;
import sba.sms.utils.HibernateUtil;

public class StudentService  {
	
	
	public void createStudent(Student student) {
	Transaction tx = null;
	Session session = HibernateUtil.getSessionFactory().openSession();

	try {

		tx = session.beginTransaction();
		session.persist(student);
		tx.commit();
	} catch (Exception e) {
		tx.rollback();
	}
}
	
	public List<Student> getAllStudents() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		
		try (Session ses = factory.openSession()){
			
			//Select * from Student
			List<Student> list = ses.createQuery("from Employee",Student.class).getResultList();
			return list;
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			factory.close();
		}
	}
	
	
	 public Student getStudentByEmail(String email) {
		 
		 SessionFactory factory = HibernateUtil.getSessionFactory();
		  try (Session session = factory.openSession()) {
			 
			  return session.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class)
	                    .setParameter("email", email)
	                    .uniqueResult();
	        } catch (Exception e) {
	            
	            throw e;
	        }
	    }
	 
	 public boolean validateStudent(String email, String password) {
		 SessionFactory factory = HibernateUtil.getSessionFactory();
		 try (Session session = factory.openSession()) {
			 Transaction tx = session.beginTransaction();
			 boolean result = StudentI.validateStudent(email, password);
			 tx.commit();
			 return result;
		 }catch (Exception e) {
			 throw e;
		 }
	 }
	 
}
