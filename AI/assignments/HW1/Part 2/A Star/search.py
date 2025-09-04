#search

import state
import frontier
import time


def search(n):
    start=time.time()
    s=state.create(n)
 
    f=frontier.create(s)
    while not frontier.is_empty(f):
        s=frontier.remove(f)
   
        if state.is_target(s):
            return [s, f[1], f[2]]
        ns=state.get_next(s)
        for i in ns:
            frontier.insert(f,i)
             
        if ((time.time()-start))>5:
            return [s, 20,10000, 10000]
        
        
    return 0



def determine_averages(n):
    start=time.time()
    
    average_time=0
    average_del=0
    average_insert=0
    average_depth=0

    i=0
    for x in range(100):
        start=time.time()    
        ans=search(n)
       
        end=time.time()
        timer=(end-start)*1000
        
        if(end-start>5):
            timer=100*1000
            
       # print("time to solve this case is ", timer)
     
        average_time+=timer
        
        #print("sum time is",average_time )
        average_del+=ans[2]
        average_insert+=ans[1]
        average_depth+=len(ans[0][1])
        i+=1
    
    
    average_time/=i
    average_del/=i
    average_insert/=i
    average_depth/=i
    print(f" For a Puzzle of {n}x{n} using A* with simple not in place depth of: {average_depth}, Average Number of inserts: {average_insert}, Average Number of deletions (checks): {average_del}, with an Average Runtime of {average_time} miliseconds")


determine_averages(2)
determine_averages(3)
determine_averages(4)






