package arbolb;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author enrique7cp@gmail.com
 */
public class Arbol {
    private Nodo raiz;
    private final int orden;
    JTextArea mostrar;
    int nivel = 0;

    public Arbol(int orden) {
        this.raiz = new Nodo(orden);
        this.orden = orden;
        this.mostrar = new JTextArea();
    }
    
    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }
    
    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        if (nodo.esHoja()) {
            nodo.insertarValor(valor);
            if (nodo.estaLlena()) {
                return dividir(nodo);
            } else {
                return nodo;
            }
            //se debe verificar si esta llena o no
        } else {
            int[] valores = nodo.getValores();
            Nodo[] hijos = nodo.getHijos();
            int posHijo = 0;
            Nodo hijo;
            Nodo aux;
            for (int x = 0; x <= nodo.getSize() - 1; x++) {
                if (valores[x] != 0) {
                    if (valor < valores[x]) {
                        if ((x - 1) < 0 || valor > valores[x - 1]) {
                            posHijo = x;
                        }
                    } else {
                        if ((x + 1) > (nodo.getSize() - 1) || valor < valores[x + 1]) {
                            posHijo = x + 1;
                        }
                    }
                }
            }
            aux = insertarRecursivo(hijos[posHijo], valor);
            if (aux != hijos[posHijo]) {
                nodo.insertarValor(aux.getValores()[0]);
                //se corren los hijos un espacio mas para introducir al segundo hijo de aux
                for (int x = hijos.length - 1; x > posHijo; x--) {
                    hijos[x] = hijos[x - 1];
                }
                hijos[posHijo] = aux.getHijos()[0];
                hijos[posHijo + 1] = aux.getHijos()[1];
                nodo.setHijos(hijos);
            }
            if (nodo.estaLlena()) {
                return dividir(nodo);
            } else {
                return nodo;
            }
        }
    }
    
    public Nodo dividir(Nodo nodo) {
        int posSubir = nodo.getOrden()/2;
        int valSubir = nodo.getValores()[posSubir];
        Nodo padre = getNodoNuevo(valSubir);
        Nodo hermano;
        if (nodo.esHoja()) {
            hermano = getNodoNuevo(valSubir);
        } else {
            hermano = getNodoNuevo();
        }
        //se elimina el valor que se subira
        nodo.eliminarValorPorPosicion(posSubir);
        //pasar valores al nodo hermano y eliminarlos del nodo principal
        for (int x = posSubir + 1; x <= nodo.getValores().length - 1; x++) {
            hermano.insertarValor(nodo.getValores()[x]);
            nodo.eliminarValorPorPosicion(x);
        }
        //pasar hijos al nodo hermano y eliminarlos del nodo principal
        for (int x = posSubir + 1; x <= nodo.getHijos().length - 1; x++) {
            hermano.insertarHijo(nodo.getHijos()[x]);
            nodo.eliminarHijoPorPosicion(x);
        }
        //se inserta el nodo principal y su hermano al nodo padre
        padre.insertarHijo(nodo);
        padre.insertarHijo(hermano);
        return padre;
    }
    
    public Nodo getNodoNuevo() {
        return new Nodo(orden);
    }
    
    public Nodo getNodoNuevo(int valor) {
        return new Nodo(orden, valor);
    }
    
    public boolean estaVacia() {
        if (raiz == null)
            return true;
        else
            return false;
    }
    
    public Nodo getPadreNodo(Nodo nodo) {
        if (!estaVacia()) {
            return getPadreRecursivo(raiz, nodo);
        } else {
            return null;
        }
    }
    
    private Nodo getPadreRecursivo(Nodo nodo, Nodo hijo) {
        if (nodo == null) {
            return null;
        } else {
            for (int x = 0; x <= nodo.getHijos().length - 1; x++) {
                if (nodo.getHijos()[x] == hijo) {
                    return nodo;
                } else {
                    if (getPadreRecursivo(nodo.getHijos()[x], hijo) != null) {
                        return nodo.getHijos()[x];
                    }
                }
            }
            return null;
        }
    }
    
    public JTextArea mostrarHojas() {
        mostrarHojasRecursivo(raiz);
        
        //System.out.println("");
        return mostrar;
    }
    
    //Busca el valor que se ingresa y devuelve TRUE o FALSE segun exista dicho valor
    public void ayudanteBuscar(int valor){
        
        if(buscarValor(raiz, valor)){
            JOptionPane.showMessageDialog(null, "El numero se encuenta en el árbol");
        } else{
            JOptionPane.showMessageDialog(null, "El numero NO se encuentra en el árbol");
        }
    }
    public boolean buscarValor(Nodo nodo, int valor) {
        if (nodo != null) {
            if (nodo.esHoja()) {
                int[] arreglo = nodo.mostrarArregloValores();
                for (int x = 0; x <= arreglo.length - 1; x++) {
                    if (arreglo[x] == valor) {
                        return true;
                    }

                }
            } else {
                for (int x = 0; x <= nodo.getHijos().length - 1; x++) {
                    if (nodo.getHijos()[x] != null) {
                        mostrarHojasRecursivo(nodo.getHijos()[x]);
                    }
                }
            }
        }
        return false;
    }
    
    private void mostrarHojasRecursivo(Nodo nodo) {
        if (nodo != null) {
            if (nodo.esHoja()) {
                int[] arreglo = nodo.mostrarArregloValores();
                for (int x = 0; x <= arreglo.length - 1; x++) {
                    //System.out.print(arreglo[x] + ", ");
                    mostrar.append(arreglo[x] + ", ");
                    
                }
            } else {
                for (int x = 0; x <= nodo.getHijos().length - 1; x++) {
                    if (nodo.getHijos()[x] != null)
                        mostrarHojasRecursivo(nodo.getHijos()[x]);
                }
            }
        }
    }
    
    
    public void Eliminar1(int valor) {
        eliminar(raiz, valor);
    }

    private void eliminar(Nodo nodo, int valor) {
        if (nodo != null) {
            if (nodo.esHoja()) {
                int[] arreglo = nodo.mostrarArregloValores();
                for (int x = 0; x <= arreglo.length - 1; x++) {
                    if (valor == arreglo[x]) {
                        arreglo[x] = 0;
                        nodo.setValores(arreglo);
                    }
                    System.out.println(arreglo[x]);
                }
            } else {
                for (int x = 0; x <= nodo.getHijos().length - 1; x++) {
                    if (nodo.getHijos()[x] != null) {
                        eliminar(nodo.getHijos()[x], valor);
                    }
                }
            }
        }
    }
    
    public void ayudanteAltura(){
        JOptionPane.showMessageDialog(null, "La altura del árbol es: " + getAltura());
    }

    public int getAltura() {
        int contt = 0;
        Nodo alt = raiz;
        while (alt != null) {
            alt = alt.getHijos()[0];
            contt++;
        }
        return contt;
    }

    public void ayudanteBuscarr(int valor){
        if(buscar(raiz, valor) == null){
            JOptionPane.showMessageDialog(null, "El valor NO se existe en el árbol");
        }else {
            JOptionPane.showMessageDialog(null, "El valor SI existe en el árbol");
        }
    }
    
    public Nodo buscar(Nodo raiz, int valor) {
        int i = 0;//se inicia buscando en el index 0

        while (i < raiz.getSize() && valor > raiz.getValores()[i]) {
            i++; //buscar la posicion del valor buscado
        }

        if (i <= raiz.getSize() && valor == raiz.getValores()[i]) {
            return raiz; //si el valor se encuentra en este nodo, se devuelve el nodo actual
        }

        if (raiz.esHoja()) {   //si se busca en la ultima hoja y no se encuentra el valor se retorna nulo
            return null;
        } else {   //si el nodo no es hoja, se sigue buscando recursivamente
            return buscar(raiz.getHijos()[i], valor);
        }
    }
    

    public void getNivel(int value) {
        int contt = 0;
        Nodo temp = buscar(raiz, value);

        if (temp == null) {
            System.out.println("No existe");
        } else {
            while (temp != null) {
                temp = temp.getHijos()[0];
                contt++;
            }
            int alt = getAltura();
            JOptionPane.showMessageDialog(null, "el nivel en que se encuentra el valor es: " + (alt - contt));
            System.out.println(alt - contt);
        }
    }
    
    public void ayudantePrint(){
        print(raiz);
    }
    

    
    public void print(Nodo nodo) {
        if (nodo != null) {
            if (nodo.esHoja()) {
                int[] arreglo = nodo.mostrarArregloValores();
                for (int x = 0; x <= arreglo.length - 1; x++) {
                    System.out.println(arreglo[x]);
                }
            }

            System.out.println("");

            for (int i = 0; i < nodo.getSize(); i++) {
                System.out.print(nodo.getValores()[i] + " ");//esta parte imprime el nodo raiz
            }


        }
        
    }
    
//    public void ayudaPrintHojas(){
//        imprimirHojas(raiz);
//    }
//    
//    public void imprimirHojas(Nodo nodo){
//        for (int i = 0; i < nodo.getSize(); i++) {
//                System.out.print(nodo.getValores()[i] + " ");//esta parte imprime el nodo raiz
//            }
//        
//            if (!nodo.esHoja())//si en caso la raiz no es hoja;
//            {
//                for (int j = 0; j <= nodo.getSize(); j++)//recorremos de forma recursiva el nodo
//                {
//                    if (nodo.getHijos()[j] != null) //preorden .
//                    {
//                        System.out.println();
//                        print(nodo.getHijos()[j]);    //de la izquierda a la derecha
//                    }
//                }
//            }
//    }
}
