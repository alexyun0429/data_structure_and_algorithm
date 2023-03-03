import java.awt.*;
import java.util.*;
import java.util.List;

class Symptom extends SymptomBase {

	public Symptom(String symptom, int severity) {
		super(symptom, severity);
	}

	@Override
	public int compareTo(SymptomBase o) {
		/* Add your code here! */
		int first = getSeverity();
		int second = o.getSeverity();

		if (first <= second) {
			if (first == second) {
				return 0;
			}
			return -1;
		}
		return 1;
	}
}

public class TreeOfSymptoms extends TreeOfSymptomsBase {
	public boolean construct = false;
	public static ArrayList<SymptomBase> inOrder;
	public static ArrayList<SymptomBase> postOrder;

	public TreeOfSymptoms(SymptomBase root) {
		super(root);

	}

	@Override
	public ArrayList<SymptomBase> inOrderTraversal() {
		/* Add your code here! */

		SymptomBase root = getRoot();
		Stack<SymptomBase> stack = new Stack<>();
		ArrayList<SymptomBase> inOrder = new ArrayList<>();
		SymptomBase curr = root;

		while (!stack.isEmpty() || curr != null) {

			while (curr != null) {
				stack.push(curr);
				curr = curr.getLeft();
			}
			root = stack.peek();
			SymptomBase node = stack.pop();
			inOrder.add(root);

			curr = node.getRight();
		}
		return inOrder;
	}

	@Override
	public ArrayList<SymptomBase> postOrderTraversal() {
		/* Add your code here! */
		SymptomBase root = getRoot();
		SymptomBase curr = root;
		Stack<SymptomBase> stack = new Stack<>();
		stack.push(curr);
		ArrayList<SymptomBase> al = new ArrayList<>();

		while (!stack.isEmpty()) {
			SymptomBase node = stack.pop();
			al.add(0, root);
			if (curr.getLeft() != null) {
				stack.push(curr.getLeft());
			}
			if (curr.getRight() != null) {
				stack.push(curr.getRight());
			}
		}
		return al;
	}

	@Override
	public void restructureTree(int severity) {
		/* Add your code here! */
		ArrayList<SymptomBase> inOrder = inOrderTraversal();
		ArrayList<Integer> severityList = new ArrayList<Integer>();
		for (int i = 0; i < inOrder.size(); i++) {
			severityList.add(inOrder.get(i).getSeverity());
		}
		// output = [1,3,6]
		Collections.sort(severityList);
		int maxThreshold = Collections.max(severityList);


		if (inOrder == null) {
			this.inOrderTraversal();
		}
		SymptomBase tempSeverity = search(severity);

		if (construct == false) {
			for (int i = 0; i < inOrder.size(); i++) {
				SymptomBase insert = inOrder.get(i);
				insert.setLeft(null);
				insert.setRight(null);
				if (insert.getSeverity() < tempSeverity.getSeverity()) {
					addLeft(tempSeverity, insert);
				} else if (insert.getSeverity() > tempSeverity.getSeverity()) {
					addRight(tempSeverity, insert);
				}
			}
			construct = true;
		} else {
			while (getRoot() != tempSeverity) {
				if (getRoot().compareTo(tempSeverity) == 1) {
					setRoot((rightRotate(getRoot())));
				} else {
					setRoot(leftRotate(getRoot()));
				}
			}
		}
		setRoot(tempSeverity);
	}


	/* Add any extra functions below */

	class TreeNode {
		int value;
		TreeNode left, right;

		TreeNode(int item) {
			value = item;
			left = right = null;
		}
	}


	private SymptomBase search(int severity) {
		SymptomBase tempSeverity = null;

		for (int i = inOrder.size() - 1; i >= 0; i--) {

			if (inOrder.get(i).getSeverity() >= severity) {
				tempSeverity = inOrder.get(i);
			} else if (tempSeverity == null) {
				tempSeverity = inOrder.get(i);
			}
		}
		return tempSeverity;
	}

	public void addRight(SymptomBase initial, SymptomBase adding) {
		if (initial.getRight() == null) {
			initial.setRight(adding);
		} else {
			if (initial.getRight().getSeverity() < adding.getSeverity()) {
				addRight(initial.getRight(), adding);
			} else {
				addLeft(initial.getLeft(), adding);
			}
		}
	}

	public void addLeft(SymptomBase initial, SymptomBase adding) {
		if (initial.getLeft() == null) {
			initial.setLeft(adding);
		} else {
			if (initial.getLeft().getSeverity() > adding.getSeverity()) {
				addLeft(initial.getLeft(), adding);
			} else {
				addRight(initial.getRight(), adding);
			}
		}
	}

	public SymptomBase rightRotate(SymptomBase y) {
		SymptomBase x = y.getLeft();
		SymptomBase z = x.getRight();

		x.setRight(y);
		y.setLeft(z);
		return x;
	}

	public SymptomBase leftRotate(SymptomBase x) {
		SymptomBase y = x.getRight();
		SymptomBase z = y.getLeft();

		y.setLeft(x);
		x.setRight(z);
		return y;
	}

}

