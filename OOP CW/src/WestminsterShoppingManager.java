
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class WestminsterShoppingManager implements ShoppingManager {
    private final ArrayList<Product> productList;
    private final Scanner input;

    public WestminsterShoppingManager() {
        productList = new ArrayList<>();
        input = new Scanner(System.in);
    }

    protected ArrayList<Product> getProductList() {
        return productList;
    }

    private String productID;
    private String productName;
    private int numberOfItems;
    private double Price;
    private String size;
    private String color;
    private String brand;
    private String warrantyPeriod;
    private Product Clothing() {
        System.out.println("----------------\nAdding a Clothing Product\n-----------------");

        addProductDetails();
        // Loop until a non-empty size is provided
        size="";
        while (size.isEmpty()) {
            System.out.print("Enter Size: ");
            size = input.nextLine();
            if (size.isEmpty()) {
                System.out.println("Size cannot be empty");
            }
        }

        // Loop until a non-empty color is provided
        color="";
        while (color.isEmpty()) {
            System.out.print("Enter Color: ");
            color = input.nextLine();
            if (color.isEmpty()) {
                System.out.println("Color cannot be empty");
            }
        }
        return new Clothing(productID, productName, numberOfItems, Price, size, color);
    }


    private Product electric() {
        System.out.println("----------------\nAdding an Electronic Product\n-----------------");
        addProductDetails();
        brand = "";
        System.out.print("Enter Brand Name: ");
        while (brand.isEmpty()) {
            brand = input.nextLine();
            if (brand.isEmpty()) {
                System.out.println("Value cannot be empty");
                System.out.print("Enter Brand Name: ");
            }
        }
        warrantyPeriod = "";
        System.out.print("Enter Warranty Period: ");
        while (warrantyPeriod.isEmpty()) {
            warrantyPeriod = input.nextLine();
            if (warrantyPeriod.isEmpty()) {
                System.out.println("Value cannot be empty");
                System.out.print("Enter Warranty Period: ");
            }
        }
        return new Electronics(productID, productName, numberOfItems, Price, brand, warrantyPeriod);
    }

    private void addProductDetails() {
        System.out.print("Enter Product ID: ");
        productID = input.nextLine();
        while (productID.isEmpty()) {
            System.out.println("Value cannot be empty");
            System.out.print("Enter Product ID: ");
            productID = input.nextLine();
        }

        System.out.print("Enter Product Name: ");
        productName = input.nextLine();
        while (productName.isEmpty()) {
            System.out.println("Value cannot be empty");
            System.out.print("Enter Product Name: ");
            productName = input.nextLine();
        }

        numberOfItems = 0; // Initialize the variable with a default value
        while (numberOfItems <= 0) {
            System.out.print("Enter Number of Items: ");
            try {
                numberOfItems = Integer.parseInt(input.nextLine());
                if (numberOfItems <= 0) {
                    System.out.println("Value should be positive.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Value cannot be empty");
            }
        }

        Price = 0.0; // Initialize the variable with a default value
        while (Price <= 0) {
            System.out.print("Enter Price of Product: ");
            try {
                Price = Double.parseDouble(input.nextLine());
                if (Price <= 0) {
                    System.out.println("Value should be positive.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Value cannot be empty");
            }
        }
    }

    protected String getProductType(Product product) {
        StringBuilder productType = new StringBuilder();
        if (product instanceof Electronics) {
            productType.append("Product type: Electronics");
            return productType.toString();
        } else if (product instanceof Clothing) {
            productType.append("Product type: Clothing");
            return productType.toString();
        }
        return null;
    }
    @Override
    public void addProduct() {
        while (true) {
            System.out.print(
                    "--------------------------------------\nEnter Type of Product You Want to Add\n--------------------------------------\n\n1. Electronic Product\n2. Clothing Product\n\nEnter a Number:");

            try {
                String type = input.nextLine();

                if (productList.size() < 50) {
                    if (type.equals("1")) {
                        Electronics newProduct = (Electronics) electric();
                        productList.add(newProduct);
                        System.out.println("--------------------------------\n" + newProduct.getProductID()
                                + " Product Added successfully\n--------------------------------");
                        break;
                    } else if (type.equals("2")) {
                        Clothing newProduct = (Clothing) Clothing();
                        productList.add(newProduct);
                        System.out.println("--------------------------------\n" + newProduct.getProductID()
                                + " Product Added successfully\n--------------------------------");
                        break;
                    } else {
                        System.out.println("\nValue cannot be empty");
                    }
                } else {
                    System.out.println("Cannot add more products. Product limit reached.");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number (1/2).");
                input.nextLine(); // Consume the invalid input
            }
        }
    }

    @Override
    public void deleteProduct() {
        System.out.print("Enter productID to delete: ");
        String deleteProductID = input.nextLine();

        boolean found = false;
        for (int i = 0; i < productList.size(); i++) {
            if (deleteProductID.equals(productList.get(i).getProductID())) {
                found = true;
                productList.remove(i);
                System.out.println("\nProduct removed successfully\n");
                System.out.println(productList.size() + " Products remaining");
                break;
            }
        }

        if (!found) {
            System.out.println("\nProduct not found\n");
        }
    }

    @Override
    public void printProductList() {
        System.out.println("------------------------------------------------");
        System.out.println(productList.size() + " Product/s available");
        System.out.println("------------------------------------------------");
        Collections.sort(productList, Comparator.comparing(Product::getProductID));

        for (Product product : productList) {
            System.out.println(formatProductInfo(product));
            System.out.println("------------------------------------------------");
        }
    }

    private String formatProductInfo(Product product) {
        StringBuilder productInfo = new StringBuilder();
        productInfo.append("");
        productInfo.append("Product ID: ").append(product.getProductID()).append("\n");
        productInfo.append("Product Name: ").append(product.getProductName()).append("\n");
        productInfo.append("No of Items: ").append(product.getAvailableItems()).append("\n");
        productInfo.append("Price: ").append(product.getPrice()).append("\n");
        if (product instanceof Electronics) {
            productInfo.append(getProductType(product)).append("\n");
            productInfo.append("Warranty period: ").append(((Electronics) product).getWarrantyPeriod()).append("\n");
            productInfo.append("Brand: ").append(((Electronics) product).getBrand()).append("\n");
        } else if (product instanceof Clothing) {
            productInfo.append(getProductType(product)).append("\n");
            productInfo.append("Size: ").append(((Clothing) product).getSize()).append("\n");
            productInfo.append("Color: ").append(((Clothing) product).getColor()).append("\n");
        }
        return productInfo.toString();
    }



    @Override
    public void saveProducts() {
        try {
            FileWriter myWriter = new FileWriter("Products.txt");
            myWriter.write(productList.size() + " Product/s available");
            myWriter.write("------------------------------------------\n");
            Collections.sort(productList, Comparator.comparing(Product::getProductID));

            for (Product product : productList) {
                myWriter.write(formatProductInfo(product));
                myWriter.write("\n------------------------------------------------\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void readProducts() {
        try (Scanner scanner = new Scanner(new File("Products.txt"))) {
            scanner.nextLine(); //skip the first line
            while (scanner.hasNextLine()) {
                String productID = scanner.nextLine().substring("Product ID:".length()).trim();
                String productName = scanner.nextLine().substring("Product Name:".length()).trim();
                int numberOfItems = Integer.parseInt(scanner.nextLine().substring("No of Items:".length()).trim());
                double price = Double.parseDouble(scanner.nextLine().substring("Price:".length()).trim());
                String productType = scanner.nextLine().substring("Product type:".length()).trim();

                Product product;
                if (productType.equals("Electronics")) {
                    String warrantyPeriod = scanner.nextLine().substring("Warranty period:".length()).trim();
                    String brand = scanner.nextLine().substring("Brand:".length()).trim();
                    product = new Electronics(productID, productName, numberOfItems, price, brand, warrantyPeriod);
                    scanner.nextLine();
                    scanner.nextLine();

                } else if (productType.equals("Clothing")) {
                    String size = scanner.nextLine().substring("Size:".length()).trim();
                    String color = scanner.nextLine().substring("Color:".length()).trim();
                    product = new Clothing(productID, productName, numberOfItems, price, size, color);
                    scanner.nextLine(); //skip the empty line
                    scanner.nextLine(); //skip the ----- line
                } else {
                    System.err.println("Invalid product type: " + productType);
                    continue; // Skip to the next product
                }

                productList.add(product);
            }
            System.out.println("Products loaded successfully from file.");
        } catch (FileNotFoundException e) {
            System.err.println("Error reading products from file: " + e.getMessage());
        }

        return;
    }



    public void showMenu() {
            System.out.println("------------------------------------------\n Welcome to Westminster Shopping Manager \n------------------------------------------");
            System.out.println("1. Open GUI");
            System.out.println("2. Console System\n");
            System.out.print("Enter option: ");

            int choice = input.nextInt();
            input.nextLine(); // Consume the newline character
            if (choice == 1) {
                new Gui(); // Call GUI to start
            } else if (choice == 2) {
                logon();
                 run();
            } else {
                System.out.println("Invalid option");
                showMenu();
            }
        }

    protected void logon() {
        while (true) {
                System.out.print("Enter user password: ");
                String userInput = input.nextLine();
                final String password = "20210715";

                if (userInput.equals(password)) {
                    System.out.println("\nCorrect password. Access granted!\n");
                    break;
                } else {
                    System.out.println("Wrong password");
                }
        }
    }

    public void menu() {
        System.out.println("\n1. Add a Product");
        System.out.println("2. Delete a Product");
        System.out.println("3. Display Product List");
        System.out.println("4. Save Products to File");
        System.out.println("5. Read Products from File");
        System.out.println("6. Exit\n");
    }


    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            menu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    printProductList();
                    break;
                case 4:
                    saveProducts();
                    break;
                case 5:
                    readProducts();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

    }

    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.run();
    }


    public void addRow(String[] strings) {
    }
}




