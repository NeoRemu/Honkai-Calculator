import java.util.Random;

public class MarkovChainSimulation {

    
    private static final double basic_damage = 100.0;       
    private static final double stack_bonous = 0.2;         
    private static final int max_stacks = 5;               
    private static final double trigger_probabailiy = 0.3;
    private static final int attack_rounds = 10;           
    private static final int simulation_runs = 100000;     

    public static void main(String[] args) {
        double[][] transitionMatrix = buildTransitionMatrix(MAX_STACKS, TRIGGER_PROBABILITY);
        simulateMarkovChain(transitionMatrix);
    }

    
    private static double[][] buildTransitionMatrix(int maxStacks, double triggerProbability) {
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

    
    private static void simulateMarkovChain(double[][] transitionMatrix) {
        Random random = new Random();
        int[] finalStackDistribution = new int[MAX_STACKS + 1];
        double totalDamage = 0.0;

        for (int run = 0; run < SIMULATION_RUNS; run++) {
            int currentState = 0;

            for (int round = 0; round < ATTACK_ROUNDS; round++) {
                double rand = random.nextDouble();
                double cumulativeProbability = 0.0;

                
                for (int nextState = 0; nextState <= MAX_STACKS; nextState++) {
                    cumulativeProbability += transitionMatrix[currentState][nextState];
                    if (rand <= cumulativeProbability) {
                        currentState = nextState;
                        break;
                    }
                }
            }

            
            finalStackDistribution[currentState]++;
            
            totalDamage += BASE_DAMAGE * (1 + STACK_BONUS * currentState);
        }

        
        System.out.println("Final Stack Distribution:");
        for (int i = 0; i <= MAX_STACKS; i++) {
            System.out.printf("stacks: ", i, (finalStackDistribution[i] / (double) SIMULATION_RUNS) * 100);
        }

        
        double expectedDamage = totalDamage / SIMULATION_RUNS;
        System.out.printf("Expected Critical Damage (ECD): ", expectedDamage);
    }
}
