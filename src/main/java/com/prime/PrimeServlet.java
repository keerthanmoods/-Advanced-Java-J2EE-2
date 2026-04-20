package com.prime;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/prime")
public class PrimeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            int num = Integer.parseInt(request.getParameter("number"));

            if (num < 0) {
                throw new IllegalArgumentException("Number must be positive");
            }

            boolean isPrime = true;

            if (num == 0 || num == 1) {
                isPrime = false;
            } else {
                for (int i = 2; i <= Math.sqrt(num); i++) {
                    if (num % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
            }

            out.println("<html><body>");
            out.println("<h2>Prime Number Result</h2>");

            if (isPrime) {
                out.println("<p>" + num + " is a Prime Number</p>");
            } else {
                out.println("<p>" + num + " is NOT a Prime Number</p>");
            }

            out.println("<a href='index.html'>Check Another</a>");
            out.println("</body></html>");

        } catch (NumberFormatException e) {
            displayError(out, "Invalid input! Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            displayError(out, e.getMessage());
        }
    }

    private void displayError(PrintWriter out, String message) {
        out.println("<html><body>");
        out.println("<h2 style='color:red;'>Error</h2>");
        out.println("<p>" + message + "</p>");
        out.println("<a href='index.html'>Try Again</a>");
        out.println("</body></html>");
    }
}