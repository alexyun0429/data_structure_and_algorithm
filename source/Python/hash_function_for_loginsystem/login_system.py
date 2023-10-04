
from login_system_base import LoginSystemBase


class LoginSystem (LoginSystemBase):

    def __init__(self):
        super().__init__()

    """
        Implement the described functions here !
    """


if __name__ == "__main__":
    """
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER 
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        The following main function is provided for simple debugging only
    """

    login = LoginSystem()

    assert login.hash_codes("GQHTMP") == login.hash_codes("H2HTN1")
    assert len(login) == 101
    assert login.check_password("a@b.c", "L6ZS9") == -1
    login.add_user("a@b.c", "L6ZS9")
    assert login.check_password("a@b.c", "ZZZZZZ") == -2
    assert login.check_password("a@b.c", "L6ZS9") == 94
    login.remove_user("a@b.c", "L6ZS9")
    assert login.check_password("a@b.c", "L6ZS9") == -1
