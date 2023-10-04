
from tree_of_symptoms_base import SymptomBase, TreeOfSymptomsBase


class Symptom(SymptomBase):
    def __init__(self, symptom, severity):
        super().__init__(symptom, severity)
    """
        Add functions here if necessary
    """


class TreeOfSymptoms(TreeOfSymptomsBase):
    def __init__(self, root: Symptom):
        super().__init__(root)

    """
        Implement the described functions here !
    """


if __name__ == "__main__":
    """
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER 
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        The following main function is provided for simple debugging only
    """

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

