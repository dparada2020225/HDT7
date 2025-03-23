import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz gráfica para buscar productos
 */
public class ProductSearchGUI extends JFrame {
    private ProductSearcher productSearcher;
    private JTextField skuField;
    private JTextArea resultArea;
    private JLabel statusLabel;
    private JButton searchButton;
    private JButton loadButton;
    private JButton showAllButton;
    private JButton clearButton;

    public ProductSearchGUI() {
        super("Buscador de Productos - Trabajo 7");
        initComponents();
        layoutComponents();

        // Cargar automáticamente el archivo CSV si está en la raíz del proyecto
        autoLoadCSV();

        // Configurar el tamaño y posición de la ventana
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        skuField = new JTextField(20);
        searchButton = new JButton("Buscar");
        loadButton = new JButton("Cargar CSV");
        showAllButton = new JButton("Mostrar Todos");
        clearButton = new JButton("Limpiar");
        resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);
        statusLabel = new JLabel("Estado: Sin datos cargados");

        // Acción del botón de búsqueda
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });

        // Acción del botón de carga
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCSVFile();
            }
        });

        // Acción del botón para mostrar todos los productos
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllProducts();
            }
        });

        // Acción del botón para limpiar
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");
            }
        });

        // Permitir buscar con Enter
        skuField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });
    }

    private void layoutComponents() {
        // Panel para la búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("SKU:"));
        searchPanel.add(skuField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        // Panel para los botones de acción
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(loadButton);
        buttonPanel.add(showAllButton);

        // Panel para el botón de carga, mostrar todos y estado
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        bottomPanel.add(statusLabel, BorderLayout.EAST);

        // Panel para el resultado
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Producto"));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Añadir el panel principal a la ventana
        setContentPane(mainPanel);
    }

    private void searchProduct() {
        if (productSearcher == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero debe cargar los datos desde el archivo CSV.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sku = skuField.getText().trim();
        if (sku.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese un SKU para buscar.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Product product = productSearcher.searchBySKU(sku);
        if (product == null) {
            resultArea.setText("No se encontró ningún producto con el SKU: " + sku);
        } else {
            resultArea.setText(product.toString());
        }
    }
    
    private void showAllProducts() {
        if (productSearcher == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero debe cargar los datos desde el archivo CSV.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        List<Product> allProducts = productSearcher.getAllProducts();
        if (allProducts.isEmpty()) {
            resultArea.setText("No hay productos para mostrar.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Total de productos: ").append(allProducts.size()).append("\n\n");
        
        int count = 0;
        for (Product product : allProducts) {
            count++;
            sb.append("=== Producto ").append(count).append(" ===\n");
            sb.append(product.toString()).append("\n\n");
            
            // Mostrar solo los primeros 50 productos para evitar problemas de rendimiento
            if (count >= 50) {
                sb.append("... y ").append(allProducts.size() - 50).append(" más.");
                break;
            }
        }
        
        resultArea.setText(sb.toString());
        resultArea.setCaretPosition(0); // Scroll al inicio
    }

    private void loadCSVFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        // Intentar establecer el directorio actual como directorio inicial
        File currentDir = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(currentDir);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            loadCSVFromFile(fileChooser.getSelectedFile());
        }
    }
    
    private void loadCSVFromFile(File file) {
        // Deshabilitar la UI durante la carga
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        searchButton.setEnabled(false);
        loadButton.setEnabled(false);
        showAllButton.setEnabled(false);
        statusLabel.setText("Estado: Cargando datos...");
        
        // Usar SwingWorker para cargar datos en segundo plano
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    productSearcher = new ProductSearcher(file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            
            @Override
            protected void done() {
                // Restaurar la UI
                setCursor(Cursor.getDefaultCursor());
                searchButton.setEnabled(true);
                loadButton.setEnabled(true);
                showAllButton.setEnabled(true);
                
                if (productSearcher != null) {
                    int count = productSearcher.getProductCount();
                    statusLabel.setText("Estado: " + count + " productos cargados");
                    JOptionPane.showMessageDialog(ProductSearchGUI.this,
                            "Se han cargado " + count + " productos.",
                            "Carga completada",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    statusLabel.setText("Estado: Error al cargar datos");
                    JOptionPane.showMessageDialog(ProductSearchGUI.this,
                            "Ocurrió un error al cargar los datos.",
                            "Error de carga",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        
        worker.execute();
    }
    
    private void autoLoadCSV() {
        // Intentar cargar automáticamente si el archivo está en la raíz
        File csvFile = new File("home appliance skus lowes.csv");
        if (csvFile.exists()) {
            loadCSVFromFile(csvFile);
        }
    }

    public static void main(String[] args) {
        try {
            // Configurar el Look and Feel para una mejor apariencia
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Ejecutar la GUI en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProductSearchGUI();
            }
        });
    }
}