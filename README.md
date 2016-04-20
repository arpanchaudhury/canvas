# Canvas

This is a simple console based Scala application which enables users to create a canvas and draw some figures inside it.

Problem Statement
-----------------

You're given the task of writing a simple console version of a drawing program. At this time, the functionality of the program is quite limited but this might change in the future. In a nutshell, the

program should work as follows:

1. create a new canvas
2. start drawing on the canvas by issuing various commands
4. undo if need to remove previous commands
3. quit

At the moment, the program should support the following commands:

* ```C w h```         - Should create a new canvas of width w and height h.
* ```L x1 y1 x2 y2``` - Should create a new line from (x1,y1) to (x2,y2). Currently only horizontal or vertical lines are supported. Horizontal and vertical lines will be drawn using the 'x' character.
* ```R x1 y1 x2 y2``` - Should create a new rectangle, whose upper left corner is (x1,y1) and lower right corner is (x2,y2). Horizontal and vertical lines will be drawn using the 'x' character.
* ```B x y c```       - Should fill the entire area connected to (x,y) with "colour" c. The behaviour of this is the same as that of the "bucket fill" tool in paint programs.
* ```U```             - Should undo the previous command except canvas creation command. 
* ```Q```             - Should quit the program.

Running Canvas
--------------

*   Unzip ```canvas.zip``` in local directory.
*   Open terminal and go to canvas folder.
*   Check permissions of sbt file inside canvas folder. It should be executable.
*   Run application ```./sbt run```
*   Run all tests ```./sbt test```

Dependencies
------------

* sbt   ~ 0.13.11
* scala ~ 2.11.8
* spec2 ~ 3.7.2

Design
------

                        ------------------
                  -- -- |  CanvasClient  |
                  |     ------------------
                  |             |
                  |             |
                  |  --------------------------   ---------------------
                  |  |   CanvasServiceFactory |   |  CommandValidator |
                  |  --------------------------   ---------------------
                  |               |                          |
                  |               |                          |
                  |      --------------------          --------------
                  -- --  |   CanvasService  | -- -- -- |  CanvasIO  |
                         --------------------          --------------
                              |   |                          |
              -- -- -- -- -- --   |                          |
              |          -----------------------      ------------------
     -----------------   |  CommandTranslator  |      | Command Parser |
     | CanvasPainter |   -----------------------      ------------------
     -----------------

Future Enhancements
-------------------

1. Use tail recursion in bucket fill method.
3. Canvas service API improvements.
4. Add integration tests for Canvas Service.

###### Note: Thought the application is quite stateful, I have encapsulated states within Canvas object. This will give flexibility to extend the API in future.
