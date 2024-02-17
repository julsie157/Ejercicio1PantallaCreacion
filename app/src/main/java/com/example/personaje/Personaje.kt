package com.example.personaje

class Personaje  (
    private var email: String,
    private var nombre: String,
    private val raza: Raza,
    private var clase: Clase,
    private var estadoVital: EstadoVital,
    private var imagenId: Int = R.drawable.gnomopocho
){
    private var id: Long = -1
    private var salud: Int = 0
    private var ataque: Int = 0
    private var experiencia: Int
    private var nivel: Int
    private var suerte: Int
    private var defensa: Int = 0


    // Enumeración para el tipo de raza y clase
    enum class Raza { Humano, Elfo, Enano, Maldito }
    enum class Clase { Brujo, Mago, Guerrero }
    enum class EstadoVital { Anciano, Joven, Adulto }

    private val mochila = Mochila(1,100,0,0,-1)// Ejemplo de peso máximo de la mochila
    // Atributos para el equipo del personaje
    private var arma: Articulo? = null
    private var proteccion: Articulo? = null

    // Inicialización de los atributos tras la construcción del objeto Personaje
    init {
        experiencia = 0
        nivel = 1
        suerte = (0..10).random() // Asigna un valor de suerte aleatorio entre 0 y 10
        calcularSalud()
        calcularAtaque()
        calcularDefensa()
    }

    fun getId(): Long {
        return id
    }

    fun setId(nuevoId: Long) {
        id = nuevoId
    }

    fun getNombre(): String {
        return nombre
    }

    fun setNombre(nuevoNombre: String) {
        nombre = nuevoNombre
    }

    fun getEmail(): String {
        return email
    }

    fun setEmail(nuevoEmail: String) {
        email = nuevoEmail
    }

    fun getImagenId(): Int {
        return imagenId
    }

    fun setImagenId(nuevoImagenId: Int) {
        imagenId = nuevoImagenId
    }

    fun getDefensa(): Int {
        return defensa
    }

    fun setDefensa(nuevaDefensa: Int) {
        defensa = nuevaDefensa
    }

    fun setNivel(nuevoNivel: Int) {
        nivel = nuevoNivel
        calcularSalud()
        calcularAtaque()
        calcularDefensa()
    }

    fun getSuerte(): Int {
        return suerte
    }

    fun setSuerte(nuevaSuerte: Int) {
        suerte = nuevaSuerte
    }

    fun getRaza(): Raza {
        return raza
    }

    fun getSalud(): Int {
        return salud
    }

    fun setSalud(nuevaSalud: Int) {
        salud = nuevaSalud
    }

    fun getAtaque(): Int {
        return ataque
    }

    fun setAtaque(nuevoAtaque: Int) {
        ataque = nuevoAtaque
    }

    fun getClase(): Clase {
        return clase
    }

    fun setClase(nuevaClase: Clase) {
        clase = nuevaClase
    }

    fun getEstadoVital(): EstadoVital {
        return estadoVital
    }

    fun setEstadoVital(nuevoEstadoVital: EstadoVital) {
        estadoVital = nuevoEstadoVital
    }

    fun getExperiencia(): Int {
        return experiencia
    }

    fun setExperiencia(experienciaGanada: Int) {
        experiencia += experienciaGanada
        while (experiencia >= 1000) {
            subirNivel()
            experiencia -= 1000 // Reducir la experiencia en 1000 al subir de nivel
        }
    }

    fun getNivel(): Int {
        return nivel
    }

    fun subirNivel() {
        if (nivel < 10) { // Limitar el nivel a 10
            nivel++
            calcularSalud() // Calcular el nuevo valor de salud al subir de nivel
            calcularAtaque() // Calcular el nuevo valor de ataque al subir de nivel
            calcularDefensa()
        }
    }

    private fun calcularSalud() {
        salud = when (nivel) {
            1 -> 100
            2 -> 200
            3 -> 300
            4 -> 450
            5 -> 600
            6 -> 800
            7 -> 1000
            8 -> 1250
            9 -> 1500
            10 -> 2000
            else -> 100 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }

    private fun calcularAtaque() {
        ataque = when (nivel) {
            1 -> 10
            2 -> 20
            3 -> 25
            4 -> 30
            5 -> 40
            6 -> 100
            7 -> 200
            8 -> 350
            9 -> 400
            10 -> 450
            else -> 10 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }

    private fun calcularDefensa() {
        defensa = when (nivel) {
            1 -> 4
            2 -> 9
            3 -> 14
            4 -> 19
            5 -> 49
            6 -> 59
            7 -> 119
            8 -> 199
            9 -> 349
            10 -> 399
            else -> 4
        }
    }

    fun pelea(monstruo: Monstruo) {
        var vidaMonstruo = monstruo.getSalud()
        var expGanada =
            monstruo.getSalud()
        var vidaPersonaje = salud
        var contador = false
        println("¡Un ${monstruo.getNombre()} se acerca!")



        while (vidaMonstruo > 0 && vidaPersonaje > 0) {

            println("¿Deseas activar la habilidad del personaje? (Sí/No)")
            val respuesta = readLine()?.toLowerCase()

            if ((respuesta == "si" || respuesta == "sí") && contador == false) {
                habilidad()
                contador = true
            }
            val evasion = suerte >= 10
            val ataqueMonstruo = if (evasion) 0 else monstruo.getAtaque()


            val defensaPersonaje = defensa * suerte / 100
            val danoMonstruo = if (evasion) 0 else ataqueMonstruo - defensaPersonaje

            if (!evasion) {
                vidaPersonaje -= danoMonstruo
            }

            println("${nombre} tiene una suerte de ${suerte}% y una defensa de ${defensaPersonaje}.")
            println("${nombre} ha recibido ${danoMonstruo} de daño. Salud de ${nombre}: ${vidaPersonaje}")

            if (vidaMonstruo > 0) {
                // Personaje ataca al monstruo
                vidaMonstruo -= ataque
                println("${nombre} ataca al ${monstruo.getNombre()}. Salud del ${monstruo.getNombre()}: ${vidaMonstruo}")
                if (vidaMonstruo <= 0) {
                    setExperiencia(expGanada)  // El personaje gana experiencia igual a la salud inicial del monstruo
                    println("${nombre} ha derrotado al ${monstruo.getNombre()} y gana ${expGanada} de experiencia.")
                    break
                }
                vidaPersonaje -= ataqueMonstruo
                println("${monstruo.getNombre()} ataca a ${nombre}. Salud de ${nombre}: ${vidaPersonaje}")
            }
        }
    }

    fun habilidad() {
        when (clase) {
            Clase.Mago -> {
                calcularSalud()
                println("$nombre utiliza su habilidad de Mago para restaurar su salud.")
            }

            Clase.Brujo -> {
                ataque *= 2
                println("$nombre utiliza su habilidad de Brujo para duplicar su ataque.")
            }

            Clase.Guerrero -> {
                suerte *= 2
                println("$nombre utiliza su habilidad de Guerrero para duplicar su suerte.")
            }
        }
    }

    fun entrenar(tiempoDeEntrenamiento: Int) {
        val factorExperienciaPorHora = 5
        val experienciaGanada = tiempoDeEntrenamiento * factorExperienciaPorHora

        setExperiencia(experienciaGanada)

        println("$nombre ha entrenado durante $tiempoDeEntrenamiento horas y ha ganado $experienciaGanada de experiencia.")
    }

    fun realizarMision(tipoMision: String, dificultad: String) {
        val probabilidadExito = when (dificultad) {
            "Fácil" -> if (nivel >= 5) 8 else 6
            "Normal" -> if (nivel >= 3) 6 else 4
            "Difícil" -> if (nivel >= 7) 4 else 2
            else -> 0
        }

        val resultado = (1..10).random()

        if (resultado <= probabilidadExito) {
            val experienciaGanada = when (tipoMision) {
                "Caza" -> 1000
                "Búsqueda" -> 500
                "Asedio" -> 2000
                "Destrucción" -> 5000
                else -> 0 // En caso de tipo de misión no reconocido
            }

            val multiplicadorExperiencia = when (dificultad) {
                "Fácil" -> 0.5
                "Normal" -> 1.0
                "Difícil" -> 2.0
                else -> 0.0 // En caso de dificultad no reconocida
            }

            val experienciaFinal = (experienciaGanada * multiplicadorExperiencia).toInt()
            setExperiencia(experienciaFinal)
            println("$nombre ha completado la misión de $tipoMision ($dificultad) con éxito y gana $experienciaFinal de experiencia.")
        } else {
            println("$nombre ha fracasado en la misión de $tipoMision ($dificultad) y no recibe ninguna recompensa.")
        }
    }

    fun cifrado(mensaje: String, ROT: Int): String {
        val abecedario: ArrayList<Char> = "abcdefghijklmnñopqrstuvwxyz".toList() as ArrayList<Char>
        var stringInv = ""
        var indice = 0

        for (i in mensaje.lowercase().toList() as ArrayList<Char>) {
            if (!i.isLetter() || i.isWhitespace()) {
                stringInv += i
            } else {
                indice = abecedario.indexOf(i) + ROT
                if (indice >= abecedario.size) {
                    indice -= abecedario.size
                    stringInv += abecedario[indice]
                } else
                    stringInv += abecedario[indice]
            }
        }
        return stringInv.filter { !it.isWhitespace() && it.isLetter() }
    }

    fun comunicacion(mensaje: String) {
        var respuesta = ""
        when (estadoVital) {
            EstadoVital.Adulto -> {
                if (mensaje.equals("¿Como estas?"))
                    respuesta = "En la flor de la vida, pero me empieza a doler la espalda"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta = "Estoy buscando la mejor solución"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta = "No me levantes la voz mequetrefe"
                        else
                            if (mensaje == nombre)
                                respuesta = "¿Necesitas algo?"
                            else
                                if (mensaje == "Tienes algo equipado?") {
                                    if (arma != null || proteccion != null) {
                                        val equipamiento = mutableListOf<String>()
                                        if (arma != null) {
                                            equipamiento.add(arma!!.getNombre().name)
                                        }
                                        if (proteccion != null) {
                                            equipamiento.add(proteccion!!.getNombre().name)
                                        }
                                        println("Tengo equipado: ${equipamiento.joinToString(", ")}")
                                    } else {
                                        println("Ligero como una pluma.")
                                    }
                                } else
                                    respuesta = "No sé de qué me estás hablando"
            }

            EstadoVital.Joven -> {
                if (mensaje.equals("¿Como estas?"))
                    respuesta = "De lujo"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta = "Tranqui se lo que hago"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta = "Eh relájate"
                        else
                            if (mensaje == nombre)
                                respuesta = "Qué pasa?"
                            else
                                if (mensaje == "Tienes algo equipado?") {
                                    if (arma != null || proteccion != null) {
                                        println("No llevo nada, agente, se lo juro.")
                                    } else {
                                        println("Regístrame si quieres.")
                                    }
                                } else
                                    respuesta = "Yo que se"

            }

            EstadoVital.Anciano -> {
                if (mensaje.equals("¿Como estas?"))
                    respuesta = "No me puedo mover"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta = "Que no te escucho!"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta = "Háblame más alto que no te escucho"
                        else
                            if (mensaje == nombre)
                                respuesta = "Las 5 de la tarde"
                            else
                                if (mensaje == "Tienes algo equipado?") {
                                    println("A ti que te importa nini!")
                                } else
                                    respuesta = "En mis tiempos esto no pasaba"
            }
        }
        when (raza) {
            Raza.Elfo -> println(cifrado(respuesta, 1))
            Raza.Enano -> println(respuesta.uppercase())
            Raza.Maldito -> println(cifrado(respuesta, 1))
            else -> println(respuesta)
        }
    }

    fun equipar(articulo: Articulo) {
        when (articulo.getTipoArticulo()) {
            Articulo.TipoArticulo.ARMA -> {
                if (articulo.getNombre() in Articulo.Nombre.BASTON..Articulo.Nombre.GARRAS) {
                    arma = articulo
                    // Aumentar el ataque del personaje según el nombre del arma
                    ataque += articulo.getAumentoAtaque()
                    println("Has equipado el arma: $articulo")
                    mochila.getContenido().remove(articulo)
                } else {
                    println("No se puede equipar el artículo. Tipo de arma no válido.")
                }
            }

            Articulo.TipoArticulo.PROTECCION -> {
                when (articulo.getNombre()) {
                    Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                        proteccion = articulo
                        // Aumentar la defensa del personaje solo si la protección es un escudo o una armadura
                        defensa += articulo.getAumentoDefensa()
                        println("Has equipado la protección: $articulo")
                        mochila.getContenido().remove(articulo)
                    }

                    else -> {
                        println("No se puede equipar el artículo. Tipo de protección no válido.")
                    }
                }
            }

            else -> {
                println("No se puede equipar el artículo. Tipo de artículo no válido.")
            }
        }
    }

    fun usarObjeto(articulo: Articulo) {
        when (articulo.getTipoArticulo()) {
            Articulo.TipoArticulo.OBJETO -> {
                when (articulo.getNombre()) {
                    Articulo.Nombre.POCION -> {
                        // Aumentar la vida del personaje al usar una poción
                        salud += articulo.getAumentoVida()
                        println("Has usado la poción y aumentado tu vida. Vida actual: $salud")
                        mochila.getContenido().remove(articulo)
                    }

                    Articulo.Nombre.IRA -> {
                        // Aumentar el ataque del personaje al usar un objeto de ira
                        ataque += articulo.getAumentoAtaque()
                        println("Has canalizado tu ira y aumentado tu ataque. Ataque actual: $ataque")
                        mochila.getContenido().remove(articulo)
                    }

                    else -> {
                        println("No se puede usar el objeto. Tipo de objeto no válido.")
                    }
                }
            }

            else -> {
                println("No se puede usar el artículo. Tipo de artículo no válido.")
            }
        }
    }

    fun getMochila(): Mochila {
        return this.mochila
    }

    override fun toString(): String {
        return "Personaje(email='$email', nombre='$nombre', raza=$raza, clase=$clase, estadoVital=$estadoVital, id=$id, salud=$salud, ataque=$ataque, experiencia=$experiencia, nivel=$nivel, suerte=$suerte, defensa=$defensa, mochila=$mochila, arma=$arma, proteccion=$proteccion)"
    }
}


