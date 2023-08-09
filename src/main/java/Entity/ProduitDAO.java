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
    
public void ajouterProduit(Produit produit,List<String> selectedTailles, List<String> selectedCouleurs) {
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
        String tailleString = String.join(",", selectedTailles);
        String couleurString = String.join(",", selectedCouleurs);
        
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
public void modifierProduit(Produit produit, List<String> taille, List<String> couleur) {
    Connection cnx = getConnection();
    
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
        String tailleString = String.join(",", taille);
        String couleurString = String.join(",", couleur);
        
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

public List<Produit> filterProduitsByPrice(float minPrice, float maxPrice) {
    List<Produit> filteredProduits = new ArrayList<>();
    Connection cnx = getConnection();

    try {
        PreparedStatement pr = cnx.prepareStatement("SELECT * FROM Produit WHERE prodprix BETWEEN ? AND ?");

        pr.setFloat(1, minPrice);
        pr.setFloat(2, maxPrice);
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
            filteredProduits.add(product);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return filteredProduits;

}

//ProduitDAO.java
public List<Produit> filterProduitsByColor(List<String> selectedColors) {
    List<Produit> filteredProduits = new ArrayList<>();
    Connection cnx = getConnection();

    try {
        String selectedColorsString = String.join(",", selectedColors);
        PreparedStatement pr = cnx.prepareStatement("SELECT * FROM Produit WHERE FIND_IN_SET(?, couleur) > 0");
        pr.setString(1, selectedColorsString);

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

            Produit product = new Produit(prodId, prodlib, proddesc, prodimg, prodprix, prodquant, taille, couleur, categorie);
            filteredProduits.add(product);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } 

    return filteredProduits;
}

public List<String> getDistinctColors() {

    List<String> couleurOptions = new ArrayList<>();
    Connection cnx = getConnection();

    try {
        PreparedStatement pr = cnx.prepareStatement("SELECT couleur FROM produit");
        ResultSet rs = pr.executeQuery();

        while (rs.next()) {
            String couleurList = rs.getString("couleur");
            System.out.println("Raw Colors: " + couleurList);

            List<String> colors = Arrays.asList(couleurList.split("\\s*,\\s*"));
            System.out.println("Split Colors: " + colors);

            for (String color : colors) {
                if (!couleurOptions.contains(color)) {
                    couleurOptions.add(color);
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return couleurOptions;
}

public List<String> getDistinctTailles() {
    List<String> tailleOptions = new ArrayList<>();
    Connection cnx = getConnection();

    try {
        PreparedStatement pr = cnx.prepareStatement("SELECT taille FROM produit");
        ResultSet rs = pr.executeQuery();

        while (rs.next()) {
            String tailleList = rs.getString("taille");
            System.out.println("Raw taille: " + tailleList);

            List<String> tailles = Arrays.asList(tailleList.split("\\s*,\\s*"));
            System.out.println("Split taille: " + tailles);

            for (String taille : tailles) {
                if (!tailleOptions.contains(taille)) {
                	tailleOptions.add(taille);
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tailleOptions;
}
public List<Produit> filterProduitsByTaille(List<String> selectedTailles) {
    List<Produit> filteredProduits = new ArrayList<>();
    Connection cnx = getConnection();

    try {
        String selectedTaillesString = String.join(",", selectedTailles);
        PreparedStatement pr = cnx.prepareStatement("SELECT * FROM Produit WHERE FIND_IN_SET(?, taille) > 0");
        pr.setString(1, selectedTaillesString);

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

            Produit product = new Produit(prodId, prodlib, proddesc, prodimg, prodprix, prodquant, taille, couleur, categorie);
            filteredProduits.add(product);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } 

    return filteredProduits;
}
public List<Produit> filterProduitsByCategorie(int categoryId) {
    List<Produit> filteredProduits = new ArrayList<>();
    Connection cnx = getConnection();

    try {
        PreparedStatement pr = cnx.prepareStatement("SELECT * FROM Produit WHERE prodcat = ?");
        pr.setInt(1, categoryId);

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

            // Fetch the category using the CategorieDAO
            CategorieDAO categorieDAO = new CategorieDAO();
            Categorie categorie = categorieDAO.getCategoryById(prodcatId);

            Produit product = new Produit(prodId, prodlib, proddesc, prodimg, prodprix, prodquant, taille, couleur, categorie);
            filteredProduits.add(product);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } 

    return filteredProduits;
}

}