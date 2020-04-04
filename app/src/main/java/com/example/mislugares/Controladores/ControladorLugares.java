package com.example.mislugares.Controladores;

import android.content.Context;
import android.util.Log;
import com.example.mislugares.Modelos.Lugar;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import java.util.List;


/**
 * Controlador de de Lugares
 */
public class ControladorLugares {

    // Hacemos un Singleton
    private static ControladorLugares instancia;
    private static Context context;
    private Realm realm;

    // Para filtrar las busquedas
    private static final int NADA = 10;
    private static final int NOMBRE_ASC = 11;
    private static final int NOMBRE_DESC = 12;
    private static final int TIPO_ASC = 13;
    private static final int TIPO_DESC = 14;
    private static final int FECHA_ASC = 15;
    private static final int FECHA_DESC = 16;


    private ControladorLugares() {

    }

    /**
     * Constructor mediante isntancia Singleton
     *
     * @param contexto Coontexto de la palicación
     * @return instancia de Controlador
     */
    public static ControladorLugares getControlador(Context contexto) {
        if (instancia == null) {
            instancia = new ControladorLugares();
        }
        //else{
        //    // Log.i("CL", "Usando el controlador Lugares existente ");
        //}
        context = contexto;
        return instancia;
    }

    /**
     * Lista todos los lugares almacenados en el sistema de almacenamiento
     *
     * @param tipoFiltro de ordenación
     * @return lista de lugares
     */
    public List<Lugar> listarLugares(int tipoFiltro) {
        // Abrimos la BD
        realm = Realm.getDefaultInstance();
        RealmResults<Lugar> listaLugares;
        // Obtenemos la lista. No la filtro aquí, porque así me ahorro consultarla cada vez que
        switch (tipoFiltro) {
            case NADA:
                listaLugares = realm.where(Lugar.class).sort("id").findAll();
                break;
            case NOMBRE_ASC:
                listaLugares = realm.where(Lugar.class).sort("nombre", Sort.ASCENDING).findAll();
                break;
            case NOMBRE_DESC:
                listaLugares = realm.where(Lugar.class).sort("nombre", Sort.DESCENDING).findAll();
                break;
            case TIPO_ASC:
                listaLugares = realm.where(Lugar.class).sort("tipo", Sort.ASCENDING).findAll();
                break;
            case TIPO_DESC:
                listaLugares = realm.where(Lugar.class).sort("tipo", Sort.DESCENDING).findAll();
                break;
            case FECHA_ASC:
                listaLugares = realm.where(Lugar.class).sort("fecha", Sort.ASCENDING).findAll();
                break;
            case FECHA_DESC:
                listaLugares = realm.where(Lugar.class).sort("fecha", Sort.DESCENDING).findAll();
                break;
            default:
                listaLugares = realm.where(Lugar.class).sort("id").findAll();
                break;
        }

        return realm.copyFromRealm(listaLugares);


    }
    // Manejar un CRUD
    // https://parzibyte.me/blog/2019/02/04/tutorial-sqlite-android-crud-create-read-update-delete/

    /**
     * Inserta un lugar en el sistema de almacenamiento
     *
     * @param lugar Lugar a insertar
     * @return verdadero si insertado
     */
    public boolean insertarLugar(Lugar lugar) {
        // se insertan sin problemas porque lugares es clave primaria, si ya están no hace nada
        // Abrimos la instancia de REALM
        realm = Realm.getDefaultInstance();
        // Escribimos https://realm.io/docs/java/6.0.2/#writes
        // O hacerlo con un hilo para hacerlo más concurrente


        realm.beginTransaction();
        boolean sal = false;
        try {
            // insertamos en su tabla, en long tenemos el id más alto creado
            realm.copyToRealm(lugar); // Copia, inserta
            sal = true;
        } catch (Exception ex) {
            Log.d("Lugares", "Error al insertar un nuevo lugar " + ex.getMessage());
        } finally {
            realm.commitTransaction();
            return sal;
        }

    }

    /**
     * Elimina un lugar del sistema de almacenamiento
     *
     * @param lugar Lugar a eliminar
     * @return veradero si elimina
     */
    public boolean eliminarLugar(Lugar lugar) {
        // Abrimos la instancia de REALM
        realm = Realm.getDefaultInstance();
        // Escribimos https://realm.io/docs/java/6.0.2/#writes
        // O hacerlo con un hilo para hacerlo más concurrente
        realm.beginTransaction();
        boolean sal = false;
        try {
            // Lo buscamos (campo, valor) y lo borramos
            Lugar l = realm.where(Lugar.class).equalTo("id", lugar.getId()).findFirst();
            l.deleteFromRealm();
            sal = true;
        } catch (Exception ex) {
            Log.d("Lugares", "Error al eliminar un nuevo lugar " + ex.getMessage());
        } finally {
            realm.commitTransaction();
            return sal;
        }

    }

    /**
     * Actualiza un lugar en el sistema de almacenamiento
     *
     * @param lugar Lugar a actualizar
     * @return verdadero si actualiza
     */
    public boolean actualizarLugar(Lugar lugar) {
        // Abrimos la instancia de REALM
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        boolean sal = false;
        try {
            //Cargamos los parámetros
            // insertamos en su tabla, en long tenemos el id más alto creado
            realm.copyToRealmOrUpdate(lugar);
            sal = true;
        } catch (Exception ex) {
            Log.d("Lugares", "Error al actualizar un nuevo lugar " + ex.getMessage());
        } finally {
            realm.commitTransaction();
            return sal;
        }

    }



}
