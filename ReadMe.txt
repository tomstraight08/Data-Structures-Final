Tom Straight tstra2
CSC385
Final Project ReadMe

3 Files
------------------------------------------------------
1)Tester.java
-includes the main method to run the program
-creates HashMap movieDict (key: movieId, value: Movie object (see Movie.java below))
-creates HashMap userDict (key: userId, value: User object (see User.java below))
-scans in from ratings.dat
	o grabs userId, movieId and user's rating from each line
	o either creates new Movie object or adds to existing Movie in movieDict and adds rating (addReview method)
	o either creates new User object or adds to existing User in userDict and adds movie to movies seen list (addMovie method)
-scans in from movies.dat
	o grabs movieId and title from each line
	o sets title for each Movie (setTitle method)
	o with each movie, compares to all movies before it (compare method)
		*avoids calucating similarities twice by only doing it for each movie before 
-writes to output file
	o loop for every user in userDict, prints results of topFive method

-------------------------------------------------------
2)Movie.java
-Field Variables
	o String title, integer movieId
	o HashMap reviews (key: userId, value: rating) - contains all scores given to this movie
	o HashMap comparisons (key: movieId, value: similarity score) - contains all similarity scores compared with every other movie 
-Constructor
	o brings in movieId, userId and rating given
	o sends review to reviews HashMap
	o sets this.movieId
-Methods
	o setTitle (called when grabbing titles from movie.dat)
	o addComparison - places similarity score with movieID key (of movie compared to) into comparisons Hashmap
	o compare
		+ parameter of movieId to compare against
		+ calculates similarity score based on equation provided in project details
		+ sets similarity score into comparisons Hashmap for both movies being compared
			* avoids calculating twice

-----------------------------------------------
3)User.java
-Field Variables
	o ArrayList moviesSeen- contains movieIds of every movie user has seen
	o userId
-Constructor
	o brings in userId and sets it
-Methods
	o addMovie - adds movieId to movieSeen ArrayList
	o topFive 
		+ parameters are movieDict and userId
		+ returns Double[][] topFive- contains top 5 highest recommended movies {movieId, predictedScore}
		+ calculates predictedScore for every unseen movie based on equation provided in project details
		+ with each new predictedScore, loops through Double[][] topFive and bumps lower scores down the line
