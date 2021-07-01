### Task 1
1. Explain the main structure of this code and the advantages and disadvantages of 
   the setup of the distributed algorithm.
   
        The sorters sort the array and the branches send a certain amount to the sorters. 
        The array starts at merge sort and is branched off via the branches into the sorters.
        The sorters sort their portions and send them back up the branches to the main method.
        The advantage of this is that we have two sorters working on their own data - in a 
        distributed algorithm this is done in parallel. There is overhead when it comes to actually 
        sending the data over the wire though so there are times where a distributed algorithm is not 
        necessary.
   
2. Run the code with different arrays to sort (different sizes, numbers etc.) and include
   code to measure the time (you can just enter start and end times). In your Readme
   describe your experiments and your analyzes of them. E.g. why is the result as it
   is? Does the distribution help? Why, why not? See this as setting up your own
   experiment and give me a good description and evaluation.
   
        I tested the given array along with a couple others. the results are:
            - 10 items: 23 ms 
            - 14 items: 16 ms
            - 20 items: 21 ms
            - 30 items: 27 ms
            - 250 items: 292 ms
            - 500 items: 621 ms
        The result makes sense given what we know of merge sort. Merge sort is O(n log n) and 
        the data here shows that seems to be the case here. Because mergesort is already O(n log n)
        we can see that the distributed algorithm here doesn't really add to the efficiency at all.
   
3. Experiment with the "tree" setup, what happens with more or less nodes when sorting
   the same array and different arrays? When does the distribution make things better?
   Does it ever make things faster? As in the previous step experiment and describe
   your experiment and your results in detail.
   
        The efficiency decreases with more nodes and branches. This makes sense becuase merge 
        sort is already pretty efficient and we are decreasing that efficiency when we add more 
        overhead by sending more things over the network.
   
4. Explain the traffic that you see on Wireshark. How much traffic is generated with
   this setup and do you see a way to reduce it?
   
        The branch uses peek and remove which increases overhead. We can increase efficiency by 
        sending the entire sorted array at once.

###Task 2
1. Do you expect changes in runtimes? Why, why not?
        
        Yes I expect the runtimes to increase. Instead of running on localhost we will be 
        sending this information over the wire which requires more overhead and should increase the 
        time needed to complete the sorting.

2. Do you see a difference how long it takes to sort the arrays? Explain the differences
   (or why there are not differences)
   
        Yes, there is a pretty drastic change in how long it takes the algorithm to complete when 
        not running on localhost. This makes sense as the data obviously has to travel much 
        farther through traffic

###Task 3
1. Where is the most time lost and could you make this more efficient?

        Most of the time here is spent sending data over the network. For merge sort in 
        particular, it is probably better to run the whole thing locally and not use a distributed 
        algorithm. However that is not necessarily the case every time. For a less efficient 
        algorithm, using a distributed sytem can imporve the efficiency. That is just not the case here.
2. Does it make sense to run the algorithm as a distributed algorithm? Why or why
   not?
        See above. For this case, it does not make sense to run the algorithm on a distributed 
        system. With a less efficient algorithm (o(n^2)), it would make sense.