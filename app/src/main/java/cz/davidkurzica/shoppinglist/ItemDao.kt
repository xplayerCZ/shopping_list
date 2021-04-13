package cz.davidkurzica.shoppinglist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): List<Item>

    @Query("SELECT * FROM item WHERE iid IN (:itemIds)")
    fun loadAllByIds(itemIds: IntArray): List<Item>

    @Query("SELECT * FROM item WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Item

    @Insert
    fun insertAll(vararg users: Item)

    @Delete
    fun delete(item: Item)
}