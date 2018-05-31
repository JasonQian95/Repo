## Learning goals

 * Interface vs. implementation
 * Abstracting low-level details with higher-level API
 * Composition vs. inheritance

## Getting Started

 1. [Fork][github-fork] this repo.

     > _Note:_ Your fork is private and is only visible to you, the TAs and the instructors.

 2. Clone the fork to your local machine (using the `git clone` command).
 
 3. Open the project in your IDE.       
    If you are using Eclipse, _File --> Import --> Existing Maven Projects_.


## Your Task

Implement the code to pass the provided unit tests.        
See the marking scheme below for more details.

### Guidance
 
In this assignment, you will abstract the details of a [basic robot API](/src/main/java/edu/toronto/csc301/IBasicRobot.java) 
with a [higher-level API ](/src/main/java/edu/toronto/csc301/IGridRobot.java) for robots that move in a grid.

You are asked to create two different implementation of the `IGridRobot` interface:

 1. By composition, where an IGridRobot uses an IBasicRobot.
 2. By inheritance, where an IGridRobot is an IBasicRobot.
  
As with the previous assignment, you should start by running 
[`SetupTest`](src/test/java/edu/toronto/csc301/SetupTest.java) 
(In Eclipse, _right click -> Run As -> JUnit Test_). 


## Deliverables

Your code, submitted as a single, non-conflicting [pull-request][github-pull-requests] from your fork to the handout repo (i.e. the repo you forked).

## Marking Scheme

Your code will be **marked automatically**, according to the following scheme:

 * 100% : Passing all tests (i.e. Get a green light from Travis CI)
 * 75%  : Failing at most 3 tests
 * 50%  : Failing between 4 to 10 tests
 * 0    : Failing more than 10 tests (or not submitting a solution)


## Important Notes

 1. Do not add any collaborators or teams to your fork!

    > Since you are the owner of your fork repo, GitHub allows you to share it with
others. Note that GitHub also allows us (the instructors and TA's) to see if
share your fork with anyone.

  If you share your fork with anyone, you will **automatically get a 0 mark** for this assignment.
  
 2. After you submit your assignment, make sure to check the results of Travis CI.
 
     > If your code passes the tests on your computer, but fails on Travis, then your code is broken.       

    It is **your responsibility** to make sure your code compiles!
  
 3. Do not modify any of given interfaces or testing code!
 
    > If you do, then Travis will no longer be useful, since it will no longer run the same tests as our auto-marker.
    


[github-guides]: https://guides.github.com/ "GitHub guides"
[github-fork]: https://guides.github.com/activities/forking/ "Guide to GitHub fork"
[github-pull-requests]: https://help.github.com/articles/using-pull-requests/ "Guide to GitHub Pull-Requests"