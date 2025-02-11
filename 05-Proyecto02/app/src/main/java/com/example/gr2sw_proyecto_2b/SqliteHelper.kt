package com.example.gr2sw_proyecto_2b

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class SqliteHelper(
    context: Context?
): SQLiteOpenHelper(
    context,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("PRAGMA foreign_keys = ON;")
        val scriptSQLCrearTablaUser =
            """
                CREATE TABLE USER(
                    id VARCHAR(100) PRIMARY KEY,
                    name VARCHAR(50),
                    email VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaUser)

        val scriptSQLCrearTablaProduct =
            """
                CREATE TABLE PRODUCTO(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(50),
                    price DECIMAL(5, 2),
                    categoria TEXT,
                    imagePath TEXT,
                    stock INTEGE
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaProduct)

        val scriptSQLCrearTablaCart =
            """
                CREATE TABLE CART(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    total DECIMAL(5, 2),
                    deliveryPayment DECIMAL(5, 2),
                    userId INTEGER,
                    FOREIGN KEY(userId) REFERENCES USER(id) ON DELETE CASCADE
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCart)

        val scriptSQLCrearTablaCartItem =
            """
                CREATE TABLE CARTITEM(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    quantity INTEGER,
                    cartId INTEGER,
                    productId INTEGER,
                    FOREIGN KEY(cartId) REFERENCES CART(id) ON DELETE CASCADE,
                    FOREIGN KEY(productId) REFERENCES PRODUCTO(id) ON DELETE CASCADE
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCartItem)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    //User
    fun crearUsuario(user: User) {
        val baseDatosEscritura = writableDatabase
        val scriptInsercion = "INSERT INTO USER(id, name, email) VALUES('${user.id}', '${user.displayName}', '${user.email}')"
        baseDatosEscritura.execSQL(scriptInsercion)
        baseDatosEscritura.close()
    }

    fun buscarUsuarioPorId(idUsuario: String): Boolean {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM USER WHERE id = '$idUsuario'"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        baseDatosLectura.close()
        return existeUsuario
    }


    //Product
    fun obtenerTodosLosProductos(): List<Product> {
        val listaProductos = mutableListOf<Product>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM PRODUCTO"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val name = resultadoConsultaLectura.getString(1)
                val price = resultadoConsultaLectura.getDouble(2)
                val categoria = resultadoConsultaLectura.getString(3)
                val imagePath = resultadoConsultaLectura.getString(4)
                val stock = resultadoConsultaLectura.getInt(5)

                val producto = Product(id, name, price, categoria, imagePath, stock)

                listaProductos.add(producto)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaProductos
    }

    fun obtenerProductoPorId(idProducto: Int): Product? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM PRODUCTO WHERE id = $idProducto"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            val id = resultadoConsultaLectura.getInt(0)
            val name = resultadoConsultaLectura.getString(1)
            val price = resultadoConsultaLectura.getDouble(2)
            val categoria = resultadoConsultaLectura.getString(3)
            val imagePath = resultadoConsultaLectura.getString(4)
            val stock = resultadoConsultaLectura.getInt(5)
            return Product(id, name, price, categoria, imagePath, stock)
        }
        return null
    }

    fun obtenerProductoPorCategoria(categoria: String): List<Product> {
        val listaProductos = mutableListOf<Product>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM PRODUCTO WHERE categoria = '$categoria'"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val name = resultadoConsultaLectura.getString(1)
                val price = resultadoConsultaLectura.getDouble(2)
                val categoria = resultadoConsultaLectura.getString(3)
                val imagePath = resultadoConsultaLectura.getString(4)
                val stock = resultadoConsultaLectura.getInt(5)
                listaProductos.add(Product(id, name, price, categoria, imagePath, stock))
            } while (resultadoConsultaLectura.moveToNext())
        }
        baseDatosLectura.close()
        return listaProductos
    }

    fun insertarProducto(product: Product) {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("name", product.name)
        valoresAGuardar.put("price", product.price)
        valoresAGuardar.put("categoria", product.categoria)
        valoresAGuardar.put("imagePath", product.imagePath)
        valoresAGuardar.put("stock", product.stock)
        val resultadosGuardar = baseDatosEscritura.insert(
            "PRODUCTO",
            null,
            valoresAGuardar
        )
    }

    fun actualizarProducto(product: Product) {
        val baseDatosEscritura = writableDatabase
        val scriptActualizacion = "UPDATE PRODUCTO SET name = '${product.name}', price = ${product.price}, categoria = '${product.categoria}', imagePath = '${product.imagePath}', stock = ${product.stock} WHERE id = ${product.id}"
        baseDatosEscritura.execSQL(scriptActualizacion)
        baseDatosEscritura.close()
    }

    fun eliminarProducto(idProducto: Int) {
        val baseDatosEscritura = writableDatabase
        val scriptEliminacion = "DELETE FROM PRODUCTO WHERE id = $idProducto"
        baseDatosEscritura.execSQL(scriptEliminacion)
        baseDatosEscritura.close()
    }

    //Cart
    fun obtenerTodosLosCarts(): List<Cart> {
        val listaCarts = mutableListOf<Cart>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM CART"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val total = resultadoConsultaLectura.getDouble(1)
                val deliveryPayment = resultadoConsultaLectura.getDouble(2)
                val userId = resultadoConsultaLectura.getInt(3)
                val state = resultadoConsultaLectura.getInt(4) == 1
                listaCarts.add(Cart(id, userId, total, deliveryPayment,state))
            } while (resultadoConsultaLectura.moveToNext())
        }
        baseDatosLectura.close()
        return listaCarts
    }

    fun obtenerCartPorState(state: Boolean): Cart? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM CART WHERE state = ${if (state) 1 else 0}"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            val id = resultadoConsultaLectura.getInt(0)
            val total = resultadoConsultaLectura.getDouble(1)
            val deliveryPayment = resultadoConsultaLectura.getDouble(2)
            val userId = resultadoConsultaLectura.getInt(3)
            return Cart(id, userId, total, deliveryPayment, state)
        }
        return null
    }

    fun insertarCart(cart: Cart) {
        val baseDatosEscritura = writableDatabase
        val scriptInsercion = "INSERT INTO CART(total, deliveryPayment, userId) VALUES(${cart.total}, ${cart.deliveryPayment}, ${cart.userId})"
        baseDatosEscritura.execSQL(scriptInsercion)
        baseDatosEscritura.close()
    }

    fun actualizarCart(cart: Cart) {
        val baseDatosEscritura = writableDatabase
        val scriptActualizacion = "UPDATE CART SET total = ${cart.total}, deliveryPayment = ${cart.deliveryPayment}, userId = ${cart.userId} WHERE id = ${cart.id}"
        baseDatosEscritura.execSQL(scriptActualizacion)
        baseDatosEscritura.close()
    }

    fun eliminarCart(idCart: Int) {
        val baseDatosEscritura = writableDatabase
        val scriptEliminacion = "DELETE FROM CART WHERE id = $idCart"
        baseDatosEscritura.execSQL(scriptEliminacion)
        baseDatosEscritura.close()
    }

    //CartItem
    fun obtenerTodosLosCartItems(): List<CartItem> {
        val listaCartItems = mutableListOf<CartItem>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM CARTITEM"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val quantity = resultadoConsultaLectura.getInt(1)
                val cartId = resultadoConsultaLectura.getInt(2)
                val productId = resultadoConsultaLectura.getInt(3)
                listaCartItems.add(CartItem(id, quantity, cartId, productId))
            } while (resultadoConsultaLectura.moveToNext())
        }
        baseDatosLectura.close()
        return listaCartItems
    }

    fun obtenerCartItemPorId(idCartItem: Int): CartItem? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM CARTITEM WHERE id = $idCartItem"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            val id = resultadoConsultaLectura.getInt(0)
            val quantity = resultadoConsultaLectura.getInt(1)
            val cartId = resultadoConsultaLectura.getInt(2)
            val productId = resultadoConsultaLectura.getInt(3)
            return CartItem(id, quantity, cartId, productId)
        }
        return null
    }

    fun insertarCartItem(cartItem: CartItem) {
        val baseDatosEscritura = writableDatabase
        val scriptInsercion = "INSERT INTO CARTITEM(quantity, cartId, productId) VALUES(${cartItem.quantity}, ${cartItem.cartId}, ${cartItem.productId})"
        baseDatosEscritura.execSQL(scriptInsercion)
        baseDatosEscritura.close()
    }

    fun actualizarCartItem(cartItem: CartItem) {
        val baseDatosEscritura = writableDatabase
        val scriptActualizacion = "UPDATE CARTITEM SET quantity = ${cartItem.quantity}, cartId = ${cartItem.cartId}, productId = ${cartItem.productId} WHERE id = ${cartItem.id}"
        baseDatosEscritura.execSQL(scriptActualizacion)
        baseDatosEscritura.close()
    }

    fun eliminarCartItem(idCartItem: Int) {
        val baseDatosEscritura = writableDatabase
        val scriptEliminacion = "DELETE FROM CARTITEM WHERE id = $idCartItem"
        baseDatosEscritura.execSQL(scriptEliminacion)
        baseDatosEscritura.close()
    }

    fun obtenerCartItemsPorCart(cartId: Int): List<CartItem> {
        val listaCartItems = mutableListOf<CartItem>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM CARTITEM WHERE cartId = ?"
        val parametroConsultaLectura = arrayOf(cartId.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametroConsultaLectura)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val quantity = resultadoConsultaLectura.getInt(1)
                val idCart = resultadoConsultaLectura.getInt(2)
                val productId = resultadoConsultaLectura.getInt(3)
                listaCartItems.add(CartItem(id, quantity, idCart, productId))
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaCartItems
    }

    fun obtenerCartItemsPorProduct(idProduct: Int): List<CartItem> {
        val listaCartItems = mutableListOf<CartItem>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM CARTITEM WHERE productId = $idProduct"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val quantity = resultadoConsultaLectura.getInt(1)
                val cartId = resultadoConsultaLectura.getInt(2)
                val productId = resultadoConsultaLectura.getInt(3)
                listaCartItems.add(CartItem(id, quantity, cartId, productId))
            } while (resultadoConsultaLectura.moveToNext())
        }
        baseDatosLectura.close()
        return listaCartItems
    }
}