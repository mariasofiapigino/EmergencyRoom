import java.util.ArrayList;

public class Fila {
    private Fila filaAnterior;
    private int nroFila;

    private String evento;
    private float reloj;

    private float random_llegada;
    private float tiempo_entre_llegadas;
    private float proxima_llegada_paciente;

    private float random_atencion_enfermero;
    private float tiempo_atencion_enfermero;
    private float fin_atencion_enfermero;

    private float tiempo_atencion_medico;
    private float fin_atencion_medico1;
    private float fin_atencion_medico2;

    private String enfermero;
    private int cola_enfermero;

    private float tiempo_calmante;
    private String medico1;
    private float tiempo_remanente1;
    private String medico2;
    private float tiempo_remanente2;
    private int cola_urgencia;
    private int cola_comun;


    private ArrayList<Paciente> pacientes;
    private String tipo_paciente;

    private String proximo_evento;
    private float proximo_reloj;

    // Metricas
    private int cant_pacientes_cola;
    private float max_pacientes_cola;
    private float tiempo_espera_urgente;
    private float tiempo_max_espera_urgente;
    private int cant_pacientes_comunes;
    private float ac_tiempo_espera;
    private float promedio_espera_comun;
    private int cant_urgencias;
    private int cant_total_pacientes;
    private float porcentaje_urgencias;
    private float ac_ocupacion_enfermero;
    private float porcentaje_ocupacion_enfermero;
    private float ac_ocupacion_medico1;
    private float porcentaje_ocupacion_medico1;
    private float ac_ocupacion_medico2;
    private float porcentaje_ocupacion_medico2;

    public Fila() {
        this.proximo_reloj = 0;
        this.proximo_evento = "Llegada Paciente";
        this.proxima_llegada_paciente = 0;
        this.pacientes = new ArrayList<>();
        this.cola_enfermero = 0;
        this.nroFila = -1;
        this.tipo_paciente = "";
        this.enfermero = "Libre";
        this.medico1 = "Libre";
        this.medico2 = "Libre";
        this.tiempo_calmante = 0;
        this.max_pacientes_cola = 0;
        this.tiempo_max_espera_urgente = 0;
        this.ac_tiempo_espera = 0;
        this.cant_pacientes_comunes = 0;
        this.promedio_espera_comun = 0;
        this.cant_total_pacientes = 0;
        this.cant_urgencias = 0;
        this.porcentaje_urgencias = 0;
        this.ac_ocupacion_enfermero = 0;
        this.ac_ocupacion_medico1 = 0;
        this.ac_ocupacion_medico2 = 0;
        this.porcentaje_ocupacion_enfermero = 0;
        this.porcentaje_ocupacion_medico1 = 0;
        this.porcentaje_ocupacion_medico2 = 0;
    }

    public Fila(Fila filaAnterior){
        this.nroFila = filaAnterior.getNroFila() + 1;
        this.filaAnterior = filaAnterior;
        this.pacientes = filaAnterior.getPacientes();
        this.cola_enfermero = filaAnterior.getCola_enfermero();
        this.evento = filaAnterior.proximo_evento;
        this.reloj  = filaAnterior.proximo_reloj;
        this.enfermero = filaAnterior.enfermero;
        this.proxima_llegada_paciente = filaAnterior.getProxima_llegada_paciente();
        this.fin_atencion_enfermero = filaAnterior.getFin_atencion_enfermero();
        this.tipo_paciente = "";
        this.medico1 = filaAnterior.getMedico1();
        this.medico2 = filaAnterior.getMedico2();
        this.cola_comun = filaAnterior.getCola_comun();
        this.cola_urgencia = filaAnterior.getCola_urgencia();
        this.fin_atencion_medico1 = filaAnterior.getFin_atencion_medico1();
        this.fin_atencion_medico2 = filaAnterior.getFin_atencion_medico2();
        this.tiempo_remanente1 = filaAnterior.getTiempo_remanente1();
        this.tiempo_remanente2 = filaAnterior.getTiempo_remanente2();
        this.max_pacientes_cola = filaAnterior.getMax_pacientes_cola();
        this.tiempo_max_espera_urgente = filaAnterior.getTiempo_max_espera_urgente();
        this.ac_tiempo_espera = filaAnterior.getAc_tiempo_espera();
        this.cant_pacientes_comunes = filaAnterior.getCant_pacientes_comunes();
        this.cant_urgencias = filaAnterior.getCant_urgencias();
        this.cant_total_pacientes = filaAnterior.getCant_total_pacientes();
        this.porcentaje_urgencias = filaAnterior.getPorcentaje_urgencias();
        this.ac_ocupacion_enfermero = filaAnterior.getAc_ocupacion_enfermero();
        this.ac_ocupacion_medico1 = filaAnterior.getAc_ocupacion_medico1();
        this.ac_ocupacion_medico2 = filaAnterior.getAc_ocupacion_medico2();
        this.porcentaje_ocupacion_enfermero = filaAnterior.getPorcentaje_ocupacion_enfermero();
        this.porcentaje_ocupacion_medico1 = filaAnterior.getPorcentaje_ocupacion_medico1();
        this.porcentaje_ocupacion_medico2 = filaAnterior.getPorcentaje_ocupacion_medico2();


        if(nroFila == 0){
            this.evento = "Inicializacion";
            generarFila(evento);
        } else{
            generarFila(filaAnterior.proximo_evento);
        }
    }

    private void generarFila(String eventoFila) {
        switch (eventoFila){
            case "Inicializacion":
                generarProximaLlegadaPaciente();
                this.proximo_evento = "Llegada Paciente";
                this.proximo_reloj = proxima_llegada_paciente;
                break;

            case "Llegada Paciente":
                generarProximaLlegadaPaciente();
                if (enfermero.equals("Libre")){
                    crearPaciente();
                }
                else {
                    crearPacienteCola();
                    cola_enfermero++;
                }
                elegirProximoEvento();
                // Metricas
                calcularMaxCola();
                calcularMaxEsperaUrgente();
                calcularPromedioEsperaComun();
                acumularOcupacion();
                calcularPorcentajeOcupacion();
                break;

            case "Fin Atencion Enfermero":
                listoPacienteEnfermeria();
                revisarColaEnfermero();
                elegirProximoEvento();
                // Metricas
                calcularMaxCola();
                calcularMaxEsperaUrgente();
                calcularPromedioEsperaComun();
                acumularOcupacion();
                calcularPorcentajeOcupacion();
                break;

            case "Fin Atencion Medico 1":
                listoPacienteMedico(1);
                revisarColaMedico(1);
                elegirProximoEvento();
                // Metricas
                calcularMaxCola();
                calcularMaxEsperaUrgente();
                calcularPromedioEsperaComun();
                contarPacientes();
                calcularPorcentajeUrgencias();
                acumularOcupacion();
                calcularPorcentajeOcupacion();
                break;

            case "Fin Atencion Medico 2":
                listoPacienteMedico(2);
                revisarColaMedico(2);
                elegirProximoEvento();
                // Metricas
                calcularMaxCola();
                calcularMaxEsperaUrgente();
                calcularPromedioEsperaComun();
                contarPacientes();
                calcularPorcentajeUrgencias();
                acumularOcupacion();
                calcularPorcentajeOcupacion();
                break;
        }
        noMostrarDestruidos();
    }


    private void generarProximaLlegadaPaciente() {
        this.random_llegada = (float) Math.random();
        this.tiempo_entre_llegadas = (float)(-5 * Math.log(1 - random_llegada));
        this.proxima_llegada_paciente = tiempo_entre_llegadas + reloj;
    }

    // Crea el paciente cuando el enfermero esta libre. Asigna estado "En Enfermeria" al paciente, asigna estado
    // "Ocupado" al enfermero, añade el paciente en el vector "pacientes" y setea el Fin Atencion Enfermero
    private void crearPaciente() {
        Paciente paciente = new Paciente();
        paciente.setEstado("En Enfermeria");
        enfermero = "Ocupado";
        pacientes.add(paciente);
        finAtencionEnfermero();
    }

    private void finAtencionEnfermero() {
        this.random_atencion_enfermero = (float) Math.random();
        this.tiempo_atencion_enfermero = (float) (0.05 + random_atencion_enfermero * (0.28 - 0.05));
        this.fin_atencion_enfermero = reloj + tiempo_atencion_enfermero;
    }

    // Crea el paciente en cola, la diferencia con el anterior es que asgina el estado "En Cola Enfermeria", no cambia
    // el estado del Enfermero y no genera el Fin Atencion Enfermero
    private void crearPacienteCola() {
        Paciente paciente = new Paciente();
        paciente.setEstado("En Cola Enfermeria");
        pacientes.add(paciente);
    }

    // Elige entre los eventos posibles al mas cercano, distinto de cero y mayor al reloj
    private void elegirProximoEvento() {
        proximo_evento = "Llegada Paciente";
        proximo_reloj = proxima_llegada_paciente;
        if (fin_atencion_enfermero < proximo_reloj  && fin_atencion_enfermero != 0 && fin_atencion_enfermero > reloj) {
            proximo_evento = "Fin Atencion Enfermero";
            proximo_reloj = fin_atencion_enfermero;
        }
        if (fin_atencion_medico1 < proximo_reloj && fin_atencion_medico1 != 0 && fin_atencion_medico1 > reloj) {
            proximo_evento = "Fin Atencion Medico 1";
            proximo_reloj = fin_atencion_medico1;
        }
        if (fin_atencion_medico2 < proximo_reloj && fin_atencion_medico2 != 0 && fin_atencion_medico2 > reloj) {
            proximo_evento = "Fin Atencion Medico 2";
            proximo_reloj = fin_atencion_medico2;
        }
    }

    // Libera al paciente del Enfermero y se le setea el "Tipo de Paciente" (Lo seteo aca porque considero que
    // una vez finalizado el analisis del enfermero, sabemos si es "Urgente" o "Comun".
    // Tambien, pasa a "Libre" el Enfermero
    private void listoPacienteEnfermeria() {
        for (Paciente paciente : pacientes) {
            if (paciente.getEstado().equals("En Enfermeria")) {
                paciente.setTipoPaciente();
                revisarPrimerosAuxilios(paciente);
                break;
            }
        }
        fin_atencion_enfermero = 0;
        enfermero = "Libre";
    }

    private void revisarPrimerosAuxilios(Paciente paciente) {
        if (medico1.equals("Libre")) {
            this.medico1 = "Atendiendo Comun";
            paciente.setEstado("En Primeros Auxilios 1");
            finAtencionMedico(1);
            if (paciente.getTipoPaciente().equals("Urgente")) {
                tiempo_calmante = (float) 3.9;
                fin_atencion_medico1 += tiempo_calmante;
                this.medico1 = "Atendiendo Urgencia";
            } else {
                // Cuento los pacientes comunes
                cant_pacientes_comunes += 1;
            }
        }
        else {
            if (medico2.equals("Libre")){
                this.medico2 = "Atendiendo Comun";
                paciente.setEstado("En Primeros Auxilios 2");
                finAtencionMedico(2);
                if (paciente.getTipoPaciente().equals("Urgente")) {
                    tiempo_calmante = (float) 3.9;
                    fin_atencion_medico2 += tiempo_calmante;
                    this.medico2 = "Atendiendo Urgencia";
                } else {
                    // Cuento los pacientes comunes
                    cant_pacientes_comunes += 1;
                }
            }
            else {
                if (paciente.getTipoPaciente().equals("Urgente")){
                    for (Paciente interrumpir : pacientes) {
                        // En el caso de ser de tipo "Urgente" quien esta siendo atendido, no se debe interrumpir
                        if (interrumpir.getEstado().equals("En Primeros Auxilios 1") && interrumpir.getTipoPaciente().equals("Comun")) {
                            interrumpir.setEstado("Interrumpido 1");
                            calcularTiempoRemanente(1);
                            paciente.setEstado("En Primeros Auxilios 1");
                            finAtencionMedico(1);
                            tiempo_calmante = (float) 3.9;
                            fin_atencion_medico1 += tiempo_calmante;
                            return;
                        }
                        if (interrumpir.getEstado().equals("En Primeros Auxilios 2") && interrumpir.getTipoPaciente().equals("Comun")) {
                            interrumpir.setEstado("Interrumpido 2");
                            calcularTiempoRemanente(2);
                            paciente.setEstado("En Primeros Auxilios 2");
                            finAtencionMedico(2);
                            tiempo_calmante = (float) 3.9;
                            fin_atencion_medico2 += tiempo_calmante;
                            return;
                        }
                    }
                    this.cola_urgencia++;
                    paciente.setEstado("Esperando Primeros Auxilios");
                    paciente.setTiempo_inicio_espera(reloj);
                } else {
                    this.cola_comun++;
                    paciente.setEstado("Esperando Primeros Auxilios");
                    paciente.setTiempo_inicio_espera(reloj);
                }
            }
        }
    }

    private void finAtencionMedico(int i) {
        this.tiempo_atencion_medico = convolucion();
        if (i == 1) { fin_atencion_medico1 = tiempo_atencion_medico + reloj; }
        else { fin_atencion_medico2 = tiempo_atencion_medico + reloj; }
    }

    private float convolucion() {
        float sumatoria = 0;
        for (int j = 0; j < 12; j++) {
            sumatoria += Math.random();
        }
        float resultado = (sumatoria - 6) * 4 + 10;
        if (resultado < 0) { resultado = convolucion();}
        return resultado;
    }

    private void calcularTiempoRemanente(int i) {
        if (i == 1){
            this.tiempo_remanente1 = fin_atencion_medico1 - reloj;
            medico1 = "Atendiendo Urgencia";
        }
        else {
            this.tiempo_remanente2 = fin_atencion_medico2 - reloj;
            medico2 = "Atendiendo Urgencia";
        }
    }

    // En caso de existir cola en el Enfermero, atiende a esos Pacientes en orden de llegada
    private void revisarColaEnfermero() {
        if (cola_enfermero > 0) {
            for (Paciente paciente: pacientes) {
                if (paciente.getEstado().equals("En Cola Enfermeria")){
                    paciente.setEstado("En Enfermeria");
                    enfermero = "Ocupado";
                    finAtencionEnfermero();
                    break;
                }
            }
            cola_enfermero--;
        }
    }

    // Una vez finalizada la atencion del medico, el paciente pasa a "Destruido"
    private void listoPacienteMedico(int i) {
        for (Paciente paciente: pacientes) {
            if (paciente.getEstado().equals("En Primeros Auxilios 1") && i == 1){
                paciente.setEstado("Destruido");
                medico1 = "Libre";
                fin_atencion_medico1 = 0;
                return;
            }
            if (paciente.getEstado().equals("En Primeros Auxilios 2") && i == 2){
                paciente.setEstado("Destruido");
                medico2 = "Libre";
                fin_atencion_medico2 = 0;
                return;
            }
        }
    }

    // Para saber que tiene que hacer el medico cuando termina una atencion (Orden de prioridad: cola urgente, interrumpido, cola comun)
    private void revisarColaMedico(int i) {
        // Primero, cola de urgencias
        if (cola_urgencia > 0) {
            for (Paciente paciente : pacientes) {
                if (paciente.getEstado().equals("Esperando Primeros Auxilios") && paciente.getTipoPaciente().equals("Urgente")){
                    paciente.setEstado("En Primeros Auxilios " + i);
                    tiempo_espera_urgente = reloj - paciente.getTiempo_inicio_espera();
                    paciente.setTiempo_inicio_espera(0);
                    finAtencionMedico(i);
                    tiempo_calmante = (float) 3.9;
                    fin_atencion_medico1 += tiempo_calmante;
                    cola_urgencia--;
                    if (i == 1) { medico1 = "Atendiendo Urgencia"; }
                    else { medico2 = "Atendiendo Urgencia"; }
                    return;
                }
            }
        } else {
            // Segundo, los pacientes comunes interrumpidos
            for (Paciente paciente : pacientes) {
                if (paciente.getEstado().equals("Interrumpido 1") && i == 1) {
                    paciente.setEstado("En Primeros Auxilios 1");
                    fin_atencion_medico1 = tiempo_remanente1 + reloj;
                    tiempo_remanente1 = 0;
                    medico1 = "Atendiendo Comun";
                    return;
                }
                else {
                    if (paciente.getEstado().equals("Interrumpido 2") && i == 2) {
                        paciente.setEstado("En Primeros Auxilios 2");
                        fin_atencion_medico2 = tiempo_remanente2 + reloj;
                        tiempo_remanente2 = 0;
                        medico2 = "Atendiendo Comun";
                        return;
                    }
                    else {
                        // Tercero, cola de comunes
                        if (paciente.getEstado().equals("Esperando Primeros Auxilios") && paciente.getTipoPaciente().equals("Comun") && i == 1){
                            paciente.setEstado("En Primeros Auxilios 1");
                            finAtencionMedico(1);
                            ac_tiempo_espera += reloj - paciente.getTiempo_inicio_espera();
                            paciente.setTiempo_inicio_espera(0);
                            cant_pacientes_comunes += 1;
                            cola_comun--;
                            medico1 = "Atendiendo Comun";
                            return;
                        }
                        if (paciente.getEstado().equals("Esperando Primeros Auxilios") && paciente.getTipoPaciente().equals("Comun") && i == 2){
                            paciente.setEstado("En Primeros Auxilios 2");
                            finAtencionMedico(2);
                            ac_tiempo_espera += reloj - paciente.getTiempo_inicio_espera();
                            paciente.setTiempo_inicio_espera(0);
                            cant_pacientes_comunes += 1;
                            cola_comun--;
                            medico2 = "Atendiendo Comun";
                            return;
                        }
                    }
                }
            }
            // Si todas las otras condiciones no se cumplen
            if (i == 1) medico1 = "Libre";
            if (i == 2) medico2 = "Libre";
        }
    }

    // Para no mostrar muchas veces un paciente ya destruido
    private void noMostrarDestruidos() {
        for (Paciente paciente : this.pacientes) {
            if (paciente.getEstado().equals("Destruido")){
                paciente.setMostrado(paciente.getMostrado() + 1);
            }
        }
    }




    // Metricas
    private void calcularMaxCola() {
        this.cant_pacientes_cola = cola_comun + cola_urgencia;
        if (max_pacientes_cola < cant_pacientes_cola) max_pacientes_cola = cant_pacientes_cola;
    }

    private void calcularMaxEsperaUrgente() {
        if (tiempo_max_espera_urgente < tiempo_espera_urgente) tiempo_max_espera_urgente = tiempo_espera_urgente;
    }

    private void calcularPromedioEsperaComun() {
        if (cant_pacientes_comunes != 0) this.promedio_espera_comun = ac_tiempo_espera / cant_pacientes_comunes;
    }

    private void contarPacientes() {
        for (Paciente paciente : pacientes) {
            if (paciente.getEstado().equals("Destruido") && paciente.getMostrado() < 1) {
                if (paciente.getTipoPaciente().equals("Urgente")) {
                    this.cant_urgencias ++;
                }
                cant_total_pacientes ++;
                break;
            }
        }
    }

    private void calcularPorcentajeUrgencias() {
        this.porcentaje_urgencias = (float) cant_urgencias / (float) cant_total_pacientes * 100;
    }

    private void acumularOcupacion() {
        if (filaAnterior.getEnfermero().equals("Ocupado")) this.ac_ocupacion_enfermero += reloj - filaAnterior.getReloj();
        if (filaAnterior.getMedico1().equals("Atendiendo Urgencia") || filaAnterior.getMedico1().equals("Atendiendo Comun")) this.ac_ocupacion_medico1 += reloj - filaAnterior.getReloj();
        if (filaAnterior.getMedico2().equals("Atendiendo Urgencia") || filaAnterior.getMedico2().equals("Atendiendo Comun")) this.ac_ocupacion_medico2 += reloj - filaAnterior.getReloj();
    }

    private void calcularPorcentajeOcupacion() {
        this.porcentaje_ocupacion_enfermero = ac_ocupacion_enfermero / reloj * 100;
        this.porcentaje_ocupacion_medico1 = ac_ocupacion_medico1 / reloj * 100;
        this.porcentaje_ocupacion_medico2 = ac_ocupacion_medico2 / reloj * 100;
    }


    // toString
    public String toString2() {
        String cadena = "";
        cadena = "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>" + nroFila + "</td>\n" +
                "\t\t\t\t\t<td>" + evento + "</td>\n" +
                "\t\t\t\t\t<td>" + reloj + "</td>\n" +
                "\t\t\t\t\t<td>" + random_llegada + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_entre_llegadas + "</td>\n" +
                "\t\t\t\t\t<td class='evento'>" + proxima_llegada_paciente + "</td>\n" +
                "\t\t\t\t\t<td>" + random_atencion_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_atencion_enfermero + "</td>\n" +
                "\t\t\t\t\t<td class='evento'>" + fin_atencion_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_atencion_medico + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_calmante + "</td>\n" +
                "\t\t\t\t\t<td class='evento'>" + fin_atencion_medico1 + "</td>\n" +
                "\t\t\t\t\t<td class='evento'>" + fin_atencion_medico2 + "</td>\n" +
                "\t\t\t\t\t<td>" + enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + cola_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + medico1 + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_remanente1 + "</td>\n" +
                "\t\t\t\t\t<td>" + medico2 + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_remanente2 + "</td>\n" +
                "\t\t\t\t\t<td>" + cola_urgencia + "</td>\n" +
                "\t\t\t\t\t<td>" + cola_comun + "</td>\n" +
                "\t\t\t\t\t<td>" + cant_pacientes_cola + "</td>\n" +
                "\t\t\t\t\t<td>" + max_pacientes_cola + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_espera_urgente + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_max_espera_urgente + "</td>\n" +
                "\t\t\t\t\t<td>" + cant_pacientes_comunes + "</td>\n" +
                "\t\t\t\t\t<td>" + ac_tiempo_espera + "</td>\n" +
                "\t\t\t\t\t<td>" + promedio_espera_comun + "</td>\n" +
                "\t\t\t\t\t<td>" + cant_urgencias + "</td>\n" +
                "\t\t\t\t\t<td>" + cant_total_pacientes + "</td>\n" +
                "\t\t\t\t\t<td>" + porcentaje_urgencias + "</td>\n" +
                "\t\t\t\t\t<td>" + ac_ocupacion_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + porcentaje_ocupacion_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + ac_ocupacion_medico1 + "</td>\n" +
                "\t\t\t\t\t<td>" + porcentaje_ocupacion_medico1 + "</td>\n" +
                "\t\t\t\t\t<td>" + ac_ocupacion_medico2 + "</td>\n" +
                "\t\t\t\t\t<td>" + porcentaje_ocupacion_medico2 + "</td>\n" +
                toStringPacientes() + "\n" +
                "\t\t\t\t</tr>\n";
        return cadena;
    }

    public String toString3(){
        String cadena = "\t\t\t<tfoot>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>" + nroFila + "</td>\n" +
                "\t\t\t\t\t<td>" + evento + "</td>\n" +
                "\t\t\t\t\t<td>" + reloj + "</td>\n" +
                "\t\t\t\t\t<td>" + random_llegada + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_entre_llegadas + "</td>\n" +
                "\t\t\t\t\t<td>" + proxima_llegada_paciente + "</td>\n" +
                "\t\t\t\t\t<td>" + random_atencion_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_atencion_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + fin_atencion_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_atencion_medico + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_calmante + "</td>\n" +
                "\t\t\t\t\t<td>" + fin_atencion_medico1 + "</td>\n" +
                "\t\t\t\t\t<td>" + fin_atencion_medico2 + "</td>\n" +
                "\t\t\t\t\t<td>" + enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + cola_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + medico1 + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_remanente1 + "</td>\n" +
                "\t\t\t\t\t<td>" + medico2 + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_remanente2 + "</td>\n" +
                "\t\t\t\t\t<td>" + cola_urgencia + "</td>\n" +
                "\t\t\t\t\t<td>" + cola_comun + "</td>\n" +
                "\t\t\t\t\t<td>" + cant_pacientes_cola + "</td>\n" +
                "\t\t\t\t\t<td>" + max_pacientes_cola + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_espera_urgente + "</td>\n" +
                "\t\t\t\t\t<td>" + tiempo_max_espera_urgente + "</td>\n" +
                "\t\t\t\t\t<td>" + cant_pacientes_comunes + "</td>\n" +
                "\t\t\t\t\t<td>" + ac_tiempo_espera + "</td>\n" +
                "\t\t\t\t\t<td>" + promedio_espera_comun + "</td>\n" +
                "\t\t\t\t\t<td>" + cant_urgencias + "</td>\n" +
                "\t\t\t\t\t<td>" + cant_total_pacientes + "</td>\n" +
                "\t\t\t\t\t<td>" + porcentaje_urgencias + "</td>\n" +
                "\t\t\t\t\t<td>" + ac_ocupacion_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + porcentaje_ocupacion_enfermero + "</td>\n" +
                "\t\t\t\t\t<td>" + ac_ocupacion_medico1 + "</td>\n" +
                "\t\t\t\t\t<td>" + porcentaje_ocupacion_medico1 + "</td>\n" +
                "\t\t\t\t\t<td>" + ac_ocupacion_medico2 + "</td>\n" +
                "\t\t\t\t\t<td>" + porcentaje_ocupacion_medico2 + "</td>\n" +
                toStringPacientes() + "\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</tfoot>\n";
        return cadena;
    }

    private String toStringPacientes(){
        String cadena = "";
        for (Paciente paciente : pacientes) {
            cadena = cadena + paciente.toString();
        }
        return cadena;
    }

    // Getter y Setter
    public Fila getFilaAnterior() {
        return filaAnterior;
    }

    public void setFilaAnterior(Fila filaAnterior) {
        this.filaAnterior = filaAnterior;
    }

    public int getNroFila() {
        return nroFila;
    }

    public void setNroFila(int nroFila) {
        this.nroFila = nroFila;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public float getReloj() {
        return reloj;
    }

    public void setReloj(float reloj) {
        this.reloj = reloj;
    }

    public float getRandom_llegada() {
        return random_llegada;
    }

    public void setRandom_llegada(float random_llegada) {
        this.random_llegada = random_llegada;
    }

    public float getTiempo_entre_llegadas() {
        return tiempo_entre_llegadas;
    }

    public void setTiempo_entre_llegadas(float tiempo_entre_llegadas) {
        this.tiempo_entre_llegadas = tiempo_entre_llegadas;
    }

    public float getProxima_llegada_paciente() {
        return proxima_llegada_paciente;
    }

    public void setProxima_llegada_paciente(float proxima_llegada_paciente) {
        this.proxima_llegada_paciente = proxima_llegada_paciente;
    }

    public float getRandom_atencion_enfermero() {
        return random_atencion_enfermero;
    }

    public void setRandom_atencion_enfermero(float random_atencion_enfermero) {
        this.random_atencion_enfermero = random_atencion_enfermero;
    }

    public float getTiempo_atencion_enfermero() {
        return tiempo_atencion_enfermero;
    }

    public void setTiempo_atencion_enfermero(float tiempo_atencion_enfermero) {
        this.tiempo_atencion_enfermero = tiempo_atencion_enfermero;
    }

    public float getFin_atencion_enfermero() {
        return fin_atencion_enfermero;
    }

    public void setFin_atencion_enfermero(float fin_atencion_enfermero) {
        this.fin_atencion_enfermero = fin_atencion_enfermero;
    }

    public String getEnfermero() {
        return enfermero;
    }

    public void setEnfermero(String enfermero) {
        this.enfermero = enfermero;
    }

    public int getCola_enfermero() {
        return cola_enfermero;
    }

    public void setCola_enfermero(int cola_enfermero) {
        this.cola_enfermero = cola_enfermero;
    }

    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(ArrayList<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public String getProximo_evento() {
        return proximo_evento;
    }

    public void setProximo_evento(String proximo_evento) {
        this.proximo_evento = proximo_evento;
    }

    public float getProximo_reloj() {
        return proximo_reloj;
    }

    public void setProximo_reloj(float proximo_reloj) {
        this.proximo_reloj = proximo_reloj;
    }

    public float getTiempo_atencion_medico() {
        return tiempo_atencion_medico;
    }

    public void setTiempo_atencion_medico(float tiempo_atencion_medico) {
        this.tiempo_atencion_medico = tiempo_atencion_medico;
    }

    public float getFin_atencion_medico1() {
        return fin_atencion_medico1;
    }

    public void setFin_atencion_medico1(float fin_atencion_medico1) {
        this.fin_atencion_medico1 = fin_atencion_medico1;
    }

    public float getFin_atencion_medico2() {
        return fin_atencion_medico2;
    }

    public void setFin_atencion_medico2(float fin_atencion_medico2) {
        this.fin_atencion_medico2 = fin_atencion_medico2;
    }

    public String getMedico1() {
        return medico1;
    }

    public void setMedico1(String medico1) {
        this.medico1 = medico1;
    }

    public float getTiempo_remanente1() {
        return tiempo_remanente1;
    }

    public void setTiempo_remanente1(float tiempo_remanente1) {
        this.tiempo_remanente1 = tiempo_remanente1;
    }

    public String getMedico2() {
        return medico2;
    }

    public void setMedico2(String medico2) {
        this.medico2 = medico2;
    }

    public float getTiempo_remanente2() {
        return tiempo_remanente2;
    }

    public void setTiempo_remanente2(float tiempo_remanente2) {
        this.tiempo_remanente2 = tiempo_remanente2;
    }

    public int getCola_urgencia() {
        return cola_urgencia;
    }

    public void setCola_urgencia(int cola_urgencia) {
        this.cola_urgencia = cola_urgencia;
    }

    public int getCola_comun() {
        return cola_comun;
    }

    public void setCola_comun(int cola_comun) {
        this.cola_comun = cola_comun;
    }

    public String getTipo_paciente() {
        return tipo_paciente;
    }

    public void setTipo_paciente(String tipo_paciente) {
        this.tipo_paciente = tipo_paciente;
    }

    public float getTiempo_calmante() {
        return tiempo_calmante;
    }

    public void setTiempo_calmante(float tiempo_calmante) {
        this.tiempo_calmante = tiempo_calmante;
    }

    public int getCant_pacientes_cola() {
        return cant_pacientes_cola;
    }

    public void setCant_pacientes_cola(int cant_pacientes_cola) {
        this.cant_pacientes_cola = cant_pacientes_cola;
    }

    public float getMax_pacientes_cola() {
        return max_pacientes_cola;
    }

    public void setMax_pacientes_cola(float max_pacientes_cola) {
        this.max_pacientes_cola = max_pacientes_cola;
    }

    public float getTiempo_espera_urgente() {
        return tiempo_espera_urgente;
    }

    public void setTiempo_espera_urgente(float tiempo_espera_urgente) {
        this.tiempo_espera_urgente = tiempo_espera_urgente;
    }

    public float getTiempo_max_espera_urgente() {
        return tiempo_max_espera_urgente;
    }

    public void setTiempo_max_espera_urgente(float tiempo_max_espera_urgente) {
        this.tiempo_max_espera_urgente = tiempo_max_espera_urgente;
    }

    public int getCant_pacientes_comunes() {
        return cant_pacientes_comunes;
    }

    public void setCant_pacientes_comunes(int cant_pacientes_comunes) {
        this.cant_pacientes_comunes = cant_pacientes_comunes;
    }

    public float getAc_tiempo_espera() {
        return ac_tiempo_espera;
    }

    public void setAc_tiempo_espera(float ac_tiempo_espera) {
        this.ac_tiempo_espera = ac_tiempo_espera;
    }

    public int getCant_urgencias() {
        return cant_urgencias;
    }

    public void setCant_urgencias(int cant_urgencias) {
        this.cant_urgencias = cant_urgencias;
    }

    public int getCant_total_pacientes() {
        return cant_total_pacientes;
    }

    public void setCant_total_pacientes(int cant_total_pacientes) {
        this.cant_total_pacientes = cant_total_pacientes;
    }

    public float getAc_ocupacion_enfermero() {
        return ac_ocupacion_enfermero;
    }

    public void setAc_ocupacion_enfermero(float ac_ocupacion_enfermero) {
        this.ac_ocupacion_enfermero = ac_ocupacion_enfermero;
    }

    public float getAc_ocupacion_medico1() {
        return ac_ocupacion_medico1;
    }

    public void setAc_ocupacion_medico1(float ac_ocupacion_medico1) {
        this.ac_ocupacion_medico1 = ac_ocupacion_medico1;
    }

    public float getAc_ocupacion_medico2() {
        return ac_ocupacion_medico2;
    }

    public void setAc_ocupacion_medico2(float ac_ocupacion_medico2) {
        this.ac_ocupacion_medico2 = ac_ocupacion_medico2;
    }

    public float getPromedio_espera_comun() {
        return promedio_espera_comun;
    }

    public void setPromedio_espera_comun(float promedio_espera_comun) {
        this.promedio_espera_comun = promedio_espera_comun;
    }

    public float getPorcentaje_urgencias() {
        return porcentaje_urgencias;
    }

    public void setPorcentaje_urgencias(float porcentaje_urgencias) {
        this.porcentaje_urgencias = porcentaje_urgencias;
    }

    public float getPorcentaje_ocupacion_enfermero() {
        return porcentaje_ocupacion_enfermero;
    }

    public void setPorcentaje_ocupacion_enfermero(float porcentaje_ocupacion_enfermero) {
        this.porcentaje_ocupacion_enfermero = porcentaje_ocupacion_enfermero;
    }

    public float getPorcentaje_ocupacion_medico1() {
        return porcentaje_ocupacion_medico1;
    }

    public void setPorcentaje_ocupacion_medico1(float porcentaje_ocupacion_medico1) {
        this.porcentaje_ocupacion_medico1 = porcentaje_ocupacion_medico1;
    }

    public float getPorcentaje_ocupacion_medico2() {
        return porcentaje_ocupacion_medico2;
    }

    public void setPorcentaje_ocupacion_medico2(float porcentaje_ocupacion_medico2) {
        this.porcentaje_ocupacion_medico2 = porcentaje_ocupacion_medico2;
    }
}