import copy
EMPTY, BLACK, WHITE = '.', '●', '○'
HUMAN, COMPUTER = '●', '○'

VALUES=[[4, -3, 2, 2, 2, 2, -3, 4], #matrix that stores how much each position is worth to be used for value
    [-3, -4, -1, -1, -1, -1, -4, -3],
    [2, -1, 1, 0, 0, 1, -1, 2],
    [2, -1, 0, 1, 1, 0, -1, 2],
    [2, -1, 0, 1, 1, 0, -1, 2],
    [2, -1, 1, 0, 0, 1, -1, 2],
    [-3, -4, -1, -1, -1, -1, -4, -3],
    [4, -3, 2, 2, 2, 2, -3, 4]]

UP, DOWN, LEFT, RIGHT = -10, 10, -1, 1
UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT = -9, 11, 9, -11
DIRECTIONS = (UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT)
VIC=10000000 #The value of a winning board (for max)
LOSS=-VIC #The value of a losing board (for max)
TIE=0 #The value of a tie

'''
The state of the game is represented by a list of 4 items:
0. The game board - a matrix (list of lists) of ints. Empty cells = 0,
   the comp's cells = COMPUTER and the human's = HUMAN
1. The heuristic value of the state.
2. Who's turn is it: HUMAN or COMPUTER
3. flag to end game

'''

#The user decides who plays first
def whoIsFirst(s):
    global HUMAN,COMPUTER
    
    choice=input("Would you like to play as black ● or ○, note black goes first\n Enter B for black or W for white ") #Prompt the user for who they want to play as
    
    if choice.lower()=='b': #If the user chooses black ignoring case, We dont need to change anything as this is the default
        s[2]=HUMAN ### your code here ###
        print("You have chosen to play as black")
    else: #If the user is not black then they are white 
        HUMAN= '○' #Set the human to white and the computer to black
        COMPUTER='●'
        s[2]=COMPUTER #As black goes first, we assign the first move to the computer
       
        
    return s

def isHumTurn(s):
#Returns True iff it the human's turn to play
    return s[2]==HUMAN

def squares():
    return [i for i in range(11, 89) if 1 <= (i % 10) <= 8]

#The HUMAN plays first (=BLACK)
def create(): 
    global HUMAN,COMPUTER,VALUES
    board = [EMPTY] * 100
    for i in squares():
        board[i] = EMPTY
    board[44], board[45] = WHITE, BLACK
    board[54], board[55] = BLACK, WHITE
    #Deleted the default settings for human and computer as @whoIsFirst overrides it anyways
 
    flat_values = [value for row in VALUES for value in row] #transform the matrix into a 1d array
    arr = [0] * 89
    
    
    #Make a matrix corresponding to the board using the values of each position
    #For example, the value of board[i] is VALUES[i]
    for x,y in enumerate(squares()): 
        arr[y]=flat_values[x]
    
    VALUES=arr
    return [board,0.00001, HUMAN,False]

def printState(s):
    rep = ''
    rep += '  %s\n' % ' '.join(map(str, range(1, 9)))
    for row in range(1, 9):
        begin, end = 10*row + 1, 10*row + 9
        rep += '%d %s\n' % (row, ' '.join(s[0][begin:end]))
    print(rep)
    
    if s[1] == VIC:
        print("Ha ha ha I won!")
    elif s[1] == LOSS:
        print("You did it!")
    elif s[1] == TIE:
        print("It's a TIE")
        

def inputMove(s):
# Reads, enforces legality and executes the user's move.

    flag=True
    while flag:
        printState(s)
        print(f"You can place a piece in these locations {legalMoves(s)} ")
        move=int(input("To make your move enter a two digits number, the first number is row and the second is column" "\n" \
        "For example: if you want to choose the first row and the third column enter 13" "\n"\
        "Enter your next move: "))
        
        if isLegal(move, s) ==False:
            print("Illegal move.")
        else:
            flag=False
            makeMove(move,s)


def value(s): #Returns the heuristic value of s
    global HUMAN,COMPUTER,VALUES
    board=s[0] #The board
    val=0 #Variable to store the score for this board
    
    maximizingPlayer=0 #This is the score the computer has on this board, the higher the better
    minimizingPlayer=0 #Thsi is the score the human has on the board, the higher the better
    n=len(VALUES) 
    for i in range(0,n): #Check every single position on board and assign points based on who holds what
        if board[i]==COMPUTER:
            maximizingPlayer+=VALUES[i] #add value of position to computer 
            
        if board[i]==HUMAN:
            minimizingPlayer+=VALUES[i] #Add value of position to computer 
            
    val=maximizingPlayer-minimizingPlayer # Since the computer wants the board as high as possible
                                          # We check the score the computer has minus the score the human gets and try to maximize it for the computer
    """     
    cornersComputer=0 #See how many corners the computer has
    cornersHuman=0 # " " the human has
   
    if s[0][11]== COMPUTER: cornersComputer+=1 #The the upper left corner
    if s[0][11]==HUMAN: cornersHuman+=1
    
    if s[0][18]== COMPUTER: cornersComputer+=1 # The top right corner
    if s[0][18]==HUMAN: cornersHuman+=1 
    
    if s[0][81]== COMPUTER: cornersComputer+=1 # Bottom left corner
    if s[0][81]== HUMAN: cornersHuman+=1
    
    if s[0][88]== COMPUTER: cornersComputer+=1 #Bottom right corner
    if s[0][88]== HUMAN: cornersHuman+=1
    
    # Using the data of who holds corners. We use a heruistic to determine the score of the board where the computer is the maximizing player
    # We take the proportion of computer corners vs human computers adjusted to account for computer or human having 0 corners 
    # and scale it based off a max score of 100
    # We use the formula because a simple ratio will yield infinity if the computer has a corner and the human has none which will far outweigh any other calculations
    corner_heurtistic= ((cornersComputer-cornersHuman)/(cornersHuman+cornersComputer))*100 if cornersHuman + cornersComputer>0 else 0
    
    
    #Second heuristic is to check the ratio of pieces the Computer has vs Human. Where each player whats to maximize their pieces on the board.
    pieces_computer=0
    pieces_human=0
    
    for i in range(11,89): #Count up the number of pieces on the board for each player
        if s[0][i]==COMPUTER:
            pieces_computer+=1
            
        if s[0][i]==HUMAN:
            pieces_human+=1
            
    # Use the same method of calculation as the corner heurtistic to avoid issues with zero pieces for each player
    board_heuristic= ((pieces_computer-pieces_computer)/(pieces_computer+pieces_human))*100
    
    
    #Third and last heurstic is to compare how many available moves each player has and compare them
    moves_computer=0
    moves_human=0
    original_move=s[2]
    
    if s[2]==COMPUTER: #If it is currently the computer's turn
        moves_computer=len(legalMoves(s)) #store how many moves the computer can make
        s[2]=HUMAN #Switch to Human's turn
        moves_human=len(legalMoves(s)) #How many moves can the human nake
        s[2]=original_move
    else: #If it is not the computer's turn then it is the human's turn
        moves_human=len(legalMoves(s)) 
        s[2]=COMPUTER
        moves_computer=len(legalMoves(s))
        
    s[2]=original_move #Reset to who's turn it was originally
    
    
    move_heuristic= ((moves_computer-moves_human)/(moves_human+moves_computer))*100
    
    #Sum up the 3 heuristic functions to get the value of this board
    val=corner_heurtistic + board_heuristic +move_heuristic +maximizingPlayer -minimizingPlayer
   
    
#Logic of the corner heurstic is that each player wants the control more corners since they are strategically, very valuable
#Logic behind the number of pieces on the board is that by having more pieces on the board, a player has more choices, and when the game ends the player with the most pieces wins
#The third heuristic is similar to the second heurstic in that the more moves a player has the more flexibility and pieces they control
 """    
    if val==0:
        val=0.00001
    s[1]=val #Set the board's value to the sum of the 3 heuristics
    return s[1]

def isFinished(s):
    global HUMAN,COMPUTER 
    #game ends once one player cant make a move
    if len(legalMoves(s))==0:
        s[3]=True
        whoWon(s)
        #Code responsible for switching turns. piazza says we dont need it
    """
    #To determine if the game is over we check if both players cant make a move. This also works incase the board is full
    
    if len(legalMoves(s))==0: #Check if current player can make a move 
        changePlayer(s) #If current player cant make a move switch turns
        if len(legalMoves(s))==0: #if second player also cant make a move, the game is over
            s[3]=True #set set to game over is false
            whoWon(s)
    """
    return (s[3])

def whoWon(s):
    global HUMAN,COMPUTER
    humanScore=0
    computerScore=0
    board=s[0]
    for piece in range(11,89):
        if board[piece]==HUMAN:
            humanScore+=1
        if board[piece]==COMPUTER:
            computerScore+=1
            
    if humanScore==computerScore:
            s[1]=0
            return
    if humanScore>computerScore:
        s[1]=-10000000
        
    else:
        s[1]=10000000
        
        
def isLegal(move, s):
    hasbracket = lambda direction: findBracket(move, s, direction)
    return s[0][move] == EMPTY and any(map(hasbracket, DIRECTIONS))

# get a list of legal moves for the player
def legalMoves(s):
    return [sq for sq in squares() if isLegal(sq, s)]

# Is there any legal move for this player
def anyLegalMove(s):
    isAny = any(isLegal(sq, s) for sq in squares())
    if (not(isAny)):
        s[3] = True
    return isAny

def makeFlips(move, s, direction):
    bracket = findBracket(move, s, direction)
    if not bracket:
        return
    square = move + direction
    while square != bracket:
        s[0][square] = s[2]
        square += direction

def changePlayer(s):
    if s[2] == COMPUTER:
            s[2] = HUMAN
    else:
       s[2] = COMPUTER

def makeMove(move, s):
    s[0][move] = s[2]
    for d in DIRECTIONS:
        makeFlips(move, s, d)
    value(s)
    changePlayer (s)
    return s

def whoWin (s):
    computerScore=0
    humanScore=0
    for sq in squares():
        piece = s[0][sq]
        if piece == COMPUTER:
            computerScore += 1
        elif piece == HUMAN:
            humanScore += 1
    if (computerScore>humanScore):
        return VIC

    elif (computerScore<humanScore):
        return LOSS

    elif (computerScore==HUMAN):
        return TIE

    return 0.00001 #not 0 because TIE is 0


def isValid(move):
    return isinstance(move, int) and move in squares()

def findBracket(square, s, direction):
    bracket = square + direction
    if s[0][bracket] == s[2]:
        return None
    opp = BLACK if s[2] is WHITE else WHITE
    while s[0][bracket] == opp:
        bracket += direction
    return None if s[0][bracket] in (EMPTY) else bracket

def getNext(s):
# returns a list of the next states of s
    ns=[]
    for m in legalMoves(s):
        tmp=copy.deepcopy(s)
        makeMove(m,tmp)
        ns+=[tmp]
    return ns
