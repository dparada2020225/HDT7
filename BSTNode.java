/**
 * Nodo para el árbol binario de búsqueda
 * Basado en la implementación del libro Java Structures, capítulo 12
 */
public class BSTNode<E extends Comparable<E>> {
    protected E data;
    protected BSTNode<E> left;
    protected BSTNode<E> right;
    
    public BSTNode(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
    
    public BSTNode<E> getLeft() {
        return left;
    }
    
    public BSTNode<E> getRight() {
        return right;
    }
    
    public E getData() {
        return data;
    }
    
    public void setLeft(BSTNode<E> left) {
        this.left = left;
    }
    
    public void setRight(BSTNode<E> right) {
        this.right = right;
    }
    
    public void setData(E data) {
        this.data = data;
    }
}