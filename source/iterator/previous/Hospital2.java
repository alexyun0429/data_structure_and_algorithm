import java.util.Iterator;
import java.util.Objects;

public class Hospital2 extends HospitalBase {
    Object[] patientTimeSlot;
    int capacity;
    int current;
    int patientHour;
    int patientMinute;
    int index;
    int sizeOfArray;

    public Hospital2() {
        /* Add your code here! */
        sizeOfArray = 10;
        patientTimeSlot = new Object[30];
        capacity = 0;
        index = 0;
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        /* Add your code here! */
        if (!(timeChecker(patient))) {
            return false;
        }
        // increase size of the array only capacity == sizeOfArray-3 || ((capacity/sizeOfArray) * 100) > 80
        if (80 < (capacity/sizeOfArray)*100) {
            Object[] temp = patientTimeSlot;
            sizeOfArray *= 2;
            patientTimeSlot = new Object[sizeOfArray];
            for (int i = 0; i < capacity; i++) {
                patientTimeSlot[i] = temp[i];
            }
        }

        if (capacity == 0) {
            patientTimeSlot[0] = patient;
            capacity++;
            return true;
        } else {
            for (int i = 0; i < capacity; i++) {
                PatientBase j = (PatientBase) patientTimeSlot[i];
                if (patient.compareTo(j) == -1) {
                    elementMoveUp(i);
                    patientTimeSlot[i] = patient;
                    capacity++;
                    return true;
                } else if (patient.compareTo(j) == 0) {
                    elementMoveUp(i + 1);
                    patientTimeSlot[i + 1] = patient;
                    capacity++;
                    return true;
                }
            }
            patientTimeSlot[capacity] = patient;
            capacity++;
            return true;
        }
    }

    @Override
    public Iterator<PatientBase> iterator() {
        current = 0;

        Iterator<PatientBase> iterator = new Iterator<>() {

            @Override
            public boolean hasNext() {
                return (patientTimeSlot.length > 1) && (patientTimeSlot[current] != null);
            }

            @Override
            public PatientBase next() {
                PatientBase patientList = (PatientBase) patientTimeSlot[current];
                current += 1;
                return patientList;
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

    // to push the elements in array to fit new element in
    public void elementMoveUp(int index) {
        for (int j = sizeOfArray - 1; j > index; j--) {
            if (j == 0) {
                return;
            }
            patientTimeSlot[j] = patientTimeSlot[j-1];
            patientTimeSlot[j-1] = null;
        }
    }


    public static void main(String[] args) {
        
        var hospital = new Hospital2();
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
