# 360-Campus-View
An interactive web-based 360° virtual campus tour application built using Java Java EE (Servlets, JSP), Apache Derby, and Pannellum JS. Features dynamic location rendering and a custom database-driven search system.
# CampusTour360

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A virtual 360-degree campus tour application. It lets users explore campus hotspots using interactive, click-and-drag panoramic views that are pulled dynamically from a database. 

---

## 📷 What it looks like

Here is a quick look at the interface in action:

| 360° Interactive Viewer | Dynamic Location Search |
| --- | --- |
| ![Viewer Page](screenshots/viewer_page.png) | ![Search Page](screenshots/search_page.png) |

> 💡 **Setup Tip:** To show your own screenshots here, create a folder named `screenshots` at the root of this project, name your images `viewer_page.png` and `search_page.png`, and GitHub will automatically display them in the table above.

---

## ✨ Features
* **Interactive 360° Viewer:** Smooth, browser-based panorama rendering using WebGL (via Pannellum).
* **Smart Search Engine:** A live search system that uses SQL wildcard filtering to let users quickly look up and jump to landmarks (like "benches" or "court").
* **Dynamic Backend:** Uses custom Java Servlets to serve standard JSP page views or raw JSON data depending on how the frontend requests it.

---

## 🛠️ Tech Stack & Requirements
* **IDE:** NetBeans 8.2
* **Java:** JDK 1.8 (Java SE 8)
* **Server:** GlassFish Server 4.1.1
* **Database:** Apache Derby (Java DB) via JDBC

---

## 📂 Project Structure

* `src/java/.../controllers/` ── The servlet logic (`TourServlet`, `SearchLocationServlet`).
* `web/images/panoramas/outdoors/` ── Where you need to put your 360° `.jpg` photos.
* `web/viewer.jsp` & `searchView.jsp` ── The dynamic frontend interfaces.
* `setup.sql` ── The database structural definition and data seeding script.

---

## 🔧 How to Make it Work (Step-by-Step)

Follow these steps to set up the project and get it running locally on your machine:

### 1. Clone the Project
```bash
git clone [https://github.com/YOUR_USERNAME/CampusTour360_Fixed.git](https://github.com/YOUR_USERNAME/CampusTour360_Fixed.git)
### 2. Open in NetBeans & Fix Dependencies
1. Open NetBeans 8.2, go to File -> Open Project, and select this project folder.
2. If you see warnings about missing libraries, right-click the project name and open Properties.
3. Under Libraries, make sure your Target Server is set to GlassFish Server 4.1.1 and your Java Platform is set to JDK 1.8.
4. GlassFish 4.1.1 comes pre-bundled with the required javax APIs (Servlet, Annotations, and EclipseLink persistence). If they are marked with a red error icon, click Add Library, select the GlassFish Endorsed API Set, or manually add your specific library JARs to clear the paths.

### 3. Add your 360° Images
* Drop your 360-degree panoramic images into the web/images/panoramas/outdoors/ directory.
* Make sure your image filenames match the paths inside the database exactly (e.g., 1-outdoor-front-gate.jpg).

### 4. Set Up the Database
1. Go to the Services tab in the top-left area of NetBeans.
2. Expand Databases, right-click Java DB, and click Start Server.
3. Right-click Java DB again, select Create Database, and name it ClassList. Set both the user and password to app and 123.
4. Open a connection to your new database, open a new SQL terminal, and copy-paste the entire contents of the setup.sql file into it. Run the script to create and seed the Location table.

### 5. Build and Run
1. Head back to the Projects tab, right-click the project root, and select Clean and Build (this compiles the code and caches your images onto the live server path).
2. Right-click the project root again and click Run. GlassFish will start up, deploy the application, and open the tour inside your web browser.

---

## License
This project is open-source and available under the MIT License.
