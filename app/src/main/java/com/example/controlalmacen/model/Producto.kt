package com.example.controlalmacen.model

/**
 * Created by ravi on 20/02/18.
 */
class Producto {
    var id = 0
    var nombre: String? = null
    var precio: String? = null
    var descripcion: String? = null
    var foto: String? = null
    var stock: String? = null
    var estado: String? = null
    var categoria: String? = null
    var fkCategoria: String? = null

    constructor() {}
    constructor(
        id: Int, nombre: String?, precio: String?, descripcion: String?, foto: String?,
        stock: String?, estado: String?, categoria: String?, fkcategoria: String?
    ) {
        this.id = id
        this.nombre = nombre
        this.precio = precio
        this.descripcion = descripcion
        this.foto = foto
        this.stock = stock
        this.estado = estado
        this.categoria = categoria
        fkCategoria = fkcategoria
    }

    companion object {
        const val TABLE_NAME = "Producto"
        const val COLUMN_ID = "idc"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_DESCRIPCION = "descripcion"
        const val COLUMN_FOTO = "foto"
        const val COLUMN_STOCK = "stock"
        const val COLUMN_ESTADO = "estado"
        const val COLUMN_CATEGORIA = "categoria"
        const val COLUMN_FKCATEGORIA = "fkcategoria"
    }
}