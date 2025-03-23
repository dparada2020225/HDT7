public class Product implements Comparable<Product> {
    private final String sku;
    private final double priceRetail;
    private final double priceCurrent;
    private final String productName;
    private final String category;
    
    public Product(String sku, double priceRetail, double priceCurrent, String productName, String category) {
        this.sku = sku;
        this.priceRetail = priceRetail;
        this.priceCurrent = priceCurrent;
        this.productName = productName;
        this.category = category;
    }
    
    public String getSku() {
        return sku;
    }
    
    public double getPriceRetail() {
        return priceRetail;
    }
    
    public double getPriceCurrent() {
        return priceCurrent;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public String getCategory() {
        return category;
    }
    
    @Override
    public int compareTo(Product other) {
        return this.sku.compareTo(other.sku);
    }
    
    @Override
    public String toString() {
        return "SKU: " + sku + 
               "\nNombre: " + productName + 
               "\nCategoría: " + category + 
               "\nPrecio Retail: $" + priceRetail + 
               "\nPrecio Actual: $" + priceCurrent;
    }
}