package com.example.mislugares.Modelos;

import com.example.mislugares.App.MyRealmConfig;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase Lugar
 */

// Voy a usar notaciones lombok, mira el Readme.
@Data
@Builder
@Getter
@Setter
public class Lugar extends RealmObject {

    @PrimaryKey  // Clave primaria
    private long id;
    @Required // Campo requerido
    private String nombre;
    @Required
    private String tipo;
    @Required
    private String fecha;
    private float latitud;
    private float longitud;
    private String imagen;


    // Cada vez que creemos uno, creamos la clave
    public Lugar() {
        this.id = MyRealmConfig.LugarID.incrementAndGet();
    }

    /**
     * Constructor de la clase Lugar
     *
     * @param id       Identificador, ID
     * @param nombre   Nombre de Lugar
     * @param tipo     Tipo de Lugar
     * @param fecha    Fecha de Lugar
     * @param latitud  Latitud del Lugar
     * @param longitud Longitud de Lugar
     * @param imagen   Imagen de Lugar
     */
    public Lugar(long id, String nombre, String tipo, String fecha, float latitud, float longitud, String imagen) {
        this.id = MyRealmConfig.LugarID.incrementAndGet();
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
        this.latitud = latitud;
        this.longitud = longitud;
        this.imagen = imagen;
    }

    public long getId() {
        return id;
    }

}
