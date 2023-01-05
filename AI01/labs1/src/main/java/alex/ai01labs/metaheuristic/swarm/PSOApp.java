package alex.ai01labs.metaheuristic.swarm;

public class PSOApp {
    public static void main(String[] args){
        ParticleSwarmOptimization algo=new ParticleSwarmOptimization();
        algo.solve();
        algo.showSolution();
    }
}
