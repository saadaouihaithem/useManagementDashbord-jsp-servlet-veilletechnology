package com.xadmin.usermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xadmin.usermanagement.DAO.UserDao;
import com.xadmin.usermanagement.bean.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public  UserDao userDao;
  
	
    
	public void init() throws ServletException {
		userDao=new UserDao();
	}


	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getServletPath();
		
		switch (action) {
		
		case "/new":
			showNewForm(request, response);
			break;
			
			
		
		case "/insert": 
			insertUser(request,response);
			break;
			
			
		
		case "/delete": 
			deleteUser(request, response);
			break;
			
			
		
		case "/edit": 
			showEditForm(request,response);
			break;
			
			
		
		case "/update": 
			UpdateUser(request, response);
			break;
			
			
		
		default:
			listUser(request,response);
			break;
		}
		}
		private void showNewForm (HttpServletRequest request ,HttpServletResponse response)throws ServletException, IOException{
			 RequestDispatcher dispatcher=request.getRequestDispatcher("user-form.jsp");
			 dispatcher.forward(request , response);


		}
		// insert user 
		private void insertUser(HttpServletRequest request ,HttpServletResponse response)throws ServletException, IOException{
			
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			String country=request.getParameter("country");
			User newUser=new User(name,email,country);
		     try {
				userDao.insertUser(newUser);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("list");
		}
		// delete method
		private void deleteUser(HttpServletRequest request ,HttpServletResponse response)throws ServletException, IOException{
			int id=Integer.parseInt(request.getParameter("id"));
			try {
				userDao.deleteUser(id);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect("list");			
			
		}
		private void showEditForm(HttpServletRequest request ,HttpServletResponse response)throws ServletException, IOException{
			int id=Integer.parseInt(request.getParameter("id"));
			User  existingUser;
			try {
				existingUser=userDao.selectUser(id);
			RequestDispatcher dispatcher =request.getRequestDispatcher("user-form.jsp");
		    request.setAttribute("user", existingUser);
		    dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		// update method to handel the update action 
		private void UpdateUser (HttpServletRequest request ,HttpServletResponse response)throws ServletException, IOException{
			int id=Integer.parseInt(request.getParameter("id"));
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			String country=request.getParameter("country");
			User user=new User(id,name,email,country);
			try {
				userDao.updateUser(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("list");
			}	
		
		private void listUser (HttpServletRequest request ,HttpServletResponse response)throws ServletException, IOException{
			
			
       try {
    	   List<User>listUser=userDao.selectAllUser();
    	   request.setAttribute("listUser", listUser);
    	   RequestDispatcher dispatcher=request.getRequestDispatcher("user-list.jsp");
    	   dispatcher.forward(request, response);
		
	} catch (Exception e) {
		e.printStackTrace();
	}		
		}

}