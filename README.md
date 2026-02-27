# laba8
This project was carried out in four stages, in accordance with the work requirements.

All four stages are contained in this repository. The requirements of the earlier stages may not be fulfilled if later stages required changes.

## [Stage 1](https://github.com/NastyaLush/itmo_programming_java_5lab)

Implement a console application that manages a collection of objects in interactive mode. The collection must store objects of the `Product` class, whose description is given below.

The developed program must meet the following requirements:

- The class whose instances are managed by the program must implement a default sort order.
- All field requirements of the class (specified as comments) must be fulfilled.
- Use a collection of type `java.util.HashMap` for storage.
- When the application starts, the collection must be automatically populated with values from a file.
- The file name must be passed to the program via an environment variable.
- Data must be stored in the file in XML format.
- Reading data from the file must be implemented using `java.io.FileReader*`.
- Writing data to the file must be implemented using `java.io.FileWriter`.
- All classes in the program must be documented in Javadoc format.
- The program must handle invalid data correctly (user input errors, missing file access permissions, etc.).

In interactive mode, the program must support the following commands:

- `help` : display help for the available commands
- `info` : output information about the collection to standard output (type, initialization date, number of elements, etc.)
- `show` : output all elements of the collection in string representation to standard output
- `insert null {element}` : add a new element with the specified key
- `update id {element}` : update the value of the collection element whose `id` equals the specified one
- `remove_key null` : remove an element from the collection by its key
- `clear` : clear the collection
- `save` : save the collection to a file
- `execute_script file_name` : read and execute a script from the specified file. The script contains commands in the same form as the user enters them in interactive mode.
- `exit` : terminate the program (without saving to the file)
- `remove_lower {element}` : remove from the collection all elements that are less than the specified one
- `history` : output the last 10 commands (without their arguments)
- `remove_lower_key null` : remove from the collection all elements whose key is less than the specified one
- `remove_any_by_unit_of_measure unitOfMeasure` : remove one element from the collection whose `unitOfMeasure` field value is equivalent to the specified one
- `average_of_manufacture_cost` : output the average value of the `manufactureCost` field for all elements
- `group_counting_by_price` : group the collection elements by the value of the `price` field and output the number of elements in each group

## [Stage 2](https://github.com/NastyaLush/itmo_programming_java_6lab)

Split the program from Lab Work No. 5 (stage 1) into client and server modules. The server module must execute commands for managing the collection. The client module must, in interactive mode, read commands, send them to the server for execution, and output the execution results.

### Requirements

- Operations for processing collection objects must be implemented using the Stream API with lambda expressions.
- Objects must be transferred between the client and server in serialized form.
- Objects in the collection sent to the client must be sorted by size.
- The client must correctly handle temporary server unavailability.
- Data exchange between the client and server must use the TCP protocol.
- On the server, input/output streams must be used for data exchange.
- On the client, a network channel must be used for data exchange.
- Network channels must be used in non-blocking mode.

### Server application responsibilities

- Work with the file that stores the collection.
- Manage the collection of objects.
- Assign automatically generated fields for objects in the collection.
- Wait for client connections and requests.
- Process received requests (commands).
- Save the collection to a file when the application terminates.
- Save the collection to a file when executing a special command available only to the server (the client cannot send this command).
- The server application must consist of the following modules (implemented as one or more classes):
  - Connection acceptance module.
  - Request reading module.
  - Received command processing module.
  - Response sending module.
- The server must run in single-threaded mode.

### Client application responsibilities

- Read commands from the console.
- Validate input data.
- Serialize the entered command and its arguments.
- Send the command and its arguments to the server.
- Process the server response (output the result of command execution to the console).
- Remove the `save` command from the client application.
- The `exit` command terminates only the client application.

### Important

Commands and their arguments must be objects of classes. Exchanging “plain” strings is not allowed. For example, for the `add` command (or its equivalent), you must form an object containing the command type and the object that should be stored in your collection.

### Additional task

Implement logging of different server workflow stages (startup, new connection received, new request received, response sent, etc.) using **Java Util Logging**.

# [Stage 3](https://github.com/NastyaLush/itmo_programming_java_7lab)

Modify the program from **Lab Work No. 6** as follows:

- Store the collection in a relational DBMS **(PostgreSQL)**. Remove file-based collection storage.
- Use database facilities *(a sequence)* to generate the `id` field.
- Update the in-memory collection state only after an object has been successfully added to the database.
- All data-retrieval commands must work with the in-memory collection, not with the database.
- Implement user registration and authentication. A user must be able to set a password.
- When storing passwords, hash them using the **SHA-1** algorithm.
- Forbid execution of commands by unauthorized users.
- When storing objects, save information about the user who created the object.
- Users must be able to view all objects in the collection, but may modify only the objects that belong to them.
- To identify the user, send the login and password with each request.
- Implement multi-threaded request processing.

---

## Multithreading requirements

- Use a **Fixed thread pool** for multi-threaded request reading.
- Use a **Cached thread pool** for multi-threaded processing of received requests.
- Use a **Cached thread pool** for multi-threaded response sending.
- Use **`synchronized`** read/write synchronization to control access to the collection.

---

## Work order / environment

- Use **PostgreSQL** as the database.
- To connect to the database on the department server, use host **`pg`**, database name **`studs`**, and username/password the same as those used for connecting to the server.

# Stage 4 (current)

The interface must be implemented using the **Swing** library.

The graphical interface of the client side must support the **Russian**, **Norwegian**, **French**, and **Spanish (Nicaragua)** languages/locales. Correct formatting of numbers, date, and time according to the selected locale must be ensured. Language switching must happen **without restarting** the application. Localized resources must be stored in a **properties file**.

Modify the program from **Lab Work No. 7** as follows:

Replace the console client with a client that has a graphical user interface (GUI).

### Client functionality must include

- An authorization/registration window.
- Display of the current user.
- A table showing all objects from the collection.
- Each object field must be a separate table column.
- Table rows can be filtered/sorted by the values of any column. Implement sorting and filtering of column values using the **Streams API**.
- Support for all commands from previous lab works.
- An area that visualizes the collection objects.
- Objects must be drawn using graphical primitives with **Graphics**, **Canvas**, or similar graphical library tools.
- Use the object’s coordinate and size data for visualization.
- Objects created by different users must be drawn in different colors.
- Clicking an object must display information about that object.
- When an object is added/removed/updated, it must automatically appear/disappear/change in the visualization area for both the owner and all other clients.
- When drawing an object, play an animation agreed upon with the instructor.
- Ability to edit individual fields of any object (owned by the user). Switching to editing must be possible both from the table (general list of objects) and from the visualization area.
