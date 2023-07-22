Overview of Data Structures and Algorithms Used
Hospital 1
Algorithm: Bucket Sort

I have implemented the Bucket Sort algorithm using an array for Hospital 1. The choice of Bucket Sort is based on the fixed time constraints of the hospital operations. With the hospital accepting only 3 patients per hour and operating for 9 hours (excluding lunch time), I use a fixed-size array of 27 (3 patients x 9 hours) to represent time slots.

To efficiently place patients in their respective time slots, I use the following formula to calculate the index:

java
Copy code
int patientHour = (patientHour - 8) * 3;
int patientMinute = patientMinute / 20;
int index = patientHour + patientMinute;
This ensures that patients are sorted and placed accurately in their designated slots.

Time Complexity:

Best case (Ω): Ω(1)
Worst case (O): O(n) [When the array is full, but since the array size is fixed, it effectively becomes O(1)]
Hospital 2
Data Structure: Sorted Dynamic Array (ArrayList)

For Hospital 2, I have implemented a Sorted Dynamic Array using the ArrayList algorithm. The choice of this data structure is suitable when there could be a large number of patient inputs, allowing for efficient insertion and retrieval of patients in sorted order.

Patients are compared based on their time of arrival (hour and minute) using the compareTo method. To maintain the sorting order, when adding a new patient, I traverse the array and find the appropriate position to insert the new patient.

Time Complexity:

Adding a patient: O(n) [Due to the traversal, but amortized time of push is O(1) because of the doubling strategy]
Iterating: O(1) [Patients are already sorted in the dynamic array]
Hospital 3
Data Structure: Doubly Linked List
Algorithm: Merge Sort

Hospital 3 uses a Doubly Linked List with the Merge Sort algorithm. This choice is made to efficiently handle unsorted data input with memory efficiency. The Doubly Linked List allows for easy traversal of patients, and the Merge Sort ensures a stable sorting order, which is essential for maintaining patient priority.

Time Complexity:

Adding a patient: Ω(1) [Due to the nature of linked list insertion]
Iterating: O(nlogn) [Due to Merge Sort]
Login System
Data Structure: Hash Table

The Login System employs a hash table to store hashed passwords instead of plain text passwords. This choice is made to protect user passwords from being easily compromised. The use of polynomial accumulation generates unique indices for emails to prevent collisions.

Time Complexity:

Storing a password: O(1)
Generating a unique index for an email: O(1)
Tree of Symptoms
Data Structure: Binary Search Tree

The Tree of Symptoms has been restructured into a Binary Search Tree. However, it's worth noting that the current implementation is not balanced, which could lead to suboptimal performance in certain cases. Consider implementing a balanced binary search tree, such as an AVL tree or Red-Black tree, to ensure more efficient operations and avoid skewed trees.

Time Complexity:

Searching for a symptom: O(log n) [In a balanced binary search tree, where n is the number of symptoms]
