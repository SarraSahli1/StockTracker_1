<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Entity.Fournisseur"%>
<%@ page import="Entity.FournisseurDAO"%>

<!DOCTYPE html>
<html>
<%@ include file="Navbar.jsp" %>
<head>
    <meta charset="UTF-8">
    <title>Modifier un fournisseur</title>
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
            font-weight: bold;
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
    <h3>Modifier un fournisseur</h3>
    <%-- Retrieve the fournisseur ID from the request parameter --%>
    <% int fournisseurId = Integer.parseInt(request.getParameter("id")); %>
    <%-- Use a DAO method to fetch the fournisseur information based on the ID --%>
    <% FournisseurDAO fournisseurDAO = new FournisseurDAO(); %>
    <% Fournisseur fournisseur = fournisseurDAO.getFournisseurById(fournisseurId); %>
    <%-- Display the fournisseur information in a form --%>
    <form action="FournisseurServlet" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= fournisseur.getFid() %>">
        
        <label>Nom:</label>
        <input type="text" name="fnom" value="<%= fournisseur.getFnom() %>" required>
        
        <label>Téléphone:</label>
        <input type="text" name="ftel" value="<%= fournisseur.getFtel() %>" required>
        
        <label>Adresse:</label>
        <input type="text" name="fadresse" value="<%= fournisseur.getFadresse() %>" required>
        
        <label>Délais:</label>
        <input type="text" name="fdelais" value="<%= fournisseur.getFdelais() %>" required>
        
        <input type="submit" value="Enregistrer les modifications">
    </form>
</body>
</html>
