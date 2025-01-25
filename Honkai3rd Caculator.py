import random

basic_damage = 100.0
stack_bonus = 0.2
max_stacks = 5
trigger_probability = 0.3
attack_rounds = 10
simulation_runs = 100000

def build_transition_matrix(max_stacks, trigger_probability):
    matrix = [[0.0 for _ in range(max_stacks + 1)] for _ in range(max_stacks + 1)]
    
    for i in range(max_stacks + 1):
        if i < max_stacks:
            matrix[i][i + 1] = trigger_probability  
            matrix[i][i] = 1 - trigger_probability  
        else:
            matrix[i][i] = 1.0 

    return matrix

def simulate_markov_chain(transition_matrix):
    final_stack_distribution = [0] * (max_stacks + 1)
    total_damage = 0.0

    for _ in range(simulation_runs):
        current_state = 0  
        
        for _ in range(attack_rounds):
            rand = random.random()
            cumulative_probability = 0.0
            
            for next_state in range(max_stacks + 1):
                cumulative_probability += transition_matrix[current_state][next_state]
                if rand <= cumulative_probability:
                    current_state = next_state
                    break

        
        final_stack_distribution[current_state] += 1
        
       
        total_damage += basic_damage * (1 + stack_bonus * current_state)

    
print("Final Stack Distribution:")
for i in range(max_stacks + 1):
    print(f"Stacks: {i}, Percentage: {final_stack_distribution[i] / simulation_runs * 100:.5f}%")

print(f"Expected Critical Damage: {total_damage / simulation_runs:.5f}")