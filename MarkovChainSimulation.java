import java.util.Random;

public class MarkovChainSimulation {

    
    private static final double basic_damage = 100.0;       
    private static final double stack_bonus = 0.2;         
    private static final int max_stacks = 5;               
    private static final double trigger_probabailiy = 0.3;
    private static final int attack_rounds = 10;           
    private static final int simulation_runs = 100000;     

    public static void main(String[] args) {
        double[][] transition_matrix = build_transition_matrix(max_stacks, trigger_probabailiy);
        simulate_MarkovChain(transition_matrix);
    }

    
    private static double[][] build_transition_matrix(int maxStacks, double triggerProbability) {
        double[][] matrix = new double[maxStacks + 1][maxStacks + 1];

        for (int i = 0; i <= maxStacks; i++) {
            if (i < maxStacks) {
                matrix[i][i + 1] = triggerProbability;   
                matrix[i][i] = 1 - triggerProbability;    
            } else {
                matrix[i][i] = 1.0;
            }
        }

        return matrix;
    }

    
    private static void simulate_MarkovChain(double[][] transitionMatrix) {
        Random random = new Random();
        int[] finalStackDistribution = new int[max_stacks + 1];
        double totalDamage = 0.0;

        for (int run = 0; run < simulation_runs; run++) {
            int currentState = 0;

            for (int round = 0; round < attack_rounds; round++) {
                double rand = random.nextDouble();
                double cumulativeProbability = 0.0;

                
                for (int nextState = 0; nextState <= max_stacks; nextState++) {
                    cumulativeProbability += transitionMatrix[currentState][nextState];
                    if (rand <= cumulativeProbability) {
                        currentState = nextState;
                        break;
                    }
                }
            }

            
            finalStackDistribution[currentState]++;
            
            totalDamage += basic_damage * (1 + stack_bonus * currentState);
        }

        
        System.out.println("Final Stack Distribution:");
        for (int i = 0; i <= max_stacks; i++) {
            System.out.println("stacks: %.5f\n", i, (finalStackDistribution[i] / (double) simulation_runs) * 100);
        }

        
        double expectedDamage = totalDamage / simulation_runs;
        System.out.println("Expected Critical Damage (ECD): %.5f\n", expectedDamage);
    }
}
