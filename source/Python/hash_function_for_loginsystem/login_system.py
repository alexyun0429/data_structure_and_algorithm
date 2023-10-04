
from login_system_base import LoginSystemBase
import random
import string


class Passenger:
    def __init__(self, email, password):
        self.email = email
        self.password = password


class LoginSystem (LoginSystemBase):

    # A special flag, denoting that the element has been removed
    # Needed for collision handling
    _DEFUNCT = object()

    def __init__(self):
        """
            Constructor
            Defines the datastructure to store patients
        """
        super().__init__()
        # Initiate the hashtable as an array with empty elements.
        self.hashtable = [None for _ in range(101)]
        self.num_of_users = 0

    def __len__(self):
        return len(self.hashtable)

    def add_user(self, email, password):
        if self.num_of_users == len(self.hashtable):
            self._resize()

        index = self.hash(email)
        found, s = self._find_slot(index, email)
        if not found:
            self.hashtable[s] = Passenger(email, self.hash_codes(password))  # insert new item
            self.num_of_users += 1
        else:
            return False
        return True

    def remove_user(self, email, password):
        index = self.hash(email)
        found, s = self._find_slot(index, email)
        if not found:
            return False
        else:
            if self.hashtable[s].password == self.hash_codes(password):
                self.hashtable[s] = LoginSystem._DEFUNCT
                self.num_of_users -= 1
                return True
            else:
                return False

    def change_password(self, email, old_password, new_password):
        index = self.hash(email)
        found, s = self._find_slot(index, email)
        if not found:
            return False
        if self.hashtable[s].password == self.hash_codes(old_password):
            self.hashtable[s].password = self.hash_codes(new_password)
            return True
        else:
            return False

    def check_password(self, email, password):
        j = self.hash(email)
        found, s = self._find_slot(j, email)
        if not found:
            return -1
        if self.hashtable[s].password == self.hash_codes(password):
            return s
        else:
            return -2

    def get_num_of_users(self):
        """
        :return: the number of users currently in the system
        """
        return self.num_of_users

    @staticmethod
    def hash_codes(key: str):
        # BKDR hash function
        seed, hash = 31, 0
        for i in key:
            hash = hash*seed + ord(i)
        return hash

    def hash(self, key):
        """
        Hash code + Compression function
        Bring the hash code to be within the range of the size of the hashtable
        :param key:
        :return: The index of the hashtable, associated with the user
        """
        length = len(self.hashtable)
        return self.hash_codes(key) % length

    def _is_available(self, i):
        """
        :param i: index i
        :return: True if the bucket i of the hashtable is empty
        """
        return self.hashtable[i] is None or self.hashtable[i] is LoginSystem._DEFUNCT

    def _find_slot(self, indx, key) -> [bool, int]:
        """
        Search for key in bucket at index indx.
        :param indx: index
        :param key:
        :return: (success, index) tuple, described as follows:
                If match was found, success is True and index denotes its location.
                If no match found, success is False and index denotes first available slot.
        """
        first_avail = None
        while True:
            if self._is_available(indx):
                if first_avail is None:
                    # mark this as first avail
                    first_avail = indx
                if self.hashtable[indx] is None:
                    # search has failed
                    return False, first_avail
            elif key == self.hashtable[indx].email:
                # found a match
                return True, indx
            # keep looking (cyclically)
            indx = (indx + 1) % len(self.hashtable)

    def _resize(self):
        """
        Resize bucket array to capacity max_capacity and
        rehash all the items.
        """
        # """
        old = self.hashtable  # use iteration to record existing items
        self.hashtable = [None for _ in range(len(self.hashtable)*3)]  # then reset table to desired capacity
        for passenger in old:
            if passenger is not None:
                index = self.hash(passenger.email)
                _, s = self._find_slot(index, passenger.email)
                self.hashtable[s] = passenger
        return True


def function_for_test():
    """
    Function that mines the keys for collisions
    """

    s1, s2 = {}, {}
    # my_str = "a@b.c"
    email = "a@b.c"
    hash_of_string = login.hash(email)
    while True:
        my_str = ''.join(random.choice(string.ascii_lowercase + string.digits) for _ in range(5))
        if login.hash(my_str) == hash_of_string:
            print(my_str, hash_of_string, login.hash(my_str) )
            break
            # raise ()
    while True:
        # my_str = "a@b.c"
        my_str = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(5))
        if login.hash_codes(my_str) in s1.keys():
            if login.hash_codes(my_str) in s2.keys():
                print("Collision", my_str, s1[login.hash_codes(my_str)], s2[login.hash_codes(my_str)])
                return my_str, s1[login.hash_codes(my_str)], s2[login.hash_codes(my_str)]
            else:
                s2[login.hash_codes(my_str)] = my_str
        else:
            s1[login.hash_codes(my_str)] = my_str


if __name__ == "__main__":
    login = LoginSystem()
    # function_for_test()

    """  ===================  Public tests   ===================  """

    assert login.hash_codes("GQHTMP") == login.hash_codes("H2HTN1")
    assert len(login) == 101

    # note for Max: L6ZS9 and a@b.c have the same hash value (94) before resizing,
    # it's important to use this particular example in the public tests

    assert login.check_password("a@b.c", "L6ZS9") == -1
    login.add_user("a@b.c", "L6ZS9")
    assert login.check_password("a@b.c", "ZZZZZZ") == -2
    assert login.check_password("a@b.c", "L6ZS9") == 94
    login.remove_user("a@b.c", "L6ZS9")
    assert login.check_password("a@b.c", "L6ZS9") == -1


    """ ============= Hidden Tests ============= """

    # ================== Hashing by the email, not by the password ==================
    ht = LoginSystem()
    ht.add_user("a@b.c", "11111")
    assert ht.check_password("a@b.c", "11111") == 94

    # ================== Change password test ==================
    ht = LoginSystem()
    ht.add_user("a@b.c", "L6ZS9")
    location_before = ht.check_password("a@b.c", "L6ZS9")
    ht.change_password("a@b.c", "L6ZS9", "NewPass")
    assert ht.check_password("a@b.c", "L6ZS9") == -2
    location_after = ht.check_password("a@b.c", "NewPass")
    assert location_after == location_before

    # ================== Insert test 1 ==================
    # Check that if the user was inserted twice, it only creates one record
    ht = LoginSystem()
    if_added = ht.add_user("a@b.c", "11111")
    assert if_added == True
    if_added = ht.add_user("a@b.c", "11111")
    assert if_added == False
    assert ht.get_num_of_users() == 1

    # ================== Insert test 2 ==================
    # Check that if the user was inserted twice, only the original record is kept
    ht = LoginSystem()
    ht.add_user("a@b.c", "11111")
    ht.add_user("a@b.c", "22222")
    assert ht.check_password("a@b.c", "22222") == -2

    # ================== Collision test 1 ==================
    ht = LoginSystem()
    ht.add_user("a@b.c", "a@b.c")
    ht.add_user("ji5p9", "ji5p9")
    ht.add_user("4pmhv", "4pmhv")
    # Assess Linear Probing
    assert ht.check_password("ji5p9", "ji5p9") - \
           ht.check_password("a@b.c", "a@b.c") == 1
    assert ht.check_password("4pmhv", "4pmhv") - \
           ht.check_password("a@b.c", "a@b.c") == 2

    # ================== Collision test 2 ==================
    # Separate test: Now check if the _DEFUNCT was being used
    # Remove the one in the middle, see if it can still find the third
    ht = LoginSystem()
    ht.add_user("a@b.c", "a@b.c")
    ht.add_user("ji5p9", "ji5p9")
    ht.add_user("4pmhv", "4pmhv")
    id_third_before = ht.check_password("4pmhv", "4pmhv")
    ht.remove_user("ji5p9", "ji5p9")
    assert ht.check_password("ji5p9", "ji5p9") == -1
    id_third_after = ht.check_password("4pmhv", "4pmhv")
    assert id_third_before == id_third_after

    # ================== Resize test ==================
    ht = LoginSystem()
    before_resize_len = len(ht)
    ht.add_user("alex@uqnet.edu.au", "11111")
    user_before = ht.check_password("alex@uqnet.edu.au", "11111")
    for i in range(102):
        rest = "@uq.edu.au"
        my_str = ''.join(random.choice(string.ascii_lowercase + string.digits) for _ in range(6))
        my_str += rest
        ht.add_user(my_str, str(i))
    after_resize_len = len(ht)
    peter_after = ht.check_password("alex@uqnet.edu.au", "11111")
    # Assert that the size changed
    assert after_resize_len > before_resize_len
    # Assert that the length is tripled
    assert len(ht) == 303
    # Assert that the users have been rehashed
    assert user_before != peter_after

    # ================== Test that the password is hashed ==================
    # Delete the user with the different password, that has the same hash code
    # That's how we would know that the hashcode is stored, and not the password itself
    ht = LoginSystem()
    ht.add_user("alex@uqnet.edu.au", "GQHTMP")
    ht.remove_user("alex@uqnet.edu.au", "H2HTN1")
    assert ht.check_password("alex@uqnet.edu.au", "GQHTMP") == -1
