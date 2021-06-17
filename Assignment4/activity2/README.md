Client server game where clients guess riddles and reveal parts of an image. 
When the image is fully revealed, the client(s) win the game

Screencast: https://youtu.be/odoICd1SeVY

Run Server:

	gradle runServer -Pport=8080

Run server default(port 9099):

	gradle runServer

Run Client: 

	gradle runClient -Pport=8080 -Phost=18.118.5.197

Run Client Default (localhost and port 9099):

	gradle runClient

Completed:

- Project runs through gradle
- Protobuf files implemented
- Main menu gives three options: leaderboard, new game, quit
- leaderboard shown with option 1
- new game sent to client with option 2
- multiple clients can enter the same game and reveal the same image faster
- multiple clients can win together
- server checks if tasks are done correctly
- client presents information and tasks are small and fast
- game quits gracefully when option 3 is chosen
- server is currently running on AWS
