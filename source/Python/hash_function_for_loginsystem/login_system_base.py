
class LoginSystemBase:

    def __init__(self):
        """
        Constructor
        Defines the datastructure to store users
        """

    def __len__(self):
        """
        Returns the actual size of the data structure, including empty elements
        :return: the size of the data structure
        """
        raise NotImplementedError('must be implemented by subclass')

    def get_num_of_users(self):
        """
        Returns the number of users currently in the system
        :return: integer, representing the number of users
        """
        raise NotImplementedError('must be implemented by subclass')

    @staticmethod
    def hash_codes(key: str):
        """
        Calculates the hashcode based on the key
        :param key:
        :return: hashcode integer
        """
        raise NotImplementedError('must be implemented')

    def add_user(self, email, password):
        """
        Adds a new user to the system.
        :return: False if the user is already in the system
                 True if the user was not in the system and has been successfully added
        """
        raise NotImplementedError('must be implemented by subclass')

    def remove_user(self, email, password):
        """
        Removes a user from the system.
        :return: True if the user was successfully removed
                 False if the user was not in the system OR the email is in the system, but the password is incorrect
        """
        raise NotImplementedError('must be implemented by subclass')

    def check_password(self, email, password) -> int:
        """
        Checks if the user is in the system.
        Returns the bucket index of the user in the hashtable.
        :param email, password: strings
        :return: if the user is not in the system
                    -> return -1

                 if the user is in the system, but the password is incorrect
                    -> return -2

                 if the user is in the system and the password is correct
                    -> return the bucket index of the user in the hashtable
        """
        raise NotImplementedError('must be implemented by subclass')

    def change_password(self, email, old_password, new_password) -> bool:
        """
        Changes the user's password in the system to 'new_password'.
        :param email: string
        :param old_password: string
        :param new_password: string
        :return: True if the user's password was changed successfully
                 False if the user could not be found or 'old_password' is incorrect
        """