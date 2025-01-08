package org.intissar.jasper2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase de arranque de la aplicación que gestiona la carga y despliegue de la interfaz gráfica.
 * Esta clase hereda de {@link Application} para iniciar la aplicación y mostrar la ventana principal.
 */
public class Main extends Application {

    /**
     * Metodo que inicializa y muestra la interfaz gráfica de usuario (GUI).
     * Carga el archivo FXML que define la interfaz y lo configura en una ventana {@link Stage}.
     *
     * @param stage El escenario principal donde se visualizará la interfaz de usuario.
     * @throws IOException Si ocurre un error al cargar el archivo FXML, como un archivo faltante o malformado.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML que define la interfaz
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/intissar/jasper2/hello-view.fxml"));
        // Crear una escena con el contenido cargado
        Scene scene = new Scene(fxmlLoader.load());
        // Establecer el título de la ventana principal
        stage.setTitle(" Informes");
        // Asignar la escena a la ventana
        stage.setScene(scene);
        // Mostrar la ventana
        stage.show();
    }

    /**
     * Metodo principal que inicia la aplicación. Llama al metodo {@link #start(Stage)}
     * para cargar y mostrar la interfaz gráfica en la ventana principal.
     *
     * @param args Los argumentos de la línea de comandos, si los hay.
     */
    public static void main(String[] args) {
        // Lanzar la aplicación
        launch();
    }
}
