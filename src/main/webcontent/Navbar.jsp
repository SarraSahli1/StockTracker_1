<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ma Barre de Navigation</title>
    <style>
        /* Styles pour la barre de navigation */
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            text
        }
        .navbar {
            background-color: #46467A;
            overflow: hidden;
            position: fixed;
            top: 0;
            width: 100%;
        }
        .navbar ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
        }
        .navbar li {
            margin: 0;
            padding: 25px;
        }
        .navbar a {
            color: white;
            text-decoration: none;
            font-size: 18px; 

        }
        .navbar a:hover {
            text-decoration: underline;
            
        }
        .logo {
            display: inline-block;
            margin-right: 20px;
            width:90px;
            height: 70px;
             
        }
        
    </style>
</head>
<body>
    <div class="navbar">
        <ul>
                    <img src="images/logo.png" alt="u are fucked no logo" class="logo">
        
            <li><a href="ProduitServlet">Produits</a></li>
            <li><a href="CategorieServlet">Categories</a></li>
            <li><a href="FournisseurServlet">Fournisseurs</a></li>
            <li><a href="TransactionServlet">Transactions</a></li>
        </ul>
    </div>
</body>
</html>
