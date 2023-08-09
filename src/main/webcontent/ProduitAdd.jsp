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
    $("form").submit(function(event) {
        var prodlib = $("input[name='prodlib']").val();
        var proddesc = $("input[name='proddesc']").val();
        var prodprix = $("input[name='prodprix']").val();
        var prodquant = $("input[name='prodquant']").val();
        var errorMessage = "";
        
        if (prodlib === "") {
            errorMessage += "Libellé is required.<br>";
        }
        if (proddesc === "") {
            errorMessage += "Description is required.<br>";
        }
        if (prodprix === "") {
            errorMessage += "Prix is required.<br>";
        }
        if (prodquant === "") {
            errorMessage += "Quantité is required.<br>";
        }
        if (errorMessage !== "") {
            $("#errorMessages").html("<div class='error-message'>" + errorMessage + "</div>");
            event.preventDefault();
        } else {
            $("#errorMessages").html(""); // Clear error messages if no errors
        }
    });
});
    </script>
</head>
<body>
    <h2>Ajouter un produit</h2>
    
    <form action="ProduitServlet?action=ProduitAdd" method="post" enctype="multipart/form-data">
        <label>Libellé:</label> <input type="text" name="prodlib"><br>
        <label>Description:</label> <input type="text" name="proddesc"><br><br>
        
       <label>Tailles:</label><br><br>
		<c:forEach var="size" items="${tailleOptions}">
		    <input type="checkbox" name="taille" value="${size}" /> ${size}
		</c:forEach>
		<input type="checkbox" name="tailleOther" value="other"> Other
		<input type="text" name="tailleOtherValue" style="display: none;">
		<br>
		<br>
		
		<label>Couleurs:</label><br><br>
		<c:forEach var="color" items="${couleurOptions}">
		    <input type="checkbox" name="couleur" value="${color}" /> ${color}
		</c:forEach>
		 <input type="checkbox" name="couleurOther" value="other"> Other
		<input type="text" name="couleurOtherValue" style="display: none;">
		<br><br>

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
