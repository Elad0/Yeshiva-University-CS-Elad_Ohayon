import state
import frontier
import time

def search(n):
    start=time.time()
    s=state.create(n)
    #print(s)
   
    f=frontier.create(s)
    count_inserts=1
    count_checked=0
    while not frontier.is_empty(f):
        s=frontier.remove(f)
        count_checked+=1 #Node removed here
       
       
        if state.is_target(s):
            
            return [s, len(s[1]),f[4], count_checked]
        ns=state.get_next(s)
        
        
        for i in ns:
            frontier.insert(f,i)
            f[4]+=1
           
        if ((time.time()-start))>5:
            return [s, 20,10000, 10000]
        
    return 0



def determine_averages(n):
    start=time.time()
    
    average_time=0
    average_del=0
    average_insert=0
    average_depth=0

    for x in range(100):
        start=time.time()    
        ans=search(n)
        end=time.time()
        

        
        if(end-start>5):
            average_time+=(100*1000)
            
        else:
             average_time+=(end-start)*1000
        
        average_del+=ans[3]
        average_insert+=ans[2]
        average_depth+=ans[1]
    
    
    average_time/=100
    average_del/=100
    average_insert/=100
    average_depth/=100
    print(f" For a Puzzle of {n}x{n} using Uniform Search depth of: {average_depth}, Average Number of inserts: {average_insert}, Average Number of deletions (checks): {average_del}, with an Average Runtime of {average_time} miliseconds")


determine_averages(2)
determine_averages(3)
determine_averages(4)


