package org.intissar.jasper2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.intissar.jasper2.BBDD.ConexionBBDD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador que maneja las acciones de la interfaz de usuario para generar informes usando JasperReports.
 * El controlador permite generar diferentes tipos de informes basados en la selección de los botones de opción.
 */
public class InterfazController {

    @FXML
    private Button btAceptar;

    @FXML
    private Button btCancelar;

    @FXML
    private ToggleGroup grupoIformes;

    @FXML
    private RadioButton rbPersonas;

    @FXML
    private RadioButton rbPCalculos;

    @FXML
    private RadioButton rbPSubinformes;

    /**
     * Genera un reporte basado en el archivo JasperReport especificado y los parámetros proporcionados.
     *
     * @param reportePath La ruta al archivo JasperReport.
     * @param parameters Los parámetros necesarios para la generación del reporte.
     */
    private void generarReporte(String reportePath, Map<String, Object> parameters) {
        try {
            // Crear una conexión a la base de datos
            ConexionBBDD db = new ConexionBBDD();

            // Cargar el archivo JasperReport
            InputStream reportStream = getClass().getResourceAsStream(reportePath);

            if (reportStream == null) {
                System.out.println("El archivo no se encuentra en la ruta: " + reportePath);
                return;
            }

            // Cargar el reporte y llenar el reporte con los parámetros
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConnection());

            // Mostrar el reporte en un visor de Jasper
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException | JRException e) {
            e.printStackTrace();
            mostrarError("Error en la base de datos", "No se pudo conectar a la base de datos o generar el reporte.");
        }
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
     *
     * @param titulo El título del cuadro de diálogo.
     * @param mensaje El mensaje que se mostrará en el cuadro de diálogo.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Maneja la acción del botón "Aceptar". Según el radio button seleccionado, genera el reporte correspondiente.
     *
     * @param event El evento generado al hacer clic en el botón "Aceptar".
     */
    @FXML
    void Aceptar(ActionEvent event) {
        // Verificar qué opción está seleccionada y generar el reporte adecuado
        if (rbPersonas.isSelected()) {
            generarReporte("/agenda.jasper", null);
        } else if (rbPCalculos.isSelected()) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IMAGE_PATH", getClass().getResource("../resources/img/agenda.png").toString());
            generarReporte("/agenda2.jasper", parameters);
        } else if (rbPSubinformes.isSelected()) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Resource_PATH", getClass().getResource("//").toString());
            generarReporte("/agenda3.jasper", parameters);
        }
    }

    /**
     * Maneja la acción del botón "Cancelar". Cierra la aplicación.
     *
     * @param event El evento generado al hacer clic en el botón "Cancelar".
     */
    @FXML
    void Cancelar(ActionEvent event) {
        // Cerrar la aplicación
        System.exit(0);
    }
}
