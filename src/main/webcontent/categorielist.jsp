<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Entity.Categorie" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
    <%@ include file="Navbar.jsp" %>

<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <meta charset="UTF-8">
    <title>Liste des catégories</title>
    <style>
        /* Add custom CSS for centering the table */
        body {
            align-items: center;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            min-height: 100vh;
            font-family: Arial, sans-serif;
            margin-top:100px;
            background-color: #E0DFFD
        }
       
        .content {
            margin-top: 20px;
        }
        table {
            border-collapse: collapse;
            width: 80%; /* Adjust the width of the table */
            max-width: 1000px; /* Limit maximum width */
            text-align: center;
            background-color: white;
        }
        th, td {
            border: 1px solid #dddddd;
            padding: 8px;
        }
        th {
            background-color: white;
        }
        
        .add-button {
            background-color: #FFC212;
            padding: 10px;
            border-radius: 8px;
            display: inline-block;
            margin-top: 10px;
            text-decoration: none; /* Add this line to remove underlining */
                        color: black;
            
            
            
        }
        .add-button a {
            color: black;
            text-decoration:none;
        }
        .add-button-container {
            text-align: right;
            padding-right: 20px;
        }
         .search-bar {
    margin-top: 20px;
    margin-right: 20px;
    margin-bottom: 20px;
background-color: rgba(192, 192, 192, 0.1);
    
}

.search-bar form {
    display: inline-block;
    
}

.search-bar input[type="text"] {
    padding: 5px;
    border-radius: 4px;
    border: 0px solid #ccc;
        flex: 1; /* This will make the input expand to fill the available space */
	width:900px;
	height:30px;
background-color: rgba(192, 192, 192, 0.1);	        
	
    
}

.search-bar button {
    padding: 5px 10px;
    border: none;
    background-color: #007BFF;
    color: white;
    border-radius: 4px;
    cursor: pointer;
}
        .container {
    display: flex;
    justify-content: space-between;
}
        
    </style>
</head>
<body>
    <h3>Liste des catégories</h3>
    <div class="search-bar">
    <form action="CategorieServlet" method="get"> <!-- Change the action to the servlet -->
        <input type="hidden" name="action" value="search"> <!-- Add a hidden input for action -->
        <input type="text" name="searchQuery" placeholder="Search...">
        <button type="submit"><i class="fas fa-search"></i></button>
    </form>
</div>
        <table border="1">
            <tr>
                <th colspan="9">
                    <div class="add-button-container">
    	<a href="CategorieAdd.jsp"class="add-button">Ajouter Catégorie</a> <!-- Add this line -->  
    	                </div>
    	  </th>
        </tr>  
        <tr>
            <th>Nom de la catégorie</th>
            <th>Actions</th>
        </tr>
        <% List<Categorie> categories = (List<Categorie>) request.getAttribute("categories"); %>
        
        <% for (Categorie categorie : categories) { %>
            <tr>
                <td><%= categorie.getCatlib() %></td>
                <td>
                    <form action="CategorieServlet" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= categorie.getCatid() %>">
    					<button type="submit" class="icon-button" title="Supprimer"><i class="fas fa-trash" style="color:#A51717"></i></button>
                    </form>
                    <form action="CategorieServlet" method="get" style="display: inline;">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="<%= categorie.getCatid() %>">
    					<button type="submit" class="icon-button" title="Modifier"><i class="fas fa-pencil-alt"></i></button>
                    </form>
                </td>
            </tr>
        <% } %>
    </table>  
</body>
</html>
