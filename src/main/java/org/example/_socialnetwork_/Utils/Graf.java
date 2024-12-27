package org.example._socialnetwork_.Utils;

import java.util.*;

public class Graf {
    private Map<Long, List<Long>> lista = new HashMap<>();

    public void addMuchie(Long nod1, Long nod2){
        lista.putIfAbsent(nod1, new ArrayList<>());
        lista.putIfAbsent(nod2, new ArrayList<>());
        lista.get(nod1).add(nod2);
        lista.get(nod2).add(nod1);
    }

    private void DFS(Long nod, Set<Long> visited){
        visited.add(nod);
        for (Long i : lista.get(nod)){
            if(!visited.contains(i)){
                DFS(i,visited);
            }
        }
    }

    private void DFS(Long nod, List<Long> noduri, Set<Long> visited){
        visited.add(nod);
        noduri.add(nod);
        for (Long i : lista.get(nod)){
            if(!visited.contains(i)){
                DFS(i, noduri, visited);
            }
        }
    }

    public int NrComponenteConexe(){
        Set<Long> visited = new HashSet<>();
        int componenteConexe = 0;

        for (Long nod : lista.keySet()){
            if (!visited.contains(nod)){
                DFS(nod, visited);
                componenteConexe++;
            }
        }

        return componenteConexe;
    }

    public List<Long> CeaMaiMareComponentaConexa(){
        Set<Long> visited = new HashSet<>();
        List<Long> componenteConexa = new ArrayList<>();
        List<Long> componentaConexaMax = new ArrayList<>();

        for (Long nod : lista.keySet()){
            if (!visited.contains(nod)){
                componenteConexa.clear();
                DFS(nod, componenteConexa, visited);

                if (componenteConexa.size() > componentaConexaMax.size()){
                    componentaConexaMax = new ArrayList<>(componenteConexa);
                }
            }
        }
        return componentaConexaMax;
    }
}
