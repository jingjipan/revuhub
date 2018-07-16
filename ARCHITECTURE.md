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

The persistence layer consists of three database classes that implement three interfaces, also with a script file.

The script file performs the initialization of the database.

Three tables are created, which are 'MOVIES', 'USERS' table and 'REVIEWS'

* 'MOVIES' consists of a 'MOVIENAME' primary key and multiple other keys which stores info of that movie.
* 'USERS' consists of a 'USERNAME' primary key and 'PASSWORD' key.
* 'REVIEWS' consists of reference foreign keys, 'USERNAME' and 'MOVIENAME'. Also with a 'REVIEW' key which is the storage of review.

These interfaces are also implemented by stub classes for testing.
Each database is associated with one data object:

* `MovieHSQLDB` implements `MoviePersistence` and stores the contents of `MovieObject`s
* `UserHSQLDB` implements `UserPersistence` and stores the contents of `UserObject`s
* `ReviewHSQLDB` implements `ReviewPersistence` and stores the contents of `ReviewObject`s

The databases, through their interfaces, allow the retrieval of, addition of objects to, editing of, and removal of their contents.

Each database is initialized once by first calling a copy dababase method in 'presentation_layer.LoginActivity'. The method would read the assets.db.DB.script and then calling a getter method in `Application.Services` which ensures the database objects are created no more than once.

`PersistenceException`s are used by the database objects internally to handle database access failures.


## Logic Layer

	package com.comp3350.rev_u_hub.logic_layer;

The logic layer consists of multiple classes which implement interfaces.  These classes are accessed through getter methods in `Application.Services`, like the database objects, to prevent multiple instantiation.

The logic layer classes access the persistence layer interfaces while providing their own interfaces for the presentation layer to utilize.

### Data Modification Classes

	AccountManagement implements AccountManager
	ReviewManagement implements ReviewManager
	RatingManagement implements MovieRatings

The `AccountManagement` class makes use of `UserSearchValidator` and `UserPersistence` to allow creation, removal, and modification of stored user objects.

* User creation is validated with constraints and the requirement of two entries of a password which must match.
* Users can be removed or modified if their password is correctly supplied to `AccountManagement`.

The `ReviewManagement` class makes use of all the search classes (accessed through `Application.Services`) and `ReviewPersistence` to allow creation, removal, and modification of stored review objects.

The `RatingManagement` class makes use of `MovieSearchValidator` and `MoviePersistence` to allow addition of ratings and retrieval of count and rating data.

* Ratings can be added to a movie but not removed
* *Note that ratings are stored in a movie and are unrelated to reviews*

### User Login

	CurrentUserStorage implements UserLogin

The `CurrentUserStorage` class makes use of `UserSearchValidator` to allow user login.  The `CurrentUserStorage` class can also be used to retrieve the current logged-in user or whether any user is logged in at all.

* `CurrentUserStorage` contains at most one `UserObject`
* Through exception messages, `CurrentUserStorage` can suggest fixes to failed logins
* Once logged in, a user may not log out of the system

### Display Listing Classes

    UserMovieStats implements UserMovieProfile
    MovieListViewer implements MovieLists

The `MovieListViewer` class retrieves lists of movies from the persistence layer, sorted by average rating.

The `UserMovieStats` class retrieves similar lists for a user, filtering for movies they have reviewed.  It also displays additional data, such as longest review and average rating of a user's reviewed movies.

### Search Classes

	ReviewQuery implements ReviewSearch
	MovieSearchEngine extends SearchEngine implements MovieSearch
	UserSearchEngine extends SearchEngine implements UserSearch

The `ReviewQuery` class uses `ReviewPersistence`'s functionality for performing review database queries to retrieve reviews.  Reviews by a user or for a movie can be retrieved in list or text form.  Specific reviews can also be retrieved using both a user and movie.

The `MovieSearchEngine` and `UserSearchEngine` classes both extend `SearchEngine` to retrieve movie and user object respectively.  By default, the `SearchEngine` class uses a loose search that allows typos within one [Damerau–Levenshtein distance](https://en.wikipedia.org/wiki/Damerau–Levenshtein_distance) of a username or movie title.  The `SearchEngine` class also allows simple (strict) searches using a username or movie title.  In addition to this looseness, the search ignores case, spaces, apostrophes, and certain substrings like `"the "`.

### Validation Classes

    MovieSearchValidator
    UserSearchValidator

The validation classes are wrappers used internally by the logic layer.  For most of logic's functionality, certain exceptions are tested for and thrown in a uniform manner.  These classes encapsulate that behaviour.  We considered adding these behaviours directly to the search classes, but the successful retrieval of empty objects were necessary for certain tests and methods.

`MovieSearchValidator` wraps a `MovieSearch` object and validates its outputs, throwing exceptions where needed.

`UserSearchValidator` wraps a `UserSearch` object and validates its outputs, throwing exceptions where needed.

Through the validator classes, we are able to easily test our exception hierarchy.

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

The HomeActivity sets it's content to activity_main.xml. This is a simple search page, for searching for an existing movie. This activity was formally known as MainActivity. It also has a search for an existing user on the application which will take you to their profile and a "My Profile" button which will navigate the current user to their profile.

### MovieOverviewActivity

The MovieOverviewActivity sets it's content to activity_movie_overview.xml. This page shows all the content for a searched movie, and all of the reviews left for that movie. It also allows you to add a review to a movie if the current user hasn't left a review on that movie before. To test this, create a new user and leave a review on any movie.

### ProfileActivity

The Profile Activity sets it's content to activity_profile.xml. This page shows the top 3 rated movies on the application (which serves as a suggestion list) and if the current user that is logged in is equal to the user profile page, then it allows that user to edit their password.

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

### Unit Tests

	package com.comp3350.rev_u_hub_tests

All unit tests can be run by running the `AllTests` class.

The unit tests do not use Services to retrieve their dependencies.

Instead, they create their own persistence stubs found in:

	package com.comp3350.rev_u_hub_tests.persistence;

* These stubs emulate the behaviour of the `HSQLDB` classes in the persistence layer.

The unit tests retrieve their test data sets from descendants of the `UnitTestHelper` class:

* `MovieUnitTestHelper`
* `UserUnitTestHelper`
* `ReviewUnitTestHelper`
* These classes allow creation of randomized movie, user, and review objects respectively

Two unit tests, `UserSearchValidatorUnitTest` and `MovieSearchValidatorUnitTest`, test our exception hierarchy through the validator classes.

One unit test class exists for each of the public interfaces in the logic layer:

* Each of these test classes tests all methods in the interface using its implementing class
* `AccountManagerUnitTest` tests `AccountManager` using `AccountManagement`
* `MovieRatingsUnitTest` tests `MovieRatings` using `RatingManagement`
* `MovieSearchUnitTest` tests `MovieSearch` using `MovieSearchEngine`
* `ReviewManagerUnitTest` tests `ReviewManager` using `ReviewManagement`
* `ReviewSearchUnitTest` tests `ReviewSearch` using `ReviewQuery`
* `UserLoginUnitTest` tests `UserLogin` using `CurrentUserStorage`
* `UserSearchUnitTest` tests `UserSearch` using `UserSearchEngine`
* `MovieListsUnitTest` tests `MovieLists` using `MovieListViewer`
* `UserMovieProfileUnitTest` tests `UserMovieProfile` using `UserMovieStats`
* A description of each test, including the randomized object it is operating on and any exceptions returned, is printed to the console during testing
* Our unit tests use stubs; the newest of our tests use `Mockito` to implement mock test doubles

Additionally, the `AdvancedMovieSearchUnitTest` class tests exceptionally complex typo-detection within the `MovieSearchEngine` class.

One unit test class, `MovieInfoUnitTest`, also exists to test the copy constructor in `MovieObject`

    package com.comp3350.rev_u_hub_androidTest

### Integration Tests

    package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;

The integration tests test the ability of the logic classes to create, delete, and modify data in the persistence layer.

The `IntegrationTests` class runs all of the integration tests.

### Acceptance Tests

All acceptance tests can be run by running the `AllAcceptanceTests` class.

One acceptance test class exists for each user story (except for the user stories that can be tested by one test)

* Each of these test classes test a user story or multiple user stories (in the case of testing viewing all the different movie information, this can be done in one test)
* `LoginTest tests if a user can login using an existing user i.e. user=admin password=123456 (it also tests the failure case of an incorrect password)
* `CreateUserTest tests if a new user can be created and then if it persists when navigating to th login page and trying to log in with the new information
* `SearchMovieTest tests to see if an existing user can search an existing movie i.e. user=admin password=123456 movie=Deadpool
* `AddReviewTest tests to see if a user can add a review to a movie they have not already left a review on. This is done using a new user.
* `ChangePasswordTest tests to see if a password can be changed by an exising user on the profil page.