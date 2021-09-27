package com.example.examen2bimestre

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Ubicacion : AppCompatActivity() {
    private lateinit var mapa: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion)
        val tituloSucursal = intent.getStringExtra("nombre").toString()
        val txtTituloSucu = findViewById<TextView>(R.id.txt_NombreSucursalE)
        txtTituloSucu.text=tituloSucursal
        val latitud = intent.getDoubleExtra("latitud",0.0)
        val longitud = intent.getDoubleExtra("longitud",0.0)
        solicitarPermisos()
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync {
            mapa=it
            establecerConfiguracionMapa()

            val ubicacion = LatLng(latitud, longitud)
            val zoom = 12f
            anadirMarcador(ubicacion, tituloSucursal)
            moverCamaraConZoom(ubicacion, zoom)
        }
    }

    fun anadirMarcador(latLng: LatLng, title: String) {
        mapa.addMarker(MarkerOptions().position(latLng).title(title))
    }

    fun moverCamaraConZoom(latLng: LatLng, zoom: Float) {
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    fun establecerConfiguracionMapa() {
        val contexto = this.applicationContext
        with(mapa) {
            val permisosFineLocation = ContextCompat.checkSelfPermission(contexto, android.Manifest.permission.ACCESS_FINE_LOCATION)
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos) {
                mapa.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }


    fun solicitarPermisos() {
        val contexto = this.applicationContext
        val permisosFineLocation = ContextCompat.checkSelfPermission(contexto, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if (!tienePermisos) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), 1
            )
        }
    }
}