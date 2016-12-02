#Look for #IMPLEMENT tags in this file. These tags indicate what has
#to be implemented to complete the Sokoban warehouse domain.

#   You may add only standard python imports---i.e., ones that are automatically
#   available on TEACH.CS
#   You may not remove any imports.
#   You may not import or otherwise source any of your own files

from search import * #for search engines
from sokoban import SokobanState, Direction, PROBLEMS, sokoban_goal_state #for Sokoban specific classes and problems
import sys, os, math

#SOKOBAN HEURISTICS
def heur_displaced(state):
  '''trivial admissible sokoban heuristic'''
  '''INPUT: a sokoban state'''
  '''OUTPUT: a numeric value that serves as an estimate of the distance of the state to the goal.'''       
  count = 0
  for box in state.boxes:
    if box not in state.storage:
      count += 1
    return count

def heur_manhattan_distance(state):
#IMPLEMENT
    '''admissible sokoban heuristic: manhattan distance'''
    '''INPUT: a sokoban state'''
    '''OUTPUT: a numeric value that serves as an estimate of the distance of the state to the goal.'''      
    #We want an admissible heuristic, which is an optimistic heuristic. 
    #It must always underestimate the cost to get from the current state to the goal.
    #The sum Manhattan distance of the boxes to their closest storage spaces is such a heuristic.  
    #When calculating distances, assume there are no obstacles on the grid and that several boxes can fit in one storage bin.
    #You should implement this heuristic function exactly, even if it is tempting to improve it.
    #Your function should return a numeric value; this is the estimate of the distance to the goal.
    count = 0;
    if state.boxes is None:
      return count
    for box in state.boxes:
      closestDist = sys.maxsize
      for storage in state.storage:
        manhattanDistance = abs(box[0] - storage[0]) + abs(box[1] - storage[1])
        if (manhattanDistance < closestDist):
          closestDist = manhattanDistance
      count += closestDist
    return count

def heur_alternate(state):
#IMPLEMENT
    '''a better sokoban heuristic'''
    '''INPUT: a sokoban state'''
    '''OUTPUT: a numeric value that serves as an estimate of the distance of the state to the goal.'''        
    #heur_min_moves has flaws.   
    #Write a heuristic function that improves upon heur_manhattan_distance to estimate distance between the current state and the goal.
    #Your function should return a numeric value for the estimate of the distance to the goal.
    
    
    '''
    # 28 23 baseline
    # improved to 29 25
    Explanation: 
    Firstly, if a box has an obstacle on two adjacent sides, that box cannot be moved,
    so if it isn't already on a storage, the state is unsolvable.
    Secondly, this heuristic also accounts for the cost of the robot moving between boxes;
    a robot moving away from all boxes is generally unproductive
    Thirdly, the algo checks manhattan distance from each box to it's closest storage,
    while removing storages so that each storage only has one associated box. Distance
    from boxes is worth more than distance from robots, so that the robot pushing a box
    in the wrong direction, but closer to another box is a negative (although this has
    almost no effect on runtime)
    
    '''
    
    '''
    if state.boxes is None:
      return 0
    '''

    # 29 24 when removed
    #twoSides = [[[0, 1], [1, 0]], [[0, 1], [-1, 0]], [[0, -1], [1, 0]], [[0, -1], [-1, 0]]]
    twoSides = [False, False, False, False]
    for box in state.boxes:
      # not checking this increases speed?? increases from 29 24 to 30 25
      if box not in state.storage:
        '''
        # not as fast as code below
        for sides in twoSides:
          #tuples
          side1 = (box[0] + sides[0][0], box[1] + sides[0][1])
          side2 = (box[1] + sides[1][0], box[1] + sides[1][1])
          if side1 in state.obstacles and side2 in state.obstacles:
            return sys.maxsize;
        '''
        if (box[0], box[1] + 1) in state.obstacles:
          twoSides[0] = True
        if (box[0] + 1, box[1]) in state.obstacles:
          twoSides[1] = True 
        if (box[0], box[1] - 1) in state.obstacles:
          twoSides[2] = True
        if (box[0] + 1, box[1]) in state.obstacles:
          twoSides[3] = True
        if (twoSides[0] and twoSides[1]) or (twoSides[1] and twoSides[2]) or (twoSides[2] and twoSides[3]) or (twoSides[3] and twoSides[0]):
          return sys.maxsize;
      
      count = 0
    
    #create copies
    boxes = list(state.boxes)
    robots = list(state.robots)
    storage = list(state.storage)
    
    for box in boxes:

      # 25 20 if removed
      robotDistance = sys.maxsize
      nearestRobot = None
      for robot in robots:
        robManDist = abs(box[0] - robot[0]) + abs(box[1] - robot[1])
        if robManDist < robotDistance:
          robotDistance = robManDist
          nearestRobot = robot
      count += robotDistance

      storageDistance = sys.maxsize
      nearestStorage = None
      for stor in storage:
        storManDist = abs(box[0] - stor[0]) + abs(box[1] - stor[1])
        '''
        #24 19 if added
        if storManDist == 1:
          count *= 0.8
        '''
        if storManDist < storageDistance:
          storageDistance = storManDist
          nearestStorage = stor
          if storManDist == 0:
            break
      # setting this to 2 makes it 29 25, from 29 24
      count += storageDistance * 2
      '''
      # no effect
      nearestRobot = nearestStorage
      '''
      # 26 21 if removed
      storage.remove(nearestStorage)
      
      '''
      # 23 17 if added
      nothingBlocking1 = True
      for i in range(min(box[0], nearestStorage[0]), max(box[0], nearestStorage[0])):
        if (i, box[1]) in state.obstacles:
          nothingBlocking1 = False
          break
      temp = max(box[0], nearestStorage[0])
      for i in range(min(box[1], nearestStorage[1]), max(box[1], nearestStorage[1])):
        if (temp, i) in state.obstacles:
          nothingBlocking1 = False
          break
        
      nothingBlocking2 = True
      for i in range(min(box[1], nearestStorage[1]), max(box[1], nearestStorage[1]) + 1):
        if (box[0], i) in state.obstacles:
          nothingBlocking2 = False
          break  
      temp = max(box[1], nearestStorage[1])
      for i in range(min(box[0], nearestStorage[0]), max(box[0], nearestStorage[0]) + 1):
        if (i, temp) in state.obstacles:
          nothingBlocking2 = False
          break
      if nothingBlocking1 or nothingBlocking2:
        count *= 0.8
      '''
      '''
      # 29 22 if added
      for i in range(min(box[0], nearestStorage[0]), max(box[0], nearestStorage[0]) + 1):
        for j in range(min(box[1], nearestStorage[1]), max(box[1], nearestStorage[1]) + 1):
          if (i, j) in state.obstacles:
            count += 1
      '''
      
    return count

def fval_function(sN, weight):
#IMPLEMENT
    """
    Provide a custom formula for f-value computation for Anytime Weighted A star.
    Returns the fval of the state contained in the sNode.

    @param sNode sN: A search node (containing a SokobanState)
    @param float weight: Weight given by Anytime Weighted A star
    @rtype: float
    """
  
    #Many searches will explore nodes (or states) that are ordered by their f-value.
    #For UCS, the fvalue is the same as the gval of the state. For best-first search, the fvalue is the hval of the state.
    #You can use this function to create an alternate f-value for states; this must be a function of the state and the weight.
    #The function must return a numeric f-value.
    #The value will determine your state's position on the Frontier list during a 'custom' search.
    #You must initialize your search engine object as a 'custom' search engine if you supply a custom fval function.
    return (1 - weight) * sN.gval + weight * sN.hval

def weighted_astar(initial_state, timebound = 10):
#IMPLEMENT
    '''Provides an implementation of weighted a-star, as described in the HW1 handout'''
    '''INPUT: a sokoban state that represents the start state and a timebound (number of seconds)'''
    '''OUTPUT: A goal state (if a goal is found), else False''' 
    se = SearchEngine('custom', 'full')
    solution = False
    total_search_time = os.times()[0]
    maxHeurWeight = 8
    for i in range(0, maxHeurWeight, 2):
      final = se.search(initState = initial_state, goal_fn = sokoban_goal_state, heur_fn = heur_alternate,
                        timebound = timebound, fval_function = fval_function, weight = 1 - (i / 10) - (1 - (maxHeurWeight / 10)))
      #costbound = initial_state.width * initial_state.height * 2 does nothing, * 1 puts soltuions to 24
      if final is not False and (solution is False or solution.gval > final.gval):
        solution = final
      timebound -= os.times()[0] - total_search_time
      if timebound <= 0:
        break
    return solution

if __name__ == "__main__":
  #TEST CODE
  solved = 0; unsolved = []; counter = 0; percent = 0; timebound = 2; #2 second time limit for each problem
  print("*************************************")  
  print("Running A-star")     

  for i in range(0,40): #note that there are 40 problems in the set that has been provided.  We just run through 10 here for illustration.

    print("*************************************")  
    print("PROBLEM {}".format(i))
    
    s0 = PROBLEMS[i] #Problems will get harder as i gets bigger

    se = SearchEngine('astar', 'full')
    final = se.search(s0, sokoban_goal_state, heur_displaced, timebound)

    if final:
      final.print_path()
      solved += 1
    else:
      unsolved.append(i)    
    counter += 1

  if counter > 0:  
    percent = (solved/counter)*100

  print("*************************************")  
  print("{} of {} problems ({} %) solved in less than {} seconds.".format(solved, counter, percent, timebound))  
  print("Problems that remain unsolved in the set are Problems: {}".format(unsolved))      
  print("*************************************") 

  solved = 0; unsolved = []; counter = 0; percent = 0; timebound = 8; #8 second time limit 
  print("Running Anytime Weighted A-star")   

  for i in range(0,40):
    print("*************************************")  
    print("PROBLEM {}".format(i))

    s0 = PROBLEMS[i] #Problems get harder as i gets bigger
    final = weighted_astar(s0, timebound)

    if final:
      final.print_path()   
      solved += 1 
    else:
      unsolved.append(i)
    counter += 1      

  if counter > 0:  
    percent = (solved/counter)*100   
      
  print("*************************************")  
  print("{} of {} problems ({} %) solved in less than {} seconds.".format(solved, counter, percent, timebound))  
  print("Problems that remain unsolved in the set are Problems: {}".format(unsolved))      
  print("*************************************") 

