
from hospital_base import HospitalBase
from patient import Patient


class Hospital_1 (HospitalBase):

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
    hospital = Hospital_1()
    hospital.add_patient(Patient("Max", "11:00"))
    hospital.add_patient(Patient("Alex", "13:20"))
    hospital.add_patient(Patient("George", "14:00"))
    list_of_patients = [Patient("Max", "11:00"), Patient("Alex", "13:20"), Patient("George", "14:00")]
    for i, el in enumerate(hospital):
        assert el == list_of_patients[i]
