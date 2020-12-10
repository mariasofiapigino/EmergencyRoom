public class Paciente {
    private static int cont;
    private int idPaciente;
    private String estado;
    private float random_tipo;
    private String tipo;
    private float tiempo_inicio_espera;
    private int mostrado;

    public Paciente() {
        cont++;
        this.idPaciente = cont;
        this.tipo = "";
        this.mostrado = 0;
    }

    public void setTipoPaciente(){
        this.tipo = "Comun";
        this.random_tipo = (float) Math.random();
        if (random_tipo < 0.4) this.tipo = "Urgente";
    }

    // toString
    @Override
    public String toString() {
        if (this.mostrado > 1){
            String cadena =
                    "\t\t\t\t\t<td>" + " " +"</td>\n" +
                            "\t\t\t\t\t<td>" + " " +"</td>\n" +
                            "\t\t\t\t\t<td>" + " " +"</td>\n" +
                            "\t\t\t\t\t<td>" + " " + "</td>\n";
            return cadena;
        } else {
            // Para que no muestre en caso de no esperar
            String hora_inicio_espera = String.valueOf(tiempo_inicio_espera);
            if (tiempo_inicio_espera == 0) hora_inicio_espera = "";

            String cadena =
                    "\t\t\t\t\t<td>" + idPaciente +"</td>\n" +
                            "\t\t\t\t\t<td>" + estado +"</td>\n" +
                            "\t\t\t\t\t<td>" + tipo +"</td>\n" +
                            "\t\t\t\t\t<td>" + hora_inicio_espera + "</td>\n";
            return cadena;
        }
    }

    // Getter y Setter
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getTiempo_inicio_espera() {
        return tiempo_inicio_espera;
    }

    public void setTiempo_inicio_espera(float tiempo_inicio_espera) {
        this.tiempo_inicio_espera = tiempo_inicio_espera;
    }

    public String getTipoPaciente() {
        return tipo;
    }

    public int getMostrado() {
        return mostrado;
    }

    public void setMostrado(int mostrado) {
        this.mostrado = mostrado;
    }

    public void reset() {
        cont = 0;
    }
}
