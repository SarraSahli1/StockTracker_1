package servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entity.Fournisseur;
import Entity.FournisseurDAO;
import Entity.Produit;
import Entity.ProduitDAO;
import Entity.Transaction;
import Entity.TransactionDAO;

public class TransactionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TransactionDAO tdao = new TransactionDAO();
    private FournisseurDAO fournisseurDAO = new FournisseurDAO();
    private ProduitDAO produitDAO = new ProduitDAO();

    public TransactionServlet() {
        super();
        tdao = new TransactionDAO();
    }

protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String action = request.getParameter("action");
    String idParam = request.getParameter("id");
    String searchQuery = request.getParameter("searchQuery"); // Add this line

    if ("TransactionAdd".equals(action)) {
        // Fetch the lists of suppliers and products and set them as attributes
        List<Fournisseur> fournisseurs = fournisseurDAO.selectallFournisseurs();
        List<Produit> produits = produitDAO.getAllProduits();
        request.setAttribute("fournisseurs", fournisseurs);
        request.setAttribute("produits", produits);

        // Forward to TransactionAdd.jsp
        request.getRequestDispatcher("TransactionAdd.jsp").forward(request, response);
    
    } else if ("TransactionEdit".equals(action) && idParam != null) {
        try {
            int transactionId = Integer.parseInt(idParam);
            Transaction transaction = tdao.getTransactionById(transactionId);
            List<Fournisseur> fournisseurs = fournisseurDAO.selectallFournisseurs();
            List<Produit> produits = produitDAO.getAllProduits();

            if (transaction != null) {
                // Set the transaction as an attribute
                request.setAttribute("transaction", transaction); // Use "transaction" instead of "t"

                // Fetch the lists of suppliers and products and set them as attributes
                request.setAttribute("fournisseurs", fournisseurs);
                request.setAttribute("produits", produits);

                // Forward to TransactionEdit.jsp
                request.getRequestDispatcher("TransactionEdit.jsp").forward(request, response);
            } else {
                // Handle case when transaction is not found
                response.sendRedirect("TransactionServlet?page=TransactionList");
            }
        } catch (NumberFormatException e) {
            // Handle invalid ID parameter
            response.sendRedirect("TransactionServlet?page=TransactionList");
        }
    }else if ("search".equals(action) && searchQuery != null) { // Add this condition
        // Perform combined search by product and supplier
        List<Transaction> transactions = tdao.searchByProductOrSupplier(searchQuery);
        System.out.println("Search query: " + searchQuery); // Add this line
        System.out.println("Retrieved transactions: " + transactions); // Add this line
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("TransactionList.jsp").forward(request, response);
    } 
    else {
        // Fetch the list of transactions and set it as an attribute
        List<Transaction> transactions = tdao.selectAllTransactions();
        request.setAttribute("transactions", transactions);

        // Forward to TransactionList.jsp
        request.getRequestDispatcher("TransactionList.jsp").forward(request, response);
    }
}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("TransactionAdd".equals(action)) {
            try {
                String dateStr = request.getParameter("tdate");
                int tentree = Integer.parseInt(request.getParameter("tentree"));
                float tprixtotal = Float.parseFloat(request.getParameter("tprixtotal"));
                int tprodId = Integer.parseInt(request.getParameter("tprod"));
                int tfournId = Integer.parseInt(request.getParameter("tfourn"));

                // Parse the date string into a Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date tdate = new Date(dateFormat.parse(dateStr).getTime());

                // Create a new Transaction object with the provided data
                Transaction newTransaction = new Transaction();
                newTransaction.setTdate(tdate);
                newTransaction.setTentree(tentree);
                newTransaction.setTprixtotal(tprixtotal);

                // Retrieve the related Product and Fournisseur objects using their DAOs
                ProduitDAO produitDAO = new ProduitDAO();
                FournisseurDAO fournisseurDAO = new FournisseurDAO();
                Produit tprod = produitDAO.getProduitById(tprodId);
                Fournisseur tfourn = fournisseurDAO.getFournisseurById(tfournId);

                newTransaction.setTprod(tprod);
                newTransaction.setTfourn(tfourn);

                // Code to add the new transaction to the database
                tdao.addTransaction(newTransaction);

                // Redirect the user back to the list of transactions or display a success message
                response.sendRedirect("TransactionServlet?page=TransactionList");
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle the parsing error as needed
            }
        } else if ("update".equals(action)) {
            try {
                int TId = Integer.parseInt(request.getParameter("id"));
                String dateStr = request.getParameter("tdate");
                int tentree = Integer.parseInt(request.getParameter("tentree"));
                float tprixtotal = Float.parseFloat(request.getParameter("tprixtotal"));
                int tprodId = Integer.parseInt(request.getParameter("tprod"));
                int tfournId = Integer.parseInt(request.getParameter("tfourn"));

                // Parse the date string into a Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date tdate = new Date(dateFormat.parse(dateStr).getTime());

                // Fetch the existing transaction data using the getTransactionById method
                Transaction existingTransaction = tdao.getTransactionById(TId);

                // If the transaction with the provided ID exists, update it
                if (existingTransaction != null) {
                    existingTransaction.setTdate(tdate);
                    existingTransaction.setTentree(tentree);
                    existingTransaction.setTprixtotal(tprixtotal);

                    // Retrieve the related Product and Fournisseur objects using their DAOs
                    ProduitDAO produitDAO = new ProduitDAO();
                    FournisseurDAO fournisseurDAO = new FournisseurDAO();
                    Produit tprod = produitDAO.getProduitById(tprodId);
                    Fournisseur tfourn = fournisseurDAO.getFournisseurById(tfournId);

                    existingTransaction.setTprod(tprod);
                    existingTransaction.setTfourn(tfourn);

                    // Code to update the transaction in the database
                    tdao.updateTransaction(existingTransaction);

                    // Redirect the user back to the list of transactions or display a success message
                    response.sendRedirect("TransactionServlet?page=TransactionList");
                } 
            } catch (NumberFormatException | ParseException e) {
                e.printStackTrace();
                // Handle the number format or parsing error as needed
            }
        } else if ("delete".equals(action)) {
            int TId = Integer.parseInt(request.getParameter("id"));
            tdao.deleteTransaction(TId);

            // Redirect the user back to the list of transactions or display a success message
            response.sendRedirect("TransactionServlet?page=TransactionList");
        }else {
            response.sendRedirect("TransactionServlet");
        }
    }
}
