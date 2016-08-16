/**
 *	Movie class
 *	Stores movie id? reviews and can calculate average
 * @author tstra2
 *
 */

import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.lang.*;

public class Movie {
	public String title;
	public Integer movieID;
	public Map<Integer, Integer> reviews = new HashMap<Integer, Integer>();
	public Map<Integer, Double> comparisons = new HashMap<Integer, Double>();
	
	//constructor adds review to reviews HashMap, adds movie to existing user or creates new user to add to
	public Movie(int movieID, int userId, int score)
	{
		this.addReview(userId, score);
		this.movieID = movieID;
	}

	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	public void addReview(int userId, int score)
	{
		reviews.put(userId, score);
	}

	public void addComparison(int movieId, double comparison) {
		//add new comparison at key movieId, value comparison
		comparisons.put(movieId, comparison);
	}

	public void compare(Movie toCompare)
	{
		int numerator = 0;
		int firstUserDenom = 0; //toCompare
		int secondUserDenom = 0; //this
			
		for(Integer user : toCompare.reviews.keySet()){      //for every user that has reviewed the other movie
			int toCompareReview = toCompare.reviews.get(user);   //get to compare review from user
			int thisReview = 0;
			
			if(this.reviews.containsKey(user))               //if this movie was reviewed by same user
			{
				thisReview = this.reviews.get(user);		//get this movie review from user
			}
			//multiply together and add to total for top
			numerator += (toCompareReview * thisReview);
			//square and add to user total for bottom
			firstUserDenom += (toCompareReview * toCompareReview);
			secondUserDenom += (thisReview)*(thisReview);
		}
		for(Integer user : this.reviews.keySet()){
			//only looking for ones that exist here but not in the other
			int thisReview = this.reviews.get(user);
			int toCompareReview = 0;
			
			if(!toCompare.reviews.containsKey(user))
			{
				secondUserDenom += (thisReview)*(thisReview);
			}

		}
		
		double sqrtFirstDenom = Math.sqrt(firstUserDenom);
		double sqrtSecondDenom = Math.sqrt(secondUserDenom);
		double similarity = ((numerator)/(sqrtFirstDenom*sqrtSecondDenom));
		this.comparisons.put(toCompare.movieID, similarity);
		toCompare.comparisons.put(this.movieID, similarity);
	}
}
