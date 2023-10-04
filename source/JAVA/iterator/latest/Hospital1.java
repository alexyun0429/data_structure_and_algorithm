import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class Hospital1 extends HospitalBase {

    private static final int START_MINS = timeToMins("08:00");
    private static final int END_MINS = timeToMins("18:00");
    private static final int LUNCH_START_MINS = timeToMins("12:00");
    private static final int LUNCH_END_MINS = timeToMins("13:00");
    private static final int MINUTES_PER_SLOT = 20;
    private static final int NUM_TIMESLOTS = (LUNCH_START_MINS - START_MINS + END_MINS - LUNCH_END_MINS) / MINUTES_PER_SLOT;

    private final PatientBase[] patients;

    public Hospital1() {
        patients = new PatientBase[NUM_TIMESLOTS];
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        String time = patient.getTime();
        int timeInMins = timeToMins(time);

        if (timeInMins < START_MINS
                || timeInMins >= END_MINS
                || timeInMins >= LUNCH_START_MINS && timeInMins < LUNCH_END_MINS
                || timeInMins % MINUTES_PER_SLOT != 0) {
            return false;
        }

        int slot;
        if (timeInMins < LUNCH_START_MINS) {
            slot = (timeInMins - START_MINS) / MINUTES_PER_SLOT;
        } else {
            int numSlotsBeforeLunch = (LUNCH_START_MINS - START_MINS) / MINUTES_PER_SLOT;
            slot = (timeInMins - LUNCH_END_MINS) / MINUTES_PER_SLOT + numSlotsBeforeLunch;
        }
        if (patients[slot] != null) {
            return false;
        }

        patients[slot] = patient;
        return true;
    }

    @Override
    public Iterator<PatientBase> iterator() {
        return Stream.of(patients).filter(Objects::nonNull).iterator();
    }

    private static int timeToMins(String time) {
        String[] components = time.split(":");
        return Integer.parseInt(components[0]) * 60 + Integer.parseInt(components[1]);
    }

    public static void main(String[] args) {
        /*
         * Public tests
         */
        {
            var hospital = new Hospital1();
            var p1 = new Patient("Max", "11:00");
            var p2 = new Patient("Alex", "13:00");
            var p3 = new Patient("George", "14:00");
            hospital.addPatient(p1);
            hospital.addPatient(p2);
            hospital.addPatient(p3);
            var patients = new Patient[] { p1, p2, p3 };
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
            var hospital = new Hospital1();
            var p1 = new Patient("Alex", "13:20");
            hospital.addPatient(p1);
            for (var patient : hospital) {
                assert Objects.equals(patient, p1);
            }
        }

        // Incorrect time is not added
        {
            var hospital = new Hospital1();
            assert !hospital.addPatient(new Patient("Alex", "13:25"));
        }

        // Do not add if the slot is occupied
        {
            var hospital = new Hospital1();
            hospital.addPatient(new Patient("Alex", "13:20"));
            assert !hospital.addPatient(new Patient("Max", "13:20"));
        }

        // Normal sort
        {
            var hospital = new Hospital1();
            var p1 = new Patient("Alex", "13:20");
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
            var hospital = new Hospital1();
            var p1 = new Patient("Alex", "13:20");
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
            var hospital = new Hospital1();
            var p1 = new Patient("Daniel", "13:20");
            var p2 = new Patient("Mark", "13:20");
            var p3 = new Patient("Ben", "13:20");
            var p4 = new Patient("Alex", "13:20");
            hospital.addPatient(p1);
            hospital.addPatient(p2);
            hospital.addPatient(p3);
            hospital.addPatient(p4);
            for (var patient : hospital) {
                assert Objects.equals(patient, p1);
            }
        }

        // Time limit test 1 (add in the beginning)
        {
            var hospital = new Hospital1();
            var random = new Random();
            for (String hour : List.of("09", "10", "11", "13", "14", "15", "16")) {
                for (String minutes : List.of("00", "20", "40")) {
                    String patientName = random.ints(8, 'a', 'z' + 1)
                            .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                            .toString();
                    String patientTime = String.format("%s:%s", hour, minutes);
                    hospital.addPatient(new Patient(patientName, patientTime));
                }
            }
            var t1 = Instant.now();
            hospital.addPatient(new Patient("aaa", "08:00"));
            var t2 = Instant.now();
            assert Duration.between(t1, t2).toNanos() < 50_000;
        }

        // Time limit test 1 (add in the end)
        {
            var hospital = new Hospital1();
            var random = new Random();
            for (String hour : List.of("09", "10", "11", "13", "14", "15", "16")) {
                for (String minutes : List.of("00", "20", "40")) {
                    String patientName = random.ints(8, 'a', 'z' + 1)
                            .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                            .toString();
                    String patientTime = String.format("%s:%s", hour, minutes);
                    hospital.addPatient(new Patient(patientName, patientTime));
                }
            }
            var t1 = Instant.now();
            hospital.addPatient(new Patient("zzz", "17:40"));
            var t2 = Instant.now();
            assert Duration.between(t1, t2).toNanos() < 50_000;
        }

        // Time limit test 1 (quick iterator)
        {
            var hospital = new Hospital1();
            var random = new Random();
            for (String hour : List.of("09", "10", "11", "13", "14", "15", "16")) {
                for (String minutes : List.of("00", "20", "40")) {
                    String patientName = random.ints(8, 'a', 'z' + 1)
                            .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                            .toString();
                    String patientTime = String.format("%s:%s", hour, minutes);
                    hospital.addPatient(new Patient(patientName, patientTime));
                }
            }
            var t1 = Instant.now();
            for (var patient : hospital) {
            }
            var t2 = Instant.now();
            assert Duration.between(t1, t2).toNanos() < 50_000;
        }
    }
}
