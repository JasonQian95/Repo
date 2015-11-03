from abc import ABCMeta, abstractmethod

class RegexTreeNode:
    
    __metaclass__ = ABCMeta
    
    """
    Note: No examples since RegexTreeNode is abstract and not to be 
    initialized; an instance of it will never exist
    """
    
    @abstractmethod
    def __init__(self, value: str):
        """
        Note: This method is strictly to be called through super(); this class 
        is abstract and should never be initialized, only inherited from. In 
        other words, type(self) is RegexTreeNode should never be true
        """
        self.value = str(value) 
        """String conversion not nessecary with this model, however it is kept
        to ensure the code still works in case of future changes
        """

    @abstractmethod
    def __eq__(self, other):
        """
        Return whether or not self's value is equal to the other's value
        
        Note: RegexTreeNode('|', LeafNode('1')) and PipeNode(LeafNode('1')) 
        are not equal, due to them having different types. If each 
        isinstance() check is removed from each subclass's __eq__() methods,
        then they would be equal, but that introduces other problems, such as
        if LeafNode('1').__eq__(variable) is called, with variable being a
        non-LeafNode. As a RegexTreeNode is only to be inherited from,
        never created, I've decided that this way is preferable,
        but it is an eaasy change to make.
        """
        return self.value == other.value

    @abstractmethod
    def __repr__(self):
        """
        Return the string representation of self (the RegexTreeNode's value)
        """
        return "'" + str(self.value) + "'"

    def get_value(self):
        """
        Return the value of self
        """
        return self._value
    
    def set_value(self, value: str):
        """
        Set the value of self, only if self does not already have a value
        """
        if '_value' in dir(self):
            raise Exception('Value has already been set')
        else:
            self._value = value

    value = property(get_value, set_value, None, None)

    
class PeriodNode(RegexTreeNode):
    
    def __init__(self, lchild, rchild):
        """
        Note: PeriodNode must have a value of "." and have exactly two children
        """
        super().__init__(".")
        self.lchild = lchild
        self.rchild = rchild

    def __eq__(self, other):
        """
        Return whether or not self is equal to other. Two PeriodNodes are 
        equal to each other if they are both PeriodNodes, have the same value,
        and their children are equal
        
        >>>a = PeriodNode(LeafNode('1'), LeafNode('0'))
        >>>b = PeriodNode(LeafNode('1'), LeafNode('1'))
        >>>a == b
        False
        
        >>>a = PeriodNode(PeriodNode(LeafNode('0'), LeafNode('1')), \
        LeafNode('0'))
        >>>b = PeriodNode(PeriodNode(LeafNode('0'), LeafNode('1')), \
        LeafNode('0'))
        >>>a == b
        True
        """
        return type(other) is PeriodNode and super().__eq__(other) and \
               self.lchild.__eq__(other.lchild) and \
               self.rchild.__eq__(other.rchild)
    
    def __repr__(self):
        """
        Return the string representation of self (the PeriodNode's value).
        The string representation of a PeriodNode is the class name, followed
        by the value, followed by the string representations of both children
        
        >>>a = PeriodNode(LeafNode('1'), LeafNode('0'))
        >>>a
        PeriodNode(LeafNode('1'), LeafNode('0'))
        
        >>>a = PeriodNode(PeriodNode(LeafNode('0'), LeafNode('1')), \
        LeafNode('0'))
        >>>a
        PeriodNode(PeriodNode(LeafNode('0'), LeafNode('1')), LeafNode('0'))
        """        
        return "PeriodNode(" + self.lchild.__repr__() + ", " \
               + self.rchild.__repr__() + ")"

    def get_lchild(self):
        """
        Return the left child of self
        
        >>>a = PeriodNode(LeafNode('1'), LeafNode('0'))
        >>>a.get_lchild()
        LeafNode('1')
        
        >>>a = PeriodNode(PeriodNode(LeafNode('0'), LeafNode('1')), \
        LeafNode('0'))
        >>>a.get_lchild()
        PeriodNode(LeafNode('0'), LeafNode('1'))
        """
        return self._lchild
 
    def set_lchild(self, lchild: int):
        """
        Set the left child of self, only if self does not already have a left
        child. Since it is set at initialization, it will never be set again
        and will always raise an error
        """
        if '_lchild' in dir(self):
            raise Exception('Left child has already been set')
        else:
            self._lchild = lchild         

    lchild = property(get_lchild, set_lchild, None, None)
    
    def get_rchild(self):
        """
        Return the right child of self
        
        >>>a = PeriodNode(LeafNode('1'), LeafNode('0'))
        >>>a.get_rchild()
        LeafNode('0')
        
        >>>a = PeriodNode(PeriodNode(LeafNode('0'), LeafNode('1')), \
        LeafNode('0'))
        >>>a.get_rchild()
        LeafNode('0')
        """
        return self._rchild
    
    def set_rchild(self, rchild: int):
        """
        Set the right child of self, only if self does not already have a
        right child. Since it is set at initialization, it will never be set
        again and will always raise an error
        """      
        if '_rchild' in dir(self):
            raise Exception('Right child has already been set')
        else:
            self._rchild = rchild            

    rchild = property(get_rchild, set_rchild, None, None) 

class PipeNode(RegexTreeNode):
    
    def __init__(self, lchild, rchild):
        """
        Note: PipeNode must have a value of "|" and have exactly two children
        """
        super().__init__("|")
        self.lchild = lchild
        self.rchild = rchild

    def __eq__(self, other):
        """
        Return whether or not self is equal to other. Two PipeNodes are equal
        to each other if they are both PeriodNodes, have the same value, and
        their children are equal
        
        >>>a = PipeNode(LeafNode('1'), LeafNode('0'))
        >>>b = PipeNode(LeafNode('1'), LeafNode('1'))
        >>>a == b
        False
        
        >>>a = PipeNode(PeriodNode(LeafNode('0'), LeafNode('1')), \
        LeafNode('0'))
        >>>b = PipeNode(PeriodNode(LeafNode('0'), LeafNode('1')), \
        LeafNode('0'))
        >>>a == b
        True
        """        
        return type(other) is PipeNode and super().__eq__(other) and \
               self.lchild.__eq__(other.lchild) and \
               self.rchild.__eq__(other.rchild)
    
    def __repr__(self):
        """
        Return the string representation of self (the PipeNode's value). The
        string representation of a PipeNode is the class name, followed by the
        value, followed by the string representations of both children
        
        >>>a = PipeNode(LeafNode('1'), LeafNode('0'))
        >>>a
        PipeNode(LeafNode('1'), LeafNode('0'))
        
        >>>a = PipeNode(PeriodNode(LeafNode('0'), LeafNode('1')), \
        LeafNode('0'))
        >>>a
        PipeNode(PeriodNode(LeafNode('0'), LeafNode('1')), LeafNode('0'))
        """             
        return "PipeNode(" + self.lchild.__repr__() + ", " + \
               self.rchild.__repr__() + ")"

    def get_lchild(self):
        """
        Return the left child of self
        
        >>>a = PipeNode(LeafNode('1'), LeafNode('0'))
        >>>a.get_lchild()
        LeafNode('1')
        
        >>>a = PipeNode(PeriodNode(LeafNode('0'), LeafNode('1')), \
        LeafNode('0'))
        >>>a.get_lchild()
        PeriodNode(LeafNode('0'), LeafNode('1'))
        """
        return self._lchild

    def set_lchild(self, lchild: int):
        """
        Set the left child of self, only if self does not already have a left
        child. Since it is set at initialization, it will never be set again
        and will always raise an error
        """        
        if '_lchild' in dir(self):
            raise Exception('Left child has already been set')
        else:
            self._lchild = lchild         

    lchild = property(get_lchild, set_lchild, None, None)
    
    def get_rchild(self):
        """
        Return the right child of self
        
        >>>a = PipeNode(LeafNode('1'), LeafNode('0'))
        >>>a.get_rchild()
        LeafNode('0')
        
        >>>a = PipeNode(PeriodNode(LeafNode('0'), LeafNode('1')), \
        LeafNode('0'))
        >>>a.get_rchild()
        LeafNode('0')
        """
        return self._rchild
    
    def set_rchild(self, rchild: int):
        """
        Set the right child of self, only if self does not already have a
        right child. Since it is set at initialization, it will never be set 
        again and will always raise an error
        """        
        if '_rchild' in dir(self):
            raise Exception('Right child has already been set')
        else:
            self._rchild = rchild            
 
    rchild = property(get_rchild, set_rchild, None, None) 
    
    
class AsteriskNode(RegexTreeNode):
    
    def __init__(self, child):
        """
        Note: AsteriskNode must have a value of "*" and have exactly one child
        """
        super().__init__("*")
        self.child = child

    def __eq__(self, other):
        """
        Return whether or not self is equal to other. Two AsteriskNodes are
        equal to each other if they are both AsteriskNodes, have the same
        value, and their child are equal
        
        >>>a = AsteriskNode(LeafNode('1'))
        >>>b = AsteriskNode(PeriodNode(LeafNode('1'), \
        AsteriskNode(LeafNode('0')))
        >>>a == b
        False
        
        >>>a = AsteriskNode(PeriodNode(LeafNode('1'), \
        AsteriskNode(LeafNode('0')))
        >>>b = AsteriskNode(PeriodNode(LeafNode('1'), \
        AsteriskNode(LeafNode('0')))
        >>>a == b
        True
        """               
        return type(other) is AsteriskNode and super().__eq__(other) and \
               self.child.__eq__(other.child)
    
    def __repr__(self):
        """
        Return the string representation of self (the AsteriskNode's value).
        The string representation of a AsteriskNode is the class name,
        followed by the value, followed by the string representations of child
        """             
        return "AsteriskNode(" + self.child.__repr__() + ")"

    def get_child(self):
        """
        Return the child of self
        
        >>>a = AsteriskNode(LeafNode('1'))
        >>>a.get_child()
        LeafNode('1')
        
        >>>a = AsteriskNode(PeriodNode(LeafNode('1'), \
        AsteriskNode(LeafNode('0')))
        >>>a.get_child()
        PeriodNode(LeafNode('1'), AsteriskNode(LeafNode('0'))
        """
        return self._child

    def set_child(self, child: int):
        """
        Set the right child of self, only if self does not already have a
        right child. Since it is set at initialization, it will never be set 
        again and will always raise an error
        """        
        if '_child' in dir(self):
            raise Exception('Child has already been set')
        else:
            self._child = child         
    
    child = property(get_child, set_child, None, None)
    
    
class LeafNode(RegexTreeNode):
    
    def __init__(self, value: str):
        """
        Precondition: Value must be one of "0", "1", "2", or "e"
        """
        if value not in ['0', '1', '2', 'e']:
            raise Exception(
                "LeafNodes must have a value of '0', '1', '2', or 'e'")
        super().__init__(value)
        

    def __eq__(self, other):
        """
        Return whether or not self is equal to other. Two LeafNodes are equal
        to each other if they are both LeafNodes and have the same value
        
        >>>a = LeafNode('1')
        >>>b = LeafNode('2')
        >>>a == b
        False
        
        >>>a = LeafNode('1')
        >>>b = LeafNode('1')
        >>>a == b
        True
        """              
        return type(other) is LeafNode and super().__eq__(other)

    def __repr__(self):
        """
        Return the string representation of self (the LeafNode's value).
        
        >>>a = LeafNode('1')
        >>>a
        LeafNode('1')
        
        >>>a = LeafNode('e')
        >>>a
        LeafNode('e')
        """             
        return "LeafNode(" + super().__repr__() + ")"