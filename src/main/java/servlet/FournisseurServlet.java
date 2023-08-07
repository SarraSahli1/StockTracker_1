package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entity.Fournisseur;
import Entity.FournisseurDAO;
import Entity.Produit;


public class FournisseurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private FournisseurDAO fournDAO;

    public FournisseurServlet() {
        super();
        fournDAO = new FournisseurDAO();
    }      
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String searchQuery = request.getParameter("searchQuery"); // Add this line

        if (action != null && action.equals("update")) {
            int id = Integer.parseInt(request.getParameter("id")); // 
            Fournisseur f = fournDAO.getFournisseurById(id); // 
            request.setAttribute("fournisseur", f);
            request.getRequestDispatcher("FournisseurEdit.jsp").forward(request, response);
        }
            else if ("search".equals(action) && searchQuery != null && !searchQuery.isEmpty()) {
                // Search for products based on the search query
                List<Fournisseur> fournisseurs = fournDAO.searchFournisseur(searchQuery);
                request.setAttribute("fournisseurs", fournisseurs);

                // Forward to ProduitList.jsp to display the search results
                request.getRequestDispatcher("FournisseurList.jsp").forward(request, response);
            
            
        } else {
            List<Fournisseur> fournisseurs = fournDAO.selectallFournisseurs();
            System.out.println("fournisseurs size: " + fournisseurs.size());
            request.setAttribute("fournisseurs", fournisseurs);
            request.getRequestDispatcher("FournisseurList.jsp").forward(request, response);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                	addFournisseur(request,response);
                    break;
                case "delete":
                	deleteFournisseur(request,response);
                    break;
                case "update":
                	updateFournisseur(request,response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/FournisseurServlet");
            }
        }
        
    }  
    private void addFournisseur(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fnom = request.getParameter("fnom");
        String ftel = request.getParameter("ftel");
        String fadresse = request.getParameter("fadresse");
        int fdelais = Integer.parseInt(request.getParameter("fdelais"));
        Fournisseur newFournisseur = new Fournisseur(fnom,ftel,fadresse,fdelais);
        fournDAO.addfourn(newFournisseur);
        response.sendRedirect(request.getContextPath() + "/FournisseurServlet");
    }
    
    private void deleteFournisseur(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int fId = Integer.parseInt(request.getParameter("id"));
        fournDAO.deletefournisseur(fId);
    	List<Fournisseur> fournisseurs = fournDAO.selectallFournisseurs();
    	request.setAttribute("fournisseurs", fournisseurs);
    	request.getRequestDispatcher("FournisseurList.jsp").forward(request, response);
    }  
    
    private void updateFournisseur(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int fid = Integer.parseInt(request.getParameter("id"));
        String fnom = request.getParameter("fnom");
        String ftel = request.getParameter("ftel");
        String fadresse = request.getParameter("fadresse");
        int fdelais = Integer.parseInt(request.getParameter("fdelais"));

        // Create a new Fournisseur object with the updated information
        Fournisseur updatedFournisseur = new Fournisseur(fid, fnom, ftel, fadresse, fdelais);

        // Call the updateFournisseur method in the FournisseurDAO to update the fournisseur in the database
        try {
            boolean rowUpdated = fournDAO.updateFournisseur(updatedFournisseur);
            if (rowUpdated) {
                // If the update is successful, redirect the user back to the list of fournisseurs
                response.sendRedirect(request.getContextPath() + "/FournisseurServlet");
            } else {
                // If the update fails, display an error message or handle it as needed
                // For example:
                response.getWriter().println("Failed to update the fournisseur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException as needed
        }
    }
    
    
}