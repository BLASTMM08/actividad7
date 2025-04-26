import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Calculadora de geometría y potencia que permite a los usuarios realizar múltiples
 * operaciones de área, perímetro y cálculo de potencia a través de una interfaz
 * basada en menú. Almacena los resultados de las operaciones en una lista,
 * maneja posibles errores de entrada del usuario, utiliza métodos para modularizar
 * el código y aplica recursividad para el cálculo de la potencia.
 */
public class GeometryCalculator {
    // Constante para el valor de PI, utilizada en los cálculos de círculo.
    private static final double PI = 3.1416;

    /**
     * Método principal para ejecutar la aplicación de la calculadora.
     * Presenta menús para la selección de figura y operación, realiza cálculos,
     * maneja errores de entrada, almacena los resultados en una lista e incluye
     * una opción de cálculo de potencia. Permite realizar múltiples operaciones
     * hasta que el usuario decide salir.
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Inicializa un objeto Scanner para leer la entrada del usuario desde la consola.
        Scanner input = new Scanner(System.in);
        // Lista para almacenar los resultados de los cálculos.
        ArrayList<Double> results = new ArrayList<>();

        // Bucle principal de la aplicación: continúa hasta que el usuario elige salir.
        while (true) {
            // Muestra el menú de figuras y obtiene la elección del usuario.
            int shapeChoice = askShapeMenu(input);
            // Sale del bucle si el usuario elige la opción 0 (Salir).
            if (shapeChoice == 0) break;

            // Muestra el menú de operaciones y obtiene la elección del usuario.
            int operationChoice = askOperationMenu(input);
            // Continúa a la siguiente iteración si el usuario elige la opción 0 (Volver).
            if (operationChoice == 0) continue;

            try {
                // Realiza el cálculo basado en las elecciones del usuario.
                double result = calculate(shapeChoice, operationChoice, input);
                // Agrega el resultado a la lista de resultados.
                results.add(result);
                // Imprime el resultado del cálculo.
                System.out.println("Resultado: " + result);
            } catch (InputMismatchException e) {
                // Captura y maneja casos donde el tipo de entrada no coincide con el tipo esperado.
                System.out.println("Entrada inválida. Intenta de nuevo.");
                input.nextLine(); // Consume la entrada inválida para evitar un bucle infinito.
            } catch (IllegalArgumentException e) {
                // Captura y maneja elecciones de figura u operación inválidas, o valores de entrada inválidos.
                System.out.println(e.getMessage());
                input.nextLine(); // Consume la entrada inválida si la hay.
            }
        }

        // Imprime todos los resultados almacenados después de que el bucle principal termina.
        System.out.println("Resultados almacenados:");
        for (double value : results) {
            System.out.println(value);
        }

        // Cierra el recurso Scanner para liberar los recursos del sistema.
        input.close();
    }

    /**
     * Muestra el menú de selección de figuras al usuario.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El entero que representa la elección de figura del usuario (1-5 para figuras, 0 para salir).
     */
    private static int askShapeMenu(Scanner input) {
        System.out.println("\nElige una figura:");
        System.out.println("1. Círculo");
        System.out.println("2. Cuadrado");
        System.out.println("3. Triángulo");
        System.out.println("4. Rectángulo");
        System.out.println("5. Pentágono");
        System.out.println("0. Salir");
        // Lee y valida la opción del menú del usuario, asegurando que esté dentro del rango válido [0, 5].
        return readMenuOption(input, 5);
    }

    /**
     * Muestra el menú de selección de operaciones al usuario.
     * Incluye operaciones geométricas (Área, Perímetro) y una opción de cálculo de Potencia.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El entero que representa la elección de operación del usuario (1 para Área, 2 para Perímetro, 3 para Potencia, 0 para volver).
     */
    private static int askOperationMenu(Scanner input) {
        System.out.println("Elige una operación:");
        System.out.println("1. Área");
        System.out.println("2. Perímetro");
        System.out.println("3. Potencia");
        System.out.println("0. Volver");
        // Lee y valida la opción del menú del usuario, asegurando que esté dentro del rango válido [0, 3].
        return readMenuOption(input, 3);
    }

    /**
     * Lee y valida una opción de menú entera de la entrada del usuario.
     * Asegura que la entrada sea un entero dentro del rango válido [0, max].
     * Repite la solicitud hasta que se reciba una entrada válida.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @param max El valor entero máximo válido para la opción del menú.
     * @return La opción de menú entera validada.
     */
    private static int readMenuOption(Scanner input, int max) {
        int option;
        // Bucle infinito hasta que se proporcione una opción válida.
        while (true) {
            System.out.print("Opción: ");
            try {
                // Lee la línea completa y elimina los espacios en blanco antes de parsear.
                option = Integer.parseInt(input.nextLine().trim());
                // Verifica si la opción parseada está dentro del rango aceptable.
                if (option >= 0 && option <= max)
                    return option; // Retorna la opción válida.
                System.out.println("Opción fuera de rango."); // Informa al usuario si la opción está fuera de rango.
            } catch (NumberFormatException e) {
                // Captura y maneja casos donde la entrada no es un entero válido.
                System.out.println("Por favor ingresa un número válido.");
            }
        }
    }

    /**
     * Calcula el resultado basándose en la figura y operación seleccionadas.
     * Si la operación es 'Potencia' (3), llama al método handlePower.
     * De lo contrario, delega al método de cálculo de área o perímetro específico de la figura.
     * @param shapeChoice El entero que representa la figura elegida (1-5).
     * @param operationChoice El entero que representa la operación elegida (1 para Área, 2 para Perímetro, 3 para Potencia).
     * @param input El objeto Scanner utilizado para leer las dimensiones necesarias.
     * @return El resultado calculado.
     * @throws IllegalArgumentException si la figura elegida no es reconocida (no entre 1 y 5 cuando la operación no es Potencia).
     */
    private static double calculate(int shapeChoice, int operationChoice, Scanner input) {
        // Maneja la operación 'Potencia' por separado ya que no depende de una figura.
        if (operationChoice == 3)
            return handlePower(input);

        // Utiliza un switch expression para llamar al método de cálculo apropiado según la figura.
        return switch (shapeChoice) {
            case 1 -> operationChoice == 1 ? circleArea(input) : circlePerimeter(input);
            case 2 -> operationChoice == 1 ? squareArea(input) : squarePerimeter(input);
            case 3 -> operationChoice == 1 ? triangleArea(input) : trianglePerimeter(input);
            case 4 -> operationChoice == 1 ? rectangleArea(input) : rectanglePerimeter(input);
            case 5 -> operationChoice == 1 ? pentagonArea(input) : pentagonPerimeter(input);
            // Lanza una excepción para elecciones de figura desconocidas cuando no se realiza la operación de Potencia.
            default -> throw new IllegalArgumentException("Figura desconocida.");
        };
    }

    /**
     * Maneja la operación de cálculo de potencia.
     * Solicita al usuario la base y el exponente, y calcula el resultado utilizando recursividad.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El resultado del cálculo de potencia.
     */
    private static double handlePower(Scanner input) {
        System.out.println("Base:");
        double base = readPositiveDouble(input); // Lee y valida la base (debe ser positiva).
        System.out.println("Exponente (entero, puede ser 0 o positivo):");
        int exponent = readPositiveInt(input); // Lee y valida el exponente (debe ser un entero no negativo).
        return powerRecursive(base, exponent); // Calcula la potencia utilizando recursividad.
    }

    /**
     * Lee un valor entero no negativo de la entrada del usuario.
     * El bucle continúa hasta que se ingresa un entero válido mayor o igual a 0.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El valor entero no negativo validado.
     */
    private static int readPositiveInt(Scanner input) {
        int value;
        // Bucle infinito hasta que se proporcione un entero no negativo válido.
        while (true) {
            System.out.print("Valor: "); // Agregado un prompt para mayor claridad.
            try {
                // Lee la línea completa y elimina los espacios en blanco antes de parsear.
                value = Integer.parseInt(input.nextLine().trim());
                // Verifica si el valor es no negativo.
                if (value >= 0) return value; // Retorna el valor válido.
                System.out.println("Debe ser 0 o mayor."); // Informa al usuario si el valor es negativo.
            } catch (NumberFormatException e) {
                // Captura y maneja casos donde la entrada no es un entero válido.
                System.out.println("Número inválido.");
            }
        }
    }

    /**
     * Calcula la potencia de una base elevada a un exponente utilizando recursividad.
     * Caso base: el exponente es 0, el resultado es 1.
     * Paso recursivo: base * powerRecursive(base, exponent - 1).
     * @param base La base (double).
     * @param exponent El exponente (entero no negativo).
     * @return El resultado de la base elevada a la potencia del exponente.
     */
    private static double powerRecursive(double base, int exponent) {
        // Caso base: cualquier número elevado a la potencia de 0 es 1.
        if (exponent == 0)
            return 1;
        // Paso recursivo: multiplica la base por el resultado del cálculo de potencia para exponente - 1.
        return base * powerRecursive(base, exponent - 1);
    }

    /**
     * Calcula el área de un círculo.
     * Solicita al usuario el radio y asegura que sea un valor positivo.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El área calculada del círculo.
     */
    private static double circleArea(Scanner input) {
        System.out.print("Radio: ");
        double radius = readPositiveDouble(input); // Lee y valida un radio positivo.
        return PI * radius * radius; // Calcula y retorna el área.
    }

    /**
     * Calcula el perímetro de un círculo.
     * Solicita al usuario el radio y asegura que sea un valor positivo.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El perímetro calculado del círculo.
     */
    private static double circlePerimeter(Scanner input) {
        System.out.println("Radio:");
        return 2 * PI * readPositiveDouble(input); // Lee un radio positivo, calcula y retorna el perímetro.
    }

    /**
     * Calcula el área de un cuadrado.
     * Solicita al usuario la longitud del lado y asegura que sea un valor positivo.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El área calculada del cuadrado.
     */
    private static double squareArea(Scanner input) {
        System.out.println("Lado:");
        double side = readPositiveDouble(input); // Lee y valida una longitud de lado positiva.
        return side * side; // Calcula y retorna el área.
    }

    /**
     * Calcula el perímetro de un cuadrado.
     * Solicita al usuario la longitud del lado y asegura que sea un valor positivo.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El perímetro calculado del cuadrado.
     */
    private static double squarePerimeter(Scanner input) {
        System.out.println("Lado:");
        return 4 * readPositiveDouble(input); // Lee una longitud de lado positiva, calcula y retorna el perímetro.
    }

    /**
     * Calcula el área de un triángulo.
     * Solicita al usuario la base y la altura y asegura que sean valores positivos.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El área calculada del triángulo.
     */
    private static double triangleArea(Scanner input) {
        System.out.println("Base:");
        double base = readPositiveDouble(input); // Lee y valida una base positiva.
        System.out.println("Altura:");
        double height = readPositiveDouble(input); // Lee y valida una altura positiva.
        return 0.5 * base * height; // Calcula y retorna el área.
    }

    /**
     * Calcula el perímetro de un triángulo.
     * Solicita al usuario las longitudes de los tres lados y asegura que sean valores positivos.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El perímetro calculado del triángulo.
     */
    private static double trianglePerimeter(Scanner input) {
        System.out.println("Lado 1:");
        double a = readPositiveDouble(input); // Lee y valida el lado a positivo.
        System.out.println("Lado 2:");
        double b = readPositiveDouble(input); // Lee y valida el lado b positivo.
        System.out.println("Lado 3:");
        double c = readPositiveDouble(input); // Lee y valida el lado c positivo.
        return a + b + c; // Calcula y retorna el perímetro.
    }

    /**
     * Calcula el área de un rectángulo.
     * Solicita al usuario la base y la altura y asegura que sean valores positivos.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El área calculada del rectángulo.
     */
    private static double rectangleArea(Scanner input) {
        System.out.println("Base:");
        double base = readPositiveDouble(input); // Lee y valida una base positiva.
        System.out.println("Altura:");
        double height = readPositiveDouble(input); // Lee y valida una altura positiva.
        return base * height; // Calcula y retorna el área.
    }

    /**
     * Calcula el perímetro de un rectángulo.
     * Solicita al usuario la base y la altura y asegura que sean valores positivos.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El perímetro calculado del rectángulo.
     */
    private static double rectanglePerimeter(Scanner input) {
        System.out.println("Base:");
        double base = readPositiveDouble(input); // Lee y valida una base positiva.
        System.out.println("Altura:");
        double height = readPositiveDouble(input); // Lee y valida una altura positiva.
        return 2 * (base + height); // Calcula y retorna el perímetro.
    }

    /**
     * Calcula el área de un pentágono regular.
     * Solicita al usuario la longitud del lado y la apotema y asegura que sean valores positivos.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El área calculada del pentágono.
     */
    private static double pentagonArea(Scanner input) {
        System.out.println("Lado:");
        double side = readPositiveDouble(input); // Lee y valida una longitud de lado positiva.
        System.out.println("Apotema:");
        double apothem = readPositiveDouble(input); // Lee y valida una apotema positiva.
        return (5 * side * apothem) / 2; // Calcula y retorna el área.
    }

    /**
     * Calcula el perímetro de un pentágono regular.
     * Solicita al usuario la longitud del lado y asegura que sea un valor positivo.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El perímetro calculado del pentágono.
     */
    private static double pentagonPerimeter(Scanner input) {
        System.out.println("Lado:");
        return 5 * readPositiveDouble(input); // Lee una longitud de lado positiva, calcula y retorna el perímetro.
    }

    /**
     * Lee un valor double positivo de la entrada del usuario.
     * El bucle continúa hasta que se ingresa un número positivo válido.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El valor double positivo validado.
     */
    private static double readPositiveDouble(Scanner input) {
        double value;
        // Bucle infinito hasta que se proporcione un double positivo válido.
        while (true) {
            System.out.print("Valor: "); // Agregado un prompt para mayor claridad.
            try {
                // Lee la línea completa y elimina los espacios en blanco antes de parsear.
                value = Double.parseDouble(input.nextLine().trim());
                // Verifica si el valor parseado es positivo.
                if (value > 0)
                    return value; // Retorna el valor válido.
                System.out.println("El valor debe ser mayor que 0."); // Informa al usuario si el valor no es positivo.
            } catch (NumberFormatException e) {
                // Captura y maneja casos donde la entrada no es un double válido.
                System.out.println("Entrada inválida. Ingresa un número válido.");
            }
        }
    }
}