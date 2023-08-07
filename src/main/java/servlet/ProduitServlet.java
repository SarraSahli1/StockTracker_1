package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Entity.Categorie;
import Entity.CategorieDAO;
import Entity.Produit;
import Entity.ProduitDAO;

@MultipartConfig
public class ProduitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProduitDAO produitDAO = new ProduitDAO();
    private CategorieDAO categorieDAO = new CategorieDAO();

    public ProduitServlet() {
        super();
        produitDAO = new ProduitDAO();
    }
    
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String action = request.getParameter("action");
    String idParam = request.getParameter("id");
    String searchQuery = request.getParameter("searchQuery"); // Add this line


    if ("ProduitAdd".equals(action)) {
        // Fetch the list of categories and set it as an attribute
        List<Categorie> categories = categorieDAO.selectallCategories();
        request.setAttribute("categories", categories);

        // Forward to ProduitAdd.jsp
        request.getRequestDispatcher("ProduitAdd.jsp").forward(request, response);
    } else if ("ProduitEdit".equals(action) && idParam != null) {
        try {
            int productId = Integer.parseInt(idParam);
            Produit produit = produitDAO.getProduitById(productId);
            List<Categorie> categories = categorieDAO.selectallCategories();
        	System.out.println("i'm invoked doget");
            System.out.println("ID Parameter: " + idParam);

            if (produit != null) {
                // Fetch the list of categories and set it as an attribute

                // Set the product as an attribute

                // Forward to ProduitEdit.jsp
                request.setAttribute("produit", produit);
                request.setAttribute("categories", categories); // Set the list of categories
                request.getRequestDispatcher("ProduitEdit.jsp").forward(request, response);

            } else {
                // Handle case when product is not found
                response.sendRedirect("ProduitServlet?page=ProduitList");
            }
        } catch (NumberFormatException e) {
            // Handle invalid ID parameter
            response.sendRedirect("ProduitServlet?page=ProduitList");
        }
    } else if ("search".equals(action) && searchQuery != null && !searchQuery.isEmpty()) {
        // Search for products based on the search query
        List<Produit> produits = produitDAO.searchProducts(searchQuery);
        request.setAttribute("produits", produits);

        // Forward to ProduitList.jsp to display the search results
        request.getRequestDispatcher("ProduitList.jsp").forward(request, response);}
    
//    }if ("filter".equals(action)) {
//        // Fetch the list of available sizes from your database
//        List<String> sizes = fetchAvailableSizesFromDatabase();
//        request.setAttribute("sizes", sizes);
//        
//        // Perform the filtering based on selected size
//        String selectedSize = request.getParameter("filterTaille");
//        List<Produit> filteredProducts = filterProductsBySize(selectedSize);
//        request.setAttribute("produits", filteredProducts);}
    else {
        // Fetch the list of produits and set it as an attribute
        List<Produit> produits = produitDAO.getAllProduits();
        request.setAttribute("produits", produits);

        // Forward to ProduitList.jsp
        request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
    }
}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("ProduitAdd".equals(action)) {
            String prodlib = request.getParameter("prodlib");
            String proddesc = request.getParameter("proddesc");

            String tailleParam = request.getParameter("taille");
            String couleurParam = request.getParameter("couleur");

            List<String> taille = new ArrayList<>();
            List<String> couleur = new ArrayList<>();

            if (tailleParam != null && !tailleParam.isEmpty()) {
                taille = Arrays.asList(tailleParam.split("\\s*,\\s*"));
            }

            if (couleurParam != null && !couleurParam.isEmpty()) {
                couleur = Arrays.asList(couleurParam.split("\\s*,\\s*"));
            }

            float prodprix = Float.parseFloat(request.getParameter("prodprix"));
            int prodquant = Integer.parseInt(request.getParameter("prodquant"));

            Part file = request.getPart("prodimg");
            String imagefilename = file.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("/") + "images" + File.separator + imagefilename;
            System.out.println(uploadPath);
            try {
                InputStream is = file.getInputStream();
                FileOutputStream fos = new FileOutputStream(uploadPath);
                int bytesRead;
                byte[] buffer = new byte[8192];

                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            int prodcatId = Integer.parseInt(request.getParameter("prodcat"));
            Categorie categorie = categorieDAO.getCategoryById(prodcatId);

            Produit produit = new Produit(prodlib, proddesc, prodprix, prodquant, imagefilename, taille, couleur,
                    categorie);

            produitDAO.ajouterProduit(produit);

            response.sendRedirect("ProduitServlet?page=ProduitList");
        } else if ("update".equals(action)) {
            try {
                int prodId = Integer.parseInt(request.getParameter("id"));
                String prodlib = request.getParameter("prodlib");
                String proddesc = request.getParameter("proddesc");
                String tailleParam = request.getParameter("taille");
                String couleurParam = request.getParameter("couleur");

                List<String> taille = Arrays.asList(tailleParam.split("\\s*,\\s*"));
                List<String> couleur = Arrays.asList(couleurParam.split("\\s*,\\s*"));

                float prodprix = Float.parseFloat(request.getParameter("prodprix"));
                int prodquant = Integer.parseInt(request.getParameter("prodquant"));

                Part file = request.getPart("prodimg");
                String imagefilename = file.getSubmittedFileName();
                String uploadPath = "C:" + File.separator + "uploads" + File.separator + imagefilename;

                try {
                    InputStream is = file.getInputStream();
                    FileOutputStream fos = new FileOutputStream(uploadPath);
                    int bytesRead;
                    byte[] buffer = new byte[8192];

                    while ((bytesRead = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }

                    fos.close();
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int prodcatId = Integer.parseInt(request.getParameter("prodcat"));
                Categorie categorie = categorieDAO.getCategoryById(prodcatId);

                Produit produit = new Produit(prodId, prodlib, proddesc,imagefilename, prodprix, prodquant, taille, couleur, categorie);
                produitDAO.modifierProduit(produit);

                response.sendRedirect("ProduitServlet?page=ProduitList");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            produitDAO.deleteProduit(id);

            response.sendRedirect("ProduitServlet?page=ProduitList");
        } else if ("search".equals(action)) { // Add a new condition for search action
            String searchQuery = request.getParameter("searchQuery");
            List<Produit> produits = produitDAO.searchProducts(searchQuery); // Call your search method
            request.setAttribute("produits", produits);
            request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
        
        } else {
            response.sendRedirect("ProduitServlet");
        }
    }
}
