package com.example.todoapplication

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TodoData")
class TodoData() : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "date")
    var date: String? = ""

    @ColumnInfo(name = "time")
    var time: String? = ""

    @ColumnInfo(name = "title")
    var title: String? = ""

    @ColumnInfo(name = "description")
    var description: String? = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        date = parcel.readString()
        time = parcel.readString()
        title = parcel.readString()
        description = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodoData> {
        override fun createFromParcel(parcel: Parcel): TodoData {
            return TodoData(parcel)
        }

        override fun newArray(size: Int): Array<TodoData?> {
            return arrayOfNulls(size)
        }
    }

}