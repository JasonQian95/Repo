## Learning goals

 * Serialization
 * Interface versus implementation
 * Common patterns/concepts: Lambda expressions, Iterators, generics.

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

In your last assignment, you implemented robots that move in a grid.
In this assignment, you will implement the grid itself, as well as a way to
serialize/deserialize it.

A grid can be used to represent the floor plan of a warehouse, and
serialization allows us to save/load floor plans to/from files.

For the purpose of your assignments, floor plans will be very simple:
  * The warehouse floor is a grid
  * Grid cells can contains [racks](https://www.bing.com/images/search?q=warehouse+racks&go=Search&qs=n&form=QBILPG&pq=warehouse+racks&sc=8-15&sp=-1&sk=)
  * A [`Rack`](/src/main/java/edu/toronto/csc301/warehouse/Rack.java) is a simple object with a single field, `capacity`.

For example, below is a floor plan of a small, rectangular warehouse containing a few racks.

![Simple floor plan](https://csc301-fall-2016.github.io/resources/warehouse-floor-plan.png)

You will need to create two implementations of the [`IGrid<T>`][IGrid] interface:

 1. Rectangular grid
 2. Flex grid, that supports warehouses of any shape (i.e. not just rectangular rooms)

For each grid, you will implement the corresponding [`IGridSerializerDeserializer`](/src/main/java/edu/toronto/csc301/grid/IGridSerializerDeserializer.java).


#### Additional Notes

 * As with the previous assignments, you should start by passing all test methods in
 [`SetupTest`](src/test/java/edu/toronto/csc301/SetupTest.java).
   > *Note:* You will need to create the `impl` package, as directed by the tests.      
   > (`impl` stands for implementation)

 * The [`src/test/resources`](/src/test/resources) contains all the data files we
use for testing.

   * Files with the same grid number (e.g. `grid.02.rect.txt` and `grid.02.flex.txt`)
     represent the same floor plan in different formats.
   * Notice that some grids do not have a `rect.txt` version. That's because they are not rectangular.


 * [`IGrid<T>`][IGrid] is a [generic interface](https://docs.oracle.com/javase/tutorial/extra/generics/simple.html), and your grid implementations should be generic as well.       
   For example, your rectangular grid class should have the following signature:
   ```java
   public class RectangularGrid<T> implements IGrid<T>
   ```

 * You may have noticed that the [IGrid<T>][IGrid] interface is very minimal. This is intentional, and you must not change the interface in any way.

 * When you implement the serialization methods, you will need to write
   to an `OutputStream`. One simple way to write strings to an output stream
   is by creating an output stream writer:
   ```java
   // output is an OutputStream
   OutputStreamWriter writer = new OutputStreamWriter(output);
   ```

 * Another note about serialization. [`RackUtil`](/src/main/java/edu/toronto/csc301/warehouse/RackUtil.java) contains helper functions that convert between `Rack` objects and `byte[]`. If you need to convert between `byte[]` and `String`, you can use:
   ```java
   // byte[] to String
   String str = new String(byteArray);
   // String to byte[]
   byte[] byteArray = str.getBytes();
   ```

## Deliverables

Your code, submitted as a single, non-conflicting [pull-request][github-pull-requests] from your fork to the handout repo (i.e. the repo you forked).

## Marking Scheme

Your code will be **marked automatically**, according to the following scheme:

 * 100% : Passing all tests (i.e. Get a green light from Travis CI)
 * 75%  : Failing at most 5 tests
 * 50%  : Failing between 6 to 10 tests
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


[IGrid]: /src/main/java/edu/toronto/csc301/grid/IGrid.java "IGrid interface"
[github-guides]: https://guides.github.com/ "GitHub guides"
[github-fork]: https://guides.github.com/activities/forking/ "Guide to GitHub fork"
[github-pull-requests]: https://help.github.com/articles/using-pull-requests/ "Guide to GitHub Pull-Requests"
