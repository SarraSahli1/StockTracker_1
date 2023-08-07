package Entity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FournisseurDAO {
	    private static String url = "jdbc:mysql://localhost:3306/stocktracker";
	    private static String login = "root";
	    private static String pwd = "";
	    private static Connection cnx = null;

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

	    public void addfourn(Fournisseur f) {
	        Connection cnx = getConnection();

	        try {
	            PreparedStatement pr = cnx.prepareStatement("INSERT INTO fournisseur VALUES (NULL,?,?,?,?)");
	            pr.setString(1, f.getFnom());
	            pr.setString(2, f.getFtel());
	            pr.setString(3, f.getFadresse());
	            pr.setInt(4, f.getFdelais());
	            
	            pr.execute();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    public void deletefournisseur(int id) {
	        Connection cnx = getConnection();

	        try {
	            PreparedStatement pr = cnx.prepareStatement("DELETE FROM fournisseur WHERE fid=?");
	            pr.setInt(1, id);
	            pr.execute();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public List<Fournisseur> selectallFournisseurs() {
	        List<Fournisseur> listfr = new ArrayList<>();
	        Connection cnx = getConnection(); // Assign the connection to cnx attribute

	        try (PreparedStatement preparedStatement = cnx.prepareStatement("select * from fournisseur");) {
	            ResultSet result = preparedStatement.executeQuery();
	            while (result.next()) {
	                int fid = result.getInt("fid");
	                String fnom = result.getString("fnom");
	                String ftel = result.getString("ftel");
	                String fadresse = result.getString("fadresse");
	                int fdelais = result.getInt("fdelais");

	                Fournisseur f = new Fournisseur(fid, fnom,ftel,fadresse,fdelais );
	                listfr.add(f);
	            }
	        } catch (SQLException ex) {
	            System.out.println(ex);
	        }
	        return listfr;
	    }
	public Fournisseur getFournisseurById(int id) {
		Fournisseur f = null;
	    try {
	        Connection cnx = getConnection();
	        PreparedStatement preparedStatement = cnx.prepareStatement("SELECT fid, fnom, ftel, fadresse, fdelais FROM fournisseur WHERE fid=?");
	        preparedStatement.setInt(1, id);

	        ResultSet rs = preparedStatement.executeQuery();
	        if (rs.next()) {
	            String fnom = rs.getString("fnom");
	            String ftel = rs.getString("ftel");
	            String fadresse = rs.getString("fadresse");
	            int fdelais = rs.getInt("fdelais");

	            f = new Fournisseur(id, fnom, ftel, fadresse, fdelais);
	        }
	    } catch (SQLException ex) {
	        System.out.println(ex);
	    }
	    return f;
	}
	public boolean updateFournisseur(Fournisseur f) throws SQLException {
	    boolean rowUpdated;
	    try (PreparedStatement preparedStatement = cnx.prepareStatement("update fournisseur set fnom=?, ftel=?, fadresse=?, fdelais=? where fid=?");) {
	        preparedStatement.setString(1, f.getFnom());
	        preparedStatement.setString(2, f.getFtel());
	        preparedStatement.setString(3, f.getFadresse());
	        preparedStatement.setInt(4, f.getFdelais());
	        preparedStatement.setInt(5, f.getFid()); // Add the fid to the query
	        rowUpdated = preparedStatement.executeUpdate() > 0;
	    }
	    return rowUpdated;
	}
	
	public List<Fournisseur> searchFournisseur(String searchQuery) {
	    List<Fournisseur> fournisseurs = new ArrayList<>();
	    Connection cnx = getConnection();

	    try {
	        PreparedStatement pr = cnx.prepareStatement("SELECT * FROM fournisseur WHERE fnom LIKE ?");
	        pr.setString(1, "%" + searchQuery + "%");
	        ResultSet rs = pr.executeQuery();

	        while (rs.next()) {
	            int fid = rs.getInt("fid");
	            String fnom = rs.getString("fnom");
	            String ftel = rs.getString("ftel");
	            String fadresse = rs.getString("fadresse");
	            int fdelais = rs.getInt("fdelais");	            
	            Fournisseur f= new Fournisseur (fid, fnom, ftel, fadresse, fdelais);
	            fournisseurs.add(f);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } 

	    return fournisseurs;
	}

	
	}


