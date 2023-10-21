/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_ed;

/**
 *
 * @author windows
 */
public class Rotas {
    
    public String origem;
    public String destino;
    public int distancia;

    public Rotas() {
    }

    public Rotas(String origem, String destino, int distancia) {
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        return "( "+ origem +" "+ destino +", "+  distancia+" km )";
    }
    
    
    
}
