
from hospital_base import HospitalBase
from patient import Patient


class Hospital_2 (HospitalBase):

    def __init__(self):
        super().__init__()

    def __iter__(self):
        """
            Add your code here!
        """

    def add_patient(self, patient: Patient):
        """
            Add your code here!
        """
        return True

    # ==============================  Add any extra functions below   ==============================


if __name__ == "__main__":
    """
            !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER 
            !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            The following main function is provided for simple debugging only
        """
    ll = Hospital_2()
    ll.add_patient(Patient("Max", "11:00"))
    ll.add_patient(Patient("Alex", "13:15"))
    ll.add_patient(Patient("George", "14:00"))
    list_of_patients = [Patient("Max", "11:00"), Patient("Alex", "13:15"), Patient("George", "14:00")]
    for i, el in enumerate(ll):
        assert el == list_of_patients[i]
