package com.example.controlalmacen.model

/**
 * Created by ravi on 20/02/18.
 */
class Categoria {
    var id = 0
    var categoria: String? = null

    constructor() {}
    constructor(id: Int, categoria: String?) {
        this.id = id
        this.categoria = categoria
    }

    companion object {
        const val TABLE_NAME = "Categoria"
        const val COLUMN_ID = "id"
        const val COLUMN_CATEGORIA = "categoria"
    }
}