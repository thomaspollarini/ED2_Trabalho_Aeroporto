/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_ed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Alunos
 */
public class Relatorio {

    public static Scanner entrada = new Scanner(System.in);

    public Relatorio() {
    }

    public static void relatorio_1(Map<String, Aeroporto> listaAero) {

        System.out.println("Digite o aeroporto de origem: ");
        String origem = entrada.next().toUpperCase();           //Pega aeroporto de origem

        if (!listaAero.containsKey(origem)) {                   //verifica existencia
            System.out.println("Aeroporto não cadastrado");
            return;
        }

        System.out.println("Digite o aeroporto de destino: ");  //Pega aeroporto de destino
        String destino = entrada.next().toUpperCase();

        if (!listaAero.containsKey(destino)) {                  //verifica existencia
            System.out.println("Aeroporto não cadastrado");
            return;
        }

        System.out.println("\nROTA DE " + origem + " PARA " + destino + ":");

        buscaCaminho(origem, destino, null); //chama função

    }

    public static void relatorio_2(Map<String, Aeroporto> listaAero, Map<String, List<Voos>> voos) {

        System.out.println("Digite o aeroporto de origem: ");   
        String origem = entrada.next().toUpperCase();               //Pega aeroporto de origem

        if (listaAero.containsKey(origem)) {                    //verifica existencia
            System.out.println("\nVOOS SEM PARADAS PARTINDO DE " + origem + ":");
            verificaVoos(origem, voos);
        } else {
            System.out.println("Aeroporto não cadastrado");
            return;
        }

    }

    public static void relatorio_3(Map<String, Aeroporto> listaAero, Map<String, List<Voos>> voos) {

        System.out.println("Digite o aeroporto de origem: ");
        String origem = entrada.next().toUpperCase();           //Pega aeroporto de origem

        if (!listaAero.containsKey(origem)) {
            System.out.println("Aeroporto não cadastrado"); //verifica existencia
            return;
        }

        System.out.println("Digite o aeroporto de destino: ");  //Pega aeroporto de destino
        String destino = entrada.next().toUpperCase();

        if (!listaAero.containsKey(destino)) {                  //verifica existencia
            System.out.println("Aeroporto não cadastrado");
            return;
        }

        System.out.println("\nROTA COM MENOR DISTANCIA DE " + origem + " PARA " + destino + ":");
        menorCaminho(origem, destino, voos, 1); //chama dijkstra modo 1, para distancia

        System.out.println("\nROTA COM MENOR DURAÇÃO DE " + origem + " PARA " + destino + ":");
        menorCaminho(origem, destino, voos, 2); //chama dijkstra modo 2, para duração
        
    }

    public static void relatorio_4(Map<String, Aeroporto> listaAero, Map<String, List<Rotas>> rotas) {

        if (AGM.AGM.size() == listaAero.size()) { //compara numero de vertices da AGM com número total de aeroportos
            System.out.println("\nÉ possível atingir qualquer aeroporto a partir de qualquer aeroporto");
        }

        System.out.println("\nVÉRTICES PONTES: ");

        for (String origem : listaAero.keySet()) {          //chama algoritmo de verificação de nó ponte para todos aeroportos
            Set<String> percorridos = new HashSet<String>();
            percorridos.add(origem);
            verificaVerticesPontes(origem, percorridos, rotas);
            if (percorridos.size() != listaAero.size()) { //se aeroportos percorridos forem menores doq total de aeroportos 
                System.out.println(origem);               //o aeroporto de origem é um nó ponte
            }
        }
    }

    public static void relatorio_5(Map<String, Aeroporto> listaAero) {

        System.out.println("Digite o aeroporto de origem: ");   //Pega aeroporto de origem
        String origem = entrada.next().toUpperCase();

        if (!listaAero.containsKey(origem)) {
            System.out.println("Aeroporto não cadastrado"); //verifica existencia
            return;
        }
        
        System.out.println("\nROTA POR TODOS AEROPORTOS COM RETORNO AO ORIGEM: ");
        int aresta=encontraRota(origem,null,0);
        
        if(aresta>listaAero.size()){ //se numero de arestas é maior q número de aeroportos, ent a rota repetiu arestas, logo n é circuito
            System.out.println("\nNão é circuito Hamiltoniano");
        }    

    }
    
    private static int buscaCaminho(String origem, String destinoB, String ant) {

        int retorno = 1;
        int i = 0;
        while (i < AGM.AGM.get(destinoB).size()) { //percorre rotas que saem do aeroporto de destino

            if (!AGM.AGM.get(destinoB).get(i).destino.equals(ant) || ant == null) { //se rota for igual origem anterior não percorre

                if (AGM.AGM.get(destinoB).get(i).destino.equals(origem) || retorno == 0) { //se destino chega a origem buscada ou se retorno for 0
                    System.out.println(AGM.AGM.get(destinoB).get(i).destino                 //imprime caminho e retorna 0
                            + " -> " + AGM.AGM.get(destinoB).get(i).origem);
                    return 0;
                } else {                                                                
                    retorno = buscaCaminho(origem, AGM.AGM.get(destinoB).get(i).destino, destinoB); //caso destino não chegue a origem
                    if (retorno == 0) {                                                             //chama recursão para caminhar nas rotas
                        System.out.println(AGM.AGM.get(destinoB).get(i).destino
                                + " -> " + AGM.AGM.get(destinoB).get(i).origem);//se retorno da recursão for 0 todas as recursões retornam imprimindo
                        return 0;
                    }
                }
            }
            i++;
        }

        return 1;

    }

    private static void verificaVoos(String origem, Map<String, List<Voos>> voos) {

        for (int i = 0; i < voos.get(origem).size(); i++) { //percorre todos os voos a partir do aeroporto de orige
            if (voos.get(origem).get(i).qtdParadas == 0) {  //caso n tenha conexões ou paradas, imprime o voo
                System.out.println("Número do Voo: " + voos.get(origem).get(i).num
                        + " - Destino: " + voos.get(origem).get(i).destino);
            }
        }

    }
    
    private static void menorCaminho(String origem, String destino, Map<String, List<Voos>> voos, int modo) {

        ArrayList<String> atuais = new ArrayList<String>(); //lista de vertices a percorrer
        atuais.add(origem);

        Map<String, String> anteriores = new HashMap<String, String>(); //Map contendo o vertice anterior aos vertices
        Map<String, Boolean> visitados = new HashMap<String, Boolean>(); //Map que marca se o vertice foi visitado
        Map<String, Integer> distancias = new HashMap<String, Integer>(); //Map que guarda distancia da origem ate os vertices

        for (String key : voos.keySet()) { //inicializa listas e maps
            if (key.equals(origem)) {   //se for origem, marca como visitado e distancia = 0
                visitados.put(key, true);
                distancias.put(key, 0);
            } else {                        //senão marca como false e inicializa distancia para comparação
                visitados.put(key, false);
                distancias.put(key, 9999999);
            }
            anteriores.put(key, "");
        }

        while (atuais.size() != 0) { //roda enquanto todos os vertices não forem visitados

            String key = atuais.get(0);  //pega vertice atual

            for (int i = 0; i < voos.get(key).size(); i++) { //percorre todos os voos partindo do vertice atual
                String vizinho = voos.get(key).get(i).destino;  //armazena destino de um voo
                int dist = 0;

                if (modo == 1) {
                    dist = voos.get(key).get(i).distancia;  //pega distancia do voo no modo 1
                } else {
                    if (modo == 2) {
                        dist = (int) voos.get(key).get(i).duracao.toMinutes()*60;   //pega a duração do voo no modo 2
                    }
                }

                if (!visitados.get(vizinho)) {    //se vizinho n foi visitado, marca como visitado e adiciona na lista atuais, 
                    visitados.put(vizinho, true);   //para ser percorrido em seguida
                    atuais.add(vizinho);
                }

                if (dist + distancias.get(key) < distancias.get(vizinho)) {     //se distancia do nó atual até o vizinho + distancia do nó atual até origem
                    distancias.put(vizinho, dist + distancias.get(key));        //for menor que distancia do vizinho até origem, 
                    anteriores.put(vizinho, key);                               //atualiza distancia do vizinho e adiciona nó atual ao anterior do vizinho
                }
            }
            atuais.remove(0); //remove nó atual dos atuais
        }

        imprimirRota(anteriores, destino); //chama função para imprimir rotas

        if (modo == 1) {
            System.out.println("Distância total: " + distancias.get(destino) + " km"); //imprime distancia total no modo 1
        } else {
            if (modo == 2) {        //imprime duração total no modo 2
                int dist = distancias.get(destino); 
                int disth = dist / 3600;
                int distm = (dist / 60) - (disth * 60);

                System.out.println("Tempo total: " + disth + "h" + distm + "m");
            }
        }
    }

    private static void imprimirRota(Map<String, String> ant, String destino) {

        ArrayList<String> imprime = new ArrayList<String>();

        while (!ant.get(destino).equals("")) {  //percorre do destino até a origem adioconando todas as arestas entre eles na lista de impressão
            imprime.add(ant.get(destino) + " -> " + destino);  //vertices que estão no map de anteriores
            destino = ant.get(destino);
        }

        for (int i = imprime.size() - 1; i >= 0; i--) {
            System.out.println(imprime.get(i));  //imprime os vertices em ordem
        }
    }
    
    private static void verificaVerticesPontes(String origem,Set<String> percorridos, Map<String, List<Rotas>> rotas) {
        
        if(percorridos.size()==1){                              //faz primeira rota saindo do aeroporto de origem
            percorridos.add(rotas.get(origem).get(0).destino);
            verificaVerticesPontes(rotas.get(origem).get(0).destino,percorridos,rotas);
        }else{
            int i=0;
            while(i<rotas.get(origem).size()){                  //percorre todos os nós sem poder voltar aos percorridos
                if(!percorridos.contains(rotas.get(origem).get(i).destino)){
                    percorridos.add(rotas.get(origem).get(i).destino);
                    verificaVerticesPontes(rotas.get(origem).get(i).destino,percorridos,rotas); //caso não alcance um nó é porque é necessario
                }                                                                               //passar pelo aeroporto origem para chegar nele
                i++;                                                                            //logo é um vertice ponte
            }
        }
    }

    private static int encontraRota(String origem, String ant,int cont) {

        int i = 0;
        while (i < AGM.AGM.get(origem).size()) { //percorre toda AGM
            if(!AGM.AGM.get(origem).get(i).destino.equals(ant)){ //não retorna para aeroportos do qual veio
                cont++;
                System.out.println(origem+ " -> "+ AGM.AGM.get(origem).get(i).destino); //imprime aresta
                cont=encontraRota(AGM.AGM.get(origem).get(i).destino,origem,cont);
            }
            i++;
        }
        
        if(ant!=null){
            System.out.println(origem+ " -> "+ ant);    //caso não haja rotas retorna pela anterior e imprime volta
            cont++;
        }
        
        return cont; //contador de arestas
    }

}
