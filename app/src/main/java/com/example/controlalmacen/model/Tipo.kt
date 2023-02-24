package com.example.controlalmacen.model

/**
 * Created by ravi on 20/02/18.
 */
class Tipo {
    var id = 0
    var tipo: String? = null

    constructor() {}
    constructor(id: Int, tipo: String?) {
        this.id = id
        this.tipo = tipo
    }

    companion object {
        const val TABLE_NAME = "Tipo"
        const val COLUMN_ID = "id"
        const val COLUMN_TIPO = "tipo"
    }
}