package org.intissar.jasper2.BBDD;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Maneja la conexión con la base de datos, permitiendo establecerla, obtenerla y cerrarla,
 * además de cargar la configuración necesaria desde un archivo de propiedades.
 */
public class ConexionBBDD {

    /** Instancia de conexión a la base de datos. */
    private static Connection connectionInstance;

    /**
     * Constructor encargado de establecer la conexión con la base de datos.
     * Lee las configuraciones desde un archivo de propiedades y establece la conexión
     * utilizando los parámetros especificados en dicho archivo.
     *
     * @throws SQLException Si hay un error al intentar conectarse a la base de datos,
     *                     por ejemplo, credenciales incorrectas o URL mal configurada.
     */
    public ConexionBBDD() throws SQLException {
        // Cargar las configuraciones desde el archivo de propiedades
        Properties config = loadDatabaseConfig();
        String databaseUrl = config.getProperty("dburl");

        try {
            // Establecer la conexión a la base de datos
            connectionInstance = DriverManager.getConnection(databaseUrl, config);
            connectionInstance.setAutoCommit(true);  // Habilitar autocommit por defecto
        } catch (SQLException e) {
            // Si ocurre un error al conectar, lanzar una excepción detallada
            throw new SQLException("Error al intentar establecer la conexión con la base de datos. " +
                    "Verifique las configuraciones en el archivo de propiedades.", e);
        }
    }

    /**
     * Obtiene la conexión activa a la base de datos.
     *
     * @return La conexión activa.
     */
    public static Connection getConnection() {
        return connectionInstance;
    }

    /**
     * Cierra la conexión con la base de datos si está activa.
     *
     * @return {@code null} una vez que la conexión haya sido cerrada.
     * @throws SQLException Si ocurre un error al cerrar la conexión,
     *                     como intentar cerrarla si ya está cerrada.
     */
    public Connection close() throws SQLException {
        if (connectionInstance != null && !connectionInstance.isClosed()) {
            connectionInstance.close();
            connectionInstance = null;  // Se establece como null una vez cerrada
        }
        return connectionInstance;
    }

    /**
     * Carga las configuraciones necesarias para la conexión desde un archivo de propiedades.
     * El archivo debe contener claves como {@code dburl}, que especifican los parámetros de conexión.
     *
     * @return Un objeto {@link Properties} con las configuraciones necesarias.
     * @throws RuntimeException Si ocurre un error al leer el archivo de propiedades,
     *                           como un archivo no encontrado o un formato incorrecto.
     */
    private static Properties loadDatabaseConfig() {
        try (FileInputStream fileStream = new FileInputStream("configuration.properties")) {
            Properties properties = new Properties();
            properties.load(fileStream);
            return properties;
        } catch (IOException e) {
            // Si el archivo no se puede cargar, lanzar una excepción personalizada
            throw new RuntimeException("No se pudo cargar el archivo de configuración", e);
        }
    }
}
