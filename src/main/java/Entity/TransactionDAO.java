package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TransactionDAO {
    private static String url = "jdbc:mysql://localhost:3306/stocktracker";
    private static String login = "root";
    private static String pwd = "";
    private static Connection cnx = null;

    public Connection getConnection() {
        if (cnx == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                cnx = DriverManager.getConnection(url, login, pwd);
                System.out.println("Connexion établie!");
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC driver not found.");
                e.printStackTrace();
            } catch (SQLException ex) {
                System.out.println("Error establishing the database connection.");
                ex.printStackTrace();
            }
        }
        return cnx;
    }

    public void addTransaction(Transaction transaction) {
        Connection cnx = getConnection();

        try {
            PreparedStatement pr = cnx.prepareStatement("INSERT INTO transaction (Tid, Tdate, Tentree, Tprixtotal, Tprod, Tfourn) VALUES (?, ?, ?, ?, ?, ?)");
            pr.setInt(1, transaction.getTid());
            pr.setDate(2, new java.sql.Date(transaction.getTdate().getTime()));
            pr.setInt(3, transaction.getTentree());
            pr.setFloat(4, transaction.getTprixtotal());
            pr.setInt(5, transaction.getTprod().getProdid());
            pr.setInt(6, transaction.getTfourn().getFid());
            pr.execute();
            int newQuantity = transaction.getTprod().getProdquant() + transaction.getTentree();
            updateProductQuantity(transaction.getTprod().getProdid(), newQuantity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateProductQuantity(int productId, int newQuantity) {
        Connection cnx = getConnection();

        try {
            PreparedStatement pr = cnx.prepareStatement("UPDATE produit SET prodquant = ? WHERE prodid = ?");
            pr.setInt(1, newQuantity);
            pr.setInt(2, productId);
            pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }}

    public void deleteTransaction(int id) {
        Connection cnx = getConnection();

        try {
            PreparedStatement pr = cnx.prepareStatement("DELETE FROM transaction WHERE Tid=?");
            pr.setInt(1, id);
            pr.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Transaction> selectAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Connection cnx = getConnection();

        try (PreparedStatement preparedStatement = cnx.prepareStatement("SELECT * FROM transaction")) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int tid = result.getInt("Tid");
                Date tdate = result.getDate("Tdate");
                int tentree = result.getInt("Tentree");
                float tprixtotal = result.getFloat("Tprixtotal");
                int tprodId = result.getInt("Tprod");
                int tfournId = result.getInt("Tfourn");

                // Retrieve the related Product and Fournisseur objects using their DAOs
                ProduitDAO produitDAO = new ProduitDAO();
                FournisseurDAO fournisseurDAO = new FournisseurDAO();

                Produit tprod = produitDAO.getProduitById(tprodId);
                Fournisseur tfourn = fournisseurDAO.getFournisseurById(tfournId);

                Transaction transaction = new Transaction(tid, tdate, tentree, tprixtotal, tprod, tfourn);
                transactions.add(transaction);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return transactions;
    }

    public Transaction getTransactionById(int id) {
        Transaction transaction = null;
        try {
            Connection cnx = getConnection();
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT * FROM transaction WHERE Tid=?");
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int tid = rs.getInt("Tid");
                Date tdate = rs.getDate("Tdate");
                int tentree = rs.getInt("Tentree");
                float tprixtotal = rs.getFloat("Tprixtotal");
                int tprodId = rs.getInt("Tprod");
                int tfournId = rs.getInt("Tfourn");

                // Retrieve the related Product and Fournisseur objects using their DAOs
                ProduitDAO produitDAO = new ProduitDAO();
                FournisseurDAO fournisseurDAO = new FournisseurDAO();

                Produit tprod = produitDAO.getProduitById(tprodId);
                Fournisseur tfourn = fournisseurDAO.getFournisseurById(tfournId);

                transaction = new Transaction(tid, tdate, tentree, tprixtotal, tprod, tfourn);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return transaction;
    }
   

        public void updateTransaction(Transaction transaction) {
            Connection cnx = getConnection();

            try {
                PreparedStatement pr = cnx.prepareStatement("UPDATE transaction SET Tdate=?, Tentree=?, Tprixtotal=?, Tprod=?, Tfourn=? WHERE Tid=?");
                pr.setDate(1, new java.sql.Date(transaction.getTdate().getTime()));
                pr.setInt(2, transaction.getTentree());
                pr.setFloat(3, transaction.getTprixtotal());
                pr.setInt(4, transaction.getTprod().getProdid());
                pr.setInt(5, transaction.getTfourn().getFid());
                pr.setInt(6, transaction.getTid());
                pr.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

public List<Transaction> searchByProductOrSupplier(String searchQuery) {
    List<Transaction> transactions = new ArrayList<>();
    Connection cnx = getConnection();

    String sqlQuery = "SELECT T.*, P.prodlib, F.fnom " +
                      "FROM transaction T " +
                      "JOIN produit P ON T.tprod = P.prodid " +
                      "JOIN fournisseur F ON T.tfourn = F.fid " +
                      "WHERE P.prodlib LIKE ? OR F.fnom LIKE ?";

    try (PreparedStatement preparedStatement = cnx.prepareStatement(sqlQuery)) {
        preparedStatement.setString(1, "%" + searchQuery + "%");
        preparedStatement.setString(2, "%" + searchQuery + "%");

        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            int tid = result.getInt("Tid");
            Date tdate = result.getDate("Tdate");
            int tentree = result.getInt("Tentree");
            float tprixtotal = result.getFloat("Tprixtotal");
            int tprodId = result.getInt("Tprod");
            int tfournId = result.getInt("Tfourn");
            String prodlib = result.getString("prodlib");
            String fnom = result.getString("fnom");
            
            Produit produit = new Produit();
            produit.setProdid(tprodId);
            produit.setProdlib(prodlib);
            
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setFid(tfournId);
            fournisseur.setFnom(fnom);
            
            Transaction transaction = new Transaction(tid, tdate, tentree, tprixtotal, produit, fournisseur);
            transactions.add(transaction);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return transactions;
}
public List<Transaction> filterTransactionsByPrice(float minPrice, float maxPrice) {
    List<Transaction> filteredTransactions = new ArrayList<>();
    Connection cnx = getConnection();

    try {
        PreparedStatement pr = cnx.prepareStatement("SELECT * FROM transaction WHERE Tprixtotal BETWEEN ? AND ?");

        pr.setFloat(1, minPrice);
        pr.setFloat(2, maxPrice);
        ResultSet resultSet = pr.executeQuery();

        while (resultSet.next()) {
            int tid = resultSet.getInt("Tid");
            Date tdate = resultSet.getDate("Tdate");
            int tentree = resultSet.getInt("Tentree");
            float tprixtotal = resultSet.getFloat("Tprixtotal");
            int tprodId = resultSet.getInt("Tprod");
            int tfournId = resultSet.getInt("Tfourn");
            
            Produit produit = new Produit();
            produit.setProdid(tprodId);
            
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setFid(tfournId);
            
            Transaction transaction = new Transaction(tid, tdate, tentree, tprixtotal, produit, fournisseur);
            filteredTransactions.add(transaction);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return filteredTransactions;

}
public List<Transaction> filterTransactionsByDate(Date startDate, Date endDate) {
    List<Transaction> filteredTransactions = new ArrayList<>();
    Connection cnx = getConnection();

    try {
        PreparedStatement pr = cnx.prepareStatement("SELECT * FROM transaction WHERE Tdate BETWEEN ? AND ?");
        pr.setDate(1, new java.sql.Date(startDate.getTime()));
        pr.setDate(2, new java.sql.Date(endDate.getTime()));

        ResultSet resultSet = pr.executeQuery();

        while (resultSet.next()) {  // Move the cursor to the first row and iterate through the result set
            int tid = resultSet.getInt("Tid");
        	Date tdate = resultSet.getDate("Tdate");
            int tentree = resultSet.getInt("Tentree");
            float tprixtotal = resultSet.getFloat("Tprixtotal");
            int tprodId = resultSet.getInt("Tprod");
            int tfournId = resultSet.getInt("Tfourn");
        
            Produit produit = new Produit();
            produit.setProdid(tprodId);
        
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setFid(tfournId);
        
            Transaction transaction = new Transaction(tid,tdate, tentree, tprixtotal, produit, fournisseur);
            filteredTransactions.add(transaction);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return filteredTransactions;
}


    }


