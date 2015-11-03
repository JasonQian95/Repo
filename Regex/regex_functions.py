"""
# Copyright 2013 Nick Cheng, Brian Harrington, Danny Heap, 2013, 2014
# Distributed under the terms of the GNU General Public License.
#
# This file is part of Assignment 1, CSC148, Winter 2014
#
# This is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This file is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this file.  If not, see <http://www.gnu.org/licenses/>.
"""

# Do not change this import statement, or add any of your own!
from regextree import RegexTree, Leaf, StarTree, DotTree, BarTree

# Do not change any of the class declarations above this comment
# Student code below this comment.


def is_regex(s: str) -> bool:
    """
    Return True if s is a valid regex expression and False otherwise.
    
    >>> is_regex('((1.(0|2)*).0)')
    True
    
    >>> is_regex('(*(2|0).2)')
    False
    
    >>> is_regex('((2|0).*2)')
    False
    """
    # Normally, this would be included under Else
    # but this is nessecary for all_regex_permutations to work.
    if len(s) == 0:
        return False
    # Leaf
    if len(s) == 1 and s in '012e':
        return True
    # Star
    elif s[-1] is '*':
        return is_regex(s[:-1])
    # For binary trees, split the regex into two children and a connecter
    elif s[0] is '(' and s[-1] is ')':
        connecter = ""
        bracket = 0
        regex1 = ""
        regex2 = ""
        regex1_complete = False
        for char in s[1:-1]:
            if char is '(':
                bracket = bracket + 1
            elif char is ')':
                bracket = bracket - 1
            elif bracket == 0 and char not in '012e*':
                if char not in '.|':
                    return False
                connecter = char
                regex1_complete = True
            if not regex1_complete:
                regex1 = regex1 + char
            else:
                regex2 = regex2 + char
        regex2 = regex2[1:]  # Because the first char is the connecter
        return is_regex(regex1) and is_regex(regex2)
    # Anything else is not valid
    else:
        return False


def all_regex_permutations(s: str) -> {str, ...}:
    """
    Return the set of all permutations of s that are also valid regexes.
    
    >>> all_regex_permutations("(.)01") == {'(0.1)', '(1.0)'}
    True
    
    >>> all_regex_permutations('1*2|()') == {'(1*|2)', '(2|1*)', '(2*|1)', \
    '(1|2*)', '(2|1)*', '(1|2)*'}
    True
    
    >>> all_regex_permutations('().12*') == {'(1.2*)', '(1*.2)', '(2.1)*',  \
    '(1.2)*', '(2.1*)', '(2*.1)'}
    True
    """
    
    def perm(s: str) -> {str, ...}:
        """
        From the week 4 lecture:
        
        Return set of all permutations of s.
        
        >>> perm("a") == {"a"}
        True
        >>> perm("ab") == {"ab", "ba"}
        True
        >>> perm("abc") == {"abc", "acb", "bac", "bca", "cab", "cba"}
        True
        """
        return (set(sum([[s[i] + p for p in perm(s[:i] + s[i + 1:])] 
                         for i in range(len(s))], [])) if len(s) > 1 else {s})    

    #  Filter for valid regexes
    permutations = perm(s)
    valid_regexes = []
    for item in permutations:
        if is_regex(item):
            valid_regexes.append(item)
    return set(valid_regexes)


def regex_match(r: 'RegexTree', s: str) -> bool:
    """
    Return True if r matches s as defined by regex convention and 
    False otherwise
    
    Precondition: r must be a valid RegexTree
    
    >>> regex_match(StarTree(BarTree(Leaf('1'), Leaf('2'))), '1122')
    True
    
    >>> regex_match(BarTree(StarTree(Leaf('1')), StarTree(Leaf('2'))), '1122')
    False
    
    >>> regex_match(DotTree(StarTree(Leaf('1')), Leaf('0')), '11110')
    True
    """
    for char in s:
        if char not in "012":
            return False
    if isinstance(r, Leaf):
        if r.get_symbol() is s or (r.get_symbol() is 'e' and s is ''):
            return True
        return False
    elif isinstance(r, StarTree):
        if s is '':
            return True
        #  Look at all the possible evenly divided subsections of s
        for counter in range(1, len(s) + 1):
            if len(s) % counter == 0:
                if (False not in 
                    [regex_match(r.get_children()[0], s[i:(i + 1) * counter]) 
                     for i in range(len(s) // counter)]):
                    return True
        return False
    elif isinstance(r, DotTree):
        #  Look at every possible split of s
        for length in range(len(s) + 1):
            s1_length = length
            s1 = s[0:s1_length]
            s2 = s[s1_length:]
            if s1_length == 0:
                s1 = ""
            elif s1_length == len(s):
                s2 = ""
            if (regex_match(r.get_children()[0], s1) and 
                    regex_match(r.get_children()[1], s2)):
                return True
        return False
    elif isinstance(r, BarTree):
        return (True if True in [regex_match(c, s) for c in r.get_children()] 
                else False)


def build_regex_tree(regex: str) -> 'RegexTree':
    """
    Return a RegexTree corresponding to regex. The RegexTree returned will 
    be the root of the tree
    
    Precondition: the regex inputted must be a valid regex
    
    >>> build_regex_tree('((0.1).2)')
    DotTree(DotTree(Leaf('0'), Leaf('1')), Leaf('2'))
    
    >>> build_regex_tree('((0.1)|(0.1))')
    BarTree(DotTree(Leaf('0'), Leaf('1')), DotTree(Leaf('0'), Leaf('1')))
    
    >>> build_regex_tree('(1|0)')
    BarTree(Leaf('1'), Leaf('0'))
    """
    if len(regex) == 1:
        return Leaf(regex)
    elif regex[-1] is '*':
        return StarTree(build_regex_tree(regex[:-1]))
    #  For binary trees, seperate regex into two children and a connecter
    connecter = ""
    bracket = 0
    regex1 = ""
    regex2 = ""
    regex1_complete = False
    for char in regex[1:-1]:
        if char is '(':
            bracket = bracket + 1
        elif char is ')':
            bracket = bracket - 1
        elif bracket == 0 and char not in '012e*':
            connecter = char
            regex1_complete = True
        if not regex1_complete:
            regex1 = regex1 + char
        else:
            regex2 = regex2 + char
    regex2 = regex2[1:]  # Because the first character is the connecter
    if connecter is '.':
        return DotTree(build_regex_tree(regex1), build_regex_tree(regex2))
    elif connecter is '|':
        return BarTree(build_regex_tree(regex1), build_regex_tree(regex2))
    
if __name__ == '__main__':
    import doctest
    doctest.testmod()