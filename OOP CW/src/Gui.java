

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;


public class Gui extends JFrame {
    static WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
    static ArrayList<Product> productList =new ArrayList<Product>() ;
    private JComboBox<String> comboBox;
    private JMenu categoryMenu;
    private JMenuItem allMenuItem;
    private JMenuItem electronicsMenuItem;
    private JMenuItem clothingMenuItem;
    JLabel productIDLabel = new JLabel();
    JLabel categoryLabel = new JLabel();
    JLabel productNameLabel = new JLabel();
    JLabel brandLabel = new JLabel();
    JLabel warrantyLabel = new JLabel();
    JLabel colorLabel = new JLabel();
    JLabel countLabel = new JLabel();
    JLabel sizeLabel = new JLabel();
    JLabel priceLabel = new JLabel();
    JLabel detailsLabel=  new JLabel();
    private JLabel totalPriceLabel;
    private JLabel finalPriceLabel;
    private JLabel discountedPriceLabel;
    private JTable finalCart;
    private JLabel firstPurchaseLabel;

    public Gui() {
        shoppingManager.readProducts();
        setTitle("Westminster Shopping Center");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);

        JPanel top = new JPanel();

        this.add(top, BorderLayout.NORTH);

        JPanel middle = new JPanel();
        setLayout(new BorderLayout());


        JPanel panel = new JPanel(new BorderLayout());
        add(panel);
        panel.add(top, BorderLayout.NORTH);
        panel.add(middle, BorderLayout.CENTER);


        JLabel label = new JLabel("Select Product Category:                               ");
        top.add(label);
        label.setVisible(true);


        String[] productTypes = {"All", "Electronics", "Clothing"};
        JComboBox<String> productTypeDropdown = new JComboBox<>(productTypes);
        top.add(productTypeDropdown, BorderLayout.NORTH);


        JButton shoppingCartButton = new JButton("Shopping cart");
        shoppingCartButton.setPreferredSize(new Dimension(120, 25));
        shoppingCartButton.setLocation(550, 10);
//        shoppingCartButton.addActionListener(this::openCart);
        top.add(shoppingCartButton);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price(£)");
        tableModel.addColumn("Info");

        JTable productTable = new JTable(tableModel);
        top.add(new JScrollPane(productTable));
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        for (Product i : shoppingManager.getProductList()) {
            Object[] row;
            if (i instanceof Electronics) {
                row = new Object[]{i.getProductID(), i.getProductName(), i.getClass().getName(), i.getPrice(), ((Electronics) i).getBrand() + "," + ((Electronics) i).getWarrantyPeriod()};
            } else {
                row = new Object[]{i.getProductID(), i.getProductName(), i.getClass().getName(), i.getPrice(), ((Clothing) i).getSize() + "," + ((Clothing) i).getColor()};
            }
            model.addRow(row);

        }
        productTypeDropdown.addActionListener(e -> {
                    String selectedCategory = (String) productTypeDropdown.getSelectedItem();
            for (Product product : shoppingManager.getProductList()) {
                boolean shouldAdd = false;

                if (selectedCategory.equals("All")) {
                    shouldAdd = true; // Show all products
                } else if (selectedCategory.equals("Electronics") && product instanceof Electronics) {
                    shouldAdd = true; // Show only Electronics
                } else if (selectedCategory.equals("Clothing") && product instanceof Clothing) {
                    shouldAdd = true; // Show only Clothing
                }

                if (shouldAdd) {
                    Object[] row;
                    if (product instanceof Electronics) {
                        row = new Object[]{product.getProductID(), product.getProductName(), product.getClass().getSimpleName(), product.getPrice(), ((Electronics) product).getBrand() + "," + ((Electronics) product).getWarrantyPeriod()};
                    } else {
                        row = new Object[]{product.getProductID(), product.getProductName(), product.getClass().getSimpleName(), product.getPrice(), ((Clothing) product).getSize() + "," + ((Clothing) product).getColor()};
                    }
                    model.addRow(row);
                }
            }

                });




        // add the ListSelectionListener to the product table
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int row = productTable.getSelectedRow();
                    // set the text of the labels according to the selected row
                    detailsLabel.setText("Product Details : ");
                    productIDLabel.setText("     Product ID : " + productTable.getValueAt(row, 0).toString());
                    productNameLabel.setText("     Product Name : " + productTable.getValueAt(row, 1).toString());
                    categoryLabel.setText("     Category : " + productTable.getValueAt(row, 2).toString());
                    if (productTable.getValueAt(row, 2).equals("Electronics")) {
                        String electronics = productTable.getValueAt(row, 4).toString();
                        String[] splitList = electronics.split(",");
                        brandLabel.setText("     Brand Name : " + splitList[0]);
                        warrantyLabel.setText("     Warranty : " + splitList[1]);
                        productTable.add(new JLabel(""));
                        // Set the visibility of the labels according to the category
                        brandLabel.setVisible(true);
                        warrantyLabel.setVisible(true);
                        colorLabel.setVisible(false);
                        sizeLabel.setVisible(false);
                    }
                    if (productTable.getValueAt(row, 2).equals("Clothing")) {
                        String clothing = productTable.getValueAt(row, 4).toString();
                        String[] splitList = clothing.split(",");
                        colorLabel.setText("     Color : " + splitList[1]);
                        sizeLabel.setText("     Size : " + splitList[0]);
                        productTable.add(new JLabel(""));
                        // Set the visibility of the labels according to the category
                        brandLabel.setVisible(false);
                        warrantyLabel.setVisible(false);
                        colorLabel.setVisible(true);
                        sizeLabel.setVisible(true);
                    }

                    String avbItems;
                    for (Product i : shoppingManager.getProductList()) {
                        if (i.getProductID().equals(productTable.getValueAt(row, 0).toString())) {
                            avbItems = String.valueOf(i.getAvailableItems());
                            countLabel.setText("     Items Available : " + avbItems + "\n");
                        }
                    }
                }
            }
        });

// set the visibility of the frame
        setVisible(true);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        this.add(bottom, BorderLayout.SOUTH);
        bottom.add(new JLabel("                                                                                                                                                                                                                                       "));
        bottom.add(detailsLabel);
        bottom.add(new JLabel(" "));
        bottom.add(productIDLabel);
        bottom.add(new JLabel(" "));
        bottom.add(productNameLabel);
        bottom.add(new JLabel(" "));
        bottom.add(categoryLabel);
        bottom.add(new JLabel(" "));
        bottom.add(brandLabel);
        bottom.add(warrantyLabel);
        bottom.add(colorLabel);
        bottom.add(sizeLabel);
        bottom.add(new JLabel(" "));
        bottom.add(countLabel);
        bottom.add(new JLabel(" "));
        bottom.add(new JLabel(" "));
        bottom.add(new JLabel(" "));

        JButton addToCart = new JButton("Add to Cart");
        bottom.add(addToCart);

// Add an action listener to the add to cart button
        addToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = productTable.getSelectedRow();
                if (row != -1) {
                    // Get the product details from the row
                    String product = productTable.getValueAt(row, 0).toString();
                    for (Product i : shoppingManager.getProductList()) {
                        if (i.getProductID().equals(productTable.getValueAt(row, 0).toString())) {
                            int count = i.getAvailableItems() -1;
                            i.setAvailableItems(count);
                            productList.add(i);

                        }
                    }

//                    String quantity = productTable.getValueAt(row, 1).toString();
//                    String price = productTable.getValueAt(row, 2).toString();

                    // Add the product details to the shopping cart table model
//                  cartDetails.add(new Object[]{product, quantity, price});
                }
            }
        });
//
//       shoppingCartButton.addActionListener(new ActionListener()
        shoppingCartButton.addActionListener(e -> {
            // create the shopping cart frame
            JFrame shoppingCart = new JFrame("Shopping Cart");
            shoppingCart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            shoppingCart.setSize(700, 400);



            // Create JLabels for displaying the prices

            finalCart = new JTable(tableModel);
            shoppingCart.add(new JScrollPane(finalCart), BorderLayout.CENTER);

            JPanel total = new JPanel();
            priceLabel.setLayout(new GridLayout(5, 1));
            finalCart.add(priceLabel, BorderLayout.SOUTH);
            totalPriceLabel = new JLabel("Total Price: $0.0       ", JLabel.RIGHT);
            total.add(totalPriceLabel);

            discountedPriceLabel = new JLabel("Discounted price : $0.0      ", JLabel.RIGHT);
            total.add(discountedPriceLabel);

            firstPurchaseLabel = new JLabel("First purchase discount: $0.0      ", JLabel.RIGHT);
            total.add(firstPurchaseLabel);

            finalPriceLabel = new JLabel("Final Price: $0.0     ", JLabel.RIGHT);
            total.add(finalPriceLabel);
            total.add(new JLabel("   "));

            // create the table for the shopping cart
            DefaultTableModel cartModel = new DefaultTableModel();
            cartModel.addColumn("Product");
            cartModel.addColumn("Quantity");
            cartModel.addColumn("Price");

            for (Product i : productList) {
                Object[] row;
                row = new Object[]{i.getProductID(), i.getProductName(),i.getAvailableItems()};
                cartModel.addRow(row);
            }

            JTable shoppingCartTable = new JTable(cartModel);
            shoppingCartTable.setFillsViewportHeight(true);


            // add the table to a scroll pane and add the scroll pane to the frame
            JScrollPane scrollPane = new JScrollPane(shoppingCartTable);
            shoppingCart.add(scrollPane);

            // make the frame visible
            shoppingCart.setVisible(true);
        });
        }
    }




