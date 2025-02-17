
package Functions;

import java.util.Objects;


public class ProductKey {
    private final String name;
    private final String barcode;

    public ProductKey(String name, String barcode) {
        this.name = name;
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductKey that = (ProductKey) o;
        return name.equals(that.name) && barcode.equals(that.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, barcode);
    }

    @Override
    public String toString() {
        return name + " (" + barcode + ")";
    }
}

