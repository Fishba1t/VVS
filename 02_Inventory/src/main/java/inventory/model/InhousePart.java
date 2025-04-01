package inventory.model;

public class InhousePart extends Part {

    // Declare fields
    private int machineId;

    // Constructor
    public InhousePart(int partId, String name, double price, int inStock, int min, int max, int machineId) {
        super(partId, name, price, inStock, min, max);
        this.machineId = machineId;
    }

    // Getter
    public int getMachineId() {
        return machineId;
    }

    // Setter
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    @Override
    public String toString() {
        return "I,"+super.toString()+","+getMachineId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        InhousePart other = (InhousePart) obj;

        return getPartId() == other.getPartId()
                && getName().equals(other.getName())
                && Double.compare(getPrice(), other.getPrice()) == 0
                && getInStock() == other.getInStock()
                && getMin() == other.getMin()
                && getMax() == other.getMax()
                && getMachineId() == other.getMachineId();
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(getPartId());
        result = 31 * result + getName().hashCode();
        result = 31 * result + Double.hashCode(getPrice());
        result = 31 * result + Integer.hashCode(getInStock());
        result = 31 * result + Integer.hashCode(getMin());
        result = 31 * result + Integer.hashCode(getMax());
        result = 31 * result + Integer.hashCode(getMachineId());
        return result;
    }

}


