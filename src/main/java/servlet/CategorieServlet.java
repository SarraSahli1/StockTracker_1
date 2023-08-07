package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entity.Categorie;
import Entity.CategorieDAO;
import Entity.Produit;

public class CategorieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategorieDAO categorieDAO;

    public CategorieServlet() {
        super();
        categorieDAO = new CategorieDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FournisseurServlet doGet method called.");
        String action = request.getParameter("action");
        String searchQuery = request.getParameter("searchQuery"); // Add this line

        if (action != null && action.equals("update")) {
            int categoryId = Integer.parseInt(request.getParameter("id"));
            Categorie category = categorieDAO.getCategoryById(categoryId);
            request.setAttribute("category", category);
            request.getRequestDispatcher("CategorieEdit.jsp").forward(request, response);
            }
            else if ("search".equals(action) && searchQuery != null && !searchQuery.isEmpty()) {
                // Search for products based on the search query
                List<Categorie> categories = categorieDAO.searchCategories(searchQuery);
                request.setAttribute("categories", categories);
                // Forward to ProduitList.jsp to display the search results
                request.getRequestDispatcher("categorielist.jsp").forward(request, response);
            
            
        } else {
            List<Categorie> categories = categorieDAO.selectallCategories();
            System.out.println("Categories size: " + categories.size());
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("categorielist.jsp").forward(request, response);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addCategory(request, response);
                    break;
                case "delete":
                	deleteCategory(request, response);
                    break;
                case "update":
                    updateCategory(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/CategorieServlet");
            }
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = request.getParameter("nom");
        Categorie newCategory = new Categorie(categoryName);
        categorieDAO.add(newCategory);
        response.sendRedirect(request.getContextPath() + "/CategorieServlet");
    }
    
private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    int categoryId = Integer.parseInt(request.getParameter("id"));
    categorieDAO.delete(categoryId);
	List<Categorie> categories = categorieDAO.selectallCategories();
	request.setAttribute("categories", categories);
	request.getRequestDispatcher("categorielist.jsp").forward(request, response);
}  
private void updateCategory(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    int categoryId = Integer.parseInt(request.getParameter("id"));
    String categoryName = request.getParameter("nom");

    // Fetch the existing category data using the getCategoryById method
    Categorie existingCategory = categorieDAO.getCategoryById(categoryId);

    // If the category with the provided ID exists, update it
    if (existingCategory != null) {
        existingCategory.setCatlib(categoryName);
        try {
            categorieDAO.updateCategorie(existingCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Redirect the user back to the list of categories
    response.sendRedirect(request.getContextPath() + "/CategorieServlet");
}




}
