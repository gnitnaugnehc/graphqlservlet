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
import service.LogInService;

@WebServlet("/signin")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final LogInService logInService;

	public LogInServlet(LogInService logInService) {
		this.logInService = logInService;
	}

	public LogInServlet() throws SQLException {
		this(new LogInService());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String usernameOrEmail = request.getParameter("usernameOrEmail");
		String password = request.getParameter("password");

		try {
			User user = logInService.authenticate(usernameOrEmail, password);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/home");
		} catch (IllegalArgumentException e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("WEB-INF/view/signin.jsp").forward(request, response);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
			request.getRequestDispatcher("WEB-INF/view/signin.jsp").forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/signin.jsp").forward(request, response);
	}
}
