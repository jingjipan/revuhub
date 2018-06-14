# Rev-U-Hub System Overview
### Architecture Design
We are using the required 3-tier architecture for the project. 
For this iteration, we will be using a simple version of a basic
architecture. We have a MoviePersistenceStub that stores all the sample movie data needed. Then, we
have a MoviePersistence interface to ensure the functionality of the storage is not known to the
other layers. Then, we have a Services class that initiates the persistence stub. Then, we have an
AccessMovies class that initiates the persistence interface using InitializeBackend. We have a 
MovieDMObject that represents a movie's data for the persistence. The MainActivity prompts a user 
to search for a movie and fails if it doesn't exist. If the movie exists, the user is redirected to 
the MovieOverviewActivity which shows specific information about the movie itself.

A sketch can be found in this directory to show the basic layout and is named architecture.png

### Major Source Code Files
MainActivity is what is run when the application is opened. Thus, it is the home screen in the
current iteration of the project. It brings the user to a screen with search functionality implemented
which will do a lookup in the temporary storage for the movie. It also 
has minor fuzzy searching capabilities that will find movie titles even if some typos exist. It
then reroutes the user to another activity that is for the specific movie that was searched or an error
Toast is displayed if the movie didn't exist. Currently, the user can only search for "DeadPool", "Thor", and
"The Avengers", but we plan to add more movies to our database in the future.

MovieOverviewActivity is the activity that is specific to a searched movie. It shows the movie name,
a poster of the movie (gathered from res/drawable), a cast of actor(s)/actress(es) (it is currently just one),
a short movie synopsis, and a list of reviews for the movie (currently a small description with no
user connected to it because we have no users yet). This page has scrollable functionality as it has been 
implemented with a scroll view.

Services initiates the persistence stub for the storage.

AccessMovie initiates the persistence interface for the storage using InitializeBackend.

MoviePersistenceStub is the temporary storage itself.

SearchHandler is a logic layer class that combines helper classes to create useable search functionality 
for the presentation layer. It uses a mildly fuzzy search to find movies in the persistence stub even if
there are typos in the search field. For example, the search "tor" will find the movie "Thor".

