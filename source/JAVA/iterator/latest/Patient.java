public class Patient extends PatientBase {

    public Patient(String name, String time) {
        super(name, time);
    }

    @Override
    public int compareTo(PatientBase o) {
        int time = timeToMins(this.getTime());
        int otherTime = timeToMins(o.getTime());

        return time - otherTime;
    }

    private static int timeToMins(String time) {
        String[] components = time.split(":");
        return Integer.parseInt(components[0]) * 60 + Integer.parseInt(components[1]);
    }
}
