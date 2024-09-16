Requirements
1.When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).  

To realise this requirement, I created a class called "MainMenu" that has enterCurrentJobDetails(), enterJobOffers(), adjustComparisonSettings() and compareJobOffers() operations to satisfy the above 4 use cases respectively.


2. When choosing to enter current job details, a user will:
1) Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consists of:
Title
Company
Location (entered as city and state)
Cost of living in the location (expressed as an index)
Yearly salary 
Yearly bonus 
Training and Development Fund
Leave Time
Telework Days per Week
2)Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

To realise this requirement, I created a class called CurrentJobDetails.
To realise 1), I added an attribute jobDetails, with type JobDetails, a class I created that contains all characters described above. So the information that user entered is saved here.
To realise 2), I added two operations called save() and cancel(), with functionality of to save information entered,  to cancel and exit without saving, respectively.


3. When choosing to enter job offers, a user will:
a. Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.
b. Be able to either save the job offer details or cancel.
c. Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

To realise this requirement, I created a class called CurrentJobOffers.
To realise a), I added an attribute currentJobOffer, with type JobOffer, a class I created that contains an attribute with type JobDetails. This currentJobOffer represents a new job offer information that a user is potentially going to add and save. In addition, I added an attribute allOffers that represents all offers saved in the system, so that every time when a new offer information is saved, this gets updated as well.
To realise b), I added two operations called saveJobOffers() and cancel(), with functionality of to save information entered,  to cancel without saving, respectively.
To realise c), 1), I created an operation called enterAnotherOffer().
To realise c), 2), I created an operation called returnToMainMenu().
To realise c), 3), I created an operation called compareOfferWIthCurrentJob().


4. When adjusting the comparison settings, the user can assign integer weights to:
Yearly salary
Yearly bonus
Training and Development Fund
Leave Time
Telework Days per Week
If no weights are assigned, all factors are considered equal.
NOTE: These factors should be integer-based from 0 (no interest/don’t care) to 9 (highest interest)

To realise this requirement, I created a class called CurrentComparsonSettings, that has an attribute called comparisonSettings with a created Type ComparisonSettings, that contains all characters described above.

In addition, I created two method save() and resetToDefault(), to allow users to save entered weight information, and to allow users to reset to default weight respectively.


5. When choosing to compare job offers, a user will:
Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.
Select two jobs to compare and trigger the comparison.
Be shown a table comparing the two jobs, displaying, for each job:
Title
Company
Location 
Yearly salary adjusted for cost of living
Yearly bonus adjusted for cost of living
TDF = Training and Development Fund
LT = Leave Time 
RWT = Telework Days per Week 
Be offered to perform another comparison or go back to the main menu.

To realise this requirement, I created a class called CurrentComparison, where it has two attribute rankedJobs with type List<JobDetails>, and comparisonResult, with created Type(Class) ComparisonResult that contains all information with characters described above. 

Operations included in this class:

The compareJobs(jobDetails job1, JobDetails job2) operation will trigger comparison with two injected job details. 

performaAnotherComparison() operations will let users select two other jobs to compare;

goBackToMainMenu() will exit and go to main menu

Also, rankJobs(CurrentComparisonSettings currentComaprisonSettings, List<JobDetails> allJobs) : List<JobDetails> operation ranks all job offer and current job details using the ranking criteria described in 6. 

The calculateScore(JobDetails jobDetails, CurrentComparisonSettings currentComaprisonSettings) : float operation calculate the score for the given job details with given comparisonSettings.


6. When ranking jobs, a job’s score is computed as the weighted average of:

AYS + AYB + TDF + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8))
where:
AYS = Yearly Salary Adjusted for cost of living
AYB = Yearly Bonus Adjusted for cost of living
TDF = Training and Development Fund  ($0 to $18000 inclusive annually)
LT = Leave Time  (0-100 whole number days inclusive)
RWT = Telework Days per Week 

NOTE: The rationale for the RWT subformula is:
-  value of an employee hour = (AYS / 260) / 8
-  commute hours per year (assuming a 1-hour/day commute):
                              1 * (260 - 52 * RWT)
-       therefore travel-time cost = (260 - 52 * RWT) * (AYS / 260) / 8

For example, if the weights are 2 for the yearly salary, 2 for the yearly bonus, 2 for the Training and Development Fund, and 1 for all other factors, the score would be computed as:
2/8 * AYS + 2/8 * AYB + 2/8 * TDF + 1/8 * (LT * AYS / 260) - 1/8 * ((260 - 52 * RWT) * (AYS / 260) / 8)))

This requirement gives specific logic of rankJobs(...) and calculateScore(...) operations described above. Our uml class diagram does not show specific implementation of certain operations. 


7. The user interface must be intuitive and responsive.

This is not represented in my design, as it will be handled entirely within the GUI implementation.


8. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).

There is no communication or saving between devices shown in our uml diagram.