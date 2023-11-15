## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Code Info

It's a low-level design for a matching engine in Java.

Matching Engine :

A Matching Engine is an electronic system that matches buy and sell orders for various markets — stock market, commodity market, and financial exchanges. The order-matching system forms the core of all electronic exchanges and executes orders from market users.

Functionalities Present :

- Add buy and sell order
- Match Order

Algorithm used :

- Utilizing a PriorityQueue for implementing FIFO (First In, First Out) order processing.
- Orders are sorted based on price, second preference is given to the oldest order in the case of similar prices.
- The design ensures that concurrent modification of the queue is unnecessary for handling remaining orders after the matching process.
  
Handling Failure Cases:

- Implemented try-catch blocks to handle potential exceptions.
- Assuming that the addition of an order to the queue will not encounter issues.
- Added retry logic for match orders.
  
Single Threaded or multi-threaded:

- The order book is designed to be multi-threaded, and for a single instance, only one thread can be executed at a time, implemented through the use of synchronized functions.
- Matching order with the order book is currently implemented as a single thread since I am using a HashMap to store it. However, it can be adapted for multi-threaded operations by utilizing ConcurrentHashMap
  (In MatchUsingMap.java class).
  
Is this scalable to 100rpm:

- Yes, the algorithm is scalable to 100 requests per minute (rpm), but as I am using a HashMap to store the order book records, it may not be suitable for a large set of unique orders due to potential collisions in the HashMap.
