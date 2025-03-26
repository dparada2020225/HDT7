import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

/**
 * - Pruebas JUnit para el insert y search por SKU 
 */
public class BSTProductSearchTest {
    
    private BinarySearchTree<Product> productTree;
    private List<Product> testProducts;
    
    @Before
    public void setUp() {
        // Inicializar el árbol BST para cada prueba
        productTree = new BinarySearchTree<>();
        
        // Crear lista de productos de prueba con datos realistas
        testProducts = new ArrayList<>();
        testProducts.add(new Product("LW1956ST", 1299.99, 1149.99, "LG 5.8 cu ft Smart Wi-Fi Enabled True Convection InstaView Electric Range", "Ranges"));
        testProducts.add(new Product("GDF640HSMSS", 849.99, 749.99, "GE Dishwasher with Front Controls", "Dishwashers"));
        testProducts.add(new Product("WRF767SDHZ", 2799.99, 2499.99, "Whirlpool 27 cu ft French Door Refrigerator", "Refrigerators"));
        testProducts.add(new Product("MHW8630HC", 1299.99, 1199.99, "Maytag 5.0 cu ft High-Efficiency Front Load Washer", "Washers"));
        testProducts.add(new Product("NX60T8751SS", 2999.99, 2699.99, "Samsung 30-in Slide-In Gas Range", "Ranges"));
        testProducts.add(new Product("FFSS2615TS", 1199.99, 999.99, "Frigidaire 25.5 cu ft Side-by-Side Refrigerator", "Refrigerators"));
        testProducts.add(new Product("KDFE104HPS", 949.99, 849.99, "KitchenAid Dishwasher with ProWash Cycle", "Dishwashers"));
    }
    
    /**
     * Prueba 1: Verifica que el árbol esté inicialmente vacío
     */
    @Test
    public void testEmptyTree() {
        assertTrue("El árbol nuevo debe estar vacío", productTree.isEmpty());
        assertEquals("El tamaño inicial debe ser 0", 0, productTree.size());
    }
    
    /**
     * Prueba 2: Prueba la inserción de productos en el BST
     */
    @Test
    public void testInsertProducts() {
        // Insertar todos los productos de prueba
        for (Product product : testProducts) {
            productTree.insert(product);
        }
        
        // Verificar que el tamaño del árbol se actualiza correctamente
        assertEquals("El árbol debe contener 7 productos", 7, productTree.size());
        assertFalse("El árbol no debe estar vacío después de inserciones", productTree.isEmpty());
    }
    
    /**
     * Prueba 3: Prueba la búsqueda de productos por SKU (existentes)
     */
    @Test
    public void testSearchExistingProducts() {
        // Insertar todos los productos de prueba
        for (Product product : testProducts) {
            productTree.insert(product);
        }
        
        // Buscar cada producto por su SKU
        for (Product expected : testProducts) {
            // Crear un producto temporal solo con el SKU para buscar
            Product searchKey = new Product(expected.getSku(), 0, 0, "", "");
            
            // Buscar el producto en el árbol
            Product found = productTree.search(searchKey);
            
            // Verificar que se encontró el producto correcto
            assertNotNull("Debería encontrar el producto con SKU: " + expected.getSku(), found);
            assertEquals("El SKU debe coincidir", expected.getSku(), found.getSku());
            assertEquals("El nombre debe coincidir", expected.getProductName(), found.getProductName());
            assertEquals("La categoría debe coincidir", expected.getCategory(), found.getCategory());
            assertEquals("El precio retail debe coincidir", expected.getPriceRetail(), found.getPriceRetail(), 0.001);
            assertEquals("El precio actual debe coincidir", expected.getPriceCurrent(), found.getPriceCurrent(), 0.001);
        }
    }
    
    /**
     * Prueba 4: Prueba la búsqueda de productos por SKU (no existentes)
     */
    @Test
    public void testSearchNonExistingProducts() {
        // Insertar todos los productos de prueba
        for (Product product : testProducts) {
            productTree.insert(product);
        }
        
        // Buscar productos con SKUs que no existen
        String[] nonExistingSKUs = {"UNKNOWN123", "NOT-IN-TREE", "ABC123XYZ", "TEST99999"};
        
        for (String sku : nonExistingSKUs) {
            Product searchKey = new Product(sku, 0, 0, "", "");
            Product found = productTree.search(searchKey);
            
            // Verificar que el producto no se encuentra
            assertNull("No debería encontrar el producto con SKU: " + sku, found);
        }
    }
    
    /**
     * Prueba 5: Verifica la estructura del BST después de inserciones
     */
    @Test
    public void testBSTStructure() {
        // Insertar productos en un orden específico para crear una estructura de árbol predecible
        productTree.insert(testProducts.get(3)); 
        productTree.insert(testProducts.get(1)); 
        productTree.insert(testProducts.get(5)); 
        productTree.insert(testProducts.get(0)); 
        productTree.insert(testProducts.get(6)); 
        productTree.insert(testProducts.get(4)); 
        productTree.insert(testProducts.get(2)); 
        
        // Verificar la estructura del árbol examinando nodos específicos
        BSTNode<Product> root = productTree.getRoot();
        assertNotNull("La raíz no debe ser nula", root);
        assertEquals("La raíz debe tener el SKU correcto", "MHW8630HC", root.getData().getSku());
        
        // Verificar el hijo izquierdo de la raíz
        BSTNode<Product> leftChild = root.getLeft();
        assertNotNull("El hijo izquierdo de la raíz no debe ser nulo", leftChild);
        assertEquals("El hijo izquierdo debe tener el SKU correcto", "GDF640HSMSS", leftChild.getData().getSku());
        
        // Verificar el hijo derecho de la raíz
        BSTNode<Product> rightChild = root.getRight();
        assertNotNull("El hijo derecho de la raíz no debe ser nulo", rightChild);
        assertEquals("El hijo derecho debe tener el SKU correcto", "NX60T8751SS", rightChild.getData().getSku());
    }
    
    /**
     * Prueba 6: Prueba inserciones y búsquedas mixtas
     */
    @Test
    public void testMixedInsertAndSearch() {
        // Insertar algunos productos
        productTree.insert(testProducts.get(0)); 
        productTree.insert(testProducts.get(2)); 
        productTree.insert(testProducts.get(4)); 
        
        // Verificar que podemos encontrarlos
        Product searchKey1 = new Product("LW1956ST", 0, 0, "", "");
        Product found1 = productTree.search(searchKey1);
        assertNotNull("Debería encontrar LW1956ST", found1);
        
        // Buscar un producto que no está en el árbol todavía
        Product searchKey2 = new Product("KDFE104HPS", 0, 0, "", "");
        Product found2 = productTree.search(searchKey2);
        assertNull("No debería encontrar KDFE104HPS todavía", found2);
        
        // Insertar más productos
        productTree.insert(testProducts.get(1)); 
        productTree.insert(testProducts.get(6)); 
        
        // Verificar que ahora podemos encontrar el que antes no estaba
        Product found3 = productTree.search(searchKey2);
        assertNotNull("Ahora debería encontrar KDFE104HPS", found3);
        assertEquals("El SKU debe coincidir", "KDFE104HPS", found3.getSku());
    }
    
    /**
     * Prueba 7: Prueba el comportamiento con un gran número de productos
     */
    @Test
    public void testLargeNumberOfProducts() {
        // Crear e insertar 100 productos con SKUs generados
        List<Product> largeProductList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            String sku = String.format("SKU%06d", i);
            Product product = new Product(sku, 100 + i, 90 + i, "Producto " + i, "Categoría " + (i % 5));
            largeProductList.add(product);
            productTree.insert(product);
        }
        
        // Verificar el tamaño del árbol
        assertEquals("El árbol debe contener 100 productos", 100, productTree.size());
        
        // Buscar algunos productos aleatorios
        for (int i = 0; i < 10; i++) {
            int randomIndex = (int) (Math.random() * 100);
            Product expected = largeProductList.get(randomIndex);
            
            Product searchKey = new Product(expected.getSku(), 0, 0, "", "");
            Product found = productTree.search(searchKey);
            
            assertNotNull("Debería encontrar el producto aleatorio: " + expected.getSku(), found);
            assertEquals("El SKU debe coincidir", expected.getSku(), found.getSku());
        }
    }
    
    /**
     * Prueba 8: Prueba específica para búsqueda con ProductSearcher
     */
    @Test
    public void testProductSearcherBySKU() {
        // Crear un ProductSearcher manual con nuestro árbol de prueba
        ProductSearcher searcher = new ProductSearcher();
        
        // Insertar todos los productos de prueba
        for (Product product : testProducts) {
            searcher.addProduct(product);
        }
        
        // Probar la búsqueda por SKU
        Product found = searcher.searchBySKU("WRF767SDHZ");
        assertNotNull("Debería encontrar el refrigerador Whirlpool", found);
        assertEquals("El nombre debe coincidir", "Whirlpool 27 cu ft French Door Refrigerator", found.getProductName());
        
        // Probar SKU que no existe
        Product notFound = searcher.searchBySKU("NONEXISTENT");
        assertNull("No debería encontrar un SKU inexistente", notFound);
        
        // Probar SKU nulo o vacío
        assertNull("SKU nulo debería devolver null", searcher.searchBySKU(null));
        assertNull("SKU vacío debería devolver null", searcher.searchBySKU(""));
    }
    
    /**
     * Clase auxiliar para implementar un ProductSearcher simplificado para las pruebas
     */
    private class ProductSearcher {
        private BinarySearchTree<Product> productTree;
        
        public ProductSearcher() {
            productTree = new BinarySearchTree<>();
        }
        
        public void addProduct(Product product) {
            productTree.insert(product);
        }
        
        public Product searchBySKU(String sku) {
            if (sku == null || sku.isEmpty()) {
                return null;
            }
            
            Product searchProduct = new Product(sku, 0, 0, "", "");
            return productTree.search(searchProduct);
        }
    }
}