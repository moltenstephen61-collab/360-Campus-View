package com.mycompany.campustour360.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Standalone servlet for the Campus Map 360 viewer feature.
 * @author Antigravity
 */
@WebServlet(name = "MapTourServlet", urlPatterns = {"/map-tour"})
public class MapTourServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        int locationId = (idParam != null) ? Integer.parseInt(idParam) : 1;

        String locationName = "Unknown Location";
        String imagePath = "";

        String dbUrl = "jdbc:derby://localhost:1527/ClassList";
        String dbUser = "app";
        String dbPass = "123";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            // Fixed the syntax error by properly closing the try-with-resources parentheses
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM APP.Location WHERE id = ?")) {

                stmt.setInt(1, locationId);
                
                // Nesting the ResultSet in a try block ensures it closes automatically
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        locationName = rs.getString("name");
                        imagePath = rs.getString("image_path");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("DATABASE ERROR in MapTourServlet: " + e.getMessage());
            e.printStackTrace(); // Helpful for debugging connection issues in the NetBeans output console
        }

        // Pass data to the JSP
        request.setAttribute("locationId", locationId);
        request.setAttribute("locationName", locationName);
        request.setAttribute("imagePath", imagePath);

        request.getRequestDispatcher("map-viewer.jsp").forward(request, response);
    }
}