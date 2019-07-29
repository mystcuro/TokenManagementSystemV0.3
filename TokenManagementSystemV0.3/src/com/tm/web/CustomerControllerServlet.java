package com.tm.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.*;


/**
 * Servlet implementation class CustomerControllerServlet
 */
@WebServlet("/CustomerControllerServlet")
public class CustomerControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomersDataBase customersDataBase; //was default made pri
	
	

	@Override
	public void init() throws ServletException {
		super.init();
		customersDataBase = new CustomersDataBase();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// listing the customers in MVC style
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			// if command null set it to LIST
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			switch (theCommand) {
			
			case "LIST":
				listCustomers(request, response);
				break;
				
			case "ADD":
				addCustomer(request, response);
				break;
			case "DELETE":
				deleteCustomer(request, response);
				break;
				
			default:
				listCustomers(request, response);				
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	
		
	}


	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//perform the delete query
		customersDataBase.deleteCustomer();
		//send back view
		listCustomersAdmin(request, response);
		
	}


	private void addCustomer(HttpServletRequest request, HttpServletResponse response) {
		// read customer info from form data form
		int id = Integer.parseInt(request.getParameter("token"));
		String Name = request.getParameter("name");
		
		// create a new student object
		Customer theCustomer = new Customer(id,Name);
		
		// add the student to the database
		try {
			customersDataBase.addCustomer(theCustomer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//send back to view page
		try {
			listCustomers(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}


	private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// get customers
		List<Customer> customers = customersDataBase.getCustomers();
		
		//set attribute
		request.setAttribute("CUSTOMERS_LIST", customers);
		//send views
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-customers.jsp");
		dispatcher.forward(request, response);		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchAlgorithmException {

		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		
		//1. commit_id : i_sha-256_f_p_p
		String hashPassword = getHashPassword("SHA-256",pass)
		
		if("admin".equals(user)&&"2aaf9646b8633a52625a019c9d07c9e601913613d9c9069124f601cd6e89ea".equals(hashPassword)) {
			try {
				listCustomersAdmin(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			response.sendRedirect("index.jsp");
		}
	}


	private void listCustomersAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get customers
		List<Customer> customers = customersDataBase.getCustomers();
		
		//set attribute
		request.setAttribute("CUSTOMERS_LIST", customers);
		//send views
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
		dispatcher.forward(request, response);		
		
	}
	
	//2. add method to generate hash commit_id : i_sha-256_f_p_p
	private static String getHashPassword(String algorithmName, String password) throws NoSuchAlgorithmException {
		//get instance
		MessageDigest md = MessageDigest.getInstance(algorithmName);
		
		//update the password for hash creation
		md.update(password.getBytes());
		
		//get the hash
		byte[] hashByte = md.digest();
		
		//convert it to string
		StringBuffer sb = new StringBuffer();
		for(byte b : hashByte) {
			sb.append(Integer.toHexString(b & 0xff).toString());
		}
		
		//return it as string
		return sb.toString();
	}
	
	
}
