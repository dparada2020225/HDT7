/**
 * Implementación de un árbol binario de búsqueda genérico
 * Basado en la implementación del libro Java Structures, capítulo 12
 */
public class BinarySearchTree<E extends Comparable<E>> {
    protected BSTNode<E> root;
    protected int count;
    
    public BinarySearchTree() {
        root = null;
        count = 0;
    }

    // Añade este método a la clase BinarySearchTree.java
    /**
     * Devuelve el nodo raíz del árbol
     * @return Nodo raíz
     */
    public BSTNode<E> getRoot() {
        return root;
    }
    
    /**
     * Añade un elemento al árbol
     * @param value El elemento a añadir
     */
    public void insert(E value) {
        root = insert(root, value);
        count++;
    }
    
    /**
     * Método auxiliar para insertar un elemento en el árbol
     * @param node Nodo actual
     * @param value Valor a insertar
     * @return Nodo resultante después de la inserción
     */
    protected BSTNode<E> insert(BSTNode<E> node, E value) {
        if (node == null) {
            return new BSTNode<>(value);
        }
        
        int comparison = value.compareTo(node.getData());
        
        if (comparison < 0) {
            node.setLeft(insert(node.getLeft(), value));
        } else if (comparison > 0) {
            node.setRight(insert(node.getRight(), value));
        }
        
        return node;
    }
    
    /**
     * Busca un elemento en el árbol
     * @param value Valor a buscar
     * @return El elemento encontrado o null si no existe
     */
    public E search(E value) {
        return search(root, value);
    }
    
    /**
     * Método auxiliar para buscar un elemento en el árbol
     * @param node Nodo actual
     * @param value Valor a buscar
     * @return El elemento encontrado o null si no existe
     */
    protected E search(BSTNode<E> node, E value) {
        if (node == null) {
            return null;
        }
        
        int comparison = value.compareTo(node.getData());
        
        if (comparison == 0) {
            return node.getData();
        } else if (comparison < 0) {
            return search(node.getLeft(), value);
        } else {
            return search(node.getRight(), value);
        }
    }
    
    /**
     * Devuelve el número de elementos en el árbol
     * @return Número de elementos
     */
    public int size() {
        return count;
    }
    
    /**
     * Verifica si el árbol está vacío
     * @return true si está vacío, false en caso contrario
     */
    public boolean isEmpty() {
        return count == 0;
    }
}