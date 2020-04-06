package com.example.mislugares.App;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.example.mislugares.Modelos.Lugar;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

import java.util.concurrent.atomic.AtomicLong;

// Esta clase, es una clase que se carga al comienzo de la palicación y con ella
// podemos usarla para crear y cargar la base de datos REALM que vayamos a usar
// y ahorranos inicializarla en todo momento
// Actúa como un singletçon de configuracióin
public class MyConfig extends Application {
    public static AtomicLong LugarID = new AtomicLong();


    @Override
    public void onCreate() {
        super.onCreate();
        // Cargamos las preferencias o escribimos las prefernecias pordefector
        prefreneciasApp();
        // Configuramos todo lo relacionado con REalmBD
        realmBD();

    }

    private void prefreneciasApp() {
        // Vamos a simular que el RSS está en una proferencia, podríamos meter más...
        // Las meto en modo provivado
        SharedPreferences prefs =
                getSharedPreferences("MisLugares", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("direccionRSS", "http://ep00.epimg.net/rss/tags/ultimas_noticias.xml");
        editor.commit();

        // Aquí metemos todo lo que queramos de configuraciones
    }

    private void realmBD() {
        //Si queremos usar otra configuración que nos ea la de por defecto, hacemos
        initRealm();
        // Cargamos la instancia
        Realm realm = Realm.getDefaultInstance();
        //Para tener el id siempre en linea
        LugarID = getIdByTable(realm, Lugar.class);
        // La cerramos
        realm.close();
    }

    // Para cofigurar la base de datos a nuestra manera
    private void initRealm() {
        // Podemos incluso cifrar el fichero
        //byte[] key = new byte[64];
        //new SecureRandom().nextBytes(key);
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1) // Versión de esquema estamos trabajando, si lo cambiamos, debemos incrementar
                .deleteRealmIfMigrationNeeded() // Podemos borrar los datos que ya haya si cambiamos el esquema
                //.encryptionKey(key) // Encruptada
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        // La boro todo lo que tenga para pruebas
        //Realm.deleteRealm(realmConfiguration);
    }
    // Para simular y crear la llaves autonumericas, el incremento de estas.
    private <T extends RealmObject> AtomicLong getIdByTable(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicLong(results.max("id").intValue()) : new AtomicLong();
    }
}