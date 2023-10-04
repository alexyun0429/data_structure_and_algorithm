
import random
import string
import time

from hospital_base import HospitalBase
from patient_solution import Patient


class Node:
    def __init__(self, patient: Patient):
        self.data = patient
        self.next = None

    def __lt__(self, other):
        return self.data < other.data

    def __le__(self, other):
        return self.data <= other.data

    def __repr__(self):
        return f"Node(data={self.data.__repr__()})"


class Hospital_2 (HospitalBase):
    """
        Requirements for Hospital 2:
            - add_patient in O(n)
            - __iter__ - as quickly as possible
    """

    def __init__(self):
        super().__init__()
        self.head = None

    def __iter__(self):
        """
        Just iterate through the elements - they are already sorted
        """
        curr = self.head
        while (curr != None):
            yield curr.data
            curr = curr.next

    def add_patient(self, patient: Patient):
        """
        Sort the elements here
        """
        curr = self.head
        newNode = Node(patient)
        if curr is None:
            # no samples in the queue
            self.head = newNode
            return True

        if newNode < curr:
            newNode.next = curr
            self.head = newNode
            return True

        #print("Values ", newNode.data, curr.data)
        #if newNode == curr:
        #    print("HERE", newNode, curr)
        prev = None
        while newNode >= curr:

            if curr.next is None:
                # we got to the end
                curr.next = newNode
                return True
            else:
                prev = curr
                curr = curr.next

        if prev is not None:
            prev.next = newNode
        newNode.next = curr
        return True

    # ==============================  Add any extra functions below   ==============================


if __name__ == "__main__":

    """  ===================  Public tests   ===================  """
    ll = Hospital_2()
    ll.add_patient(Patient("Max", "11:00"))
    ll.add_patient(Patient("Alex", "13:15"))
    ll.add_patient(Patient("George", "14:00"))
    list_of_patients = [Patient("Max", "11:00"), Patient("Alex", "13:15"), Patient("George", "14:00")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]
    assert i == 2
    """ ============= Hidden Tests ============= """
    # ================== One element only ==================
    ll = Hospital_2()
    ll.add_patient(Patient("Alex", "13:15"))
    for el in ll:
        assert el == Patient("Alex", "13:15")

    # ================== Normal Sort ==================
    ll = Hospital_2()
    ll.add_patient(Patient("Alex", "13:15"))
    ll.add_patient(Patient("Max", "11:00"))
    ll.add_patient(Patient("George", "14:00"))
    ll.add_patient(Patient("Lisa", "09:00"))
    list_of_patients = [Patient("Lisa", "09:00"), Patient("Max", "11:00"),
                        Patient("Alex", "13:15"), Patient("George", "14:00")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]

    # ================== Same names ==================
    ll = Hospital_2()
    ll.add_patient(Patient("Alex", "13:15"))
    ll.add_patient(Patient("Alex", "11:00"))
    ll.add_patient(Patient("Alex", "14:00"))
    ll.add_patient(Patient("Alex", "09:00"))
    list_of_patients = [Patient("Alex", "09:00"), Patient("Alex", "11:00"),
                        Patient("Alex", "13:15"), Patient("Alex", "14:00")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]

    # ================== Same times, different names ==================
    ll = Hospital_2()
    ll.add_patient(Patient("Daniel", "13:15"))
    ll.add_patient(Patient("Mark", "13:15"))
    ll.add_patient(Patient("Ben", "13:15"))
    ll.add_patient(Patient("Alex", "13:15"))
    list_of_patients = [Patient("Daniel", "13:15"), Patient("Mark", "13:15"),
                        Patient("Ben", "13:15"), Patient("Alex", "13:15")]
    for i, el in enumerate(ll):
        #print(el,  list_of_patients[i])
        assert el == list_of_patients[i]

    # ================== Same times, different names 2 ==================
    ll = Hospital_2()
    ll.add_patient(Patient("Alexc", "13:15"))
    ll.add_patient(Patient("Alexz", "13:15"))
    ll.add_patient(Patient("Alexb", "13:15"))
    ll.add_patient(Patient("Alexa", "13:15"))
    list_of_patients = [Patient("Alexc", "13:15"), Patient("Alexz", "13:15"),
                        Patient("Alexb", "13:15"), Patient("Alexa", "13:15")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]

    # ================== Time limit test 1==================

    ll = Hospital_2()
    for i in range(1000):
        patient_name = ''.join(random.choice(string.ascii_lowercase) for _ in range(8))
        hour = random.choice(["09", "10", "11", "13", "14", "15", "16", "17"])
        minutes = random.choice(range(60))
        patient_time = f"{hour}:{minutes:02}"
        ll.add_patient(Patient(patient_name, patient_time))
    t1 = time.time()
    ll.add_patient(Patient('ZZZZZZ', "18:00"))
    t2 = time.time()
    # print(t2-t1)
    t1 = time.time()
    for i in ll:
        # only the first element - should be quick
        # no sorting is done in here
        break
    t2 = time.time()
    assert t2-t1 < 0.00005

    # ================== Time limit test 2==================
    # Insertion sort - if the value is added to the beginning- it is also quick
    ll = Hospital_2()
    for i in range(1000):
        patient_name = ''.join(random.choice(string.ascii_lowercase) for _ in range(8))
        hour = random.choice(["09", "10", "11", "13", "14", "15", "16", "17"])
        minutes = random.choice(range(60))
        patient_time = f"{hour}:{minutes:02}"
        ll.add_patient(Patient(patient_name, patient_time))
    t1 = time.time()
    ll.add_patient(Patient('a', "08:00"))
    t2 = time.time()
    assert t2-t1 < 0.00005