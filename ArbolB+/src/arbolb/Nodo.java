package arbolb;

/**
 *
 * @author enrique7cp@gmail.com
 */
public class Nodo {

    private int[] valores;
    private Nodo[] hijos;
    private final int orden;

    public Nodo(int orden) {
        this.valores = new int[orden];
        this.hijos = new Nodo[orden + 1];
        this.orden = orden;
    }

    public Nodo(int orden, int valor) {
        this.valores = new int[orden];
        this.hijos = new Nodo[orden + 1];
        this.orden = orden;
        insertarValor(valor);
    }

    public int[] getValores() {
        return valores;
    }

    public void setValores(int[] valores) {
        this.valores = valores;
    }

    public Nodo[] getHijos() {
        return hijos;
    }

    public void setHijos(Nodo[] hijos) {
        this.hijos = hijos;
    }

    public int getOrden() {
        return orden;
    }

    public void ordenarValores() {
        if (!estaVacia()) {
            int size = valores.length - 1;
            int aux;
            for (int x = 0; x <= size; x++) {
                for (int y = 0; y <= size - 1; y++) {
                    if (valores[y] > valores[y + 1] && valores[y + 1] != 0) {
                        aux = valores[y];
                        valores[y] = valores[y + 1];
                        valores[y + 1] = aux;
                    }
                }
            }
        }
    }

    public void insertarValor(int valor) {
        for (int x = 0; x <= valores.length - 1; x++) {
            if (valores[x] == 0) {
                valores[x] = valor;
                break;
            }
        }
        ordenarValores();
    }

    public void insertarHijo(Nodo nodo) {
        for (int x = 0; x <= hijos.length - 1; x++) {
            if (hijos[x] == null) {
                hijos[x] = nodo;
                break;
            }
        }
    }

    public void eliminarValorPorPosicion(int pos) {
        valores[pos] = 0;
    }
    
    public void eliminarValorPorValor(int valor) {
        for (int x = 0; x <= valores.length - 1; x++) {
            if (valores[x] == valor) {
                valores[x] = 0;
            }
        }
    }
    

    public void eliminarHijoPorPosicion(int pos) {
        hijos[pos] = null;
    }

    public boolean estaVacia() {
        for (int x = 0; x <= valores.length - 1; x++) {
            if (valores[x] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean estaLlena() {
        int cont = 0;
        for (int x = 0; x <= valores.length - 1; x++) {
            if (valores[x] != 0) {
                cont++;
            }
        }
        if (cont == valores.length) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ordenMax() {
        int cont = 0;
        for (int x = 0; x <= valores.length - 1; x++) {
            if (valores[x] != 0) {
                cont++;
            }
        }
        if (cont >= orden - 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esHoja() {
        for (int x = 0; x <= valores.length - 1; x++) {
            if (hijos[x] != null) {
                return false;
            }
        }
        return true;
    }

    public int getSize() {
        int cont = 0;
        for (int x = 0; x <= valores.length - 1; x++) {
            if (valores[x] != 0) {
                cont++;
            } else {
                break;
            }
        }
        return cont;
    }

    public int[] mostrarArregloValores() {
        int[] arreglo = new int[getSize()];
        for (int x = 0; x <= arreglo.length - 1; x++) {
            arreglo[x] = valores[x];
        }
        return arreglo;
    }

    public void mostrar() {
        for (int x = 0; x <= valores.length - 1; x++) {
            if (valores[x] != 0) {
                System.out.print(valores[x] + ", ");
            }
        }
        System.out.println("");
    }
}
