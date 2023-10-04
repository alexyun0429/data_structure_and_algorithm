
import random
import string
import time

from hospital_base import HospitalBase
from patient_solution import Patient, time_to_mins


class Hospital_1(HospitalBase):

    def __init__(self):
        super().__init__()

        self.START_MINS = time_to_mins("08:00")
        self.END_MINS = time_to_mins("18:00")
        self.LUNCH_START_MINS = time_to_mins("12:00")
        self.LUNCH_END_MINS = time_to_mins("13:00")
        self.MINUTES_PER_SLOT = 20
        self.NUM_TIMESLOTS = (self.LUNCH_START_MINS - self.START_MINS + self.END_MINS - self.LUNCH_END_MINS)\
                             / self.MINUTES_PER_SLOT
        self.patients = [None] * int(self.NUM_TIMESLOTS)

    def __iter__(self):
        for el in self.patients:
            if el is not None:
                yield el

    def add_patient(self, patient: Patient):
        time_in_mins = time_to_mins(patient.time)
        if time_in_mins < self.START_MINS or time_in_mins >= self.END_MINS \
            or time_in_mins >= self.LUNCH_START_MINS and time_in_mins < self.LUNCH_END_MINS \
            or time_in_mins % self.MINUTES_PER_SLOT != 0:
            return False
        if time_in_mins < self.LUNCH_START_MINS:
            slot = int((time_in_mins - self.START_MINS) / self.MINUTES_PER_SLOT)
        else:
            numSlotsBeforeLunch = (self.LUNCH_START_MINS - self.START_MINS) / self.MINUTES_PER_SLOT
            slot = int((time_in_mins - self.LUNCH_END_MINS) / self.MINUTES_PER_SLOT + numSlotsBeforeLunch)

        if self.patients[slot] is not None:
            return False
        self.patients[slot] = patient
        return True


if __name__ == "__main__":

    """  ===================  Public tests   ===================  """
    ll = Hospital_1()
    ll.add_patient(Patient("Max", "11:00"))
    ll.add_patient(Patient("Alex", "13:00"))
    ll.add_patient(Patient("George", "14:00"))
    list_of_patients = [Patient("Max", "11:00"), Patient("Alex", "13:00"), Patient("George", "14:00")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]

    """ ============= Hidden Tests ============= """
    # ================== One element only ==================
    ll = Hospital_1()
    ll.add_patient(Patient("Alex", "13:20"))
    for el in ll:
        assert el == Patient("Alex", "13:20")

    # ================== Incorrect time is not added ==================
    ll = Hospital_1()
    if_added = ll.add_patient(Patient("Alex", "13:25"))
    assert not if_added

    # ================== Do not add if the slot is occupied ==================
    ll = Hospital_1()
    ll.add_patient(Patient("Alex", "13:20"))
    if_added = ll.add_patient(Patient("Max", "13:20"))
    assert not if_added


    # ================== Normal Sort ==================
    ll = Hospital_1()
    ll.add_patient(Patient("Alex", "13:20"))
    ll.add_patient(Patient("Max", "11:00"))
    ll.add_patient(Patient("George", "14:00"))
    ll.add_patient(Patient("Lisa", "09:00"))
    list_of_patients = [Patient("Lisa", "09:00"), Patient("Max", "11:00"),
                        Patient("Alex", "13:20"), Patient("George", "14:00")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]

    # ================== Same names ==================
    ll = Hospital_1()
    ll.add_patient(Patient("Alex", "13:20"))
    ll.add_patient(Patient("Alex", "11:00"))
    ll.add_patient(Patient("Alex", "14:00"))
    ll.add_patient(Patient("Alex", "09:00"))
    list_of_patients = [Patient("Alex", "09:00"), Patient("Alex", "11:00"),
                        Patient("Alex", "13:20"), Patient("Alex", "14:00")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]

    # ================== Same times, different names ==================
    ll = Hospital_1()
    ll.add_patient(Patient("Daniel", "13:20"))
    ll.add_patient(Patient("Mark", "13:20"))
    ll.add_patient(Patient("Ben", "13:20"))
    ll.add_patient(Patient("Alex", "13:20"))
    list_of_patients = [Patient("Alex", "13:20"), Patient("Ben", "13:20"),
                        Patient("Daniel", "13:20"), Patient("Mark", "13:20")]
    for el in ll:
        assert el == Patient("Daniel", "13:20")

    # ================== Time limit test 1==================
    # Add in the beginning
    ll = Hospital_1()
    for hour in ["09", "10", "11", "13", "14", "15", "16"]:
        for minutes in ["00", "20", "40"]:
            patient_name = ''.join(random.choice(string.ascii_lowercase) for _ in range(8))
            patient_time = hour+":"+minutes
            ll.add_patient(Patient(patient_name, patient_time))
    t1 = time.time()
    ll.add_patient(Patient('aaa', "08:00"))
    t2 = time.time()
    assert t2-t1 < 0.00005

    # ================== Time limit test 2==================
    # Add in the end
    ll = Hospital_1()
    for hour in ["09", "10", "11", "13", "14", "15", "16"]:
        for minutes in ["00", "20", "40"]:
            patient_name = ''.join(random.choice(string.ascii_lowercase) for _ in range(8))
            patient_time = hour+":"+minutes
            ll.add_patient(Patient(patient_name, patient_time))
    t1 = time.time()
    ll.add_patient(Patient('zzz', "17:40"))
    t2 = time.time()
    assert t2-t1 < 0.00005

    # ================== Time limit test 3==================
    # Quick iterator
    ll = Hospital_1()
    for hour in ["09", "10", "11", "13", "14", "15", "16"]:
        for minutes in ["00", "20", "40"]:
            patient_name = ''.join(random.choice(string.ascii_lowercase) for _ in range(8))
            patient_time = hour+":"+minutes
            ll.add_patient(Patient(patient_name, patient_time))
    t1 = time.time()
    for el in ll:
        pass
    t2 = time.time()
    assert t2-t1 < 0.00005
