# To-Do List

A lightweight command line to-do list written in Kotlin.

## What it can do

Program allows user to:

* create new to-do list records
* remove existing to-do list records
* list all existing records in human readable format either displaying the entire list or displaying it by pages
* mark or unmark records as done

## How it works

This program works with file todo-list.json and creates it if it doesn't exist. The data are stored as a JSON object with 2 fields:

`count` - **integer**, represents the total number of records in current list.

`items` - **array of JSON objects**, each object represents one to-do record (`TodoItem` class).

This record in turn has next schema:

`message` - **string**, this is the content of the to-do item filled by user.

`isDone` - **bool**, represents the completion status of the to-do item, false by default.

`createTime` - **string**, time when task was created in a human readable format (`dd MMMM yyyy, HH:mm:ss`)

`editTime` - **string**, time when task was edited last time (by now, the only way to edit the task is to toggle its `isDone` flag).


## Usage

Program supports several commands in short and full form, and also contains included help (type `h` or `help` in program):

```
ls, list [--not-done]  - show the entire list. For lists that are too large, pagination mode is recommended - more. Optionally, you can set the key to display only undone tasks.
m, more [--not-done]   - show the list page by page. Page size: 20 tasks. Optionally, you can set the key to display only undone tasks.
n, next                - show next page in pagination mode.
p, prev                - show previous page in pagination mode.
q, quit                - exit pagination mode.
"+ <task_content>"     - create and save new task.
"- n"                  - removed nth task.
d, done n              - toggle nth task' done status.
exit                   - exit the program.
h, help                - show this message.
```


### Prerequisites

Requires JRE or JDK 1.8 and higher to be installed


### Launching

Build with Gradle:
`./gradlew build`

Run produced JAR **JetRoom-fat-1.0-SNAPSHOT** located in `buid/libs/`:
`java -jar JetRoom-fat-1.0-SNAPSHOT`


## Running the tests

Run with Gradle:
`./gradlew clean test`


## Deployment

Add additional notes about how to deploy this on a live system


## Built With

* [Kotlin](https://kotlinlang.org/) - The main development language
* [Gradle](https://gradle.org/) - Dependency Management


## License

This project is licensed under the MIT License.
