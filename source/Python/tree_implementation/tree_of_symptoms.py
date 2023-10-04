
from tree_of_symptoms_base import SymptomBase, TreeOfSymptomsBase


class Symptom(SymptomBase):
    def __init__(self, symptom, severity):
        super().__init__(symptom, severity)

    def __lt__(self, other):
        return self.severity < other.severity

    def __le__(self, other):
        return self.severity <= other.severity


class TreeOfSymptoms(TreeOfSymptomsBase):
    def __init__(self, root: Symptom):
        super().__init__(root)

    def in_order_traversal(self):
        in_order = []
        self._in_order_recursion(self.root, in_order)
        return in_order

    def post_order_traversal(self):
        post_order = []
        self._post_order_recursion(self.root, post_order)
        return post_order

    def tree_restructure(self, severity):

        arr = self.in_order_traversal()
        arr.sort()
        # This part can be improved to log (n) instead of O(n)
        i = 0
        while arr[i].severity < severity:
            i += 1
        # copy array elements back to binary tree
        left = self.array_to_bst(arr, 0, i-1)
        right = self.array_to_bst(arr, i+1, len(arr)-1)
        self.root = arr[i]
        self.root.left = left
        self.root.right = right

    def array_to_bst(self, arr, start, end):
        # Base Case
        if start > end:
            return None
        mid_num = (start+end) // 2
        node = arr[mid_num]
        node.left = self.array_to_bst(arr, start, mid_num-1)
        node.right = self.array_to_bst(arr, mid_num+1, end)
        return node

    def _in_order_recursion(self, node, result):
        if node is None:
            return node
        self._in_order_recursion(node.left, result)
        result.append(node)
        self._in_order_recursion(node.right, result)

    def _post_order_recursion(self, node, result):
        if node is None:
            return node
        self._post_order_recursion(node.left, result)
        self._post_order_recursion(node.right, result)
        result.append(node)


if __name__ == "__main__":

    # Public Tests

    cough = Symptom("Cough", severity=3)
    fever = Symptom("Fever", severity=6)
    red_eyes = Symptom("Red Eyes", severity=1)

    red_eyes.left = cough
    red_eyes.right = fever

    tree = TreeOfSymptoms(red_eyes)
    in_order_traversal = tree.in_order_traversal()
    correct_traversal = [cough, red_eyes, fever]
    for i, el in enumerate(in_order_traversal):
        assert el == correct_traversal[i]
    assert tree.root == red_eyes

    tree.tree_restructure(severity=2)
    in_order_traversal = tree.in_order_traversal()
    correct_traversal = [red_eyes, cough, fever]
    for i, el in enumerate(in_order_traversal):
        assert el == correct_traversal[i]
    assert tree.root == cough

    # Private Tests

    cough = Symptom("Cough", severity=2)
    fever = Symptom("Fever", severity=6)
    # chest_pain = Symptom("Chest pain", severity=7)
    blured_vision = Symptom("Blured vision", severity=9)
    red_eyes = Symptom("Red Eyes", severity=1)

    cough.left = fever
    cough.right = red_eyes
    red_eyes.left = blured_vision

    tree = TreeOfSymptoms(cough)
    in_order_traversal = tree.in_order_traversal()
    correct_traversal = [fever, cough, blured_vision, red_eyes]
    for i, el in enumerate(in_order_traversal):
        assert el == correct_traversal[i]
    assert tree.root == cough

    tree.tree_restructure(severity=3)
    in_order_traversal = tree.in_order_traversal()
    correct_traversal = [red_eyes, cough, fever, blured_vision]
    for i, el in enumerate(in_order_traversal):
        assert el == correct_traversal[i]
    assert tree.root == fever


