package backend.server;

import backend.logic.*;
import shared.Cliente;
import shared.Linea;
import shared.Producto;
import shared.Categoria;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final InvoiceManager invoiceManager;
    private final CashierManager cashierManager;
    private final StatisticsManager statisticsManager;
    private final ClientManager clientManager;
    private final ProductManager productManager;
    private final CategoryManager categoryManager;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.invoiceManager = new InvoiceManager();
        this.cashierManager = new CashierManager();
        this.statisticsManager = new StatisticsManager();
        this.clientManager = new ClientManager();
        this.productManager = new ProductManager();
        this.categoryManager = new CategoryManager();
    }

    @Override
    public void run() {
        try (
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                PrintWriter writer = new PrintWriter(output, true)
        ) {
            String request;
            while ((request = reader.readLine()) != null) {
                // Procesar la solicitud recibida
                String response = processRequest(request);
                writer.println(response);  // Enviar la respuesta al Frontend
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String processRequest(String request) {
        // Dividir la solicitud en acción y parámetros
        String[] parts = request.split(" ");
        String action = parts[0];

        switch (action) {
            case "GET_INVOICES":
                return getInvoices();
            case "GET_CASHIERS":
                return getCashiers();
            case "GET_STATISTICS":
                return getStatistics();
            case "CREATE_INVOICE":
                return createInvoice(parts);
            case "UPDATE_CASHIER":
                return updateCashier(parts);
            case "GET_CLIENTS":
                return getClients();
            case "ADD_CLIENT":
                return addClient(parts);
            case "GET_PRODUCTS":
                return getProducts();
            case "ADD_PRODUCT":
                return addProduct(parts);
            case "GET_CATEGORIES":
                return getCategories();
            case "ADD_CATEGORY":
                return addCategory(parts);
            default:
                return "Unknown request";
        }
    }

    private String getInvoices() {
        return invoiceManager.getAllInvoicesAsString(); // Asume que existe un método que retorna las facturas en formato String
    }

    private String getCashiers() {
        return cashierManager.getAllCashiersAsString();
    }

    private String getStatistics() {
        return statisticsManager.getStatisticsAsString();
    }

    private String createInvoice(String[] parts) {
        String clientId = parts[1];
        String products = parts[2];
        double total = Double.parseDouble(parts[3]);

        // Convertir el String products en una List<Linea>
        List<Linea> lineas = parseProducts(products);

        boolean success = invoiceManager.createInvoice(clientId, lineas, total);
        return success ? "Invoice created successfully" : "Failed to create invoice";
    }

    private List<Linea> parseProducts(String products) {
        List<Linea> lineas = new ArrayList<>();
        String[] productArray = products.split(",");

        for (String productString : productArray) {
            String[] productData = productString.split("-");
            String productId = productData[0];
            int cantidad = Integer.parseInt(productData[1]);
            double descuento = Double.parseDouble(productData[2]);

            // Crear el objeto Producto y Linea
            Producto producto = new Producto();
            producto.setCodigo(productId);

            Linea linea = new Linea(producto, cantidad, descuento, productId);
            lineas.add(linea);
        }
        return lineas;
    }

    private String updateCashier(String[] parts) {
        String cashierId = parts[1];
        String name = parts[2];
        String shift = parts[3];

        boolean success = cashierManager.updateCashier(cashierId, name, shift);
        return success ? "Cashier updated successfully" : "Failed to update cashier";
    }

    // Métodos de Client
    private String getClients() {
        return clientManager.getAllClients().toString();
    }

    private String addClient(String[] parts) {
        String clientId = parts[1];
        String name = parts[2];
        String phone = parts[3];
        String email = parts[4];
        double discount = Double.parseDouble(parts[5]);

        Cliente cliente = new Cliente(clientId, name, phone, email, discount);
        boolean success = clientManager.addClient(cliente);
        return success ? "Client added successfully" : "Failed to add client";
    }

    // Métodos de Product
    private String getProducts() {
        return productManager.getAllProducts().toString();
    }

    private String addProduct(String[] parts) {
        String productId = parts[1];
        String description = parts[2];
        String unit = parts[3];
        double price = Double.parseDouble(parts[4]);
        int stock = Integer.parseInt(parts[5]);

        Producto producto = new Producto(productId, description, unit, price, stock, null);
        boolean success = productManager.addProduct(producto);
        return success ? "Product added successfully" : "Failed to add product";
    }

    // Métodos de Category
    private String getCategories() {
        return categoryManager.getAllCategories().toString();
    }

    private String addCategory(String[] parts) {
        String categoryId = parts[1];
        String name = parts[2];

        Categoria categoria = new Categoria(categoryId, name);
        boolean success = categoryManager.addCategory(categoria);
        return success ? "Category added successfully" : "Failed to add category";
    }
}
