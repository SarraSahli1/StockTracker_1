package Entity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Entity.Produit; // Import your entity class

public class ProduitDAO {
    private static String url = "jdbc:mysql://localhost:3306/stocktracker";
    private static String login = "root";
    private static String pwd = "";
    private static Connection cnx = null;
    private CategorieDAO catdao= new CategorieDAO();
    public Connection getConnection() {
    	 if (cnx == null) {
             try {
                 Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is loaded
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
public void ajouterProduit(Produit produit) {
    Connection cnx = getConnection();
    
    try {
        PreparedStatement pr = cnx.prepareStatement("INSERT INTO produit (prodlib, proddesc, prodimg, prodprix, prodquant,prodcat, taille, couleur) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        pr.setString(1, produit.getProdlib());
        pr.setString(2, produit.getProddesc());
        pr.setString(3, produit.getProdimg());
        pr.setFloat(4, produit.getProdprix());
        pr.setInt(5, produit.getProdquant());
        pr.setInt(6, produit.getProdcat().getCatid());

        // Combine the sizes and colors into comma-separated strings
        String tailleString = String.join(",", produit.getTaille());
        String couleurString = String.join(",", produit.getCouleur());
        
        pr.setString(7, tailleString);
        pr.setString(8, couleurString);

        pr.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database access errors
    } finally {
        // Close resources (statements, connection, etc.)
    }
}
public List<Produit> getAllProduits() {
    List<Produit> produits = new ArrayList<>();
    Connection cnx = getConnection(); // Assign the connection to cnx attribute

    try (PreparedStatement preparedStatement = cnx.prepareStatement("SELECT * FROM produit")) {
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            int prodid = result.getInt("prodid");
            String prodlib = result.getString("prodlib");
            String proddesc = result.getString("proddesc");
            float prodprix = result.getFloat("prodprix");
            int prodquant = result.getInt("prodquant");
            String prodimg = result.getString("prodimg");
            // Add more attributes as needed
            List<String> taille = Arrays.asList(result.getString("taille").split("\\s*,\\s*"));
            List<String> couleur = Arrays.asList(result.getString("couleur").split("\\s*,\\s*"));

            // Retrieve category information
            int prodcatId = result.getInt("prodcat");
            Categorie categorie = catdao.getCategoryById(prodcatId); // Implement this method to get the category by ID

            // Create a new Product object and add it to the list
            Produit produit = new Produit(prodid, prodlib, proddesc, prodimg, prodprix, prodquant, taille, couleur,categorie ); // Replace null with actual lists of sizes and colors
            produits.add(produit);
        }
    } catch (SQLException ex) {
        System.out.println(ex);
    }
    return produits;
}
public void deleteProduit(int id) {
    Connection cnx = getConnection();

    try {
        PreparedStatement pr = cnx.prepareStatement("DELETE FROM produit WHERE prodid=?");
        pr.setInt(1, id);
        pr.execute();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
public void modifierProduit(Produit produit) {
    Connection cnx = getConnection(); // You need to implement this method to get a database connection
    
    try {
        String query = "UPDATE produit SET prodlib=?, proddesc=?, prodimg=?, prodprix=?, prodquant=?, prodcat=?, taille=?, couleur=? WHERE prodid=?";
        PreparedStatement pr = cnx.prepareStatement(query);
        
        pr.setString(1, produit.getProdlib());
        pr.setString(2, produit.getProddesc());
        pr.setString(3, produit.getProdimg());
        pr.setFloat(4, produit.getProdprix());
        pr.setInt(5, produit.getProdquant());
        pr.setInt(6, produit.getProdcat().getCatid());

        // Combine the sizes and colors into comma-separated strings
        String tailleString = String.join(",", produit.getTaille());
        String couleurString = String.join(",", produit.getCouleur());
        
        pr.setString(7, tailleString);
        pr.setString(8, couleurString);
        pr.setInt(9, produit.getProdid());

        pr.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }    

}

public Produit getProduitById(int id) {
    Produit produit = null;
    try {
        Connection cnx = getConnection();
        PreparedStatement preparedStatement = cnx.prepareStatement("SELECT prodid, prodlib, proddesc, prodimg, prodprix, prodquant, prodcat, taille, couleur FROM produit WHERE prodid=?");
        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            String prodlib = rs.getString("prodlib");
            String proddesc = rs.getString("proddesc");
            String prodimg = rs.getString("prodimg");
            float prodprix = rs.getFloat("prodprix");
            int prodquant = rs.getInt("prodquant");
            int prodcatId = rs.getInt("prodcat");
            String tailleString = rs.getString("taille");
            String couleurString = rs.getString("couleur");
            
            // Convert sizes and colors from comma-separated strings to lists
            List<String> taille = Arrays.asList(tailleString.split(","));
            List<String> couleur = Arrays.asList(couleurString.split(","));
            
            CategorieDAO categorieDAO = new CategorieDAO();
            Categorie categorie = categorieDAO.getCategoryById(prodcatId);

            produit = new Produit(id, prodlib, proddesc,prodimg, prodprix, prodquant, taille, couleur, categorie);
        }
    } catch (SQLException ex) {
        System.out.println(ex);
    }
    return produit;
}
public List<Produit> searchProducts(String searchQuery) {
    List<Produit> products = new ArrayList<>();
    Connection cnx = getConnection(); // Implement your getConnection method

    try {
        PreparedStatement pr = cnx.prepareStatement("SELECT * FROM produit WHERE prodlib LIKE ?");
        pr.setString(1, "%" + searchQuery + "%");
        ResultSet rs = pr.executeQuery();

        while (rs.next()) {
            int prodId = rs.getInt("prodid");
            String prodlib = rs.getString("prodlib");
            String proddesc = rs.getString("proddesc");
            String prodimg = rs.getString("prodimg");
            float prodprix = rs.getFloat("prodprix");
            int prodquant = rs.getInt("prodquant");
            int prodcatId = rs.getInt("prodcat");
            String tailleString = rs.getString("taille");
            String couleurString = rs.getString("couleur");
            
            // Convert sizes and colors from comma-separated strings to lists
            List<String> taille = Arrays.asList(tailleString.split(","));
            List<String> couleur = Arrays.asList(couleurString.split(","));
            
            CategorieDAO categorieDAO = new CategorieDAO();
            Categorie categorie = categorieDAO.getCategoryById(prodcatId);

            Produit product = new Produit(prodId, prodlib, proddesc,prodimg, prodprix, prodquant, taille, couleur, categorie);
            products.add(product);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } 

    return products;
}


}
