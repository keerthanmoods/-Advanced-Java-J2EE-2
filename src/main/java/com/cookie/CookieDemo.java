package com.cookie;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CookieDemo")
public class CookieDemo extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");

        Cookie[] cookies = request.getCookies();

        String name = null;
        int visitCount = 0;

        // Read existing cookies
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user")) {
                    name = c.getValue();
                }
                if (c.getName().equals("count")) {
                    visitCount = Integer.parseInt(c.getValue());
                }
            }
        }

        // If new user
        if (username != null && !username.isEmpty()) {
            name = username;
            visitCount = 1;

            Cookie userCookie = new Cookie("user", name);
            userCookie.setMaxAge(30); // expires in 30 seconds

            Cookie countCookie = new Cookie("count", "1");
            countCookie.setMaxAge(30);

            response.addCookie(userCookie);
            response.addCookie(countCookie);
        }
        // Existing user → increment count
        else if (name != null) {
            visitCount++;

            Cookie countCookie = new Cookie("count", String.valueOf(visitCount));
            countCookie.setMaxAge(30);

            response.addCookie(countCookie);
        }

        // HTML Output
        out.println("<html><body>");

        if (name != null) {
            out.println("<h2 style='color:blue;'>Welcome back " + name + "!</h2>");
            out.println("<h3 style='color:magenta;'>You have visited this page "
                    + visitCount + " times</h3>");
        } else {
            out.println("<h3 style='color:red;'>No user found. Please enter your name.</h3>");
        }

        // Display all cookies
        out.println("<h3>Cookies List:</h3>");
        if (cookies != null) {
            for (Cookie c : cookies) {
                out.println("Name: " + c.getName() + " | Value: " + c.getValue() + "<br>");
            }
        }

        out.println("<br><a href='index.html'>Go Back</a>");
        out.println("</body></html>");
    }
}