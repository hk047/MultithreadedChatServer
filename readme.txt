Simple Chatting System README



ChatServer

- The ChatServer can be initialised as a default, with no command line arguments, simply with ChatServer

- It will default to connect to port 14001 when no arguments are given, and if you try to connect with different arguments but they are invalid.

- It may also be given additional parameters to use different ports, e.g. ChatServer -csp 14005

- The ChatServer must be initialised before ChatClients try to connect, otherwise there will be nothing for them to connect to!

- When a ChatClient connects to the ChatServer, the ChatServer displays the connection details.\

- To exit the ChatServer, type EXIT into the ChatServer.  This will disconnect and end all the client too.


- The ChatServer utilises ClientManager threads to handle data to and fro the clients and server.

- It also utilises ExitHandler threads to ensure it shuts down appropriately when the command is given by the user.




ChatClient

- The ChatClient can also be initialised as a default, with ChatClient

- It can also be initialised with a cca parameter to change the IP address, e.g. ChatClient -cca 192.168.10.250

- It can also be initialised with a cap parameter to change the port, e.g. \'93java ChatClient -ccp 14005

- Both can be change using both parameters on the command line like \'93java ChatClient -cca 192.168.10.250 -ccp 14005

- The ChatClient can send a message that will be received by the ChatServer and broadcasted back to all the connected ChatClients.

- If any ChatClient disconnects or is abruptly terminated, it does not affect the other ChatClients or the ChatServer.

- A new ChatClient can join a ChatServer without causing any issues and seamlessly join the chatting!




- The client uses a multithreaded solution, supporting reading inputs form the console and displaying messages at the same time.

- This is done by having SeverSender and ServerReader threads.

