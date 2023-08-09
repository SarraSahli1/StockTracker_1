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
    
     <!-- Include jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <script>
    $(document).ready(function() {
        // Show/hide the text fields based on "Other" checkbox selection
        $("input[name='tailleOther']").change(function() {
            if ($(this).is(":checked")) {
                $("input[name='tailleOtherValue']").show();
            } else {
                $("input[name='tailleOtherValue']").hide();
            }
        });
        
        $("input[name='couleurOther']").change(function() {
            if ($(this).is(":checked")) {
                $("input[name='couleurOtherValue']").show();
            } else {
                $("input[name='couleurOtherValue']").hide();
            }
        });
    });
    </script>


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
        
     <label>Tailles:</label>
		<c:forEach var="size" items="${tailleOptions}">
    	<input type="checkbox" name="taille" value="${size}" ${produit.getTaille().contains(size) ? 'checked' : ''}> ${size}<br>
		</c:forEach>
		<input type="checkbox" name="tailleOther" value="other"> Other
		<input type="text" name="tailleOtherValue" style="display: none;">
		<br><br>

		<label>Couleurs:</label>
		<c:forEach var="color" items="${couleurOptions}">
		    <input type="checkbox" name="couleur" value="${color}" ${produit.getCouleur().contains(color) ? 'checked' : ''}> ${color}<br>
		</c:forEach>
		<input type="checkbox" name="couleurOther" value="other"> Other
		<input type="text" name="couleurOtherValue" style="display: none;">
		<br>
		<br>



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

