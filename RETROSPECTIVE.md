RETROSPECTIVE.md

### From the previous iteration: discuss a part of your project that has not been as successful as you would have liked, and how it can be improved in this iteration

For our previous iterations, specifically the planning, process and release parts, we were not fully successful with writing our documentation and eliminating code smells observed
###### Problems:
(Architectural description is included, but lacks a diagram, or does not fully represent the state of the application)
(issues in GitLab are assigned to a milestone, and then assigned to individual team members)
(unfinished issues are clearly labelled/identified by being moved to a new milestone for iteration 2)
(issues are updated with actual time spent on task to help with future estimation.)
(too complicated logic layer)
(single responsibility principle )
(open/closed principle)
(complex interface hierarchy structure)
###### Solutions
In this iteration's planning, process and release parts, we improved documentation and dealt with code smells observed. 
As a team, we communicated with each other much more than previous iterations.  We talked about ways to improve the application, like following SOLID design principles, reducing complex structures, and making our code readable and understandable. 
	We combined and merged files in logic layer, make it clearer and easier understand.  We checked for SOLID design principle issues in each of our classes, made sure our code looked neat, and removed or combined complex interfaces to make things simpler.

### Determine concrete (and realistic) ways of improvement, and decide how its success will be evaluated at the end of the iteration (measurable and objective)
Our improvements will be a success if we limit the number of interfaces to no more than one of each for each user story and in turn have a class for each.  We have nine logic interfaces that each satisfy a distinct role in our project.  We also have one class that satisfies each of these interfaces.  This is a success.  Our improvements will also be a success if we eliminate all SOLID prinicple issues and code smells identified in interations one and two.  These improvements will be successful if no further code smells appear in our iteration 3 feedback.

### Also include a chart (as an image) showing the 2 data points of project velocity from the last two iterations. 
For Iteration 1, we estimated our work to take 171 hours and we completed it in 154 hours.

For Iteration 2, we estimated our work to take 208 horus and we completed it in 272 hours.

![Project velocity](Project velocity.jpg)