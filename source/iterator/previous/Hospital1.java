import java.util.Iterator;
import java.util.Objects;

public class Hospital1 extends HospitalBase {
    Object[] patientTimeSlot;
    int patientHour;
    int patientMinute;
    int current;

    public Hospital1() {
        /* Add your code here! */
        patientTimeSlot = new Object[27];
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        /* Add your code here! */
        if (!timeChecker(patient)) {
            return false;
        }

        // after lunch break
        if (patientHour > 12) {
            patientHour -= 1;
        }
        //
        patientHour = (patientHour - 8) * 3;
        patientMinute = (patientMinute / 20);

        if (patientTimeSlot[patientHour + patientMinute] != null) {
            return false;
        } else {
            patientTimeSlot[patientHour + patientMinute] = patient;
            return true;
        }
    }

    @Override
    public Iterator<PatientBase> iterator() {
        /* Add your code here! */
        PatientBase[] initial = new PatientBase[27];
        int real = 0;
        current = 0;

        for (Object o : patientTimeSlot) {
            if (o != null) {
                initial[real] = (PatientBase) o;
                real += 1;
            }
        }
        return new Iterator<PatientBase>() {
            @Override
            public boolean hasNext() {
                return initial[current] != null;
            }

            @Override
            public PatientBase next() {
                PatientBase iniPatient = initial[current];
                current += 1;
                return iniPatient;
            }
        };
    }

    /* Add any extra functions below */
    private boolean timeChecker(PatientBase patient) {
        patientHour = Integer.parseInt(patient.getTime().split(":")[0]);
        patientMinute = Integer.parseInt(patient.getTime().split(":")[1]);

        if ((patientHour != 12) && (8 <= patientHour) && (patientHour < 18)) {
            return (patientMinute % 20 == 0) && (patientMinute < 60);
        } else return false;
    }

    public static void main(String[] args) {
        /*
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * REMOVE THE MAIN METHOD BEFORE SUBMITTING TO THE AUTOGRADER
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * The following main method is provided for simple debugging only
         */
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
            if (!Objects.equals(patient, patients[i++])) {
                System.err.println("Wrong patient encountered, check your implementation!");
            }
        }
    }
}
