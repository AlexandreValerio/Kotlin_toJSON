# Kotlin_toJSON Readme - PA 2020/2021
## Alexandre Valério Rodrigues

Kotlin_toJSON is a library where you can insert a kotlin type and get the expected JSON in string. This library stores the elements in memory in order to use them for many other purposes other than serializing, such as searching or other functions. It uses a visitor pattern design, so it is easy to add new funcionalities without changing the core classes.
In the last version a Graphical User Interface was added, using SWT. You can use this library with or without the interface.

## JsonGenerator

This is the class called by the user. You must call this class with the object you want to transform to JSON. You should call it with a composed type, such as a List (JSON Array), a data Class (JSON Object) or a Map (JSON OBject). Inside this composed elements, the library supports the following:
- String;
- Int;
- Boolean;
- Double;
- Null;
- Enums;
- Other lists, maps or data class.

JsonGenerator has the following functions:
- toJson() - It returns the JSON formatted string;
- searchString() - Returns a list with all strings of the created JSON;
- searchKey(key: String) - Returns a list with all values of the given object key (if there is any, otherwise if retuns a list of null);
- getAllKeys() - Returns a list with all object keys.

## Other Classes

JsonArray, JsonObject and JsonSimple are JsonElement types. When any kotlin element is given, it is stored on these classes based on its type. JsonArray for lists, JsonObject for data classes and Maps, and JsonSimple for the primitive types. Serializing and `interface`.Search and Visitor Interface types, and are used on the visitor pattern design, to introduce new funcionalities in a modular and easy way.

## Anotations

For now, this library has two annotatios:
- RemoveProperty - if you don't want to have one specific data class property in JSON (like an ID for example);
- ChangeKey(newKey: String) - use if you want to change a specific key name in a data class

## Usage

UserScenarioTest is a file with the data class used on tests, and here you can also test annotations and adding lists or any other elements that you want.

```kotlin
data class Student(
    val number: Int,
    val name: String,
    //@RemoveProperty
    val ID: Int,
    val nameOfFriends: MutableList<Any?>,
    val type: StudentType?,
    //@ChangeKey("nova chave")
    val nulidade: String?,
    val masculino: Boolean,

    val bestFriend: Student? = null,
)

enum class StudentType {
    Bachelor, Master, Doctoral
}

val chicaFriends = mutableListOf<Any?>()
    chicaFriends.add("Maria")
    chicaFriends.add("Margarida")


    val chica = Student(2,"Francisca", 1423451, chicaFriends, StudentType.Bachelor, null, false)

    val friends = mutableListOf<Any?>()
    friends.add("Joao")
    friends.add("Joana")
    friends.add("Mario")
    friends.add(null)
    //friends.add(chica)

    val cristiano = Student( 7, "Cristiano", 6812374, friends, StudentType.Doctoral, null, true, chica)

```

### Lib usage without interface
```kotlin
    val jsonGen = JsonGenerator(cristiano)
    val jsonSerialized = jsonGen.toJson()
    println(jsonSerialized)
```

### Lib usage with interface
```kotlin
    val jsonGen = JsonGenerator(cristiano)
    val jsonSerialized = jsonGen.toJson()
    jsonGen.toJsonVisual()
```

### Inserting new Frames or new Actions

You can insert new Frames or Actions. For that, you must use based on the examples already done in the Actions.kt and Frames.kt. After that, you must inser the name of these new Actions/Frames in the di.properties file. Note: You have to write the complete name (including the package), for example, "interface.DefaultSetup". 

### Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
