
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author viviane
 */
public class Tabuleiro15 {
    
     //contem o tabuleiro atual, o tabuleiro pai e o custo
        public static Map<Integer[], ArrayList<Integer[]>> aberto = new HashMap<Integer[], ArrayList<Integer[]>>();
        //contem o tabuleiro atual e o pai
        public static Map<Integer[], ArrayList<Integer[]>> fechado = new HashMap<Integer[], ArrayList<Integer[]>>();

        static int filhosGerados = 0;
        //resultado final buscado
        static final int[] solucao = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
    
    public static void main(String[] args){
         
      
        //pega o tabuleiro inicial e coloca numa matriz
        /*Scanner input = new Scanner(System.in);
        System.out.println("Digite os valores: ");
        String tab = input.nextLine();
        String array[] = tab.split(" ");
        int[][] atual = new int[4][4];
        System.out.println(tab);
        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                atual[i][j] = Integer.parseInt(array[i+j]);
            }
        }*/
   //     Integer[] atual = {5, 1, 2, 3, 0, 6, 7, 4, 9, 10, 11, 8, 13, 14, 15, 12};
//        Integer[] atual = {2, 3, 4, 7, 1, 5, 6, 8, 10, 11, 12, 15, 9, 0, 13, 14};
          Integer[] atual = {5, 1, 2, 3, 6, 7, 11, 4, 13, 9, 10, 8, 14, 15, 12, 0};       
        
        
             ArrayList<Integer[]> atualPai = new ArrayList<Integer[]>();
        

        Integer[] pai = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
            atualPai.add(pai);

        Integer[] pesoReal = {0};  
        atualPai.add(pesoReal);
        Integer[] pesoHeuristica = {pesoH(atual)};  
        atualPai.add(pesoHeuristica);        
        

        aberto.put(atual, atualPai);
        aEstrela(atual, atualPai);
    }

     private static void aEstrela(Integer[] atual, ArrayList<Integer[]> pai) {
         pai.remove(2);
         
         aberto.remove(atual);
         fechado.put(atual, pai);
             
         if(ehSolucao(atual)){
             
             imprimeCaminho(atual);
             System.exit(0);
         }else{
             
             
             gerafilhos(atual);
             Integer[] menor = menor();
             aEstrela(menor, aberto.get(menor));
         }
     }
     
     //gera todos os filhos possíveis de um determinado nó e os adiciona na lista dos abertos.
    private static void gerafilhos(Integer[] atual) {
        Integer[] filho1 = new Integer[16];
        Integer[] filho2 = new Integer[16];
        Integer[] filho3 = new Integer[16];
        Integer[] filho4 = new Integer[16];
        for(int i = 0; i<16; i++){
                filho1[i] = atual[i];
                filho2[i] = atual[i];
                filho3[i] = atual[i];
                filho4[i] = atual[i];
            
        }
        for(int i=0; i<16; i++){
                    if (atual[i]==0){
                        if(i>3){
                            filho1[i] = filho1[i-4];
                            filho1[i-4] = 0;
                            adiciona(filho1, atual);
                        }
                        if(i<12){
                            filho2[i] = filho2[i+4];
                            filho2[i+4] = 0;                            
                            adiciona(filho2, atual);
                        }
                        if(i%4!=0){
                            filho3[i] = filho3[i-1];
                            filho3[i-1] = 0;                            
                            adiciona(filho3, atual);
                        }
                        if(i%4!=3){
                            filho4[i] = filho4[i+1];
                            filho4[i+1] = 0;    
                            adiciona(filho4, atual);
                        }
                        break;
                    }
                }

            }
    
    
    
    

        private static void adiciona(Integer[] gerado, Integer[] pai){
                            //peso da heuristica
                            //peso até agora

                            if(fechado.containsKey(gerado)==false){
                            if(aberto.containsKey(gerado)){
                            Integer[] real = {fechado.get(pai).get(1)[0] + 1};
                            Integer[] heuristica = {pesoH(gerado)};
                            
                            ArrayList<Integer[]> s = new ArrayList<Integer[]>();
                                    s.add(pai);
                                    s.add(real);
                                    s.add(heuristica);

                                if(aberto.get(gerado).get(1)[0]+aberto.get(gerado).get(2)[0]>(real[0]+heuristica[0])){
                                    
                                    aberto.replace(gerado, s);
                                }
                            }else{
                                                            Integer[] real = {fechado.get(pai).get(1)[0] + 1};
                            Integer[] heuristica = {pesoH(gerado)};
                            
                            ArrayList<Integer[]> s = new ArrayList<Integer[]>();
                                    s.add(pai);
                                    s.add(real);
                                    s.add(heuristica);
                                
                                aberto.put(gerado, s);        
                                filhosGerados++;
                            }
                            }

    }
    //retorna o menor valor da lista dos valores abertos
    private static Integer[] menor() {
                    Integer min = Integer.MAX_VALUE;
                    Integer[] menor = new Integer[16];
                    for (Entry<Integer[], ArrayList<Integer[]>> entrada : aberto.entrySet()) {
                        int novo = entrada.getValue().get(1)[0]+entrada.getValue().get(2)[0];
			if (novo<min) {
                            min = novo;
                            menor = entrada.getKey();
			}
                    }
                    return menor;
    }

    private static Integer pesoH(Integer[] gerado) {
        Integer peso = 0;
        peso = h1(gerado);
        //peso = h2(gerado);
        //peso = h3(gerado);
        //peso = h4(gerado);
        return peso;
    }

    private static Integer h1(Integer[] gerado) {
        Integer fora = 0;
        for(int i=0; i<16; i++){
                if(gerado[i]!=solucao[i]){
                    fora++;
                }
            
        }
        return fora;
    }

    private static boolean ehSolucao(Integer[] atual) {
        for(int i = 0; i<16; i++){
                if(atual[i]!=solucao[i]){
                    return false;
                }
            }
        return true;
    }

    private static void imprimeCaminho(Integer[] atual) {
        System.out.println(fechado.get(atual).get(1)[0]);
        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                System.out.print(atual[4*i+j]+" ");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("filhos: "+ filhosGerados);
        if(fechado.get(atual).get(0)[0]!=-1){
        imprimeCaminho(fechado.get(atual).get(0));
        }
    }

    private static Integer h2(Integer[] gerado) {
        Integer fora = 0;
        for(int i=0; i<15; i++){
            if (gerado[i+1]!=gerado[i]+1){
                fora++;
            }
        }
        return fora;
    }





}


