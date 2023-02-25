import java.util.Iterator;
import java.util.Objects;

public class Hospital3 extends HospitalBase {
    int patientHour;
    int patientMinute;
    Node head, tail = null;

    public Hospital3() {
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        /* Add your code here! */
        if (!(timeChecker(patient))) {
            return false;
        }

        Node newNode = new Node(patient);

        if (head == null) {
            head = tail = newNode;
            head.prev = null;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        tail.next = null;
        return true;
    }

    @Override
    public Iterator<PatientBase> iterator() {
        mergeSort(head);
        while(head.prev != null) {
            head = head.prev;
        }
        Iterator<PatientBase> iterator = new Iterator<>() {
            Node current = head;

            @Override
            public boolean hasNext() {
                return (current != null && head.prev == null);
            }

            @Override
            public PatientBase next() {
                PatientBase patient = current.patient;
                current = current.next;
                return patient;
            }
        };
        return iterator;
    }

    /* Add any extra functions below */

    private boolean timeChecker(PatientBase patient) {
        patientHour = Integer.parseInt(patient.getTime().split(":")[0]);
        patientMinute = Integer.parseInt(patient.getTime().split(":")[1]);

        if ((patientHour != 12) && (8 <= patientHour) && (patientHour < 18)) {
            return (patientMinute < 60);
        } else return false;
    }

    Node split(Node head) {
        Node fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        Node temp = slow.next;
        slow.next = null;
        return temp;
    }

    class Node {
        PatientBase patient;
        Node prev;
        Node next;

        Node(PatientBase patient) {
            this.patient = patient;
        }
    }

    Node merge(Node first, Node second) {
        if (first == null) {
            return second;
        }

        if (second == null) {
            return first;
        }

        if (first.patient.compareTo(second.patient) == -1) {
            first.next = merge(first.next, second);
            first.next.prev = first;
            first.prev = null;
            return first;
        } else {
            second.next = merge(first, second.next);
            second.next.prev = second;
            second.prev = null;
            return second;
        }
    }

    Node mergeSort(Node node) {
        if (node == null || node.next == null) {
            return node;
        }
        Node second = split(node);

        node = mergeSort(node);
        second = mergeSort(second);

        return merge(node, second);
    }

    public static void main(String[] args) {

        var hospital = new Hospital3();
        var p8 = new Patient("3", "10:00");
        var p2 = new Patient("5", "10:40");
        var p3 = new Patient("6", "13:00");
        var p10 = new Patient("7", "13:00");
        var p5 = new Patient("8", "12:20");
        var p6 = new Patient("2", "10:00");
        var p1 = new Patient("10", "17:40");
        var p7 = new Patient("1", "08:00");
        var p9 = new Patient("9", "17:20");
        var p4 = new Patient("8", "17:00");
        var p11 = new Patient("4", "10:00");

        hospital.addPatient(p7);
        hospital.addPatient(p2);
        hospital.addPatient(p3);
        hospital.addPatient(p4);
        hospital.addPatient(p5);
        hospital.addPatient(p6);
        hospital.addPatient(p7);
        hospital.addPatient(p8);
        hospital.addPatient(p9);
        hospital.addPatient(p10);
        hospital.addPatient(p11);

        var patients = new Patient[] {p7, p6, p8, p4, p5, p6, p7, p8, p9, p10, p11};
        int i = 0;
        for (var patient : hospital) {
            assert Objects.equals(patient, patients[i++]);
        }
        for (PatientBase patientBase : hospital) {
            System.out.println(patientBase);
        }

    }


}
