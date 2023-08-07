<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="Entity.Categorie" %>
<%@ page import="Entity.Produit" %>
<%@ page import="Entity.ProduitDAO" %>

<!DOCTYPE html>
<html>
<%@ include file="Navbar.jsp" %>

<head>
    <title>Modifier un produit</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        h2 {
            text-align: center;
            margin-top: 20px;
        }
        form {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            margin: 0 auto;
            width: 60%;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-top: 80px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="number"],
        select,
        textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="file"] {
            margin-top: 5px;
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
        img {
            max-width: 100px;
            max-height: 100px;
        }
    </style>
</head>
<body>
    <h2>Modifier un produit</h2>
    <%
        int id = Integer.parseInt(request.getParameter("id"));
        ProduitDAO pDAO = new ProduitDAO();
        Produit p = pDAO.getProduitById(id);
    %>

    <form action="ProduitServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= p.getProdid() %>">
        
        <label>Libellé du produit:</label>
        <input type="text" name="prodlib" value="<%= p.getProdlib() %>">
        
        <label>Description du produit:</label>
        <textarea name="proddesc"><%= p.getProddesc() %></textarea>
        
        <label>Image actuelle du produit:</label><br>
        <img src="images/<%= p.getProdimg() %>" alt="Product Image">
        
        <label>Nouvelle image du produit:</label>
        <input type="file" name="prodimg">
        
        <label>Prix du produit:</label>
        <input type="number" step="0.01" name="prodprix" value="<%= p.getProdprix() %>">
        
        <label>Quantité du produit:</label>
        <input type="number" name="prodquant" value="<%= p.getProdquant() %>">
        
        <label>Tailles (séparées par des virgules):</label>
        <input type="text" name="taille" value="<%= p.getTaille() %>">
        
        <label>Couleurs (séparées par des virgules):</label>
        <input type="text" name="couleur" value="<%= p.getCouleur() %>">

        <label>Catégorie:</label>
        <select name="prodcat">
            <option value="-1">Sélectionnez une catégorie</option>
            <c:forEach var="categorie" items="${categories}">
                <option value="${categorie.getCatid()}" ${categorie.getCatid() == p.getProdcat().getCatid() ? 'selected' : ''}>
                    ${categorie.getCatlib()}
                </option>
            </c:forEach>
        </select>

        <br><br><br><br>
        ID: <%= p.getProdid() %><br>
        Libellé: <%= p.getProdlib() %><br>
        Description: <%= p.getProddesc() %><br>
        <input type="submit" value="Enregistrer les modifications">
    </form>
</body>
</html>
