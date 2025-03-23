// ProductSearcher.java
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal para buscar productos por SKU
 */
public class ProductSearcher {
    private BinarySearchTree<Product> productTree;
    private List<Product> allProducts;
    
    public ProductSearcher(String csvFilePath) {
        loadProductsFromCSV(csvFilePath);
    }
    
    /**
     * Carga los productos desde el archivo CSV
     * @param csvFilePath Ruta del archivo CSV
     */
    public void loadProductsFromCSV(String csvFilePath) {
        productTree = CSVReader.loadProductsFromCSV(csvFilePath);
        System.out.println("Se han cargado " + productTree.size() + " productos.");
        
        // Guardar todos los productos en una lista para poder mostrarlos todos
        collectAllProducts();
    }
    
    /**
     * Recorre el árbol y guarda todos los productos en la lista
     */
    private void collectAllProducts() {
        allProducts = new ArrayList<>();
        collectProductsInOrder(productTree.getRoot(), allProducts);
    }
    
    /**
     * Recorre el árbol en orden y añade los productos a la lista
     * @param node Nodo actual
     * @param products Lista donde se guardarán los productos
     */
    private void collectProductsInOrder(BSTNode<Product> node, List<Product> products) {
        if (node != null) {
            collectProductsInOrder(node.getLeft(), products);
            products.add(node.getData());
            collectProductsInOrder(node.getRight(), products);
        }
    }
    
    /**
     * Busca un producto por su SKU
     * @param sku SKU del producto a buscar
     * @return Producto encontrado o null si no existe
     */
    public Product searchBySKU(String sku) {
        if (sku == null || sku.isEmpty()) {
            return null;
        }
        
        // Crear un producto temporal con el SKU buscado para usar en la comparación
        Product searchProduct = new Product(sku, 0, 0, "", "");
        return productTree.search(searchProduct);
    }
    
    /**
     * Devuelve el número de productos cargados
     * @return Número de productos
     */
    public int getProductCount() {
        return productTree.size();
    }
    
    /**
     * Devuelve todos los productos
     * @return Lista con todos los productos
     */
    public List<Product> getAllProducts() {
        return allProducts;
    }
}