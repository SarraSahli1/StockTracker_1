<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="Navbar.jsp" %>
<head>
    <meta charset="ISO-8859-1">
    <title>Fournisseur</title>
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
        	margin-top: 80px;
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            margin: 0 auto;
            width: 60%;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-top:80px;
            
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
    <h3>Ajouter Fournisseur</h3> 
    <form action="FournisseurServlet" method="Post">
        <input type="hidden" name="action" value="add">
        <label for="fnom">Nom:</label>
        <input type="text" id="fnom" name="fnom" required><br>

        <label for="ftel">Téléphone:</label>
        <input type="text" id="ftel" name="ftel" required><br>

        <label for="fadresse">Adresse:</label>
        <input type="text" id="fadresse" name="fadresse" required><br>

        <label for="fdelais">Délais:</label>
        <input type="text" id="fdelais" name="fdelais" required><br>

        <input type="submit" value="Ajouter">
    </form>
</body>
</html>
