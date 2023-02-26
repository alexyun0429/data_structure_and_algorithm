import java.util.Objects;
import java.util.Random;

public class LoginSystem extends LoginSystemBase {

    class Tuple<T, U> {
        T item1;
        U item2;

        Tuple(T t, U u) {
            this.item1 = t;
            this.item2 = u;
        }
    }

    static class Passenger {
        static final Passenger defunct = new Passenger(null, 0);

        String email;
        int passwordHash;

        Passenger(String email, int passwordHash) {
            this.email = email;
            this.passwordHash = passwordHash;
        }
    }

    private Passenger[] hashTable;
    private int numUsers;

    public LoginSystem() {
        this.hashTable = new Passenger[101];
        this.numUsers = 0;
    }

    @Override
    public int size() {
        return hashTable.length;
    }

    @Override
    public int getNumUsers() {
        return numUsers;
    }

    @Override
    public int hashCode(String key) {
        int seed = 31;
        long hash = 0;
        for (char c : key.toCharArray()) {
            hash = hash * seed + (int)c;
        }
        return (int) hash;
    }

    @Override
    public boolean addUser(String email, String password) {
        if (numUsers == hashTable.length) {
            resize();
        }

        int index = hash(email);
        Tuple<Boolean, Integer> result = findSlot(index, email);
        boolean alreadyExists = result.item1;
        if (alreadyExists) {
            return false;
        }
        hashTable[result.item2] = new Passenger(email, hashCode(password));
        numUsers++;
        return true;
    }

    @Override
    public boolean removeUser(String email, String password) {
        int index = hash(email);
        Tuple<Boolean, Integer> result = findSlot(index, email);
        boolean wasFound = result.item1;
        int slot = result.item2;
        if (!wasFound) {
            return false;
        }
        if (hashTable[slot].passwordHash == hashCode(password)) {
            hashTable[slot] = Passenger.defunct;
            numUsers--;
            return true;
        }
        return false;
    }

    @Override
    public int checkPassword(String email, String password) {
        int index = hash(email);
        Tuple<Boolean, Integer> result = findSlot(index, email);
        boolean wasFound = result.item1;
        int slot = result.item2;
        if (!wasFound) {
            return -1;
        }
        if (hashTable[slot].passwordHash == hashCode(password)) {
            return slot;
        }
        return -2;
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        int index = hash(email);
        Tuple<Boolean, Integer> result = findSlot(index, email);
        boolean wasFound = result.item1;
        int slot = result.item2;
        if (!wasFound) {
            return false;
        }
        if (hashTable[slot].passwordHash == hashCode(oldPassword)) {
            hashTable[slot].passwordHash = hashCode(newPassword);
            return true;
        }
        return false;
    }

    private void resize() {
        Passenger[] old = hashTable;
        hashTable = new Passenger[old.length * 3];

        for (Passenger passenger : old) {
            if (passenger != null) {
                int index = hash(passenger.email);
                Tuple<Boolean, Integer> slot = findSlot(index, passenger.email);
                hashTable[slot.item2] = passenger;
            }
        }
    }

    private int hash(String key) {
        return Math.floorMod(hashCode(key), hashTable.length);
    }

    private boolean isAvailable(int index) {
        return hashTable[index] == null || hashTable[index] == Passenger.defunct;
    }

    private Tuple<Boolean, Integer> findSlot(int index, String key) {
        int firstAvail = -1;
        while (true) {
            if (isAvailable(index)) {
                if (firstAvail == -1) {
                    firstAvail = index;
                }
                if (hashTable[index] == null) {
                    return new Tuple<>(false, firstAvail);
                }
            } else if (Objects.equals(key, hashTable[index].email)) {
                return new Tuple<>(true, index);
            }
            index = (index + 1) % hashTable.length;
        }
    }

    public static void main(String[] args) {
        /*
         * Public tests
         */
        {
            LoginSystem loginSystem = new LoginSystem();
            assert loginSystem.hashCode("GQHTMP") == loginSystem.hashCode("H2HTN1");
            assert loginSystem.size() == 101;

            assert loginSystem.checkPassword("a@b.c", "L6ZS9") == -1;
            loginSystem.addUser("a@b.c", "L6ZS9");
            assert loginSystem.checkPassword("a@b.c", "ZZZZZZ") == -2;
            assert loginSystem.checkPassword("a@b.c", "L6ZS9") == 94;
            loginSystem.removeUser("a@b.c", "L6ZS9");
            assert loginSystem.checkPassword("a@b.c", "L6ZS9") == -1;
        }

        /*
         * Hidden tests
         */
        // Hashing by the email, not by the password
        {
            LoginSystem loginSystem = new LoginSystem();
            loginSystem.addUser("a@b.c", "11111");
            assert loginSystem.checkPassword("a@b.c", "11111") == 94;
        }

        // Change password test
        {
            LoginSystem loginSystem = new LoginSystem();
            loginSystem.addUser("a@b.c", "L6ZS9");
            int locationBefore = loginSystem.checkPassword("a@b.c", "L6ZS9");
            loginSystem.changePassword("a@b.c", "L6ZS9", "NewPass");
            assert loginSystem.checkPassword("a@b.c", "L6ZS9") == -2;
            int locationAfter = loginSystem.checkPassword("a@b.c", "NewPass");
            assert locationAfter == locationBefore;
        }

        // Insert test 1
        // Check that if the user was inserted twice, it only creates one record
        {
            LoginSystem loginSystem = new LoginSystem();
            boolean isAdded = loginSystem.addUser("a@b.c", "11111");
            assert isAdded;
            isAdded = loginSystem.addUser("a@b.c", "11111");
            assert !isAdded;
            assert loginSystem.getNumUsers() == 1;
        }

        // Insert test 2
        // Check that if the user was inserted twice, only the original record is kept
        {
            LoginSystem loginSystem = new LoginSystem();
            loginSystem.addUser("a@b.c", "11111");
            loginSystem.addUser("a@b.c", "22222");
            assert loginSystem.checkPassword("a@b.c", "22222") == -2;
        }

        // Collision test 1
        {
            LoginSystem loginSystem = new LoginSystem();
            loginSystem.addUser("a@b.c", "a@b.c");
            loginSystem.addUser("ji5p9", "ji5p9");
            loginSystem.addUser("4pmhv", "4pmhv");
            assert loginSystem.checkPassword("ji5p9", "ji5p9")
                    - loginSystem.checkPassword("a@b.c", "a@b.c") == 1;
            assert loginSystem.checkPassword("4pmhv", "4pmhv")
                    - loginSystem.checkPassword("a@b.c", "a@b.c") == 2;
        }

        // Collision test 2
        {
            LoginSystem loginSystem = new LoginSystem();
            loginSystem.addUser("a@b.c", "a@b.c");
            loginSystem.addUser("ji5p9", "ji5p9");
            loginSystem.addUser("4pmhv", "4pmhv");
            int idThirdBefore = loginSystem.checkPassword("4pmhv", "4pmhv");
            loginSystem.removeUser("ji5p9", "ji5p9");
            assert loginSystem.checkPassword("ji5p9", "ji5p9") == -1;
            int idThirdAfter = loginSystem.checkPassword("4pmhv", "4pmhv");
            assert idThirdBefore == idThirdAfter;
        }

        // Resize test
        {
            LoginSystem loginSystem = new LoginSystem();
            int beforeResizeLen = loginSystem.size();
            loginSystem.addUser("alex@uqnet.edu.au", "11111");
            int userBefore = loginSystem.checkPassword("alex@uqnet.edu.au", "11111");
            Random random = new Random();
            for (int i = 0; i < 102; i++) {
                String email = random.ints(6, 'a', 'z' + 1)
                        .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                        .toString() + "@uq.edu.au";
                loginSystem.addUser(email, Integer.toString(i));
            }
            int afterResizeLen = loginSystem.size();
            int userAfter = loginSystem.checkPassword("alex@uqnet.edu.au", "11111");
            assert afterResizeLen > beforeResizeLen;
            assert loginSystem.size() == 303;
            assert userBefore != userAfter;
        }

        // Test that the password is hashed
        // Delete the user with the different password, that has the same hash code
        // That's how we would know that the hashcode is stored, and not the password itself
        {
            LoginSystem loginSystem = new LoginSystem();
            loginSystem.addUser("alex@uqnet.edu.au", "GQHTMP");
            loginSystem.removeUser("alex@uqnet.edu.au", "H2HTN1");
            assert loginSystem.checkPassword("alex@uqnet.edu.au", "GQHTMP") == -1;
        }
    }
}
