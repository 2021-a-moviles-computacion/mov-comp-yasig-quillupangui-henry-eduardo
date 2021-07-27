package com.example.moviles_computacion_2021_b

import android.os.Parcel
import android.os.Parcelable

class BEntrenador(
    val nombre: String?,
    val descripcion: String?,
    val liga: DLiga?,
) :Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(DLiga::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel?, flag: Int) {
//        if(parcel != null){
//            parcel.writeString(nombre)
//            parcel.writeString(descripcion)
//        }
        parcel?.writeString(nombre)
        parcel?.writeString(descripcion)
        parcel?.writeParcelable(liga, flag)
    }

    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BEntrenador> {
        override fun createFromParcel(parcel: Parcel): BEntrenador {
            return BEntrenador(parcel)
        }

        override fun newArray(size: Int): Array<BEntrenador?> {
            return arrayOfNulls(size)
        }
    }
}