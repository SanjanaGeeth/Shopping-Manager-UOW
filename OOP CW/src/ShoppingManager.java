public interface ShoppingManager {
    // Add a new product to the system
    void addProduct();

    // Delete a product from the system
    void deleteProduct();

    // Print the list of products in the system
    void printProductList();

    // Save the product list in a file
    void saveProducts();

    // Read the product list from a file
    void readProducts();
}