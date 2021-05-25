package lib


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


fun main(){

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


    val lista1 = mutableListOf<Any?>()
    val lista2 = mutableListOf<Any>()

    lista1.add(2)
    lista1.add("maria")
    lista1.add(true)

    lista2.add(2)
    lista2.add("4")

    lista1.add(lista2)

    val a = mutableMapOf<Any, Any>()
    a[1] = "zezinho"
    a[2] = "zezao"
    a["manel"] = 50.2
    a[true] = lista1
    a[false] = lista2


    val jsonGen = JsonGenerator(cristiano)
    val jsonSerialized = jsonGen.toJson()
    println(jsonSerialized)
    /** Pesquisa para retornar todas as Strings*/
    //println(jsonGen.searchString())
    /** Pesquisa para determinar qual o value com o determinado key (se existir)*/
    //println(jsonGen.searchKey("number"))
    ///println(jsonGen.getAllKeys())
    jsonGen.toJsonVisual()


}


