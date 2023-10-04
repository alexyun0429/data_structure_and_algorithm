import java.util.regex.Pattern;

public class LoginSystem extends LoginSystemBase {

    private User[] userTable;
    private int size = 101;
    private int numUsers;
    private int c = 31;
    private int index;
    private User flag;

    public LoginSystem() {
        userTable = new User[size];
        numUsers = 0;

    }

    @Override
    public int size() {
        if (size == numUsers) {
            this.size = size * 3;
        }
        return this.size;
    }

    @Override
    public int getNumUsers() {
        /* Add your code here! */
        return numUsers;
    }

    @Override
    public int hashCode(String key) {
        /* Add your code here! */
        if (key.length() == 1) {
            return key.charAt(0);
        } else {
            int hashing1 = hashCode(key.substring(0, key.length() - 1)) * c;
            int hashing2 = key.charAt(key.length() - 1);
            return (hashing1 + hashing2);
        }
    }

    @Override
    public boolean addUser(String email, String password) {
        /* Add your code here! */
        if (size - 5 < numUsers) {
            size = size * 3;
        }
        return false;
    }

    @Override
    public boolean removeUser(String email, String password) {
        /* Add your code here! */
        int i = 0;
        int index = (hashCode(email) + i) % size;
        while (userTable[index] != null && i < size) {
            if (userTable[index].equals(email)) {
                userTable[index] = flag;
                return true;
            }
            i++;
            index = (hashCode(email) + i) % size;
        }
        return false;
    }

    // if email is exist and equal
    @Override
    public int checkPassword(String email, String password) {
        /* Add your code here! */
        return 0;
    }

    // if email is exist and equal then change
    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        /* Add your code here! */
        return false;
    }

    /* Add any extra functions below */

    private int compression(int hashcode) {
        return hashcode % size;
    }

    private boolean checkEmail(String email) {
        String emailRange = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRange);

        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    class User {
        String email;
        int password;
        int hashcode;
        Boolean flag;

        public User(String email, String password) {
            this.email = email;
            this.password = LoginSystem.this.hashCode(password);
            this.flag = false;
        }
    }


// need to implement flag but couldnt
    public int userChecked(String email) {
        index = compression(hashCode(email));

        while (userTable[index] != null) {
            if (userTable[index].email.equals(email)) {
                return index;
            }
            index++;
            if (index == userTable.length - 1) {
                index = 0;
            }
        }
        return index;
    }
}