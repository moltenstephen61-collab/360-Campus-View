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
 * @author Yahm3
 */
@WebServlet(name = "TourServlet", urlPatterns = {"/tour"})
public class TourServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String idParam = request.getParameter("id");
    int locationId = (idParam != null) ? Integer.parseInt(idParam) : 1;

    String locationName = "Unknown Location";
    String imagePath = "";

    // CHANGED: Aligned to your correct active database (ClassList)
    String dbUrl = "jdbc:derby://localhost:1527/ClassList";
    String dbUser = "app";
    String dbPass = "123";

    try {
      Class.forName("org.apache.derby.jdbc.ClientDriver");
      
      // CHANGED: Added APP. schema prefix
      try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass); 
           PreparedStatement stmt = conn.prepareStatement("SELECT * FROM APP.Location WHERE id = ?")) {

        stmt.setInt(1, locationId);
        
        // CHANGED: Managed ResultSet safely inside try block
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            locationName = rs.getString("name");
            imagePath = rs.getString("image_path");
          }
        }
      }
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("DATABASE ERROR in TourServlet (Single Selection): " + e.getMessage());
      e.printStackTrace();
    }

    int nextId = (locationId >= 13) ? 1 : locationId + 1;

    String acceptHeader = request.getHeader("Accept");
    if (acceptHeader != null && acceptHeader.contains("application/json")) {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      StringBuilder locationsJson = new StringBuilder("[");
      try {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        
        // CHANGED: Added APP. schema prefix
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             PreparedStatement allStmt = conn.prepareStatement("SELECT * FROM APP.Location ORDER BY id")) {
          
          // CHANGED: Managed ResultSet safely inside try block
          try (ResultSet allRs = allStmt.executeQuery()) {
            boolean first = true;
            while (allRs.next()) {
              if (!first) locationsJson.append(",");
              locationsJson.append("{")
                .append("\"id\":").append(allRs.getInt("id")).append(",")
                .append("\"name\":\"").append(allRs.getString("name")).append("\",")
                .append("\"imagePath\":\"").append(allRs.getString("image_path")).append("\"")
                .append("}");
              first = false;
            }
          }
        }
      } catch (ClassNotFoundException | SQLException e) {
        System.err.println("DATABASE ERROR in TourServlet (JSON List Fetch): " + e.getMessage());
        e.printStackTrace();
      }
      locationsJson.append("]");

      response.getWriter().write(
          "{\"locationName\":\"" + locationName + "\"," +
          "\"imagePath\":\"" + imagePath + "\"," +
          "\"nextId\":" + nextId + "," +
          "\"locations\":" + locationsJson + "}"
          );
      return;
    }

    request.setAttribute("locationName", locationName);
    request.setAttribute("imagePath", imagePath);
    request.setAttribute("nextId", nextId);
    request.getRequestDispatcher("viewer.jsp").forward(request, response);
  }
}