import random
import string
import time

from hospital_base import HospitalBase
from patient_solution import Patient

"""
    Solution using Singly linked list and QuickSort
        - add_patient to the beginning of the list irrespectfully of the time 
                O(1)
        - iterator first prepares the elements using QuickSort, then iterates through the elements 
                O(n log n) + O(n) = O(n log n)
"""


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


def get_middle(head):
    # Utility function to get the middle
    # of the linked list
    if head is None:
        return head
    slow = head
    fast = head
    while fast.next is not None and fast.next.next is not None:
        slow = slow.next
        fast = fast.next.next
    return slow


class Hospital_3(HospitalBase):
    """
        Requirements for Hospital 1:
            - add_patient in O(1)
            - display_timetable - O(n log n)
    """

    def __init__(self):
        super().__init__()
        self.head = None
        self.tail = None

    def __iter__(self):

        # Merge Sort
        # Base case if head is None
        if self.head is None:
            return
        # Base case if only one element
        if self.head.next is None:
            yield self.head.data
            return
        # complete the iterator afterwards
        self.head = self.mergeSort(self.head)
        curr = self.head
        while curr is not None:
            yield curr.data
            curr = curr.next

    def add_patient(self, patient: Patient):
        # Adds participants to the end of the list
        if self.head is None:
            self.head = Node(patient)
            self.tail = self.head
            return True

        newNode = Node(patient)
        self.tail.next = newNode
        self.tail = newNode
        return True

    # ==============================  Add any extra functions below   ==============================

    def sortedMerge(self, a, b):
        # Base cases
        if a is None:
            return b
        if b is None:
            return a
        if a <= b:
            result = a
            result.next = self.sortedMerge(a.next, b)
        else:
            result = b
            result.next = self.sortedMerge(a, b.next)
        return result

    def mergeSort(self, h):

        # Base case if head is None
        if h is None or h.next is None:
            return h

        # get the middle of the list
        middle = get_middle(h)
        nexttomiddle = middle.next

        # set the next of middle node to None
        middle.next = None

        # Apply mergeSort on left list
        left = self.mergeSort(h)

        # Apply mergeSort on right list
        right = self.mergeSort(nexttomiddle)

        # Merge the left and right lists
        sortedlist = self.sortedMerge(left, right)
        return sortedlist


if __name__ == "__main__":

    """  ===================  Public tests   ===================  """
    """
    ll = Hospital_3()
    ll.add_patient(Patient("Max", "11:00"))
    ll.add_patient(Patient("Alex", "12:15"))
    ll.add_patient(Patient("George", "13:00"))
    list_of_patients = [Patient("Max", "11:00"), Patient("Alex", "12:15"), Patient("George", "13:00")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]
    """

    """ ============= Hidden Tests ============= """
    # ================== One element only ==================
    ll = Hospital_3()
    ll.add_patient(Patient("Alex", "13:15"))
    for el in ll:
        assert el == Patient("Alex", "13:15")
    # ================== Normal Sort ==================
    ll = Hospital_3()
    ll.add_patient(Patient("Alex", "13:15"))
    ll.add_patient(Patient("Max", "11:00"))
    ll.add_patient(Patient("George", "14:00"))
    ll.add_patient(Patient("Lisa", "09:00"))
    list_of_patients = [Patient("Lisa", "09:00"), Patient("Max", "11:00"),
                        Patient("Alex", "13:15"), Patient("George", "14:00")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]

    # ================== Same names ==================
    ll = Hospital_3()
    ll.add_patient(Patient("Alex", "13:15"))
    ll.add_patient(Patient("Alex", "11:00"))
    ll.add_patient(Patient("Alex", "14:00"))
    ll.add_patient(Patient("Alex", "09:00"))
    list_of_patients = [Patient("Alex", "09:00"), Patient("Alex", "11:00"),
                        Patient("Alex", "13:15"), Patient("Alex", "14:00")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]

    # ================== Same times, different names ==================
    ll = Hospital_3()
    ll.add_patient(Patient("Daniel", "13:15"))
    ll.add_patient(Patient("Mark", "13:15"))
    ll.add_patient(Patient("Ben", "13:15"))
    ll.add_patient(Patient("Alex", "13:15"))
    list_of_patients = [Patient("Daniel", "13:15"), Patient("Mark", "13:15"),
                        Patient("Ben", "13:15"), Patient("Alex", "13:15")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]

    # ================== Same times, different names 2 ==================
    ll = Hospital_3()
    ll.add_patient(Patient("Alexc", "13:15"))
    ll.add_patient(Patient("Alexz", "13:15"))
    ll.add_patient(Patient("Alexb", "13:10"))
    ll.add_patient(Patient("Alexa", "13:15"))
    list_of_patients = [Patient("Alexb", "13:10"), Patient("Alexc", "13:15"),
                        Patient("Alexz", "13:15"), Patient("Alexa", "13:15")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]

    # ================== Time limit test 1==================

    ll = Hospital_3()
    for i in range(1000):
        patient_name = ''.join(random.choice(string.ascii_lowercase) for _ in range(8))
        hour = random.choice(["08", "09", "10", "11", "13", "14", "15", "16", "17"])
        minutes = random.choice(range(60))
        patient_time = f"{hour}:{minutes:02}"
        ll.add_patient(Patient(patient_name, patient_time))
    t1 = time.time()
    ll.add_patient(Patient('ZZZZZZ', "18:00"))
    t2 = time.time()
    assert t2 - t1 < 0.00005
