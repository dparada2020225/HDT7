# HDT7 - Buscador de Productos con BST

## Autores
- Harry Mendez
- Denil Parada - 24761

## Descripción
Este proyecto implementa un sistema para buscar el mejor precio de productos usando árboles binarios de búsqueda (BST). El programa lee datos de productos desde un archivo CSV y permite al usuario buscar productos específicos por su código SKU, mostrando información relevante como el precio retail, precio actual, nombre y categoría.

## Funcionalidades
- Implementación genérica de Árboles Binarios de Búsqueda (BST)
- Carga de datos desde archivos CSV
- Búsqueda eficiente de productos por SKU
- Interfaz gráfica para facilitar el uso del programa

## Estructura del proyecto

### Clases principales
- `BSTNode.java`: Implementación del nodo para el árbol binario de búsqueda
- `BinarySearchTree.java`: Implementación genérica del árbol binario de búsqueda
- `Product.java`: Clase que representa un producto con sus atributos
- `CSVReader.java`: Clase para leer y procesar el archivo CSV
- `ProductSearcher.java`: Clase principal que gestiona la búsqueda de productos
- `ProductSearchGUI.java`: Interfaz gráfica para interactuar con el programa

## Cómo usar
1. Ejecute la aplicación iniciando la clase `ProductSearchGUI`
2. Si el archivo CSV está en la raíz del proyecto, se cargará automáticamente
3. De lo contrario, use el botón "Cargar CSV" para seleccionar el archivo
4. Ingrese un código SKU en el campo de texto y haga clic en "Buscar"
5. La información del producto se mostrará en el área de resultados
6. También puede ver todos los productos cargados usando el botón "Mostrar Todos"

## Dataset
El programa está diseñado para trabajar con el conjunto de datos que se puede descargar de:
https://www.kaggle.com/datasets/polartech/40000-home-appliance-sku-data-from-lowescom

## Referencias y recursos
- Duane A. Bailey. (2007). Java Structures: Data Structures in Java for the Principled Programmer (Cap. 12)
- Oracle. (2023). Java Documentation - Collections Framework. https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/package-summary.html
- GeeksforGeeks. (2023). Binary Search Tree. https://www.geeksforgeeks.org/binary-search-tree-data-structure/
- Oracle. (2023). Java Swing Tutorial. https://docs.oracle.com/javase/tutorial/uiswing/

## Agradecimientos
Agradecemos la asistencia de Claude (Anthropic) en la optimización del código y mejoras en el manejo de errores para el procesamiento de archivos CSV.