# Architecture.md

**Rev-U-Hub Iteration 2**

## Data Objects

	package com.comp3350.rev_u_hub.data_objects;

Data objects are passed between our system's layers to transfer information.

All data objects implement the `SearchableObject` interface.
This interface requires a `boolean isEmpty()` method.
Implementing `SearchableObject` allows data object to be searched for using generic search methods.

`MovieObject` represents a movie.  It contains:

* A title
* A synopsis of its plot
* A description of its cast members
* A count of number of ratings given
* An average rating
* A path to a file containing an associated image

Of these fields, only the average rating and count of ratings can be modified by user activity.  The movies come pre-loaded into our persistence layer and cannot be removed or added during runtime.

`UserObject` represents a user.  It contains:

* A username
* A password

A user's password or username can be modified by user activity.  The user's current password must be supplied for both operations.  Users can also be created and removed.  Removing a user or changing a user's username will delete all reviews created by that user.

`ReviewObject` represents a review.  It contains:

* The text body of the review
* Its creator's username
* Its target movie's title

Reviews can be created and destroyed by users.  A review's text body can be edited.  The review's text-based references to its user and movie are immutable as the review only exists as a relationship between one user and one movie that user reviews.


## Persistence Layer

	package com.comp3350.rev_u_hub.persistence_layer;

The persistence layer consists of three database classes that implement three interfaces.
These interfaces are also implemented by stub classes for testing.
Each database is associated with one data object:

* `MovieHSQLDB` implements `MoviePersistence` and stores the contents of `MovieObject`s
* `UserHSQLDB` implements `UserPersistence` and stores the contents of `UserObject`s
* `ReviewHSQLDB` implements `ReviewPersistence` and stores the contents of `ReviewObject`s

The databases, through their interfaces, allow the retrieval of, addition of objects to, editing of, and removal of their contents.

Each database is initialized once by first calling ***EXPLAIN HOW DB IS INITIALIZED*** and then calling a getter method in `Application.Services` which ensures the database objects are created no more than once.

`PersistenceException`s are used by the database objects internally to handle database access failures.


## Logic Layer

	package com.comp3350.rev_u_hub.logic_layer;

The logic layer consists of multiple classes which implement interfaces.  These classes are accessed through getter methods in `Application.Services`, like the database objects, to prevent multiple instantiation.

The logic layer classes access the persistence layer interfaces while providing their own interfaces for the presentation layer to utilize.

### Data Modification Classes

	AccountManagement implements AccountManager
	ReviewManagement implements ReviewManager
	RatingManagement implements MovieRatings

The `AccountManagement` class makes use of `UserSearch` and `UserPersistence` to allow creation, removal, and modification of stored user objects.

* User creation is validated with constraints and the requirement of two entries of a password which must match.
* Users can be removed or modified if their password is correctly supplied to `AccountManagement`.

The `ReviewManagement` class makes use of all the search classes (accessed through `Application.Services`) and `ReviewPersistence` to allow creation, removal, and modification of stored review objects.

The `RatingManagement` class makes use of `MovieSearch` and `MoviePersistence` to allow addition of ratings and retrieval of count and rating data.

* Ratings can be added to a movie but not removed
* *Note that ratings are stored in a movie and are unrelated to reviews*

### User Login

	CurrentUserStorage implements UserLogin

The `CurrentUserStorage` class makes use of `UserSearch` to allow user login.  The `CurrentUserStorage` class can also be used to retrieve the current logged-in user or whether any user is logged in at all.

* `CurrentUserStorage` contains at most one `UserObject`
* Through exception messages, `CurrentUserStorage` can suggest fixes to failed logins
* Once logged in, a user may not log out of the system

### Search Classes

	ReviewQuery implements ReviewSearch
	MovieSearchEngine extends SearchEngine implements MovieSearch
	UserSearchEngine extends SearchEngine implements UserSearch

The `ReviewQuery` class uses `ReviewPersistence`'s functionality for performing review database queries to retrieve reviews.  Reviews by a user or for a movie can be retrieved in list or text form.  Specific reviews can also be retrieved using both a user and movie.

The `MovieSearchEngine` and `UserSearchEngine` classes both extend `SearchEngine` to retrieve movie and user object respectively.  By default, the `SearchEngine` class uses a loose search that allows typos within one [Damerau–Levenshtein distance](https://en.wikipedia.org/wiki/Damerau–Levenshtein_distance) of a username or movie title.  The `SearchEngine` class also allows simple (strict) searches using a username or movie title.

### Exceptions

	package com.comp3350.rev_u_hub.logic_layer.exceptions;

The logic layer's classes communicate with one another and with the presentation layer through exceptions whenever a method finds itself in an invalid state.  This is done to prevent uncatched passing of nulls or empty objects.

*Note: `SearchEngine` and its children do not use these exceptions*

***I think we should make these classes package private and just have a dedicated public movie-search-bar wrapper that handles exceptions safely***

The logic layer's exception hierarchy is as follows:

* `Exception`
	* `MovieDataException`
		* `MovieDataInvalidRatingException`		*Rating constraint failed*
		* `MovieDataDataNoRatingsException`		*Movie not rated yet*
		* `MovieDataDataNotFoundException`		*Movie does not exist in DB*
	* `UserCreationException`
		* `UserCreationFailedException`			*Creation failed*
		* `UserCreationDuplicateException`		*User already exists*
		* `UserCreationUserConstraintException`		*Username constraint failed*
		* `UserCreationPasswordConstraintException`	*Password constraint failed*
		* `UserCreationPasswordMismatchException`	*Two passwords not equal*
	* `UserDataException`
		* `UserDataNotFoundException`			*User does not exist in DB*
		* `UserDataWrongPasswordException`		*Wrong password supplied*
	* `ReviewCreationException`
		* `ReviewCreationFailedException`		*Creation failed*
		* `ReviewCreationDuplicateException`		*Review already exists*
		* `ReviewCreationNoUserException`		*User does not exist in db*
	* `ReviewDataException`
		* `ReviewDataNotFoundException`			*Review does not exist in db*
		* `ReviewDataNoMovieException`			*Movie does not exist in db*
		* `ReviewDataNoUserException`			*User does not exist in db*
		* `ReviewDataWrongUserException`		*User is not logged in*


## Presentation Layer

    package com.comp3350.rev_u_hub.presentation_layer

The presentation layer consists of multiple activity classes that serve as the different "pages" on the application. Each activity class has a cooresponding .xml layout file to setup up the front end. The activites serve as the minor logic portion that is required in order for android to properly serve the pages.

The activites use Application/Services.java class in order to access all the required interfaces and methods that are used to manipulate the application data.

### LoginActivity

The LoginActivity serves as the launcher for the application. It's content view is set to activity_login.xml. This page has a simple login form with a button that will launch the HomeActivity if the user exists. To test current user login: Username 'admin', Password '123456'. There is also a button that will launch a new activity for creating a new user in the application. 

### CreateUserActivity

The CreateUserActivity sets it's content to activity_create_user.xml. This is a simple create account page that checks to see if a username exists already, and if doesn't and the password fields match, a new user is created and is "logged in". This will lauch the HomeActivity. To test already existing user: Username: 'admin'. To test creating a new user, use any username besides 'admin', 'test1', and 'test2'. 

### HomeActivity

The HomeActivity sets it's content to activity_main.xml. This is a simple search page, for searching for an existing movie. This activity was formally known as MainActivity.

### MovieOverviewActivity

The MovieOverviewActivity sets it's content to activity_movie_overview.xml. This page shows all the content for a searched movie, and all of the reviews left for that movie. It also allows you to add a review to a movie if the current user hasn't left a review on that movie before. To test this, create a new user and leave a review on any movie.


## Application

### Main

The Main.java class simply sets and gets the database path name. This was similar in the sample project.

### Services

The Services.java class serves as centeralized hub that initializes and provides access to: 
* MoviePersistence
* ReviewPersistence
* UserPersistence
* MovieSearch
* UserSearch 
* ReviewSearch
* AccountManager
* UserLogin
* ReviewManager
* MovieRatings

## Test Classes