
class SymptomBase:
    def __init__(self, symptom, severity):
        self.symptom = symptom
        self.severity = severity
        self.left = None
        self.right = None

    def __repr__(self):
        return '({0}, {1})'.format(self.symptom, self.severity)

    def __eq__(self, other):
        return (self.symptom == other.symptom) & (self.severity == other.severity)

    def __hash__(self):
        return hash(self.symptom + str(self.severity))


class TreeOfSymptomsBase:
    def __init__(self, root):
        self.root = root

    def in_order_traversal(self):
        """
        Performs an in-order traversal of the tree.
        :return: the list of symptoms, corresponding to the in-order traversal
        """
        raise NotImplementedError('must be implemented by subclass')

    def post_order_traversal(self):
        """
        Performs a post-order traversal of the tree.
        :return: the list of symptoms, corresponding to the post-order traversal
        """
        raise NotImplementedError('must be implemented by subclass')

    def tree_restructure(self, severity):
        """
        Restructures the tree.
        """
        raise NotImplementedError('must be implemented by subclass')


