
"""
            !!!!!  DO NOT MODIFY THIS FILE  !!!!!
"""


class HospitalBase:

    def __init__(self):
        """
            Constructor
            Defines the datastructure to store patients
        """

    def __iter__(self):
        """
            An iterator through the elements of the datastructure.
            Iterates through the patients in the correct order according to their time
        """
        raise NotImplementedError('must be implemented by subclass')

    def add_patient(self, patient):
        """
            Adds a new patient to the datastructure
        """
        raise NotImplementedError('must be implemented by subclass')
