# Canvas

This is a simple console based Scala application which enables users to create a canvas and draw some figures inside it.

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
2. Add Undo command.
3. Canvas service API improvements.
4. Add integration tests for Canvas Service.

###### Note: Thought the application is quite stateful, I have encapsulated states within Canvas object. This will give flexibility to extend the API in future i.e. adding Undo operation.
