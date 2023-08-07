<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="Entity.Categorie" %>
<!DOCTYPE html>
<html>
<%@ include file="Navbar.jsp" %>
<head>
    <title>Ajouter un produit</title>
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
        	margin-top: 20px;
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            margin: 0 auto;
            width: 60%;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                                    margin-top:80px;
            
        }
        form input[type="text"],
        form select,
        form input[type="file"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        form select {
            appearance: none;
            -webkit-appearance: none;
            padding: 10px;
            background: url('arrow-down.png') no-repeat right;
        }
        form input[type="submit"] {
            background-color: #FFC212;
            color: black;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }
        form input[type="submit"]:hover {
            background-color: #E0B806;
        }
    </style>
</head>
<body>
    <h2>Ajouter un produit</h2>
    <form action="ProduitServlet?action=ProduitAdd" method="post" enctype="multipart/form-data">
        <label>Libellé:</label> <input type="text" name="prodlib"><br>
        <label>Description:</label> <input type="text" name="proddesc"><br>
        <label>Tailles (séparées par des virgules):</label> <input type="text" name="taille"><br>
        <label>Couleurs (séparées par des virgules):</label> <input type="text" name="couleur"><br>
        <label>Prix:</label> <input type="text" name="prodprix"><br>
        <label>Quantité:</label> <input type="text" name="prodquant"><br>
        <label>Image:</label> <input type="file" name="prodimg"><br>
        <label>Catégorie:</label>
        <select name="prodcat">
            <option value="-1">Sélectionnez une catégorie</option>
            <c:forEach var="categorie" items="${categories}">
                <option value="${categorie.getCatid()}">${categorie.getCatlib()}</option>
            </c:forEach>
        </select><br>
        <input type="submit" value="Ajouter">
    </form>
</body>
</html>
