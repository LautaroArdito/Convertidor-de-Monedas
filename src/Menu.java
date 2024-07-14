import java.util.Scanner;

public class Menu {
    private static final int OPCION_CAMBIO_DIVISAS = 1;
    private static final int OPCION_HISTORIAL = 2;
    private static final int OPCION_SALIR = 3;
    private static final int DIVISA_DOLAR_PESO_ARG = 1;
    private static final int DIVISA_PESO_ARG_DOLAR = 2;
    private static final int DIVISA_DOLAR_EURO = 3;
    private static final int DIVISA_EURO_DOLAR = 4;
    private static final int DIVISA_OTRA = 5;
    private static final int DIVISA_VOLVER = 6;

    private static final Scanner lectorUsuario = new Scanner(System.in);
    private static final ConsultaApi consulta = new ConsultaApi();
    private static final Menu menu = new Menu();

    public String getMenuPrincipal(){
        return """
                *************************************
                Bienvenido al conversor de monedas!
                Por favor, elige una opción:
                1 - Cambio de divisas
                2 - Historial de consultas
                3 - Salir
                *************************************
                """;
    }

    public String getMenuDivisas(){
        return """
                *************** DIVISAS ***************
                1 - Dolar -> Peso argentino
                2 - Peso argentino -> Dolar
                3 - Dolar -> Euro
                4 - Euro -> Dolar
                5 - Otra moneda
                6 - Volver atrás
                *************************************
                """;
    }

    private static void gestionarCambioDivisas(){
        int opcionDivisas = 0;
        int cantidad = 0;
        String monedaOrigen = "";
        String monedaDestino = "";
        Moneda moneda;

        while (opcionDivisas != DIVISA_VOLVER) {
            System.out.println(menu.getMenuDivisas());
            System.out.println("Por favor, elige una opción:\n");
            opcionDivisas = lectorUsuario.nextInt();
            lectorUsuario.nextLine();
            if (opcionDivisas == DIVISA_VOLVER) {
                break;
            }

            try {
                System.out.println("Ingrese la cantidad que desea convertir\n");
                cantidad = lectorUsuario.nextInt();
                lectorUsuario.nextLine();
                switch (opcionDivisas) {
                    case DIVISA_DOLAR_PESO_ARG:
                        moneda = consulta.buscarMoneda("USD", "ARS", cantidad);
                        //System.out.println(moneda);
                        System.out.println("RESULTADO: "+cantidad + " Dolar/es son "+moneda.conversion_result()+" Pesos argentinos");
                        break;
                    case DIVISA_PESO_ARG_DOLAR:
                        moneda = consulta.buscarMoneda("ARS", "USD", cantidad);
                        System.out.println("RESULTADO: "+cantidad + " Pesos argentinos son "+moneda.conversion_result()+" Dolar/es");
                        break;
                    case DIVISA_DOLAR_EURO:
                        moneda = consulta.buscarMoneda("USD", "EUR", cantidad);
                        System.out.println("RESULTADO: "+cantidad + " Dolar/es son "+moneda.conversion_result()+" Euro/s");
                        break;
                    case DIVISA_EURO_DOLAR:
                        moneda = consulta.buscarMoneda("EUR", "USD", cantidad);
                        System.out.println("RESULTADO: "+cantidad + " Euro/s son "+moneda.conversion_result()+" Dolar/es");
                        break;
                    case DIVISA_OTRA:
                        System.out.println("Ingrese moneda origen en formato XXX ej: USD , ARS , EUR");
                        monedaOrigen = lectorUsuario.nextLine().toUpperCase();
                        System.out.println(monedaOrigen);
                        System.out.println("Ingrese la moneda desitno en formato XXX");
                        monedaDestino = lectorUsuario.nextLine().toUpperCase();
                        moneda = consulta.buscarMoneda(monedaOrigen, monedaDestino, cantidad);
                        System.out.println("RESULTADO: "+cantidad + " "+monedaOrigen+" son "+moneda.conversion_result()+" "+monedaDestino);
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, elige una opción del 1 al 6.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                lectorUsuario.nextLine(); // Limpiar la entrada incorrecta
            }
        }
    }

    public static void main(String[] args) {
        int opcion = 0;

        while (opcion != OPCION_SALIR) {
            System.out.println(menu.getMenuPrincipal());
            opcion = lectorUsuario.nextInt();
            lectorUsuario.nextLine();

            switch (opcion) {
                case OPCION_CAMBIO_DIVISAS:
                    gestionarCambioDivisas();
                    break;
                case OPCION_HISTORIAL:
                    System.out.println("HISTORIAL");
                    //mostrarHistorial();
                    break;
                case OPCION_SALIR:
                    System.out.println("Gracias por usar la app :D");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción del 1 al 3.");
            }
        }
        lectorUsuario.close();
    }
}
