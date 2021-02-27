package com.demo.HibernateCriteriaQueries;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("deprecation")
public class App{
	private static SessionFactory factory;
	
	public static void main(String[] args)
	{
		try
		{
			factory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		}
		catch(Throwable ex){
			System.err.println("Failed to create sessionFactory object:."+ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		
		App app=new App();
		
		
		/* To add employees */
		Integer empID1=app.addEmployee(1,"zara","Ali",18,"PG",20000);
		System.out.println("Insert "+empID1+" record successfully.");
		
		Integer empID2=app.addEmployee(2,"Sanam","Puri",33,"UG",19000);
		System.out.println("Insert "+empID2+" record successfully.");
		
		Integer empID3=app.addEmployee(3,"Sonal","Patil",25,"PG",30000);
		System.out.println("Insert "+empID3+" record successfully.");
		
		Integer empID4=app.addEmployee(4,"Kapil","Sharma",20,"UG",25000);
		System.out.println("Insert"+empID4+" record successfully.");
		
		 /* Method to  READ all the employees having salary more than 25000 */
		app.listEmployees();
		
		/*Count employees*/
		app.countEmployee();
		
		/* Method to print sum of salaries */
		app.totalSalary();
		
		 /* Method to  READ all the employees having salary less than 25000 */
		app.listEmployeesBySalaryLessThan();
		
		 /* Method to  READ all the employees by like operation*/
		app.listEmployeesByUsingLikeOperation();
		
		/* Method to  READ all the employees by pagination operation*/
		app.listEmployeesByUsingPaginationOperation();
		
		/* Method to  READ all the employees by multiple restrictions*/
		app.listEmployeesByUsingMultipleRestrictions();
		
	}
	
	public Integer addEmployee(int id,String firstName, String lastName,int age,String education, int salary)
	{
		Session session = factory.openSession();
	    Transaction tx = null;
	      Integer employeeID = null;
	      
	      try 
	      {
	         tx = session.beginTransaction();
	         Employee employee = new Employee(id,firstName,lastName,age,education,salary);
	         employeeID = (Integer) session.save(employee); 
	         tx.commit();
	      } 
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } 
	      finally 
	      {
	         session.close(); 
	      }
	      return employeeID;
	   }

	  
	   public void listEmployees( ) 
	   {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try 
	      {
	         tx = session.beginTransaction();
	        
			Criteria cr = session.createCriteria(Employee.class);
	         cr.add(Restrictions.gt("salary", 25000));
	         List<Employee> employees = cr.list();
	         System.out.println("All the employees whose salary is greater than 25000");
	         for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();)
	         {
	            Employee employee = iterator.next(); 
	            System.out.print(" First Name:" + employee.getFirstName()); 
	            System.out.print(" Last Name:" + employee.getLastName()); 
	            System.out.print(" Age:" + employee.getAge());
	            System.out.print(" Education:" + employee.getEducation());
	            System.out.println(" Salary:" + employee.getSalary()); 
	         }
	         tx.commit();
	      } 
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } 
	      finally 
	      {
	         session.close(); 
	      }
	   }
	   
	   /* Method to  READ all the employees having salary less than 25000 */
	   public void listEmployeesBySalaryLessThan( ) 
	   {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try 
	      {
	         tx = session.beginTransaction();
	        
			Criteria cr = session.createCriteria(Employee.class);
	         cr.add(Restrictions.lt("salary", 25000));
	         List<Employee> employees = cr.list();
	         System.out.println("All the employees whose salary is less than 25000");
	         for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();)
	         {
	            Employee employee = iterator.next(); 
	            System.out.print(" First Name:" + employee.getFirstName()); 
	            System.out.print(" Last Name:" + employee.getLastName()); 
	            System.out.print(" Age:" + employee.getAge());
	            System.out.print(" Education:" + employee.getEducation());
	            System.out.println(" Salary:" + employee.getSalary()); 
	         }
	         tx.commit();
	      } 
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } 
	      finally 
	      {
	         session.close(); 
	      }
	   }
	   
	   /* Method to print total number of records */
	   public void countEmployee(){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try 
	      {
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(Employee.class);
	         cr.setProjection(Projections.rowCount());
	         List<Employee> rowCount = cr.list();
	         System.out.println("Total number of employees.");
	         System.out.println("Total Count: " + rowCount.get(0) );
	         tx.commit();
	      }
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } 
	      finally 
	      {
	         session.close(); 
	      }
	   }
	  
	   
	   public void totalSalary()
	   {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try 
	      {
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(Employee.class);
	         cr.setProjection(Projections.sum("salary"));
	         List<Employee> totalSalary = cr.list();
	         System.out.println("Total salary of all employees:");
	         System.out.println("Total Salary: " + totalSalary.get(0) );
	         tx.commit();
	      } 
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally 
	      {
	         session.close(); 
	      }
	   }
	   
	   public void listEmployeesByUsingLikeOperation() 
	   {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try 
	      {
	         tx = session.beginTransaction();
	        
			Criteria cr = session.createCriteria(Employee.class);
	         cr.add(Restrictions.like("firstName","%S%"));
	         List<Employee> employees = cr.list();
	         System.out.println("List employees based on like operator:");
	         for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();)
	         {
	            Employee employee = iterator.next(); 
	            System.out.print(" First Name:" + employee.getFirstName()); 
	            System.out.print(" Last Name:" + employee.getLastName()); 
	            System.out.print(" Age:" + employee.getAge());
	            System.out.print(" Education:" + employee.getEducation());
	            System.out.println(" Salary:" + employee.getSalary()); 
	         }
	         tx.commit();
	      } 
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } 
	      finally 
	      {
	         session.close(); 
	      }
	     
	   }
	   
	   public void listEmployeesByUsingPaginationOperation() 
	   {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try 
	      {
	         tx = session.beginTransaction();
	        
			Criteria cr = session.createCriteria(Employee.class);
	         cr.addOrder(Order.desc("salary")).setFirstResult(0).setMaxResults(2).list();
	         List<Employee> employees = cr.list();
	         System.out.println("List employees based on pagination operation:");
	         for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();)
	         {
	            Employee employee = iterator.next(); 
	            System.out.print(" First Name:" + employee.getFirstName()); 
	            System.out.print(" Last Name:" + employee.getLastName()); 
	            System.out.print(" Age:" + employee.getAge());
	            System.out.print(" Education:" + employee.getEducation());
	            System.out.println(" Salary:" + employee.getSalary()); 
	         }
	         tx.commit();
	      } 
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } 
	      finally 
	      {
	         session.close(); 
	      }
	   }
	   
	   public void listEmployeesByUsingMultipleRestrictions() 
	   {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try 
	      {
	         tx = session.beginTransaction();
	        
			Criteria cr = session.createCriteria(Employee.class);
			Criterion salaryGreaterThan = Restrictions.gt("salary", 25000);
		    Criterion ageGreaterThanOrEqual = Restrictions.ge("age", 28);
		    Criterion education = Restrictions.in("education", "PG", "Phd");
		    LogicalExpression orExp = Restrictions.or(salaryGreaterThan, ageGreaterThanOrEqual);
		       cr.add(orExp);
		       cr.add(education);
		       System.out.println("All the empoyees whose salary is greater than 25000 or whose age is greater than 28 and educaton is either PG or Phd");
		       List<Employee> employees = cr.list();
		 
		         for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();)
		         {
		            Employee employee = iterator.next(); 
		            System.out.print(" First Name:" + employee.getFirstName()); 
		            System.out.print(" Last Name:" + employee.getLastName()); 
		            System.out.print(" Age:" + employee.getAge());
		            System.out.print(" Education:" + employee.getEducation());
		            System.out.println(" Salary:" + employee.getSalary()); 
		         }
		         tx.commit();
	         }
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } 
	      finally 
	      {
	         session.close(); 
	      }
	   }	  
}