
package inventory.repository;

import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventoryRepositoryInMemory {

    // Declare fields
    private ObservableList<Product> products;
    private ObservableList<Part> allParts;
    private int autoPartId;
    private int autoProductId;

    public InventoryRepositoryInMemory(){
        this.products = FXCollections.observableArrayList();
        this.allParts= FXCollections.observableArrayList();
        this.autoProductId=0;
        this.autoPartId=0;
    }

    // Declare methods
    /**
     * Add new product to observable list products
     * @param product
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Remove product from observable list products
     * @param product
     */
    public void removeProduct(Product product) {
        products.remove(product);
    }
    /**
     * Accepts search parameter and if an ID or name matches input, that product is returned
     * @param searchNameOrId
     * @return
     */
    public Product lookupProduct(String searchNameOrId) {
        // Daca se cauta cu un nume/ID gol, return null
        if (searchNameOrId.equals("")) {
            return null;
        }
        boolean isFound = false;

        for (Product p : products) {
            // Daca produsul corespunde cu numele sau ID-ul cautat, il returnam
            if (p.getName().contains(searchNameOrId) || (p.getProductId() + "").equals(searchNameOrId)) {
                return p;
            }
            // Marcam ca am intrat in for, deci lista nu e goala
            isFound = true;
        }

        // Daca nu am intrat deloc in for (lista goala), returnam produsul "default"
        if (isFound == false) {
            Product product = new Product(0, null, 0.0, 0, 0, 0, null);
            return product;
        }

        // Daca lista nu e goala, dar nu s-a gasit produsul cautat, returnam null.
        return null;
    }


    /**
     * Update product at given index
     * @param index
     * @param product
     */
    public void updateProduct(int index, Product product) {
        products.set(index, product);
    }

    /**
     * Getter for Product Observable List
     * @return
     */
    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> list) {
        products=list;
    }

    /**
     * Add new part to observable list allParts
     * @param part
     */
    public Part addPart(Part part) {
        allParts.add(part);
        return allParts.get(allParts.size()-1);
    }

    /**
     * Removes part passed as parameter from allParts
     * @param part
     */
    public void deletePart(Part part) {
        allParts.remove(part);
    }

    /**
     * Accepts search parameter and if an ID or name matches input, that part is returned
     * @param searchNameOrId
     * @return
     */
    public Part lookupPart(String searchNameOrId) {
        for(Part p:allParts) {
            if(p.getName().contains(searchNameOrId) || (p.getPartId()+"").equals(searchNameOrId)) return p;
        }
        return null;
    }

    /**
     * Update part at given index
     * @param index
     * @param part
     */
    public void updatePart(int index, Part part) {
        allParts.set(index, part);
    }

    /**
     * Getter for allParts Observable List
     * @return
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @param list
     */
    public void setAllParts(ObservableList<Part> list) {
        allParts=list;
    }

    /**
     * Method for incrementing part ID to be used to automatically
     * assign ID numbers to parts
     * @return
     */
    public int getAutoPartId() {
        autoPartId++;
        return autoPartId;
    }

    /**
     * Method for incrementing product ID to be used to automatically
     * assign ID numbers to products
     * @return
     */
    public int getAutoProductId() {
        autoProductId++;
        return autoProductId;
    }


    public void setAutoPartId(int id){
        autoPartId=id;
    }

    public void setAutoProductId(int id){
        autoProductId=id;
    }

}