# Appointment System

In this section, I needed to implement an appointment system for three different hospitals, that would allow patients to book appointments according to their desired time. In particular, I needed to complete the following functionality:
• iterator/ iter - iterates through the patients in the correct order according to their time
• addPatient/add patient - adds a new patient to the system. Returns True if the patient is successfully
added, returns False otherwise.

It is known that all three hospitals work from 08:00 to 18:00 with a lunch break from 12:00 to 13:00. Additionally, each hospital has different time complexity requirements for each function. Based on these requirements, I had to choose the best data structure to represent the appointment system, as well as the best algorithm to order the patients by their times. The requirements are the following:
• Hospital 1: The timeslots are fixed. Each appointment lasts exactly 20 minutes. For example, a new patient may book an appointment for 08:00, 08:20, 08:40, etc.
Any other timeslots, e.g. 08:01, 08:22, 08:41 are considered incorrect and should result in a patient not being added to the system. Each timeslot may be occupied by at most one patient, i.e. two patients can not both be booked for the 08:40 appointment. The patient is not added to the system if they request a timeslot that is already occupied.
addPatient/add patient; iterator/ iter - as quickly as possible.
• Hospital 2: addPatient/add patient in O(n); iterator/ iter - as quickly as possible. If two patients
requested the same time, give a priority to the patient that is already in the system.
• Hospital 3: addPatient/add patient in O(1); iterator/ iter - as quickly as possible. If two patients requested the same time, give a priority to the patient that is already in the system.
