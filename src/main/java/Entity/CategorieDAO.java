package Entity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class CategorieDAO {
    private static String url = "jdbc:mysql://localhost:3306/stocktracker";
    private static String login = "root";
    private static String pwd = "";
    private static Connection cnx = null;
    private List<Categorie> categories; // Add this field

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

    public void add(Categorie p) {
        Connection cnx = getConnection();

        try {
            PreparedStatement pr = cnx.prepareStatement("INSERT INTO categorie VALUES (NULL,?)");
            pr.setString(1, p.getCatlib());
            pr.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        Connection cnx = getConnection();

        try {
            PreparedStatement pr = cnx.prepareStatement("DELETE FROM categorie WHERE catid=?");
            pr.setInt(1, id);
            pr.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Categorie> selectallCategories() {
        List<Categorie> listcat = new ArrayList<>();
        Connection cnx = getConnection(); // Assign the connection to cnx attribute

        try (PreparedStatement preparedStatement = cnx.prepareStatement("select * from categorie");) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int catid = result.getInt("catid");
                String catlib = result.getString("catlib");
                Categorie cat = new Categorie(catid, catlib);
                listcat.add(cat);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listcat;
    }
public Categorie getCategoryById(int id) {
    Categorie cat = null;
    try {
        Connection cnx = getConnection();
        PreparedStatement preparedStatement = cnx.prepareStatement("SELECT catid, catlib FROM categorie WHERE catid=?");
        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            String catlib = rs.getString("catlib");
            cat = new Categorie(id, catlib);
        }
    } catch (SQLException ex) {
        System.out.println(ex);
    }
    return cat;
}
public boolean updateCategorie(Categorie cat) throws SQLException {
    boolean rowUpdated;
    try (PreparedStatement preparedStatement = cnx.prepareStatement("update categorie set catlib=? where catid=?");) {
        preparedStatement.setString(1, cat.getCatlib());
        preparedStatement.setInt(2, cat.getCatid());
        rowUpdated = preparedStatement.executeUpdate() > 0;
    }
    return rowUpdated;
}

public void setCategories(List<Categorie> categories) {
    this.categories = categories;
}

public List<Categorie> getCategories() {
    return categories;
}
public List<Categorie> searchCategories(String searchQuery) {
    List<Categorie> categories = new ArrayList<>();
    Connection cnx = getConnection(); // Implement your getConnection method

    try {
        PreparedStatement pr = cnx.prepareStatement("SELECT * FROM categorie WHERE catlib LIKE ?");
        pr.setString(1, "%" + searchQuery + "%");
        ResultSet rs = pr.executeQuery();

        while (rs.next()) {
            int catid = rs.getInt("catid");
            String catlib = rs.getString("catlib");
            Categorie cat= new Categorie (catid, catlib);
            categories.add(cat);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } 

    return categories;
}


}
