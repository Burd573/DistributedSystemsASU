##ALL TASKS:

Screencast: https://youtu.be/ei9jSAH-PbM

Run client with:

    gradle runClient -Phost=localhost -Pport=8080

##TASK1:

Run server with:

    gradle runTask1 -Pport=8080

Completed:

- pop functionality added
- display functionality added
- count functionality added
- switch functionality added

To use default port(8000):
    
    gradle runTask1

##TASK2:

Run server with:

    gradle runTask2 -Pport=8080

To use default port(8000):

    gradle runTask1

Completed:

- Allow unbounded incoming connections to server
- No client blocks
- String list is shared among clients

##TASK3:

Run server with:

    gradle runTask3 -Pport=8080 -Pbound=2

To use default port(8000) and default bounds(3):

    gradle runTask1

Completed:

- Allows a certain number of connections at a time, specified by arguments or default value(2)


