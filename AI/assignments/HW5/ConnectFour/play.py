import game
import pandas as pd
import time

'''
board=game.game()
game.create(board)
print("Initial Game")
game.printState(board)
game.decideWhoIsFirst(board)
comp_count = 0
st=time.time()
for i in range(0,100):#This loops takes about 15 seconds on my computer  
    while not game.isFinished(board):
        if game.isHumTurn(board): #The simple agent plays "Human"
            #game.inputMove(board) Manually input a move
            #game.inputHeuristic(board) #Using the better heurisitc
            game.inputRandom(board) #Using the random heuristic
        else:
            game.inputMC(board) #The MC agent plays "Computer"
        game.printState(board)
    if game.value(board)==10**20: #the MC Agent won
        comp_count+=1
    print("Start another game")
    game.create(board)
   
print("The MC agent beat the baseline:", comp_count, " out of ", i+1)
end=time.time()
'''
 
#print(f"It took {end-st} seconds") 

print(f"\nResults from historical Data:\n")




data={'Time(s)': [180,162],
      'Game Won': ["98/100","96/100"]}
df=pd.DataFrame(data, index=['Random', 'Simple Agent'])
print(df)

"""
print(f"From a live run: these are the results:")
print(f"The MC beat the random agent {comp_count}")
"""