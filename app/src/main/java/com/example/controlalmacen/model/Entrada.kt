package com.example.controlalmacen.model


/**
 * Created by ravi on 20/02/18.
 */
class Entrada {
    var id = 0
    var cantidad: String? = null
    var fecha: String? = null
    var idProducto: String? = null
    var foto: String? = null
    var nombre: String? = null
    var idTipo: String? = null
    var tipo: String? = null

    constructor() {}
    constructor(
        id: Int,
        cantidad: String?,
        fecha: String?,
        idProducto: String?,
        foto: String?,
        nombre: String?,
        idTipo: String?,
        tipo: String?
    ) {
        this.id = id
        this.cantidad = cantidad
        this.fecha = fecha
        this.idProducto = idProducto
        this.foto = foto
        this.nombre = nombre
        this.idTipo = idTipo
        this.tipo = tipo
    }

    companion object {
        const val TABLE_NAME = "Entrada"
        const val COLUMN_ID = "idc"
        const val COLUMN_CANTIDAD = "cantidad"
        const val COLUMN_FECHA = "fecha"
        const val COLUMN_IDPRODUCTO = "idProducto"
        const val COLUMN_FOTO = "foto"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_IDTIPO = "idTipo"
        const val COLUMN_TIPO = "tipo"
    }
}