/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_ed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author windows
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Map<String,Aeroporto> listaAero = new HashMap<String,Aeroporto>(); //cria Map de aeroportos para armazenar dados da leitura
        ArrayList<Rotas> listaRotas = new ArrayList<Rotas>();   //cria lista de rotas para armazenar dados da leitura
        ArrayList<Voos> listaVoos = new ArrayList<Voos>();      //cria lista de voos para armazenar dados da leitura
        
        lerArquivo(listaAero, listaRotas, listaVoos); //chama função para ler arquivo
        calculaDistRotas(listaAero,listaRotas); //função para calcular distancia entre aeroportos nas rotas
        calculaDistVoos(listaAero,listaVoos);   //função para calcular distancia entre aeroportos nos voos
        calculaDuracao(listaAero,listaVoos);    //função para calcular duração dos voos
        
        Map<String,List<Rotas>> grafoRotas = criaGrafoRotas(listaRotas); //cria grafo de rotas
        Map<String,List<Voos>> grafoVoos = criaGrafoVoos(listaVoos); //cria grafo de voos
        
        Map<String,List<Rotas>> grafoAGM = criaGrafoRotas(listaRotas); //cria grafo de rotas para criar AGM
        AGM.criaAGM(grafoAGM); //cria AGM
        
        int x=1;
        while(x!=0){
            System.out.println("\n====================RELATÓRIOS===================="
                    + "\n1-Relatorio 1: Rota entre dois aeroportos"
                    + "\n2-Relatorio 2: Voos s/ escalas de um aeroporto"
                    + "\n3-Relatorio 3: Viagem com menor distancia e tempo"
                    + "\n4-Relatorio 4: Conexão entre aeroportos"
                    + "\n5-Relatorio 5: Rota de um aeroportos para todos"
                    + "\n6-Imprime Grafos"
                    + "\n0-Sair");
            
            x=Relatorio.entrada.nextInt();
            
            switch(x){
                case 1:
                    Relatorio.relatorio_1(listaAero); 
                break;
                
                case 2:
                    Relatorio.relatorio_2(listaAero,grafoVoos);
                break;  
                
                case 3:
                    Relatorio.relatorio_3(listaAero, grafoVoos);
                break;    
                
                case 4:
                    Relatorio.relatorio_4(listaAero, grafoRotas);
                break;    
                
                case 5:
                    Relatorio.relatorio_5(listaAero);
                break;
                
                case 6:
                    ImprimeGrafoRotas(grafoRotas);
                    ImprimeGrafoVoos(grafoVoos);
                break;
                
                case 0:
                    System.out.println("Finalizando...");
                return;
                
                default:
                    System.out.println("ERRO: Opção invalida");
                break;
            }
        }
    }

    public static void lerArquivo(Map<String,Aeroporto> listaAero, ArrayList<Rotas> listaRotas, ArrayList<Voos> listaVoos) {

        try {   
            FileReader arq = new FileReader(".\\src\\Voos.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";
            int i = 0;
            while (!(linha = lerArq.readLine()).equals("!")) { //armazena cada linha do arquivo até primeiro !
                String[] split = linha.split(",");     //separa variaveis da linha por ,

                Aeroporto aero = new Aeroporto();

                aero.nome = split[0].toString();  //adiciona variaveis em um objeto aeroporto
                aero.fuso = split[1].toString();
                aero.cordX = Integer.parseInt(split[2].toString());
                aero.cordY = Integer.parseInt(split[3].toString());
                listaAero.put(aero.nome,aero); //adiciona aeroporto na lista de aeroportos

            }

            while (!(linha = lerArq.readLine()).equals("!")) { //armazena cada linha do arquivo até primeiro !
                String[] split = linha.split(","); //separa variaveis da linha por ,

                Rotas rota = new Rotas();   //adiciona variaveis em um objeto rotas
                rota.origem = split[0].toString();
                rota.destino = split[1].toString();

                listaRotas.add(rota); //adiciona rota na lista de rotas
            }

            while (!(linha = lerArq.readLine()).equals("!")) { //armazena cada linha do arquivo até primeiro !
                String[] split = linha.split(",");  //separa variaveis da linha por ,

                Voos voo = new Voos();    //adiciona variaveis em um objeto voos
                voo.num = split[0].toString();
                voo.origem = split[1].toString();
                voo.hrPartida = split[2].toString()+"M";
                voo.destino = split[3].toString();
                voo.hrChegada = split[4].toString()+"M";
                voo.qtdParadas = Integer.parseInt(split[5].toString());
                
                listaVoos.add(voo); //adiciona voo na lista de voos
            }
            arq.close();
            lerArq.close(); //fecha buffers
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void calculaDistRotas(Map<String,Aeroporto> listaAero, ArrayList<Rotas> listaRotas) {
        
        for(int i=0;i<listaRotas.size();i++){ //percorre toda lista de rotas

            Aeroporto aero1 = listaAero.get(listaRotas.get(i).origem);  //pega aeroporto de origem
            Aeroporto aero2 = listaAero.get(listaRotas.get(i).destino); //pega aeroporto de destino

            int distancia=(int)Math.sqrt(Math.pow((aero1.cordX - aero2.cordX), 2) + Math.pow((aero1.cordY - aero2.cordY), 2));
            //formula para calcular distancia baseado no teorema de Pitagoras,
            //se cria cria um triangulo pegando a diferença das cordenadas x e y
            //onde a hipotenusa é a distancia entre os aeroportos
            
            listaRotas.get(i).distancia = distancia*10; //coloca na escala real
        }
    }
    
    public static void calculaDistVoos(Map<String,Aeroporto> listaAero, ArrayList<Voos> listaVoos){
        for(int i=0;i<listaVoos.size();i++){ //percorre toda lista de voos

            Aeroporto aero1 = listaAero.get(listaVoos.get(i).origem);//pega aeroporto de origem
            Aeroporto aero2 = listaAero.get(listaVoos.get(i).destino);//pega aeroporto de destino

            int distancia=(int)Math.sqrt(Math.pow((aero1.cordX - aero2.cordX), 2) + Math.pow((aero1.cordY - aero2.cordY), 2));
            //mesma formula usada na distancia das rotas

            listaVoos.get(i).distancia = distancia*10;//coloca na escala real
        }
    }
    
    public static void calculaDuracao(Map<String,Aeroporto> listaAero, ArrayList<Voos> listaVoos){
        
        for(int i=0;i<listaVoos.size();i++){ //percorre toda lista de voos

            Aeroporto aero1 = listaAero.get(listaVoos.get(i).origem); //pega aeroporto de origem
            Aeroporto aero2 = listaAero.get(listaVoos.get(i).destino);//pega aeroporto de destino
            
            ZoneOffset fusoP= ZoneOffset.of(aero1.fuso);  //pega fuso horario do primeiro aeroporto
            ZoneOffset fusoC= ZoneOffset.of(aero2.fuso);  //pega fuso horario do segundo aeroporto
            
            LocalTime tempoP= LocalTime.parse(listaVoos.get(i).hrPartida.toUpperCase(), DateTimeFormatter.ofPattern("hmma")); //pega hora de partida do voo
            LocalTime tempoC= LocalTime.parse(listaVoos.get(i).hrChegada.toUpperCase(), DateTimeFormatter.ofPattern("hmma")); //pega hora de chegada do voo
            
            OffsetTime partida= OffsetTime.of(tempoP,fusoP);  //atualiza hora de partida de acordo com fuso horario
            OffsetTime chegada= OffsetTime.of(tempoC,fusoC);  //atualiza hora de chegada de acordo com fuso horario
            
            Duration duracao=Duration.between(partida,chegada); //pega diferença de tempo entre as horas
            
            if(duracao.isNegative()){  //caso as horas estejam em dias diferentes o resultado será negativo
                duracao=duracao.plusDays(1);    //nesses casos basta somar mais 24h ao resultado, para chegar na diferença real
            }
            
            listaVoos.get(i).duracao= duracao; //armazena duração nos voos
        }
        
    }

    public static Map<String, List<Rotas>> criaGrafoRotas(ArrayList<Rotas> listaRotas) {

        Map<String, List<Rotas>> grafo = new HashMap<String, List<Rotas>>(); //Map que armazenara grafo

        for(int i=0;i<listaRotas.size();i++){ //percorre todas as rotas na lista
            if(!grafo.containsKey(listaRotas.get(i).origem)){   //se origem da rota n tem indice no Map 
                List<Rotas> rotas= new ArrayList<>();           //cria-se uma nova lista de rotas
                
                grafo.put(listaRotas.get(i).origem, rotas);     //adiciona rota atual
                grafo.get(listaRotas.get(i).origem).add(listaRotas.get(i)); //cria indice apontando para lista
                
            }else{
                grafo.get(listaRotas.get(i).origem).add(listaRotas.get(i)); //caso ja exista um indice, adiciona a rota na lista
            }
            
            if(!grafo.containsKey(listaRotas.get(i).destino)){  //faz a rota inversa, ja q é um grafo não-direcional
                List<Rotas> rotas= new ArrayList<>();
                
                grafo.put(listaRotas.get(i).destino, rotas);
                grafo.get(listaRotas.get(i).destino).add(new Rotas(listaRotas.get(i).destino,listaRotas.get(i).origem,listaRotas.get(i).distancia));
                
            }else{
                grafo.get(listaRotas.get(i).destino).add(new Rotas(listaRotas.get(i).destino,listaRotas.get(i).origem,listaRotas.get(i).distancia));
            }
        }
        
        return grafo;
    }
    
    public static Map<String, List<Voos>> criaGrafoVoos(ArrayList<Voos> listaVoos) {

        Map<String, List<Voos>> grafo = new HashMap<String, List<Voos>>(); //Map que armazenara grafo

        for(int i=0;i<listaVoos.size();i++){ //percorre todos os voos na lista
            if(!grafo.containsKey(listaVoos.get(i).origem)){    //se origem do voo n tem indice no Map 
                List<Voos> voos= new ArrayList<>();     //cria-se uma nova lista de voos
                
                grafo.put(listaVoos.get(i).origem, voos); //adiciona voo atual
                grafo.get(listaVoos.get(i).origem).add(listaVoos.get(i)); //cria indice apontando para lista
                
            }else{
                grafo.get(listaVoos.get(i).origem).add(listaVoos.get(i)); //caso ja exista um indice, adiciona o voo na lista
            }
        }
        
        return grafo;
    }
    
    public static void ImprimeGrafoRotas(Map<String,List<Rotas>> grafo) {
        System.out.println("\nGRAFO DE ROTAS: ");  //imprime grafo de rotas
        for(String key : grafo.keySet()){
            System.out.println(key + " -> " + grafo.get(key));
        }
    }
    
    public static void ImprimeGrafoVoos(Map<String,List<Voos>> grafo) {
        System.out.println("\nGRAFO DE VOOS: "); //imprime grafo de voos

        for(String key : grafo.keySet()){
            System.out.println(key + " -> " + grafo.get(key));
        }
    }
}
