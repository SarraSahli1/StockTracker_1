<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="Entity.Produit" %>
<!DOCTYPE html>
<html>
    <%@ include file="Navbar.jsp" %>

<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <title>Liste des produits</title>
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
            display: flex;
            align-items: flex-start;
            margin: 20px;
            width: 80%; /* Adjust the width of the content */
        }
        
        .filter-section {
            flex: 1;
            background-color: white;
            padding: 20px;
            border: 1px solid #dddddd;
            border-radius: 8px;
            margin-right: 20px;
            height: 80vh; /* 80% of the viewport height */
            overflow-y: auto;
        }
        
        .product-table {
            flex: 4;
            background-color: white;
            border: 1px solid #dddddd;
            border-radius: 8px;
            padding: 20px;
            height: 80vh; /* 80% of the viewport height */
            overflow-y: auto;
        }
        
        table {
            border-collapse: collapse;
            width: 100%; /* Adjust the width of the table */
            max-width: 1000px; /* Limit maximum width */
            text-align: center;
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
            width:800px;
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
    <div class="content">
        <div class="filter-section">
    <h3>Filter Options</h3>
    <form action="ProduitServlet" method="get">
        <input type="hidden" name="action" value="filter">
        <label for="filterTaille">Filter by Size:</label>
        <select id="filterTaille" name="filterTaille">
            <option value="">All</option>
            <c:forEach items="${sizes}" var="size">
                <option value="${size}">${size}</option>
            </c:forEach>
        </select>
        <button type="submit">Apply Filter</button>
    </form>
</div>

        <div class="product-table">
            <h3>Liste des produits</h3>
            <div class="search-bar">
                <form action="ProduitServlet" method="get">
                    <input type="hidden" name="action" value="search">
                    <input type="text" name="searchQuery" placeholder="Search...">
                    <button type="submit"><i class="fas fa-search"></i></button>
                </form>
            </div>
            <table border="1">
                <tr>
                    <th colspan="9" >
                        <div class="add-button-container">
                            <a href="ProduitServlet?action=ProduitAdd" class="add-button">Ajouter Produit</a>
                        </div>
                    </th>
                </tr>
                <tr>
                    <th>Libellé du produit</th>
                    <th>Description</th>
                    <th>Taille</th>
                    <th>Couleur</th>
                    <th>Prix</th>
                    <th>Quantité</th>
                    <th>Image</th>
                    <th>Catégorie</th>
                    <th>Actions</th>
                </tr>
                <% List<Produit> produits = (List<Produit>) request.getAttribute("produits"); %>
                <% for (Produit produit : produits) { %>
                    <tr>
                        <td><%= produit.getProdlib() %></td>
                        <td><%= produit.getProddesc() %></td>
                        <td><%= produit.getTaille().toString() %></td>
                        <td><%= produit.getCouleur().toString() %></td>
                        <td><%= produit.getProdprix() %></td>
                        <td><%= produit.getProdquant() %></td>
                        <td><img src="${pageContext.request.contextPath}/images/<%= produit.getProdimg() %>" width="100" height="100" alt="no Image"></td>
                        <td><%= produit.getProdcat().getCatlib() %></td>
                        <td>
                            <form action="ProduitServlet" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="<%= produit.getProdid() %>">
                                <button type="submit" class="icon-button" title="Supprimer"><i class="fas fa-trash" style="color:#A51717"></i></button>
                            </form>
                            <form action="ProduitServlet" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="ProduitEdit">
                                <input type="hidden" name="id" value="<%= produit.getProdid() %>">
                                <button type="submit" class="icon-button" title="Modifier"><i class="fas fa-pencil-alt"></i></button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </table>
        </div>
    </div>
</body>
</html>
