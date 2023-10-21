/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_ed;

import java.time.Duration;

/**
 *
 * @author windows
 */
public class Voos {
    public String num;
    public String origem;
    public String destino;
    public String hrPartida;
    public String hrChegada;
    public int qtdParadas;
    public int distancia;
    public Duration duracao;

    public Voos() {
        
    }

    public Voos(String num, String origem, String destino, String hrPartida, String hrChegada, int qtdParadas, int distancia) {
        this.num = num;
        this.origem = origem;
        this.destino = destino;
        this.hrPartida = hrPartida;
        this.hrChegada = hrChegada;
        this.qtdParadas = qtdParadas;
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        
        int durH=(int)duracao.toHours();
        int durM=(int)duracao.toMinutes()-(durH*60);
        
        return "("+" Num: " +num +", Dest: " + destino +  ", Dist: " +  distancia +  " km, Paradas: " + qtdParadas+", Duração: "+durH+"h"+durM+"m )";
    }
    
    
    
}
