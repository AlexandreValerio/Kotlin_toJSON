package project


/** Esta classe cria uma lista de JsonElements, de modo a guardár os elementos do array em memória
 */

class JsonArray(val key: String? = null, val receivedList: List<Any?>, parent: JsonElement? = null) : JsonElement(receivedList, parent){

    /** Lista para guardar os elementos filhos deste elemento composto*/
    val children = mutableListOf<JsonElement>()
    val childrenSize get() = children.size

    /** Lista para guardar os elementos em memória*/
    val auxArray = mutableListOf<JsonElement>()

    /** Nao preciso de retornar nada, apenas altero a lista auxArray, Quando quiser pegar nela posso fazer um get ou algo assim
     */
    fun addJsonArray(){


        receivedList.forEach(){
            if(it is String || it is Int || it is Boolean || it == null || it is Enum<*> || it is Double) { 
                val simple = JsonSimple(null, it, this)
                auxArray.add(simple)
            }
            else if(it is List<*>){
                val aux = JsonArray(null, it as List<Any?>, this) /** Vai instanciar a classe, passando a lista e depois chama a função recursivamente */
                aux.addJsonArray()
                auxArray.add(aux)

            }
            /** Caso seja um objeto */
            else {
                val aux = JsonObject(null, it, this)
                aux.addJsonObj()
                auxArray.add(aux)
            }
        }

        }

    override fun accept(v: Visitor) {
        v.visit(this)
        auxArray.forEach(){
            it.accept(v)
        }
        v.endOfArrayVisit(this)

    }


}