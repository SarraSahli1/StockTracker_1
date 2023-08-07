<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="Entity.Categorie" %>
<%@ page import="Entity.Produit" %>
<%@ page import="Entity.Fournisseur" %>
<%@ page import="Entity.CategorieDAO" %>
<%@ page import="Entity.ProduitDAO" %>
<%@ page import="Entity.FournisseurDAO" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html>
<%@ include file="Navbar.jsp" %>

<head>
    <title>Ajouter une transaction</title>
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
            width: 60%;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-top: 80px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="date"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        select {
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
    <h3>Ajouter une transaction</h3> 
    <form action="TransactionServlet?action=TransactionAdd" method="post">
        <input type="hidden" name="action" value="add">
        
        <label>Date:</label>
        <input type="date" name="tdate" required><br>
        
        <label>Entrée:</label>
        <input type="number" name="tentree" required><br>
        
        <label>Prix Total:</label>
        <input type="number" step="0.01" name="tprixtotal" required><br>
        
        <label>Produit:</label>
        <select name="tprod" required>
            <option value="-1">Sélectionnez un produit</option>
            <c:forEach var="produit" items="${produits}">
                <option value="${produit.getProdid()}">${produit.getProdlib()}</option>
            </c:forEach>
        </select><br>
        
        <label>Fournisseur:</label>
        <select name="tfourn" required>
            <option value="-1">Sélectionnez un fournisseur</option>
            <c:forEach var="fournisseur" items="${fournisseurs}">
                <option value="${fournisseur.getFid()}">${fournisseur.getFnom()}</option>
            </c:forEach>
        </select><br>
        
        <input type="submit" value="Ajouter">
    </form>
</body>
</html>
