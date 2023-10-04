import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

class Symptom extends SymptomBase {

	public Symptom(String symptom, int severity) {
		super(symptom, severity);
	}

	@Override
	public int compareTo(SymptomBase o) {
		return this.getSeverity() - o.getSeverity();
	}
}

public class TreeOfSymptoms extends TreeOfSymptomsBase {

	public TreeOfSymptoms(SymptomBase root) {
		super(root);
	}

	@Override
	public ArrayList<SymptomBase> inOrderTraversal() {
		ArrayList<SymptomBase> inOrder = new ArrayList<>();
		inOrderRecursion(this.getRoot(), inOrder);
		return inOrder;
	}

	@Override
	public ArrayList<SymptomBase> postOrderTraversal() {
		ArrayList<SymptomBase> postOrder = new ArrayList<>();
		postOrderRecursion(this.getRoot(), postOrder);
		return postOrder;
	}

	@Override
	public void restructureTree(int severity) {
		ArrayList<SymptomBase> arr = inOrderTraversal();
		Collections.sort(arr);
		int i = 0;
		while (arr.get(i).getSeverity() < severity ){
			i += 1;
		}

		SymptomBase left = arrayToBST(arr, 0, i - 1);
		SymptomBase right = arrayToBST(arr, i + 1, arr.size() - 1);

		this.setRoot(arr.get(i));
		this.getRoot().setLeft(left);
		this.getRoot().setRight(right);
	}

	private static SymptomBase arrayToBST(ArrayList<SymptomBase> arr, int start, int end) {
		// Base Case
	    if (start > end) {
	    	return null;
	    }

	    int midNum = (start + end) / 2;
        SymptomBase node = arr.get(midNum);

        node.setLeft(arrayToBST(arr, start, midNum - 1));
        node.setRight(arrayToBST(arr, midNum + 1, end));
	    return node;
	}

	private static void inOrderRecursion(SymptomBase node, ArrayList<SymptomBase> result) {
		// Base Case
	    if (node == null)
	    	return;

	    inOrderRecursion(node.getLeft(), result);
	    result.add(node);
        inOrderRecursion(node.getRight(), result);
	}

	private static void postOrderRecursion(SymptomBase node, ArrayList<SymptomBase> result) {
		// Base Case
	    if (node == null)
	    	return;

	    postOrderRecursion(node.getLeft(), result);
        postOrderRecursion(node.getRight(), result);
        result.add(node);
	}

	public static void main(String[] args) {
        /*
         * Public tests
         */
		{
			var cough = new Symptom("Cough", 3);
			var fever = new Symptom("Fever", 6);
			var redEyes = new Symptom("Red Eyes", 1);

			redEyes.setLeft(cough);
			redEyes.setRight(fever);

			var tree = new TreeOfSymptoms(redEyes);
	        var inOrderTraversal = tree.inOrderTraversal();
	        var correctTraversal = new Symptom[] { cough, redEyes, fever };
	        int i = 0;
	        for (var patient : inOrderTraversal) {
	            assert Objects.equals(patient, correctTraversal[i++]);
	        }
	        assert tree.getRoot() == redEyes;

	        tree.restructureTree(2);
	        inOrderTraversal = tree.inOrderTraversal();
	        correctTraversal = new Symptom[] { redEyes, cough, fever};
	        i = 0;
	        for (var patient : inOrderTraversal) {
	        	assert Objects.equals(patient, correctTraversal[i++]);
	        }
	        assert tree.getRoot() == cough;
		}

		/*
         * Private tests
         */
		{
			var cough = new Symptom("Cough", 2);
			var fever = new Symptom("Fever", 6);
			var blurredVision = new Symptom("Blurred vision", 9);
			var redEyes = new Symptom("Red Eyes", 1);

			cough.setLeft(fever);
		    cough.setRight(redEyes);
		    redEyes.setLeft(blurredVision);

	        var tree = new TreeOfSymptoms(cough);
	        var inOrderTraversal = tree.inOrderTraversal();
	        var correctTraversal = new Symptom[] { fever, cough, blurredVision, redEyes };
	        int i = 0;
	        for (var patient : inOrderTraversal) {
	            assert Objects.equals(patient, correctTraversal[i++]);
	        }
	        assert tree.getRoot() == cough;
		}

		{
			var cough = new Symptom("Cough", 2);
			var fever = new Symptom("Fever", 6);
			var blurredVision = new Symptom("Blurred vision", 9);
			var redEyes = new Symptom("Red Eyes", 1);


			cough.setLeft(fever);
		    cough.setRight(redEyes);
		    redEyes.setLeft(blurredVision);

	        var tree = new TreeOfSymptoms(cough);
	        tree.restructureTree(3);
	        var inOrderTraversal = tree.inOrderTraversal();
	        var correctTraversal = new Symptom[] { redEyes, cough, fever, blurredVision};
	        int i = 0;
	        for (var patient : inOrderTraversal) {
	        	assert Objects.equals(patient, correctTraversal[i++]);
	        }
	        assert tree.getRoot() == fever;
		}
	}
}
