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
import Entity.Transaction;

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
        String searchQuery = request.getParameter("searchQuery");

        if ("ProduitAdd".equals(action)) {
            List<Categorie> categories = categorieDAO.selectallCategories();
            request.setAttribute("categories", categories);

            // Assuming that you have predefined lists of taille and couleur options
            List<String> tailleOptions = Arrays.asList("Small", "Medium", "Large");
            List<String> couleurOptions = Arrays.asList("Red", "Blue", "Green");

            request.setAttribute("tailleOptions", tailleOptions);
            request.setAttribute("couleurOptions", couleurOptions);

            // Forward to ProduitAdd.jsp
            request.getRequestDispatcher("ProduitAdd.jsp").forward(request, response);
        } else if ("ProduitEdit".equals(action) && idParam != null) {
            try {
                int productId = Integer.parseInt(idParam);
                Produit produit = produitDAO.getProduitById(productId);
                List<Categorie> categories = categorieDAO.selectallCategories();
                
                if (produit != null) {
                    // Fetch the list of categories and set it as an attribute
                    request.setAttribute("categories", categories);

                    // Set the product as an attribute
                    request.setAttribute("produit", produit);

                    // Assuming that you have predefined lists of taille and couleur options
                    List<String> tailleOptions = Arrays.asList("Small", "Medium", "Large");
                    List<String> couleurOptions = Arrays.asList("Red", "Blue", "Green");

                    request.setAttribute("tailleOptions", tailleOptions);
                    request.setAttribute("couleurOptions", couleurOptions);

                    // Forward to ProduitEdit.jsp
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
            request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
        }
        else if ("filterByPrice".equals(action)) {
            String minPriceStr = request.getParameter("minPrice");
            String maxPriceStr = request.getParameter("maxPrice");

            float minPrice = Float.parseFloat(minPriceStr);
            float maxPrice = Float.parseFloat(maxPriceStr);

            List<Produit> produits = produitDAO.filterProduitsByPrice(minPrice, maxPrice);
            request.setAttribute("produits", produits);

            // Forward to TransactionList.jsp
            request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
        }
        else if ("filterByColor".equals(action)) {
            List<String> couleurOptions = produitDAO.getDistinctColors();
            System.out.println("Distinct Colors: " + couleurOptions);

            request.setAttribute("couleurOptions", couleurOptions);

            String[] selectedColorsArray = request.getParameterValues("color");
            List<String> selectedColors = Arrays.asList(selectedColorsArray);

            List<Produit> filteredProduits = produitDAO.filterProduitsByColor(selectedColors);

            request.setAttribute("produits", filteredProduits);
            request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
        }else if ("filterByTaille".equals(action)) {
            List<String> tailleOptions = produitDAO.getDistinctTailles();
            System.out.println("Distinct Tailles: " + tailleOptions);

            request.setAttribute("tailleOptions", tailleOptions);

            String[] selectedTaillesArray = request.getParameterValues("taille");
            List<String> selectedTailles = Arrays.asList(selectedTaillesArray);

            List<Produit> filteredProduits = produitDAO.filterProduitsByTaille(selectedTailles);

            request.setAttribute("produits", filteredProduits);
            request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
        }
        else if ("filterByCategorie".equals(action)) {
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            List<Produit> filteredProduits = produitDAO.filterProduitsByCategorie(categoryId);
            request.setAttribute("produits", filteredProduits);
            request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
        }

        
        else {
            // Fetch the list of produits and set it as an attribute
            List<Produit> produits = produitDAO.getAllProduits();
            request.setAttribute("produits", produits);
            List<String> couleurOptions = produitDAO.getDistinctColors();
            request.setAttribute("couleurOptions", couleurOptions);
            List<String> tailleOptions = produitDAO.getDistinctTailles();
            request.setAttribute("tailleOptions", tailleOptions);
            List<Categorie> categories = categorieDAO.selectallCategories();
            request.setAttribute("categories", categories);
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

            String[] selectedTaillesArray = request.getParameterValues("taille");
            String[] selectedCouleursArray = request.getParameterValues("couleur");
            List<String> selectedTailles = Arrays.asList(selectedTaillesArray);
            List<String> selectedCouleurs = Arrays.asList(selectedCouleursArray);
            if (request.getParameter("tailleOther") != null) {
                String tailleOtherValue = request.getParameter("tailleOtherValue");
                if (!tailleOtherValue.isEmpty()) {
                    List<String> newTailles = new ArrayList<>(selectedTailles);
                    newTailles.add(tailleOtherValue);
                    selectedTailles = newTailles;
                }
            }

            if (request.getParameter("couleurOther") != null) {
                String couleurOtherValue = request.getParameter("couleurOtherValue");
                if (!couleurOtherValue.isEmpty()) {
                    List<String> newCouleurs = new ArrayList<>(selectedCouleurs);
                    newCouleurs.add(couleurOtherValue);
                    selectedCouleurs = newCouleurs;
                }
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

            Produit produit = new Produit(prodlib, proddesc, prodprix, prodquant, imagefilename, selectedTailles, selectedCouleurs,
                    categorie);

            produitDAO.ajouterProduit(produit, selectedTailles, selectedCouleurs);

            response.sendRedirect("ProduitServlet?page=ProduitList");
        } else if ("update".equals(action)) {
        	try {
                int prodId = Integer.parseInt(request.getParameter("id"));
                String prodlib = request.getParameter("prodlib");
                String proddesc = request.getParameter("proddesc");

                String[] selectedTaillesArray = request.getParameterValues("taille");
                String[] selectedCouleursArray = request.getParameterValues("couleur");
                List<String> selectedTailles = Arrays.asList(selectedTaillesArray);
                List<String> selectedCouleurs = Arrays.asList(selectedCouleursArray);
                if (request.getParameter("tailleOther") != null) {
                    String tailleOtherValue = request.getParameter("tailleOtherValue");
                    if (!tailleOtherValue.isEmpty()) {
                        List<String> newTailles = new ArrayList<>(selectedTailles);
                        newTailles.add(tailleOtherValue);
                        selectedTailles = newTailles;
                    }
                }

                if (request.getParameter("couleurOther") != null) {
                    String couleurOtherValue = request.getParameter("couleurOtherValue");
                    if (!couleurOtherValue.isEmpty()) {
                        List<String> newCouleurs = new ArrayList<>(selectedCouleurs);
                        newCouleurs.add(couleurOtherValue);
                        selectedCouleurs = newCouleurs;
                    }
                }
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

                Produit produit = new Produit(prodId, prodlib, proddesc, imagefilename, prodprix, prodquant, selectedTailles, selectedCouleurs, categorie);
                produitDAO.modifierProduit(produit, selectedTailles, selectedCouleurs);

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
        
        }
        else if ("filterByPrice".equals(action)) {
        	try {
                float minPrice = Float.parseFloat(request.getParameter("minPrice"));
                float maxPrice = Float.parseFloat(request.getParameter("maxPrice"));
                
                List<Produit> filteredProduits = produitDAO.filterProduitsByPrice(minPrice, maxPrice);
                
                request.setAttribute("filteredProduits", filteredProduits);
                // Forward to the same JSP for displaying the filtered results
                request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // Handle invalid input (minPrice or maxPrice not valid floats)
                response.sendRedirect("ProduitServlet");
            }}
        else if ("filterByColor".equals(action)) {
            List<String> couleurOptions = produitDAO.getDistinctColors();
            System.out.println("Distinct Colors: " + couleurOptions);

            request.setAttribute("couleurOptions", couleurOptions);

            String[] selectedColorsArray = request.getParameterValues("color");
            List<String> selectedColors = Arrays.asList(selectedColorsArray);

            List<Produit> filteredProduits = produitDAO.filterProduitsByColor(selectedColors);

            request.setAttribute("produits", filteredProduits); // Set the attribute name to "produits"
            request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
        }
        else if ("filterByTaille".equals(action)) {
            List<String> tailleOptions = produitDAO.getDistinctTailles();
            System.out.println("Distinct tailles: " + tailleOptions);

            request.setAttribute("tailleOptions", tailleOptions);

            String[] selectedTaillesArray = request.getParameterValues("taille");
            List<String> selectedTailles = Arrays.asList(selectedTaillesArray);

            List<Produit> filteredProduits = produitDAO.filterProduitsByTaille(selectedTailles);

            request.setAttribute("produits", filteredProduits); // Set the attribute name to "produits"
            request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
        }
        else if ("filterByCategorie".equals(action)) {
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            List<Produit> filteredProduits = produitDAO.filterProduitsByCategorie(categoryId);
            request.setAttribute("produits", filteredProduits);
            
            request.getRequestDispatcher("ProduitList.jsp").forward(request, response);
        }
        else {

            response.sendRedirect("ProduitServlet");
        }
    }
    
    
}
