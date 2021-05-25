package lib

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestCases(){

    private fun createScenario(): JsonGenerator{
        val friends = mutableListOf<Any?>()
        friends.add("Joao")
        friends.add("Joana")
        friends.add("Mario")
        friends.add(null)
        val chicaFriends = mutableListOf<Any?>()
        chicaFriends.add("Maria")
        chicaFriends.add("Margarida")
        val chica = Student(2,"Francisca", 1423451, chicaFriends, StudentType.Bachelor, null, false)

        val cristiano = Student( 7, "Cristiano", 6812374, friends, StudentType.Doctoral, null, true, chica)
        val jsonGen = JsonGenerator(cristiano)
        jsonGen.toJson()
        return jsonGen
    }

private val correctJson = """{
"ID": 6812374,
"bestFriend": {
"ID": 1423451,
"bestFriend": null,
"masculino": false,
"name": "Francisca",
"nameOfFriends": ["Maria", "Margarida"],
"nulidade": null,
"number": 2,
"type": "Bachelor"
},
"masculino": true,
"name": "Cristiano",
"nameOfFriends": ["Joao", "Joana", "Mario", null],
"nulidade": null,
"number": 7,
"type": "Doctoral"
}""".trimIndent()

    @Test
    fun testSerializedJson() {

        assertEquals(correctJson, createScenario().toJson(), "Your JSON is not equal ")
}

    private val correctStringSearch = "[Francisca, Maria, Margarida, Cristiano, Joao, Joana, Mario]"
    @Test
    fun testStringSearch() {
    /** O searchString devolve uma lista de strings, pelo que o teste é realizado com o toString*/
        assertEquals(correctStringSearch, createScenario().searchString().toString(), "Your String search is incorrect")
    }


    private val correctElement = "[7, 2]"
    @Test
    fun testPropertySearch(){
        /** Devolve uma lista com o(s) elemento(s) associados à key pretendida, pelo que o teste é realizado com o toString*/
        assertEquals(correctElement, createScenario().searchKey("number").toString(), "Your search is incorrect")
    }

}
