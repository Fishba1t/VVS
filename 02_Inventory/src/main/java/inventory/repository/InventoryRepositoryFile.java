package inventory.repository;


import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.validator.PartValidator;
import inventory.validator.ProductValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.StringTokenizer;

public class InventoryRepositoryFile {

    private static String filename = "data/items.txt";
    private InventoryRepositoryInMemory inventoryRepositoryInMemory;

    private PartValidator partValidator;
    private ProductValidator productValidator;

    public InventoryRepositoryFile(PartValidator partValidator, ProductValidator productValidator) {
        this.inventoryRepositoryInMemory = new InventoryRepositoryInMemory();
        this.partValidator=partValidator;
        this.productValidator=productValidator;
        readParts();
        readProducts();
    }

    public void readParts(){
        //ClassLoader classLoader = InventoryRepository.class.getClassLoader();
        File file = new File(filename);
        ObservableList<Part> listP = FXCollections.observableArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line=br.readLine())!=null){
                Part part=getPartFromString(line);
                if (part!=null)
                    listP.add(part);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inventoryRepositoryInMemory.setAllParts(listP);
    }

    private Part getPartFromString(String line){
        Part item=null;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        String type=st.nextToken();
        if (type.equals("I")) {
            int id= Integer.parseInt(st.nextToken());
            inventoryRepositoryInMemory.setAutoPartId(id);
            String name= st.nextToken();
            double price = Double.parseDouble(st.nextToken());
            int inStock = Integer.parseInt(st.nextToken());
            int minStock = Integer.parseInt(st.nextToken());
            int maxStock = Integer.parseInt(st.nextToken());
            int idMachine= Integer.parseInt(st.nextToken());
            item = new InhousePart(id, name, price, inStock, minStock, maxStock, idMachine);
        }
        if (type.equals("O")) {
            int id= Integer.parseInt(st.nextToken());
            inventoryRepositoryInMemory.setAutoPartId(id);
            String name= st.nextToken();
            double price = Double.parseDouble(st.nextToken());
            int inStock = Integer.parseInt(st.nextToken());
            int minStock = Integer.parseInt(st.nextToken());
            int maxStock = Integer.parseInt(st.nextToken());
            String company=st.nextToken();
            item = new OutsourcedPart(id, name, price, inStock, minStock, maxStock, company);
        }
        return item;
    }

    public void readProducts(){
        //ClassLoader classLoader = InventoryRepository.class.getClassLoader();
        File file = new File(filename);

        ObservableList<Product> listP = FXCollections.observableArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line=br.readLine())!=null){
                Product product=getProductFromString(line);
                if (product!=null)
                    listP.add(product);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inventoryRepositoryInMemory.setProducts(listP);
    }

    private Product getProductFromString(String line){
        Product product=null;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        String type=st.nextToken();
        if (type.equals("P")) {
            int id= Integer.parseInt(st.nextToken());
            inventoryRepositoryInMemory.setAutoProductId(id);
            String name= st.nextToken();
            double price = Double.parseDouble(st.nextToken());
            int inStock = Integer.parseInt(st.nextToken());
            int minStock = Integer.parseInt(st.nextToken());
            int maxStock = Integer.parseInt(st.nextToken());
            String partIDs=st.nextToken();

            StringTokenizer ids= new StringTokenizer(partIDs,":");
            ObservableList<Part> list= FXCollections.observableArrayList();
            while (ids.hasMoreTokens()) {
                String idP = ids.nextToken();
                Part part = inventoryRepositoryInMemory.lookupPart(idP);
                if (part != null)
                    list.add(part);
            }
            product = new Product(id, name, price, inStock, minStock, maxStock, list);
            product.setAssociatedParts(list);
        }
        return product;
    }

    public void writeAll() {

        //ClassLoader classLoader = InventoryRepository.class.getClassLoader();
        File file = new File(filename);

        BufferedWriter bw = null;
        ObservableList<Part> parts= inventoryRepositoryInMemory.getAllParts();
        ObservableList<Product> products= inventoryRepositoryInMemory.getProducts();

        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (Part p:parts) {
                System.out.println(p.toString());
                bw.write(p.toString());
                bw.newLine();
            }

            for (Product pr:products) {
                String line=pr.toString()+",";
                ObservableList<Part> list= pr.getAssociatedParts();
                int index=0;
                while(index<list.size()-1){
                    line=line+list.get(index).getPartId()+":";
                    index++;
                }
                if (index==list.size()-1)
                    line=line+list.get(index).getPartId();
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Part addPart(Part part){
        partValidator.validate(part);
        writeAll();
        return inventoryRepositoryInMemory.addPart(part);
    }

    public void addProduct(Product product){
        productValidator.validate(product);
        inventoryRepositoryInMemory.addProduct(product);
        writeAll();
    }

    public void addProduct2(Product product) {
        inventoryRepositoryInMemory.addProduct(product);
    }

    public int getAutoPartId(){
        return inventoryRepositoryInMemory.getAutoPartId();
    }

    public int getAutoProductId(){
        return inventoryRepositoryInMemory.getAutoProductId();
    }

    public ObservableList<Part> getAllParts(){
        return inventoryRepositoryInMemory.getAllParts();
    }

    public ObservableList<Product> getAllProducts(){
        return inventoryRepositoryInMemory.getProducts();
    }

    public Part lookupPart (String search){
        return inventoryRepositoryInMemory.lookupPart(search);
    }

    public Product lookupProduct (String search){
        return inventoryRepositoryInMemory.lookupProduct(search);
    }

    public void updatePart(int partIndex, Part part){
        partValidator.validate(part);
        inventoryRepositoryInMemory.updatePart(partIndex, part);
        writeAll();
    }

    public void updateProduct(int productIndex, Product product){
        productValidator.validate(product);
        inventoryRepositoryInMemory.updateProduct(productIndex, product);
        writeAll();
    }

    public void deletePart(Part part){
        inventoryRepositoryInMemory.deletePart(part);
        writeAll();
    }
    public void deleteProduct(Product product){
        inventoryRepositoryInMemory.removeProduct(product);
        writeAll();
    }

    public InventoryRepositoryInMemory getInventory(){
        return inventoryRepositoryInMemory;
    }

    public void setInventory(InventoryRepositoryInMemory inventoryRepositoryInMemory){
        this.inventoryRepositoryInMemory = inventoryRepositoryInMemory;
    }
}