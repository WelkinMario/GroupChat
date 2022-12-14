## Online Chat room in local network

### Keywords
Network Connection, Version control, Multithreading, Synchronization, User Identification

### Introduction
This project is inspired by the assignment in the course Computer Network, at which I implemented a local SMTP and POP3 system. The GroupChat includes a server as the medium for the communication and the client application used to chat with others.

The server must run at first to set up the server socket, thereby listening new connections from clients. For each client, the server forks a thread to handle its actions. The server thread then receives the message of its client and sends that message out to every clients in the chat room.

On the client side, it needs to connect to the server firstly. It gets the user's input to send to the server. However, it must also listen the messages from the server at the same time. Here I used two threads to avoid locking the main thread: one of them is for waiting user's input and send it, and the other one for waiting server's message (other clients' inputs). 

Lastly, a simple account identification is implemented. The client has to log in as an reserved account or as a guest. They are also able to sign up a new account. The server would store the account information locally. 

### Things remaining to improve
1. The server should limit the number of clients based on the operating hardware
2. The length of messages should be checked to implement a limit on the maximum length 
3. A fully practicable GUI for client
4. The client can do nothing but chatting, creating a GUI might help in adding more options

### Features to be added
1. Clients can choose direct message someone else or create a separate chat room
2. The client has their basic information shown to the other clients, achieving socialization with this application
