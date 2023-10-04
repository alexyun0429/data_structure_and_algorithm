import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Hospital2 extends HospitalBase {

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

    @Override
    public boolean addPatient(PatientBase patient) {
        Node newNode = new Node(patient);
        Node curr = head;

        if (curr == null) {
            head = newNode;
            return true;
        }

        if (newNode.compareTo(curr) < 0) {
            newNode.next = curr;
            head = newNode;
            return true;
        }

        Node prev = null;
        while (newNode.compareTo(curr) >= 0) {
            if (curr.next == null) {
                curr.next = newNode;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }

        if (prev != null) {
            prev.next = newNode;
        }
        newNode.next = curr;
        return true;
    }

    @Override
    public Iterator<PatientBase> iterator() {
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

    public static void main(String[] args) {
        /*
         * Public tests
         */
        {
            var hospital = new Hospital2();
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
            var hospital = new Hospital2();
            var p1 = new Patient("Alex", "13:15");
            hospital.addPatient(p1);
            for (var patient : hospital) {
                assert Objects.equals(patient, p1);
            }
        }

        // Normal sort
        {
            var hospital = new Hospital2();
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
            var hospital = new Hospital2();
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
            var hospital = new Hospital2();
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
            var hospital = new Hospital2();
            var p1 = new Patient("Alexc", "13:15");
            var p2 = new Patient("Alexz", "13:15");
            var p3 = new Patient("Alexb", "13:15");
            var p4 = new Patient("Alexa", "13:15");
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

        // Time limit test 1 (iterate only first element)
        {
            var hospital = new Hospital2();
            var random = new Random();
            for (int i = 0; i < 1000; i++) {
                String patientName = random.ints(8, 'a', 'z' + 1)
                        .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                        .toString();
                List<String> hours = List.of("09", "10", "11", "13", "14", "15", "16", "17");
                String hour = hours.get(random.nextInt(hours.size()));
                int minutes = random.nextInt(60);
                String patientTime = String.format("%s:%02d", hour, minutes);
                hospital.addPatient(new Patient(patientName, patientTime));
            }
            var t1 = Instant.now();
            for (var patient : hospital) {
                break;
            }
            var t2 = Instant.now();
            assert Duration.between(t1, t2).toNanos() < 50_000;
        }

        // Time limit test 1 (add to beginning)
        {
            var hospital = new Hospital2();
            var random = new Random();
            for (int i = 0; i < 1000; i++) {
                String patientName = random.ints(8, 'a', 'z' + 1)
                        .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                        .toString();
                List<String> hours = List.of("09", "10", "11", "13", "14", "15", "16", "17");
                String hour = hours.get(random.nextInt(hours.size()));
                int minutes = random.nextInt(60);
                String patientTime = String.format("%s:%02d", hour, minutes);
                hospital.addPatient(new Patient(patientName, patientTime));
            }
            var t1 = Instant.now();
            hospital.addPatient(new Patient("a", "08:00"));
            var t2 = Instant.now();
            assert Duration.between(t1, t2).toNanos() < 50_000;
        }
    }
}
