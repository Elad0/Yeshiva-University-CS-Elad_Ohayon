import pandas as pd


print("Table for 2x2")
df=pd.DataFrame(columns=["Technique","Depth", "Inserts", "Removes", "Time (Ms)"])
df.loc[0]=["BFS", 1.61, 4.61, 3.8, 0.019996]
df.loc[1]=["IDS", 1.47, 7.65, 6.18, 0.03000]
df.loc[2]=["Uniform", 1.52, 3.38, 3.65, 0.03521]
df.loc[3]=["A*-h1", 1.79, 2.95, 3.1, .0254]
df.loc[4]=["A*-h2", 1.4, 2.28, 2.47, .019999]
print(df,"\n____________________________________________________________________\n")



print("Table for 3x3")
df=pd.DataFrame(columns=["Technique","Depth", "Inserts", "Removes", "Time (Ms)"])
df.loc[0]=["BFS", 6.24, 777.38, 459.62, 2.916]
df.loc[1]=["IDS", 6.12,1213.88,699.31,4.809453]
df.loc[2]=["Uniform", 5.63, 474.9, 276.7, 3.889]
df.loc[3]=["A*-h1", 5.4, 38.7, 22.7, .47413]
df.loc[4]=["A*-h2", 5.6, 19.58, 10.67, .59876]
print(df,"\n____________________________________________________________________\n")


print("Table for 4x4 (Parenthesis denote often timeouts)")
print("For searches of time> 5 seconds. The test is timed out and 10,000 is added to average insertions and deletions\n20 is added to Depth\n100 seconds are added to the timer")
df=pd.DataFrame(columns=["Technique","Depth", "Inserts", "Removes", "Time (Ms)"])
df.loc[0]=["BFS", "(15.72)","(158,257.61)", "(76,566.88)", "(44,525.44)" ]
df.loc[1]=["IDS", "(15.69)", "(147,771.73)", "(71,799.44)", "(46,444.524)"]
df.loc[2]=["Uniform", "(13.64)", "(38,187.01)", "(23,208.65)", "(54,440.40)"]
df.loc[3]=["A*-h1", 15.35, 7132.83, 4375.56, 7333.14]
df.loc[4]=["A*-h2", 15.84, 1165.2, 680.21, 2199.55]
print(df,"\n____________________________________________________________________\n")