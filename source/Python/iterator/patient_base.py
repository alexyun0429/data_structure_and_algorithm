
"""
            !!!!!  DO NOT MODIFY THIS FILE  !!!!!
"""


class PatientBase:

    def __init__(self, name, time):
        self.name = name
        self.time = time

    def __repr__(self):
        return '({0}, {1})'.format(self.name, self.time)

    def __eq__(self, other):
        return self.name == other.name and self.time == other.time

    def __lt__(self, other):
        if not isinstance(other, PatientBase):
            # Don't attempt to compare against unrelated types
            return NotImplemented
        raise NotImplementedError('must be implemented by subclass')
