	/**
	 *	User class
	 *	Stores user's seen movies, method to create top 5
	 * @author tstra2
	 *
	 */

import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.lang.*;
	
public class User {
	public ArrayList<Integer> moviesSeen = new ArrayList<Integer>();
	public int userId;


	public User(int tempUserID)
	{
		userId = tempUserID;
	}
	
	
	public void addMovie(int movieID)
	{
		moviesSeen.add(movieID);
	}
	
	//method for top 5 returning 2d array with rating and movieId
	
	public Double[][] topFive(Map<Integer, Movie> movieDict, int userId)
	{
		Double[][] topFive = {{0.0,0.0},{0.0,0.0},{0.0,0.0},{0.0,0.0},{0.0,0.0}}; //[movieId][score]
		
		int allMovies = movieDict.size();
		int movieCounter = 1;
		while(movieCounter <= allMovies)   //for every movie
		{

			double totalRatingTimesSim = 0;
			double totalSim = 0;
			
			if(!this.moviesSeen.contains(movieCounter))  //every movie thats not in user's seen list
			{
				for(int index = 0; index < (moviesSeen.size()); index++) //every movie they have seen
				{
					Movie j = movieDict.get(moviesSeen.get(index));   //set j (movie seen)
					double simIJ = j.comparisons.get(movieCounter);
					totalRatingTimesSim += (j.reviews.get(this.userId)*simIJ);   //add (rating(u,j)*sim(i,j)) to total numerator
					totalSim += simIJ;  //add sim(i,j) to total denom
				}
				
				double predictedRating = totalRatingTimesSim / totalSim;  //predicted rating (u,i)
				double movieId = movieCounter;
				
				for(int z = 0; z < 5; z++)   //going through array of users top 5 {movieId, predictedRating}, comparing and replacing if higher
				{
					double tempRating;
					double tempMovieId;
					if(predictedRating > topFive[z][1])
					{
						tempMovieId = topFive[z][0];
						tempRating = topFive[z][1];
						topFive[z][0] = movieId;
						topFive[z][1] = predictedRating;
						predictedRating = tempRating;
						movieId = tempMovieId;
					}
				}
			}
			movieCounter++;
		}
		return topFive;
	}
}
