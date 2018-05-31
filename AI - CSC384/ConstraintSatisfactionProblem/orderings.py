#Look for #IMPLEMENT tags in this file. These tags indicate what has
#to be implemented.

import random
import sys

'''
This file will contain different variable ordering heuristics to be used within
bt_search.

var_ordering == a function with the following template
    ord_type(csp)
        ==> returns Variable 

    csp is a CSP object---the heuristic can use this to get access to the
    variables and constraints of the problem. The assigned variables can be
    accessed via methods, the values assigned can also be accessed.

    ord_type returns the next Variable to be assigned, as per the definition
    of the heuristic it implements.

val_ordering == a function with the following template
    val_ordering(csp,var)
        ==> returns [Value, Value, Value...]
    
    csp is a CSP object, var is a Variable object; the heuristic can use csp to access the constraints of the problem, and use var to access var's potential values. 

    val_ordering returns a list of all var's potential values, ordered from best value choice to worst value choice according to the heuristic.

'''

'''
ord_random(csp):
A var_ordering function that takes a CSP object csp and returns a Variable object var at random.  var must be an unassigned variable.
'''
def ord_random(csp):
    var = random.choice(csp.get_all_unasgn_vars())
    return var

'''
val_arbitrary(csp,var):
A val_ordering function that takes CSP object csp and Variable object var,
and returns a value in var's current domain arbitrarily.
'''
def val_arbitrary(csp,var):
    return var.cur_domain()

def ord_mrv(csp):
#IMPLEMENT
    '''
    ord_mrv(csp):
    A var_ordering function that takes CSP object csp and returns Variable object var, 
    according to the Minimum Remaining Values (MRV) heuristic as covered in lecture.  
    MRV returns the variable with the most constrained current domain 
    (i.e., the variable with the fewest legal values).
    '''
    smallestDomain = sys.maxsize
    smallestV = None;
    for v in csp.get_all_unasgn_vars():
        if v.cur_domain_size() < smallestDomain:
            smallestDomain = v.cur_domain_size()
            smallestV = v
    return smallestV

def ord_dh(csp):
#IMPLEMENT
    '''
    ord_dh(csp):
    A var_ordering function that takes CSP object csp and returns Variable object var,
    according to the Degree Heuristic (DH), as covered in lecture.
    Given the constraint graph for the CSP, where each variable is a node, 
    and there exists an edge from two variable nodes v1, v2 iff there exists
    at least one constraint that includes both v1 and v2,
    DH returns the variable whose node has highest degree.
    '''
    highestDegree = -1
    highestV = None;
    for v in csp.get_all_unasgn_vars():
        count = 0
        for cons in csp.get_cons_with_var(v):
            for variable in cons.get_scope():
                if variable in csp.get_all_unasgn_vars():
                    count += 1
        if count > highestDegree:
            highestDegree = count
            highestV = v
    return highestV

def val_lcv(csp,var):
#IMPLEMENT
    '''
    val_lcv(csp,var):
    A val_ordering function that takes CSP object csp and Variable object var,
    and returns a list of Values [val1,val2,val3,...]
    from var's current domain, ordered from best to worst, evaluated according to the 
    Least Constraining Value (LCV) heuristic.
    (In other words, the list will go from least constraining value in the 0th index, 
    to most constraining value in the $j-1$th index, if the variable has $j$ current domain values.) 
    The best value, according to LCV, is the one that rules out the fewest domain values in other 
    variables that share at least one constraint with var.
    '''
    possibleValues = var.cur_domain()
    ruledOut = []
    constraints = csp.get_cons_with_var(var)
    for v in possibleValues:
        '''
                count = 0
        for c in constraints:
            scope = c.get_scope();
            index = 0
            for i in range(0, len(scope)):
                if scope[i].name == var.name:
                    index = i
                    break
            for t in c.sat_tuples:
                if t[index] != v:
                    count += 1
        ruledOut.append(count)
        '''
        total_count = 0
        for c in constraints:
            scope = c.get_scope();
            notRuledOutValues = []
            for i in range(0, len(scope)):
                notRuledOutValues.append([])
            index = 0
            for i in range(0, len(scope)):
                if scope[i].name == var.name:
                    index = i
                    break
            for t in c.sat_tuples:
                if t[index] != v:
                    for i in range(0, len(scope)):
                        if i != index and t[i] not in notRuledOutValues[i]:
                            notRuledOutValues[i].append(t[i])
        
            total_domain = 0
            count = 0
            for var in scope:
                total_domain += var.cur_domain_size()
            for tup in notRuledOutValues:
                count += len(tup)        
            total_count += total_domain - count
        ruledOut.append(total_count)
    for i in range(0, len(possibleValues)):
        lowest = i
        for j in range(i + 1, len(ruledOut)):
            if ruledOut[j] < ruledOut[lowest]:
                lowest = j
        tempV = possibleValues[i]
        tempRuledOut = ruledOut[i]
        possibleValues[i] = possibleValues[lowest]
        ruledOut[i] = ruledOut[lowest]
        possibleValues[lowest] = tempV
        ruledOut[lowest] = tempRuledOut
    return possibleValues

def ord_custom(csp):
#IMPLEMENT
    '''
    ord_custom(csp):
    A var_ordering function that takes CSP object csp and returns Variable object var,
    according to a Heuristic of your design.  This can be a combination of the ordering heuristics 
    that you have defined above.
    ''' 
    # ord_mrv
    smallestDomain = sys.maxsize
    smallestV = None;
    allUnasgnVars = csp.get_all_unasgn_vars()
    for v in allUnasgnVars:
        domainSize = v.cur_domain_size()
        if domainSize < smallestDomain:
            smallestDomain = domainSize
            smallestV = v
        if domainSize == smallestDomain:
            # ord_dh
            highestDegree = -1
            highestV = None;
            for var in [v, smallestV]:
                count = 0
                allCons = csp.get_cons_with_var(var)
                for cons in allCons:
                    scope = cons.get_scope()
                    for variable in scope:
                        if variable in allUnasgnVars:
                            count += 1
                if count > highestDegree:
                    highestDegree = count
                    highestV = var
            smallestV = highestV  
        
    return smallestV