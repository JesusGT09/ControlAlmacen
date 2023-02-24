package com.example.controlalmacen.model

/**
 * Created by ravi on 20/02/18.
 */
class Usuario {
    var id = 0
    var identificacion: String? = null
    var nombre: String? = null
    var apellido: String? = null
    var correo: String? = null
    var password: String? = null
    var telefono: String? = null
    var rol: String? = null

    constructor() {}
    constructor(id: Int, identificacion: String?, nombre: String?, apellido: String?,
    correo: String?, password: String?, telefono: String?, rol: String?
    ) {
        this.id = id
        this.identificacion = identificacion
        this.nombre = nombre
        this.apellido = apellido
        this.correo = correo
        this.password = password
        this.telefono = telefono
        this.rol = rol
    }

    companion object {
        const val TABLE_NAME = "Usuario"
        const val COLUMN_ID = "id"
        const val COLUMN_IDENTIFICACION = "identificacion"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APELLIDO = "apellido"
        const val COLUMN_CORREO = "correo"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_TELEFONO = "telefono"
        const val COLUMN_ROL = "rol"
    }
}
