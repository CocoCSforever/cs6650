Upic ski resorts all use RFID lift ticket readers so that every time a skiier gets on a ski lift, the time of the ride and the skier ID are recorded.

In this course, through a series of assignments, we'll build a scalable distributed cloud-based system that can record all lift rides from all Upic resorts. This data can then be used as a basis for data analysis, answering such questions as:

which lifts are most heavily used?
which skiers ride the most lifts?
How many lifts do skiers ride on average per day at resort X?
In Assignment 1, we'll build a client that generates and sends lift ride data to a server in the cloud. The server will simply accept and validate requests, and send an HTTP 200/201 response. In Assignment 2, we'll add the processing and storage logic to the server, and send a richer set of requests. In Assignments 3 and 4, we'll get a little crazy, so make sure you lay solid code and design foundations in the first two assignments!
## Submission Requirements
Submit your work to Canvas Assignment 1 as a pdf document. The document should contain:

1. the URL for your git repo. *Make sure that the code for the client part 1 and part 2 are in seperate folders in your repo*
1. a 1-2 page description of your client design. Include major classes, packages, relationships, whatever you need to convey concisely how your client works.Include Little's Law throughput predictions.
1. Client (Part 1) -  This should be a screen shot of your output window with your wall time and throughput. Also make sure you include the client configuration in terms of number of threads used.
1. Client (Part 2) - run the client as per Part 1, showing the output window for each run with the specified performance statistics listed at the end.

## Grading:
1. Server implementation working (10 points)
2. Client design description (5 points) - clarity of description, good design practies used
3. Client Part 1 - (10 points) - Output window showing best throughput. Points deducted if actual throughput not close to Little's Law predictions.
4. Client Part 2 - (10 points) - 5 points for throughput within 5% of Client Part 1. 5 points for calculations of mean/median/p99/max/throughput (as long as they are sensible).
5. Plot of throughput over time (5 points)
6. Bonus Points 2 points for fastest 3 clients, and 1 point for next fastest 3 clients.

Your client will send 200K POST requests to your server. You should create an object that generates a random skier lift ride event that can be used to form a POST request. Follow the rules below:

skierID - between 1 and 100000
resortID - between 1 and 10
liftID - between 1 and 40
seasonID - 2024
dayID - 1
time - between 1 and 360