<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Entity.Transaction" %>
<%@ page import="Entity.Produit" %>
<%@ page import="Entity.Fournisseur" %>
<%@ page import="Entity.ProduitDAO" %>
<%@ page import="Entity.FournisseurDAO" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html>
<head>
    <title>Modifier une transaction</title>
</head>
<body>
    <h2>Modifier une transaction</h2>
    
    <form action="TransactionServlet" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${transaction.getTid()}">
        
        <label>Date:</label>
        <input type="date" name="tdate" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${transaction.getTdate()}' />" required><br>
        
        <label>Entrée:</label>
        <input type="number" name="tentree" value="${transaction.getTentree()}" required><br>
        
        <label>Prix Total:</label>
        <input type="number" step="0.01" name="tprixtotal" value="${transaction.getTprixtotal()}" required><br>
        
        <label>Produit:</label>
        <select name="tprod">
            <c:forEach var="produit" items="${produits}">
                <option value="${produit.getProdid()}" ${transaction.getTprod().getProdid() == produit.getProdid() ? 'selected' : ''}>${produit.getProdlib()}</option>
            </c:forEach>
        </select><br>
        
        <label>Fournisseur:</label>
        <select name="tfourn">
            <c:forEach var="fournisseur" items="${fournisseurs}">
                <option value="${fournisseur.getFid()}" ${transaction.getTfourn().getFid() == fournisseur.getFid() ? 'selected' : ''}>${fournisseur.getFnom()}</option>
            </c:forEach>
        </select><br>
        
        <input type="submit" value="Modifier">
    </form>
</body>
</html>
