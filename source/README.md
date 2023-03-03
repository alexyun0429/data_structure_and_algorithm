# Overview description of data structure and algorithm used

# Hospital 1

I have chosen array with bucket sorting. It is because bucket was fixed to as we know the time is fixed. Through making mistake of going through linked lists (single and doubly), found out sort it and inserting into bucket was good.
Anyway since, data (patient) would have to be sorted, therefore, I chose bucket because when we know the exact amount it is good in time and space efficiency.

I have use helper methods such compareTo and timeChecker to break the time to be equals to an array index which allows exact place to know as soon as data is added.

patientHour = (patientHour - 8) * 3;
patientMinute = (patientMinute / 20);

These lines are the equation to convert to index like.

For instance, 10:20 will be broke down to hour and minute.
So for hour, (10-8)*3 = 6
For minute, 20/20 = 1

patientTimeSlot[patientHour + patientMinute] = patient; 

And add together to be index. 6+1=7.
So 10:20 will be at array index of 7.

This equation was implemented in addPatient() method.

Then when I iterate, I have created array[27] to copy paste exactly what addPatient gives to be returned.

Other algorithms would gives no beneficial of knowing the exact length or size of data input in comes of space efficiency, however, at my case of customised bucket sort have high efficiency of time and space complexity.

Best case (Ω) would be Ω(1)
And worst-case (O) of O(n) if it was n number of input. However, we know the limitation clearly that we can only accept 3 patients per hour and hospital operates only 9 hours excluding an hour of lunch time. 3 x 9 = 27 of array size is fixed. Therefore, as I assumed O(1) would be worst case.
for (Object o : patientTimeSlot) {
    if (o != null) {
        initial[real] = (PatientBase) o;
real += 1; }
}


# Hospital 2

I have used Sorted dynamic array using doubling strategy. I have implemented arrayList algorithm.
The reason why I have chosen is because there could be too many data (patient) input. To shorten the time, I have chosen array. Although it is O(n) insertion to meet the criteria, it will be Amortised time of push is O(1). However, for incremental strategy analysis would be O(n). It might be efficient when there are limitations like hospital 1 but not for hospital 2.

I have used compareTo method to order the patients. It split to hour and minutes, initially check on hours then moved on to minutes to be compared.
Since the add method had to be O(n), I have sorted by going through the array and compare the data added and sorted it.
If new patient is earlier than existing patient, then old patient would be pushed to next index and add new patient to that index.
Also, since the system needed priority, I could not choose an unstable sort. Therefore, I have implemented algorithm which always compare to and push each index to add and which have time complexity of O(n).
Since it is already sorted by the time iterate to print, it is O(1) for an iterator.
It is best because even though inserting is O(n), Amortised time of push is O(1) and iterating is O(1).

Best case would be Ω(n) and in terms of adds an element at the end of the array is Amortised O(1) because it is doubling strategy (if 80% of array is full, will be doubled).
worst case is O(n).


# Hospital3

I have used doubly linked list with merge sort.
Because list are easy to insert unsorted data with memory efficiency.
Then I have thought that since it is unsorted, to reduce the risk of error, I have chosen stable sorting method which is merge sort.
Moreover, data input is unlimited, hence, I have chosen link over an array.
Although it was not in criteria, doubly linked can easily traverse previous and next which gives advantage of allocation, reallocation of memory and deletion.

I have used merge sort to order. It is because to sort list with priority needed stable algorithms.
Due stable sort, I could maintain the priority of users. Because unsorted algorithm swaps the equal data order.

addPatient is always Ω (1) because it is unsorted list insertion.
Iterator is worst time O(nlogn) whenever we divide a number into half in every step, it can be represented using a logarithmic function, which is log n and the number of steps can be represented by log n + 1(at most). And to merge the subarrays, made by dividing the original array of n elements, a running time of O(n) will be required. So n(log n + 1) = nlogn


# Login System

Used hash code to store the password instead of the plain text password because
- Many people use the same password over many accounts, to prevent further damage after hacked.
- It is hard to encrypt.
- Hard to encrypt it by just looking.
- It is quick and easy to store.
- Difficult to create an initial input that would match a specific desired output.

Also, I have stored the email in the hash table becase email will be hashed and compressed to be index but there are chances of having same index after hashed. Therefore, using the characteristic of uniqueness of email, system can prevent duplication. Also, this system required linear probing, by just checking the index there might be collision which are not detectable. Moreover, to change password, system has to check the email as reference to give authority. The email and password could be stored in separate table but that is low space efficiency, hence, not appropriate.

I have used polynomial accumulation. Partition the bits of the key into a sequence of components of fixed length.
It is suitable because polynomial generates with the string.
Generates large range of index.
Also, prime numbers in between 30 to 43 are particularly beneficial when working with English character strings. Because in a list of over 50,000 words, produced less than 7 collisions in each case.


# Tree of Symptoms

As it has been restructured, it is now Binary search tree.

However, it is not a balanced tree. It is because the root is randomly chosen and might give a situation of adding on only either one side (left-left-left-left-.....-left).
Also, there might be cases such height of left child and right child might differ by more than 1.
