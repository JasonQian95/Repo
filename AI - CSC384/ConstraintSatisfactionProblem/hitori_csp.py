#Look for #IMPLEMENT tags in this file. These tags indicate what has
#to be implemented to complete the hitori models.  

'''
Construct and return hitori CSP models.
'''

from cspbase import *
import itertools

def hitori_csp_model_1(initial_hitori_board):
    '''Return a CSP object representing a hitori CSP problem along 
       with an array of variables for the problem. That is return

       hitori_csp, variable_array

       where hitori_csp is a csp representing hitori using model_1
       and variable_array is a list of lists

       [ [  ]
         [  ]
         .
         .
         .
         [  ] ]

       such that variable_array[i][j] is the Variable (object) that
       you built to represent the value to be placed in cell i,j of
       the hitori board (indexed from (0,0) to (8,8))

       
       
       The input board is specified as a list of n lists. Each of the
       n lists represents a row of the board. Each item in the list 
       represents a cell, and will contain a number between 1--n.
       E.g., the board
    
       -------------------  
       |1|3|4|1|
       |3|1|2|4|
       |2|4|2|3|
       |1|2|3|2|
       -------------------
       would be represented by the list of lists
       
       [[1,3,4,1],
       [3,1,2,4],
       [2,4,2,3],
       [1,2,3,2]]
       
       This routine returns Model_1 which consists of a variable for
       each cell of the board, with domain equal to {0,i}, with i being
       the initial value of the cell in the board. 
       
       Model_1 also contains BINARY CONSTRAINTS OF NOT-EQUAL between
       all relevant variables (e.g., all pairs of variables in the
       same row, etc.)
       
       All of the constraints of Model_1 MUST BE binary constraints 
       (i.e., constraints whose scope includes exactly two variables).
    '''
    ##IMPLEMENT
    csp = CSP("myCSP")
    variables = []
    for row in range(0, len(initial_hitori_board)):
        variables.append([])
        for column in range(0, len(initial_hitori_board[row])):
            var = Variable(str(row) + str(column), [0, initial_hitori_board[row][column]])
            variables[row].append(var)
            csp.add_var(var)
    
    length = len(variables)
    for row in range(0, length):
        for var1 in range(0, len(variables[row]) - 1):
            for var2 in range(var1 + 1, length):
                constraint = Constraint("r" + str(row) + ":" + str(var1) + "." + str(var2), [variables[row][var1], variables[row][var2]])
                satisfyingTuples = []
                domain1 = variables[row][var1].domain()
                domain2 = variables[row][var2].domain()
                for var1Value in domain1:
                    for var2Value in domain2:
                        if var1Value == 0 and var2Value == 0 and abs(var1 - var2) != 1:
                            satisfyingTuples.append([var1Value, var2Value])
                        elif var1Value != var2Value:
                            satisfyingTuples.append([var1Value, var2Value])
                constraint.add_satisfying_tuples(satisfyingTuples)
                csp.add_constraint(constraint)
    
    #variables inverted
    variablesAsColumns = []
    for row in range(0, len(variables)):
        variablesAsColumns.append([])
    for row in range(0, len(variables)):
        for column in range(0, len(variables)):
            variablesAsColumns[column].append(variables[row][column])
    
    length = len(variablesAsColumns)
    for row in range(0, length):
        for var1 in range(0, len(variablesAsColumns[row]) - 1):
            for var2 in range(var1 + 1, length):
                constraint = Constraint("c" + str(row) + ":" + str(var1) + "." + str(var2), [variablesAsColumns[row][var1], variablesAsColumns[row][var2]])
                satisfyingTuples = []                
                domain1 = variablesAsColumns[row][var1].domain()
                domain2 = variablesAsColumns[row][var2].domain()
                for var1Value in domain1:
                    for var2Value in domain2:
                        if var1Value == 0 and var2Value == 0 and abs(var1 - var2) != 1:
                            satisfyingTuples.append([var1Value, var2Value])
                        elif var1Value != var2Value:
                            satisfyingTuples.append([var1Value, var2Value])
                constraint.add_satisfying_tuples(satisfyingTuples)
                csp.add_constraint(constraint)
    return csp, variables

##############################

def hitori_csp_model_2(initial_hitori_board):
    '''Return a CSP object representing a hitori CSP problem along 
       with an array of variables for the problem. That is return

       hitori_csp, variable_array

       where hitori_csp is a csp representing hitori using model_1
       and variable_array is a list of lists

       [ [  ]
         [  ]
         .
         .
         .
         [  ] ]

       such that variable_array[i][j] is the Variable (object) that
       you built to represent the value to be placed in cell i,j of
       the hitori board (indexed from (0,0) to (8,8))

       
       
       The input board is specified as a list of n lists. Each of the
       n lists represents a row of the board. Each item in the list 
       represents a cell, and will contain a number between 1--n.
       E.g., the board
    
       -------------------  
       |1|3|4|1|
       |3|1|2|4|
       |2|4|2|3|
       |1|2|3|2|
       -------------------
       would be represented by the list of lists
       
       [[1,3,4,1],
       [3,1,2,4],
       [2,4,2,3],
       [1,2,3,2]]

       The input board takes the same input format (a list of n lists 
       specifying the board as hitori_csp_model_1).
   
       The variables of model_2 are the same as for model_1: a variable
       for each cell of the board, with domain equal to {0,i}, where i is
       the initial value of the cell.

       However, model_2 has different constraints.  In particular, instead
       of binary not-equals constraints, model_2 has 2n n-ary constraints
       that resemble a modified all-different constraint.  Each constraint
       is over n variables.  For any given row (resp. column), that 
       constraint will incorporate both the adjacent black squares and 
       no repeated numbers rules.
       
    '''

    ###IMPLEMENT
    csp = CSP("myCSP")
    variables = []
    for row in range(0, len(initial_hitori_board)):
        variables.append([])
        for column in range(0, len(initial_hitori_board[row])):
            var = Variable(str(row) + str(column), [0, initial_hitori_board[row][column]])
            variables[row].append(var)
            csp.add_var(var)
    
    for row in range(0, len(variables)):
        constraint = Constraint("r" + str(row), variables[row])
        satisfyingTuples = []
        
        totalCombinations = pow(2, len(variables[row]))
        for i in range(0, totalCombinations):
            satisfyingTuples.append([])
        #permutate
        for var in range(0, len(variables[row])):
            totalPortions = pow(variables[row][var].domain_size(), var + 1)
            for portion in range(0, totalPortions):
                for pos in range(portion * totalCombinations // totalPortions, (portion + 1) * totalCombinations // totalPortions):
                    satisfyingTuples[pos].append(variables[row][var].domain()[portion % variables[row][var].domain_size()])
        #filter
        notSatisfyingTuples = []
        for t in satisfyingTuples:
            for i in range(0, len(t)):
                if t[i] == 0:
                    if i != len(t) - 1 and t[i] == t[i + 1]:
                        notSatisfyingTuples.append(t)
                        break
                elif t.count(t[i]) != 1:
                    notSatisfyingTuples.append(t)
                    break
        for t in notSatisfyingTuples:
            satisfyingTuples.remove(t)
        constraint.add_satisfying_tuples(satisfyingTuples)
        csp.add_constraint(constraint)
      
    #variables inverted
    variablesAsColumns = []
    for row in range(0, len(variables)):
        variablesAsColumns.append([])
    for row in range(0, len(variables)):
        for column in range(0, len(variables)):
            variablesAsColumns[column].append(variables[row][column])
    
    for row in range(0, len(variablesAsColumns)):
        constraint = Constraint("c" + str(row), variablesAsColumns[row])
        satisfyingTuples = []
        
        totalCombinations = pow(2, len(variablesAsColumns[row]))
        for i in range(0, totalCombinations):
            satisfyingTuples.append([])
        #permutate
        for var in range(0, len(variablesAsColumns[row])):
            totalPortions = pow(variables[row][var].domain_size(), var + 1)
            for portion in range(0, totalPortions):
                for pos in range(portion * totalCombinations // totalPortions, (portion + 1) * totalCombinations // totalPortions):
                    satisfyingTuples[pos].append(variables[row][var].domain()[portion % variables[row][var].domain_size()])
        #filter
        notSatisfyingTuples = []
        for t in satisfyingTuples:
            for i in range(0, len(t)):
                if t[i] == 0:
                    if i != len(t) - 1 and t[i] == t[i + 1]:
                        notSatisfyingTuples.append(t)
                        break
                elif t.count(t[i]) != 1:
                    notSatisfyingTuples.append(t)
                    break
        for t in notSatisfyingTuples:
            satisfyingTuples.remove(t)
        constraint.add_satisfying_tuples(satisfyingTuples)
        csp.add_constraint(constraint)
    return csp, variables