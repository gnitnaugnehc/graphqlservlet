package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import service.RegisterService;

@WebServlet("/signup")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final RegisterService registerService;

	public RegisterServlet(RegisterService registerService) {
		this.registerService = registerService;
	}

	public RegisterServlet() throws SQLException {
		this(new RegisterService());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");

		try {
			User user = registerService.registerUser(username, email, password, confirmPassword);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/home");
		} catch (IllegalArgumentException e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("WEB-INF/view/signup.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/signup.jsp").forward(request, response);
	}
}
