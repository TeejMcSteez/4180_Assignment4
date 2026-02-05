package com.example.assignment4

import android.os.Parcelable
import android.os.Parcel

data class Student(
    var name: String,
    var email: String,
    var suid: String,
    var role: String,
    var imageId: Int
) : Parcelable {
    constructor(parcel: Parcel): this (
        name = parcel.readString() ?: "",
        email = parcel.readString() ?: "",
        suid = parcel.readString() ?: "",
        role = parcel.readString() ?: "",
        imageId = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(suid)
        parcel.writeString(role)
        parcel.writeInt(imageId)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(source: Parcel) = Student(source)
        override fun newArray(size: Int) = arrayOfNulls<Student>(size)
    }
}