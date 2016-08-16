/**
 *	Tester class
 *	main method- creates movieDict and userDict, scans input, creates output
 * @author tstra2
 *
 */
import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.lang.Integer;

public class Tester {

	public static void main(String[] args) {
		try
		{
			
			Map<Integer, Movie> movieDict = new HashMap<Integer, Movie>();
			Map<Integer, User> userDict = new HashMap<Integer, User>();
			
			
			//Reading data  from file 1
			Scanner scanner = new Scanner(new FileReader("src/ratings.dat"));
			String fileRead;
			// reading in ratings, creating review objects (movieId and score)
			System.out.println("Scanning ratings...");
			while(scanner.hasNext())
			{
				fileRead = scanner.nextLine();
				Scanner line = new Scanner(fileRead);
				Integer tempUserId = Integer.parseInt(line.next());
				Integer tempMovieId = Integer.parseInt(line.next());
				Integer tempRating = Integer.parseInt(line.next());
				
              //create movie if doesnt exist before adding to movieDict
        		if(!movieDict.containsKey(tempMovieId))
        		{
        			Movie newMovie = new Movie(tempMovieId, tempUserId, tempRating);
        			movieDict.put(tempMovieId, newMovie);
        		}
        		else
        			movieDict.get(tempMovieId).addReview(tempUserId, tempRating);
				
				//create user if doesnt exist before adding to userDict
				if(!userDict.containsKey(tempUserId))
				{
					User user = new User(tempUserId);
					user.addMovie(tempMovieId);
					userDict.put(tempUserId, user);
				}
				else
				{
					User user = userDict.get(tempUserId);
					user.addMovie(tempMovieId);
				}
			}
			System.out.println("Done");
			
			System.out.println("Scanning movies...");
			//Reading titles from movies file 2
			Scanner scanner2 = new Scanner(new FileReader("src/movies.dat")).useDelimiter("|");
			String fileRead2;
			while(scanner2.hasNext())
			{
				fileRead2 = scanner2.nextLine();
				Scanner line = new Scanner(fileRead2);
				line.useDelimiter("\\|");
				Integer tempMovie = Integer.parseInt(line.next());
				String tempTitle = line.next();
				
				//find movie and add title
				Movie thisMovie = movieDict.get(tempMovie);
				thisMovie.setTitle(tempTitle);
				
				//and compare reviews
				//compare to all in list as we add the next- avoids doing any twice
				
					int i = 1;
					while(i <= tempMovie)
					{
						Movie toCompare = movieDict.get(i);
						thisMovie.compare(toCompare);
						i++;
					}
	
			}
			scanner.close();
			System.out.println("Done");
			
			System.out.println("Total Movies: " + movieDict.size());
			System.out.println("Total Users: " + userDict.size());
			
			// for each user get top 5 and send to output
			FileWriter fw = new FileWriter("src/output.txt");
			
			System.out.println("Calculating top 5 for every user...");
			for(Integer user : userDict.keySet())
			{
				
				fw.write("user ID: " + user + " top 5 recommendations: ");
				Double[][] results = userDict.get(user).topFive(movieDict, user);
				for(int i = 0; i < 5; i++)
				{
					int movieID = results[i][0].intValue();
					if(results[i][1] == 0.0)
						fw.write("null");
					else
						fw.write(movieDict.get(movieID).title + "::" + results[i][1]);
				}
				fw.write("\n");
			}
			fw.flush();
			fw.close();
			System.out.println("Done");

		}
		 // handle exceptions
        catch (FileNotFoundException fnfe)
        {
            System.out.println("file not found");
        }
		 catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
		

	}
	

}
