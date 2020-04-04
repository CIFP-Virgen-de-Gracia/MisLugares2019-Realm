package com.example.mislugares.App;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

// Esta clase, es una clase que se carga al comienzo de la palicación y con ella
// podemos usarla para crear y cargar la base de datos REALM que vayamos a usar
// y ahorranos inicializarla en todo momento
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Realm.init(getApplicationContext());

        //Si queremos usar otra configuración que nos ea la de por defecto, hacemos
        initRealm();

        // Cargamos la instancia
        Realm realm = Realm.getDefaultInstance();
        // La cerramos
        realm.close();


    }

    private void initRealm() {
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1) // Versión de esquema estamos trabajando, si lo cambiamos, debemos incrementar
                .deleteRealmIfMigrationNeeded() // Podemos borrar los datos que ya haya si cambiamos el esquema
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}