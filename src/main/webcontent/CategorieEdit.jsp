<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Entity.Categorie"%>
<%@ page import="Entity.CategorieDAO"%>

<!DOCTYPE html>
<html>
<%@ include file="Navbar.jsp" %>

<head>
    <meta charset="ISO-8859-1">
    <title>Modifier une catégorie</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        h3 {
            text-align: center;
            margin-top: 20px;
        }
        form {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            margin: 0 auto;
            width: 40%;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-top: 80px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #FFC212;
            color: black;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #E0B806;
        }
    </style>
</head>
<body>
    <h3>Modifier une catégorie</h3>
    <% int categoryId = Integer.parseInt(request.getParameter("id")); %>
    <% CategorieDAO categorieDAO = new CategorieDAO(); %>
    <% Categorie category = categorieDAO.getCategoryById(categoryId); %>
    <form action="CategorieServlet" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= category.getCatid() %>">
        <label>Nom de la catégorie:</label>
        <input type="text" name="nom" value="<%= category.getCatlib() %>">
        <input type="submit" value="Enregistrer les modifications">
    </form>
</body>
</html>
