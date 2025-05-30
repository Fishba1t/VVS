
package inventory.model;


import java.util.Objects;

public class Part {

    // Declare fields
    private int partId;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    // Constructor
    public Part(int partId, String name, double price, int inStock, int min, int max) {
        this.partId = partId;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    // Getters
    public int getPartId() {
        return partId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getInStock() {
        return inStock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    // Setters
    public void setPartId(int partId) {
        this.partId = partId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }


    @Override
    public String toString() {
        return this.partId+","+this.name+","+this.price+","+this.inStock+","+
                this.min+","+this.max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return partId == part.partId && Double.compare(part.price, price) == 0 && inStock == part.inStock && min == part.min && max == part.max && name.equals(part.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partId, name, price, inStock, min, max);
    }
}