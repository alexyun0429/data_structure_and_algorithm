
from patient_base import PatientBase

# ==== Solution ====
class Patient(PatientBase):
    def __init__(self, name, time):
        super().__init__(name, time)

    def __lt__(self, other):
        if not isinstance(other, PatientBase):
            # don't attempt to compare against unrelated types
            return NotImplemented
        # compare the patients based on their time
        time = time_to_mins(self.time)
        time_other = time_to_mins(other.time)
        return time < time_other

    def __le__(self, other):
        if not isinstance(other, PatientBase):
            return NotImplemented
        time = time_to_mins(self.time)
        time_other = time_to_mins(other.time)
        return time <= time_other


def time_to_mins(time):
    hour, minute = map(int, time.split(":"))
    return hour * 60 + minute