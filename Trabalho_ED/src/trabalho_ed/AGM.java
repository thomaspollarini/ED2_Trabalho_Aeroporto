/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_ed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alunos
 */
public class AGM {
    
    public static Map<String,List<Rotas>> AGM =new HashMap<String,List<Rotas>>();
    
    public static void criaAGM(Map<String,List<Rotas>> grafo){
        
        ArrayList<String> T= new ArrayList<String>();
        T.add("ABQ");
        
        
        algPrim(T, grafo);
        System.out.println("");
        
    }
    
    private static void algPrim(ArrayList<String> T, Map<String,List<Rotas>> grafo) {

        ArrayList<Rotas> AGMTemp =new ArrayList<Rotas>();
        while (T.size() != grafo.size()){//repete algoritmo até todos os vertices serem percorridos

            String node = T.get(T.size() - 1); //pega vertice do topo da lista de percorridos
            removeT(node, grafo); //chama função para remover arestas q chegam ao nó percorrido

            int menor = 99999;

            String origem="",destino="";
            int i = 0;
            int tamT = T.size();  //verifica tamanho da lista de percorridos
            while (i < tamT) {      //percorre lista de percorridos
                String vertOrigem = T.get(i);   //seleciona vertice de origem

                for(int j=0;j<grafo.get(vertOrigem).size();j++){
                    String vertDestino= grafo.get(vertOrigem).get(j).destino;
                    int peso= grafo.get(vertOrigem).get(j).distancia;
                    
                    if (peso < menor) {     //compara os vertices para encontrar o que tem menor peso
                            menor = peso;
                            origem=vertOrigem;
                            destino=vertDestino;
                    }
                }
                i++;
            }
            
            AGMTemp.add(new Rotas(origem,destino,menor)); //cria lista de rotas da agm

            T.add(destino);

        }
        
        AGM= Principal.criaGrafoRotas(AGMTemp); //cria grafo baseado na lista de rotas

    }
    
    private static void removeT(String nome,  Map<String,List<Rotas>> grafo ) {

        for(String key : grafo.keySet()){  //percorre todas as arestas removendo as que tem origem como destino
            for(int i=0;i<grafo.get(key).size();i++){
                if(grafo.get(key).get(i).destino.equals(nome)){
                    grafo.get(key).remove(i);
                }
            }
        }
    }
    
    public static void imprimeAGM(){
        for(String key : AGM.keySet()){ //imprime AGM
            System.out.println(key + " -> " + AGM.get(key));
        }
        
    }
    
    
}
