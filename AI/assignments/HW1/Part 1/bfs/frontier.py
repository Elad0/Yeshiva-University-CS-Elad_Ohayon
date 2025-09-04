#implements a queue
#[head, tail] - the first and last items on a linked list
#inserts to the tail and removes from the head
#every item is [value, next_item]

count_inserted=0
count_deleted=0

def create(s):
    global count_inserted
    count_inserted+=1
    
    p=[s,None,count_inserted, count_deleted ]
    return [p, p]

def is_empty(f):
    return f[0] == None

def insert(f, s):
    global count_inserted
    count_inserted+=1
    #inserts state s to the frontier
    p = [s, None, count_inserted, count_deleted ] #New item
    if is_empty(f):
        f[0] = p #The head points to the new item
        f[1] = p #The tail points to the new item
    else:
        f[1][1] = p #Connects the last item to the new item
        f[1] = p    #The tail points to the new item

def remove(f):
    global count_deleted
    if is_empty(f):
        return 0 
    
    
    count_deleted+=1
    
    p = f[0][0] #value of the item at the head of the queue
    f[0] = f[0][1] #Moves the head to the next item
    if f[0] == None: #If the head is None
        f[1] = None  #the tail should also be None
    return p

def get_insertions():
    global count_inserted
    return count_inserted

def get_deletions():
    global count_deleted
    return count_deleted


