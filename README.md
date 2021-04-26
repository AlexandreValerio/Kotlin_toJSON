# Kotlin_toJSON Readme - Programação Avançada 2020/2021

Kotlin_toJSON is a library where you can insert a kotlin type and get the expected JSON in string. This library stores the elements in memory in order to use them for many other purposes other than serializing, such as searching or other functions. It uses a visitor pattern design, so it is easy to add new funcionalities without changing the core classes.

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

JsonArray, JsonObject and JsonSimple are JsonElement types. When any kotlin element is given, it is stored on these classes based on its type. JsonArray for lists, JsonObject for data classes and Maps, and JsonSimple for the primitive types. Serializing and Search and Visitor Interface types, and are used on the visitor pattern design, to introduce new funcionalities in a modular and easy way.

## Anotations

For now, this library has two annotatios:
- RemoveProperty - if you don't want to have one specific data class property in JSON (like an ID for example);
- ChangeKey(newKey: String) - use if you want to change a specific key name in a data class

## TestCases and UserScenarioTest

In TestCases you have three tests:
- testSerialization - it tests the JSON serialization, and checks if it is correct (Note: if add any property annotations in the UserScenarioTest, you will change the outcome of toJson);
- testStringSearch - it tests the function stringSearch based on the expected answer;
- testPropertySearch - it tests the function searchKey, and therefore, tests if reflection is being done in a correct manner.

UserScenarioTest is a file with the data class used on tests, and here you can also test annotations and adding lists or any other elements that you want.
