import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class Simulacion {
    public int cantSimulaciones;
    public int contador;
    public int desde;    // MostrarDesde
    public int hasta;    // MostrarHasta
    private Fila fila;
    private ArrayList<Fila> filasMostrar;
    private Fila ultimaFila;
    private String html;

    public Simulacion(int cantSimulaciones, int contador, int desde, int cantFilas) throws IOException {
        this.cantSimulaciones = cantSimulaciones;
        this.contador = contador;
        this.desde = desde;
        this.hasta = cantFilas;
        this.filasMostrar = new ArrayList<Fila>();
        this.html = "";
        generarSimulacion();
    }

    public void generarSimulacion() throws IOException {

        for (int i = 0; i < cantSimulaciones; i++) {
            Fila filaAnterior;
            if (i == 0){
                filaAnterior = new Fila();
                this.fila = new Fila(filaAnterior);

            } else {
                filaAnterior = fila;
                this.fila = new Fila(filaAnterior);

            }
            if (i >= desde && i <= hasta){
                filasMostrar.add(fila);
                this.html = html + fila.toString2();
            }

            if (i == cantSimulaciones - 1){
                this.ultimaFila = fila;
            }
        }
        this.html = html + ultimaFila.toString3();
        crearHTML();
    }

    public void crearHTML() throws IOException {
        Writer wr2 = new FileWriter("src/prueba.html");
        String encabezados_compus = "";
        for (int i = 0; i < ultimaFila.getPacientes().size(); i++) {
            encabezados_compus = encabezados_compus + htmlCompu();
        }
        wr2.write(html1(encabezados_compus));
        wr2.write(html);
        wr2.flush();
        wr2.close();
    }

    public String html1(String encabezados_compus){
        String cadena = "<html>\n" +
                "\t<head>\n" +
                "\t\t<title>Sala de Urgencias</title>\n" +
                "\t\t <link rel=\"shortcut icon\" href=\"computadora1.png\">\n" +
                "\t\t <link href=\"Style.css\" type=\"text/css\" rel=\"stylesheet\" media=\"\">\n" +
                "\t\t <link rel=\"shortcut icon\" href=\"stethoscope.png\">" +
                "\t</head>\n" +
                "\t\n" +
                "\t<body>\n" +
                "\n" +
                "\t\t<table class=\"tabla71\">\n" +
                "\t\t\t<thead>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<th class='reloj'>Nro Fila</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Evento</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Reloj</th>\n" +
                "\t\t\t\t\t<th>RND Llegada Paciente</th>\n" +
                "\t\t\t\t\t<th>Tiempo entre Llegadas</th>\n" +
                "\t\t\t\t\t<th>Proxima Llegada Paciente</th>\n" +
                "\t\t\t\t\t<th>RND Atencion Enfermero</th>\n" +
                "\t\t\t\t\t<th>Tiempo de Atencion</th>\n" +
                "\t\t\t\t\t<th>Fin Atencion Enfermero</th>\n" +
                "\t\t\t\t\t<th>Tiempo de Atencion</th>\n" +
                "\t\t\t\t\t<th>Tiempo de Calmante</th>\n" +
                "\t\t\t\t\t<th>Fin Atencion Medico 1</th>\n" +
                "\t\t\t\t\t<th>Fin Atencion Medico 2</th>\n" +
                "\t\t\t\t\t<th class='objetospermanentes'>Enfermero</th>\n" +
                "\t\t\t\t\t<th class='objetospermanentes'>Cola Enfermero</th>\n" +
                "\t\t\t\t\t<th class='objetospermanentes'>Estado Medico 1</th>\n" +
                "\t\t\t\t\t<th class='objetospermanentes'>Tiempo Remanente 1</th>\n" +
                "\t\t\t\t\t<th class='objetospermanentes'>Estado Medico 2</th>\n" +
                "\t\t\t\t\t<th class='objetospermanentes'>Tiempo Remanente 2</th>\n" +
                "\t\t\t\t\t<th class='objetospermanentes'>Cola Urgencia</th>\n" +
                "\t\t\t\t\t<th class='objetospermanentes'>Cola Comun</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Cant Pacientes Cola</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Max Pacientes Cola</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Tiempo Espera Urgente</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Tiempo Max Espera Urgente</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Cant Pacientes Comunes</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Ac Tiempo Espera</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Promedio Tiempo Espera</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Cant Urgencias</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Cant Total Pacientes</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Porcentaje Urgencias</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Ac Ocupacion Enfermero</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Porcentaje Ocupacion Enfermero</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Ac Ocupacion Medico 1</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Porcentaje Ocupacion Medico 1</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Ac Ocupacion Medico 2</th>\n" +
                "\t\t\t\t\t<th class='reloj'>Porcentaje Ocupacion Medico 2</th>\n" +
                encabezados_compus +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</thead>\n" +
                "\t\t\t\n" +
                "\t\t\t<tbody>\n";
        return cadena;
    }

    public String html2(Fila fila){
        String cadena = "\t\t\t</tbody>\n" +
                "\t\t\t\n" +
                fila.toString3() +
                "\t\t\t\n" +
                "\t\t</table>\n" +
                "\t</body>\n" +
                "\n" +
                "</html>";
        return cadena;
    }

    public String htmlCompu(){
        String cadena = "\t\t\t\t\t<th>Id Paciente</th>\n" +
                "\t\t\t\t\t<th>Estado</th>\n" +
                "\t\t\t\t\t<th>Tipo</th>\n" +
                "\t\t\t\t\t<th>Hora Inicio Espera</th>\n";
        return cadena;
    }
}
