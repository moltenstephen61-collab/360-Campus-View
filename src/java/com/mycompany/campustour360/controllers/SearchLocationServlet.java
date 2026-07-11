package com.mycompany.campustour360.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yahm3
 */
// Added the missing WebServlet annotation route mapping
@WebServlet(name = "SearchLocationServlet", urlPatterns = {"/search"})
public class SearchLocationServlet extends HttpServlet {

  private static final String DB_URL = "jdbc:derby://localhost:1527/ClassList";
  private static final String DB_USER = "app";
  private static final String DB_PASS = "123";

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String search = request.getParameter("search");
    if (search == null) {
      search = "";
    }

    List<Map<String, Object>> locations = new ArrayList<>();
    int firstId = 1;
    String firstName = "";
    String firstImagePath = "";

    try {
      // Explicitly load the Derby driver class first
      Class.forName("org.apache.derby.jdbc.ClientDriver");
      
      // Added APP. schema prefix to the Location table name
      try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
          PreparedStatement stmt = conn.prepareStatement(
              "SELECT id, name, image_path FROM APP.Location WHERE LOWER(name) LIKE ? ORDER BY id")) {

        stmt.setString(1, "%" + search.toLowerCase() + "%");

        try (ResultSet rs = stmt.executeQuery()) {
          boolean first = true;
          while (rs.next()) {
            Map<String, Object> loc = new HashMap<>();
            loc.put("id", rs.getInt("id"));
            loc.put("name", rs.getString("name"));
            loc.put("imagePath", rs.getString("image_path"));
            locations.add(loc);

            // Grab the first result to load into pannellum
            if (first) {
              firstId = rs.getInt("id");
              firstName = rs.getString("name");
              firstImagePath = rs.getString("image_path");
              first = false;
            }
          }
        }
      }
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("DATABASE ERROR in SearchLocationServlet: " + e.getMessage());
      e.printStackTrace(); // Helps track down query failures in the NetBeans console
    }

    request.setAttribute("locations", locations);
    request.setAttribute("search", search);
    request.setAttribute("locationId", firstId);
    request.setAttribute("locationName", firstName);
    request.setAttribute("imagePath", firstImagePath);
    request.getRequestDispatcher("searchView.jsp").forward(request, response);
  }
}