import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Hospital3 extends HospitalBase {

    static class Node implements Comparable<Node> {
        PatientBase data;
        Node next;

        Node(PatientBase patient) {
            data = patient;
        }

        @Override
        public int compareTo(Node o) {
            return data.compareTo(o.data);
        }

        @Override
        public String toString() {
            return "Node(data=" + data.toString() + ")";
        }
    }

    private Node head;
    private Node tail;

    @Override
    public boolean addPatient(PatientBase patient) {
        Node newNode = new Node(patient);

        if (head == null) {
            head = newNode;
            tail = head;
            return true;
        }
        tail.next = newNode;
        tail = newNode;
        return true;
    }

    @Override
    public Iterator<PatientBase> iterator() {
        if (head != null && head.next != null) {
            head = mergeSort(head);
        }

        return new Iterator<>() {
            Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public PatientBase next() {
                if (hasNext()) {
                    PatientBase nextData = current.data;
                    current = current.next;
                    return nextData;
                }
                return null;
            }
        };
    }

    private Node mergeSort(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node middle = getMiddle(head);
        Node nextToMiddle = middle.next;

        middle.next = null;

        Node left = mergeSort(head);
        Node right = mergeSort(nextToMiddle);

        return sortedMerge(left, right);
    }

    private Node sortedMerge(Node a, Node b) {
        Node result;

        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.compareTo(b) <= 0) {
            result = a;
            result.next = sortedMerge(a.next, b);
        } else {
            result = b;
            result.next = sortedMerge(a, b.next);
        }
        return result;
    }

    private Node getMiddle(Node head) {
        if (head == null) {
            return null;
        }

        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        /*
         * Public tests
         */
        {
            var hospital = new Hospital3();
            var p1 = new Patient("Max", "11:00");
            var p2 = new Patient("Alex", "13:15");
            var p3 = new Patient("George", "14:00");
            hospital.addPatient(p1);
            hospital.addPatient(p2);
            hospital.addPatient(p3);
            var patients = new Patient[] {p1, p2, p3};
            int i = 0;
            for (var patient : hospital) {
                assert Objects.equals(patient, patients[i++]);
            }
        }

        /*
         * Hidden tests
         */
        // One element only
        {
            var hospital = new Hospital3();
            var p1 = new Patient("Alex", "13:15");
            hospital.addPatient(p1);
            for (var patient : hospital) {
                assert Objects.equals(patient, p1);
            }
        }

        // Normal sort
        {
            var hospital = new Hospital3();
            var p1 = new Patient("Alex", "13:15");
            var p2 = new Patient("Max", "11:00");
            var p3 = new Patient("George", "14:00");
            var p4 = new Patient("Lisa", "09:00");
            hospital.addPatient(p1);
            hospital.addPatient(p2);
            hospital.addPatient(p3);
            hospital.addPatient(p4);
            var patients = List.of(p4, p2, p1, p3);
            int i = 0;
            for (var patient : hospital) {
                assert Objects.equals(patient, patients.get(i++));
            }
        }

        // Same names
        {
            var hospital = new Hospital3();
            var p1 = new Patient("Alex", "13:15");
            var p2 = new Patient("Alex", "11:00");
            var p3 = new Patient("Alex", "14:00");
            var p4 = new Patient("Alex", "09:00");
            hospital.addPatient(p1);
            hospital.addPatient(p2);
            hospital.addPatient(p3);
            hospital.addPatient(p4);
            var patients = List.of(p4, p2, p1, p3);
            int i = 0;
            for (var patient : hospital) {
                assert Objects.equals(patient, patients.get(i++));
            }
        }

        // Same times, different names
        {
            var hospital = new Hospital3();
            var p1 = new Patient("Daniel", "13:15");
            var p2 = new Patient("Mark", "13:15");
            var p3 = new Patient("Ben", "13:15");
            var p4 = new Patient("Alex", "13:15");
            hospital.addPatient(p1);
            hospital.addPatient(p2);
            hospital.addPatient(p3);
            hospital.addPatient(p4);
            var patients = List.of(p1, p2, p3, p4);
            int i = 0;
            for (var patient : hospital) {
                assert Objects.equals(patient, patients.get(i++));
            }
        }

        // Same times, different names 2
        {
            var hospital = new Hospital3();
            var p1 = new Patient("Alexc", "13:15");
            var p2 = new Patient("Alexz", "13:15");
            var p3 = new Patient("Alexb", "13:10");
            var p4 = new Patient("Alexa", "13:15");
            hospital.addPatient(p1);
            hospital.addPatient(p2);
            hospital.addPatient(p3);
            hospital.addPatient(p4);
            var patients = List.of(p3, p1, p2, p4);
            int i = 0;
            for (var patient : hospital) {
                assert Objects.equals(patient, patients.get(i++));
            }
        }

        // Time limit test 1 (add to beginning)
        {
            var hospital = new Hospital3();
            var random = new Random();
            for (int i = 0; i < 1000; i++) {
                String patientName = random.ints(8, 'a', 'z' + 1)
                        .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                        .toString();
                List<String> hours = List.of("08", "09", "10", "11", "13", "14", "15", "16", "17");
                String hour = hours.get(random.nextInt(hours.size()));
                int minutes = random.nextInt(60);
                String patientTime = String.format("%s:%02d", hour, minutes);
                hospital.addPatient(new Patient(patientName, patientTime));
            }
            var t1 = Instant.now();
            hospital.addPatient(new Patient("ZZZZZZ", "18:00"));
            var t2 = Instant.now();
            assert Duration.between(t1, t2).toNanos() < 50_000;
        }
    }
}
