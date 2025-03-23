import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para leer el archivo CSV de productos
 */
public class CSVReader {

    /**
     * Lee el archivo CSV y carga los productos en un árbol binario de búsqueda
     * @param filePath Ruta del archivo CSV
     * @return Árbol binario de búsqueda con los productos
     */
    public static BinarySearchTree<Product> loadProductsFromCSV(String filePath) {
        BinarySearchTree<Product> productTree = new BinarySearchTree<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            // Índices de las columnas que nos interesan
            int skuIndex = -1;
            int priceRetailIndex = -1;
            int priceCurrentIndex = -1;
            int productNameIndex = -1;
            int categoryIndex = -1;
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                // Si es la primera línea, encontrar los índices de las columnas
                if (isFirstLine) {
                    for (int i = 0; i < values.length; i++) {
                        String header = values[i].replace("\"", "").trim();
                        if (header.equalsIgnoreCase("SKU")) {
                            skuIndex = i;
                        } else if (header.equalsIgnoreCase("PRICE_RETAIL")) {
                            priceRetailIndex = i;
                        } else if (header.equalsIgnoreCase("PRICE_CURRENT")) {
                            priceCurrentIndex = i;
                        } else if (header.equalsIgnoreCase("PRODUCT_NAME")) {
                            productNameIndex = i;
                        } else if (header.equalsIgnoreCase("CATEGORY")) {
                            categoryIndex = i;
                        }
                    }
                    
                    isFirstLine = false;
                    
                    // Verificar que se encontraron todas las columnas necesarias
                    if (skuIndex == -1 || priceRetailIndex == -1 || priceCurrentIndex == -1 ||
                            productNameIndex == -1 || categoryIndex == -1) {
                        throw new IOException("No se encontraron todas las columnas requeridas en el CSV. Índices encontrados: SKU=" + skuIndex + 
                                             ", PRICE_RETAIL=" + priceRetailIndex + ", PRICE_CURRENT=" + priceCurrentIndex + 
                                             ", PRODUCT_NAME=" + productNameIndex + ", CATEGORY=" + categoryIndex);
                    }
                    
                    System.out.println("Índices de columnas encontrados: SKU=" + skuIndex + 
                                     ", PRICE_RETAIL=" + priceRetailIndex + ", PRICE_CURRENT=" + priceCurrentIndex + 
                                     ", PRODUCT_NAME=" + productNameIndex + ", CATEGORY=" + categoryIndex);
                    continue;
                }
                
                try {
                    // Procesar los datos de cada producto
                    if (values.length <= skuIndex || values.length <= priceRetailIndex || 
                        values.length <= priceCurrentIndex || values.length <= productNameIndex || 
                        values.length <= categoryIndex) {
                        continue; // Fila incompleta, saltamos
                    }
                    
                    String sku = values[skuIndex].replace("\"", "").trim();
                    
                    // Manejar valores faltantes o no numéricos
                    double priceRetail = 0.0;
                    try {
                        String priceRetailStr = values[priceRetailIndex].replace("\"", "").trim();
                        if (!priceRetailStr.isEmpty()) {
                            priceRetail = Double.parseDouble(priceRetailStr);
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar error y usar valor predeterminado
                    }
                    
                    double priceCurrent = 0.0;
                    try {
                        String priceCurrentStr = values[priceCurrentIndex].replace("\"", "").trim();
                        if (!priceCurrentStr.isEmpty()) {
                            priceCurrent = Double.parseDouble(priceCurrentStr);
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar error y usar valor predeterminado
                    }
                    
                    String productName = values[productNameIndex].replace("\"", "").trim();
                    String category = values[categoryIndex].replace("\"", "").trim();
                    
                    // Crear y añadir el producto al árbol
                    if (!sku.isEmpty()) {
                        Product product = new Product(sku, priceRetail, priceCurrent, productName, category);
                        productTree.insert(product);
                    }
                } catch (Exception e) {
                    // Capturar cualquier error en el procesamiento de una línea individual
                    System.err.println("Error al procesar una línea del CSV: " + e.getMessage());
                    // Continuar con la siguiente línea
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
        
        return productTree;
    }
}