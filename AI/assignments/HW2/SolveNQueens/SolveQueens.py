'''
Created on Sep 20, 2023

@author: eohay
'''

columns = [] #columns is the locations for each of the queens
# columns[r] is a number c if a queen is placed at row r and column c.
size = 4

import random #hint -- you will need this for the following code: column=random.randrange(0,size)
import pandas as pd
import time

def place_n_queens(size, lst): #Function to randomly place queens
    lst.clear()
    row = 0
    while row < size:
        column=random.randrange(0,size)
        lst.append(column)
        row+=1 
    

def display(): #Display the board
    for row in range(len(columns)):
        for column in range(size):
            if column == columns[row]:
                print('♛', end=' ')
            else:
                print(' .', end=' ')
        print()
        
        
        
# Functions to be used with base dfs and backtracking to solve the n-queens problem       
def solve_queen_base_dfs(size):
    columns.clear()
    number_of_moves = 0 
    number_of_iterations = 0
    row = 0
    column = 0
    # iterate over rows of board
    while True: #Keep using dfs until we get a solution
        #place queen in next row
        #print(columns)
        #print("I have ", row, " number of queens put down")
        #display()
        #print(number_of_moves)
        while column < size: # While we have not placed all queens
            number_of_iterations+=1
            
            if modified_next_row_safe(column, columns):
                place_in_next_row(column)
                number_of_moves+=1
                
                row += 1
                column = 0
                break
            else:
                column += 1
                
        # if I could not find an open column or if board is full
        if (column == size or row == size):
            number_of_iterations+=1
            # if board is full, we have a solution
            if row == size:
                #display()
                #print(number_of_moves)
                return number_of_iterations, number_of_moves
            # I couldn't find a solution so I now backtrack
            prev_column = remove_in_current_row()
            number_of_moves+=1
            if (prev_column == -1): #I backtracked past column 1
                print("There are no solutions")
                #print(number_of_moves)
                return number_of_iterations, number_of_moves
            # try previous row again
            row -= 1
            # start checking at column = (1 + value of column in previous row)
            column = 1 + prev_column


def place_in_next_row(column):
    columns.append(column)

def remove_in_current_row():
    if len(columns) > 0:
        return columns.pop()
    return -1


def modified_next_row_safe(column, lst): #Same as given method just modified to accept a list to perform the operations on
    row = len(lst)
    #print(lst, column)
    # check column
    for col in lst:
        if column == col:
            return False

    # check diagonal
    for queen_row, queen_column in enumerate(lst):
        if queen_column - queen_row == column - row:
            return False

    # check other diagonal
    for queen_row, queen_column in enumerate(lst):
        if ((size - queen_column) - queen_row
            == (size - column) - row):
            return False
    
    return True



#Function to solve the n_queens problem using British Mueseum
def solve_nqueens_british_musuem(size):
    columns.clear()
    # British Museum Algorithim that randomly places queens until it gets a solution and breaks if not ssafe and tries another solution
    iterations=0
    num_placements=0
    while(True): #Keep on generating random boards  until we get a solution
        iterations+=1
        place_n_queens(size, columns)
        num_placements+=size
        
        if valid_board(columns): #Check if we got a valid board
            return iterations, num_placements
        
        #print(columns)
    
def valid_board(columns): #check if we have a valid board 
    duplicate=[] 
    

    for x in range(size): #Use an empty array and gradually check if adding each row keeps the validity
      
        val=modified_next_row_safe(columns[x], duplicate)
        
        if val:
            duplicate.append(columns[x])
            continue
        return False
    return True


#Class to solve the n-queens-problem using random restart hillclimbing with minimizing conflict
class HillClimbingSolution:
    
    def __init__(self, size):
        self.size=size #the size
        self.number_iterations=0
        self.number_queens_placed=0
        self.board=[]
        self.solution=self.solve_queens_problem([], size)
        
        
    def generate_optimal_board(self, size): #Found this interesting paper on placing queens while doing research https://dl.acm.org/doi/pdf/10.1145/122319.122322
                                            #This is essentially the best way to generate a board as in some cases it solves the n-queens problem instantly
        pos=1
        board=[]
        for _ in range(size): #start at position 1 and for every row +1 we move the col 2 and rap arround the board if we reach the end
            board.append(pos)
            pos+=2
            
            if pos>=size:
                pos=0
        return board
    
    def generate_random_board(self, size): #generate a board with all queens placed at random
        board=[]
        row = 0
        while row < size:
            column=random.randrange(0,size)
            board.append(column)
            row+=1
        return board 

    def solve_queens_problem(self, board, size):
        if size<4: 
            raise Exception("No Solution for N<4")
            
        max_iterations=size**2 if size>32 else 1000 #I found this to be an optimal number of iterations beforre a restart
        
        while(True): #We continue until we get a solution
            board=self.generate_optimal_board(size) # We start with a pre generated board
            self.number_queens_placed+=size
            self.number_iterations+=1
            for _ in range(max_iterations):
                queens_in_conflict=self.calculate_conflicts(board, size) # We get a list of all queens that are in conflict with one another
            
                if sum(queens_in_conflict)==0: #If we have no queens in conflict that means we have reached a solution
                    self.board=board
                    return
                row_random_queen=random.choice([index for index in range(len(queens_in_conflict)) if queens_in_conflict[index] > 0]) # Pick a random queen out of those in conflict
                
                row_conflicts=self.generate_row_conflicts(board, row_random_queen, size)    # for the chosen queen generate how many conflicts every column has
                min_collisions=min(row_conflicts) # determine the least number of conflictss we have
                
              
                new_position=random.choice([index for index in range(len(row_conflicts)) if row_conflicts[index]==min_collisions]) # Generate a list of all rows with the min number of conflicts and randomly choose one
                #We do this incase we have multiple rows with min conflict
                self.number_queens_placed+=1
                self.number_iterations+=1
                board[row_random_queen]=new_position #Update our board
                
              
                
                
            
   
      
    def calculate_conflicts(self, lst, size): #This function returns the number of conflicts each queen  has
        conflicts=[0]* size
        
        for i in range(size):
            for j in range(i + 1, size):
                # Check for conflicts in the same column
                if lst[i] == lst[j]: # if the queens share the same column, they are both in conflict and increment both
                    conflicts[i] += 1
                    conflicts[j] += 1

                    # Check for conflicts in diagonals
                if abs(lst[i] - lst[j]) == j - i: #by using abs we check both diagonals. Increment both queens since they "eat" eachother
                    conflicts[i] += 1
                    conflicts[j] += 1    
     
        return conflicts
      

    def generate_row_conflicts(self, board, queen_index, size_b): #This function generates how many conflicts each row has for a given queen {queen_index} 
        row_conflicts=[0]*size_b
     
    
        for index, col in enumerate(board):
            
            if index == queen_index:
                continue
            
            row_conflicts[col] += 1  # add automatically when we see a queen there since there will be a conflict


            if col+abs(index-queen_index)>=0 and col+abs(index-queen_index)<size_b: #Check diagonals of the queen and if there is another queen there increment the counter
                row_conflicts[col + abs(index - queen_index)] += 1
                
            if  col - abs(index - queen_index)>=0 and col - abs(index - queen_index)<size_b: #Check diagonals of the queen and if there is another queen there increment the counter
                row_conflicts[col - abs(index - queen_index)] += 1
        
        return row_conflicts

         
    def display_board(self, size,board): #Simple function to display the board
        for row in range(len(board)):
            for column in range(size):
                if column == board[row]:
                    print('♛', end=' ')
                else:
                    print(' .', end=' ')
            print()


    def return_values(self): #return counters
        return self.number_iterations, self.number_queens_placed

    def get_board(self): #Return the board
        return self.board

#Class to solve n queens using forward searching through forward propagation
class ForwardCheckingSoluion: 
    
    def __init__(self, size):
        self.size=size
        self.number_iterations=0 
        self.number_queens_placed=0 
        self.board=[]
        self.solution=self.solve_n_queens(size)
        
    
    def display_board(self, size,board): #function to display chess board
        for row in range(len(board)):
            for column in range(size):
                if column == board[row]:
                    print('♛', end=' ')
                else:
                    print(' .', end=' ')
            print()
             
     
     
    def solve_n_queens(self, size): #function to solve the problem
        max_iterations=size**2 if size>32 else 1000
        while True:
            queens=[]
            propogation_board=[[0]* size for _ in range(size)] #generate matrix which will be propagated
            
            available_locations = {key: set(range(size)) for key in range(size)} #keep track of which positions are allowed in a dict of sets for each row ie each queen and what col it can be placed in
            self.number_iterations+=1

            for _ in range(max_iterations):
                
                #print(f"Queens placed are {queens}")
                #print(f"This is the board {propogation_board}")
                #print(f"Avalible spaces are {available_locations}")
                if self.place_queen(size, queens, propogation_board,available_locations)==False: #check and place a queen if we are able to
                    self.propogate_board(len(queens)-1, queens[len(queens)-1], propogation_board, False,available_locations, len(queens)+1 ) #If we cannot place a queen legally call propogate_board with direction=False to remove the previous queen
                    queens.pop() #update the board
                    
                
                
                self.number_queens_placed+=1
                
                if(len(queens)==size): #We have reached a solution
                    self.board=queens
                    return queens
                
                self.number_iterations+=1
        
        
    def place_queen(self, size, queens, propogation_board, available_locations): #Place a queen on the board
        if self.determine_position(queens, propogation_board, available_locations) == False: #Check if there is a legal position we can place the queen and False if there isnt
            return False
        row_placed=len(queens)-1
        col_placed=queens[len(queens)-1]
        self.propogate_board(row_placed, col_placed, propogation_board, True,available_locations, len(queens)+1) #Place the queen and propagate the board
        
        
        return True #return True to indicate a queen was successfuly placed
    
    def determine_position(self, queens,propogation_board,available_locations): #Determine where to place a queen. Returns True if there exists such a position, else false
        checked_positions=[] #Keep track of checked values incase we backtrack
        queen=len(queens) #The queen we are placing
        
        if len(available_locations[queen])==0:
            return False
        
        
        position=(queen, random.choice(list(available_locations[queen]))) #Randomly an avalible position to place a queen
        available_locations[queen].discard(position[1]) #update available positions
        checked_positions.append(position) #keep track of checked positions incase we need to backtrack and hence, reset positions
        
        while propogation_board[position[0]] [position[1]]!=0 and len(available_locations[queen])>0: #keep on testing positions until we  run out of positions to check or find a valid position
            position=(queen, random.choice(list(available_locations[queen])))
            available_locations[queen].discard(position[1])
            checked_positions.append(position)
            
            
        if len(available_locations[queen])==0 and propogation_board[position[0]] [position[1]]!=0: #Need to backtrack since we cant place anything
          
            return False
        
        queens.append(position[1]) #valid position found. Update the board
        return True
        
    def propogate_board(self, row, col, propogation_board, direction, available_locations, marker): #Function to propogate the board given a queen is placed in row,col 
        propogation_board[row][col]="Q"  #Direction is used to indicate whether we are placing a queen or removing it from the board
        #Use a marker to denote the queen so we dont remove overlap
        for i in range(self.size):
            for j in range(self.size):

                if propogation_board[i][j]=="Q": #Skip if we encounter a queen
                    continue
                
                if row==i or col==j: #If we are in the same row or column as the queen then mark it
                    
                    if direction and propogation_board[i][j]==0: #Case we propagate the board
                        propogation_board[i][j]=marker #update the board with a marker unique to the queen
                        
                    elif propogation_board[i][j]==marker: #Case we clear a queen from the board
                        propogation_board[i][j]=0 
                        available_locations[i].add(j) #Add this position as we can now place a queen there again
                
     
                    
                if j+ abs(i - row)==col and j+ abs(i - row)==col: #Check for one diagonal
                    if direction and propogation_board[i][j]==0: 
                        propogation_board[i][j]=marker

                    elif propogation_board[i][j]==marker: #Case we clear a queen from the board
                        propogation_board[i][j]=0
                        available_locations[i].add(j)
                
            
                if j- abs(i - row) ==col and j- abs(i - row)==col: #Check for the other diagonal
                    if direction and propogation_board[i][j]==0: 
                        propogation_board[i][j]=marker
                
                    elif propogation_board[i][j]==marker: #Case we clear a queen from the board
                        propogation_board[i][j]=0
                        available_locations[i].add(j)
                        
                        
        if direction==False:
            propogation_board[row][col]=0

    def return_values(self): #Return the counters
        return self.number_iterations, self.number_queens_placed
    
    def get_board(self): #Return the solution
        return self.board

# Hill Climbing works best since it was able to quickly converge to a solution through starting with an optimal board and didnt have to start from scratch like forward searching.
# Also the implementation of hillclimbing is must faster than the forward checking from a bug o perspective
# Also due to its implementation the forward searching implementation is much more likely to try placing a queen in a position that previously didnt work

base_dfs = pd.DataFrame(columns=['n', 'number_of_iterations', 'number_of_moves', 'time (Seconds)'])
for n in range(10,26):
    start_time=time.time()
    size=n
    num_iter,num_q=solve_queen_base_dfs(n)
    end_time=time.time()
    base_dfs.loc[n-10]=[n,num_iter,num_q,end_time-start_time]
print("Table for base dfs with backtracking\n",base_dfs,"\n")


british_museum = pd.DataFrame(columns=['n', 'number_of_iterations', 'number_of_moves', 'time (Seconds)'])
for n in range(4,10):
    start_time=time.time()
    size=n
    num_iter,num_q=solve_nqueens_british_musuem(n)
    end_time=time.time()
    british_museum.loc[n-4]=[n,num_iter,num_q,end_time-start_time]
print("Table for British Museum\n",british_museum,"\n")


 
hill_climbing = pd.DataFrame(columns=['n', 'number_of_iterations', 'number_of_moves', 'time (Seconds)'])
values_to_check=[x for x in range(10, 41)]+[x for x in range(45, 59, 5)]+[x for x in range(60, 91, 10)]+[x for x in range(100, 1001, 100)]

counter=0
for n in values_to_check:
    start_time=time.time()
    sol=HillClimbingSolution(n)
    num_iter,num_q=sol.return_values()
    end_time=time.time()
    hill_climbing.loc[counter]=[n,num_iter,num_q,end_time-start_time]
    counter+=1
print("Table for Random Restart Hill-CLimbing (Best Method)\n",hill_climbing,"\n")        


forward_checking = pd.DataFrame(columns=['n', 'number_of_iterations', 'number_of_moves', 'time (Seconds)'])
for n in range(10,41):
    start_time=time.time()
    sol=ForwardCheckingSoluion(n)
    num_iter,num_q=sol.return_values()
    end_time=time.time()
    forward_checking.loc[n-10]=[n,num_iter,num_q,end_time-start_time]
print("Table for Forward Checking\n",forward_checking,"\n")

forward_sec=0
forward_moves=0
forward_iterations=0

hill_sec=0
hill_moves=0
hill_iterations=0

for i in range(0,40):
    st=time.time()
    sol=ForwardCheckingSoluion(25)
    forward_sec+=(time.time()-st)
    num_iter,num_q=sol.return_values()
    forward_iterations+=num_iter
    forward_moves+=num_q
    
    hill_st=time.time()
    sol=HillClimbingSolution(25)
    hill_sec+=(time.time()-hill_st)
    num_iter,num_q=sol.return_values()
    hill_moves+=num_q
    hill_iterations+=num_iter

    
print(f"For 100 runs of n=25, below is a table depicting comparisions between hill climbing and forward checking")
data={'Time': [forward_sec/40,hill_sec/40],
      'Queens Placed': [forward_moves/40,hill_moves/40],
      'Iterations': [forward_iterations/40, hill_iterations/40]}
df=pd.DataFrame(data, index=['Forward Checking', 'Hill-Climbing'])
print(df)