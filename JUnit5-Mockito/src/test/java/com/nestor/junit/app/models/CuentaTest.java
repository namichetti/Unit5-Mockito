package com.nestor.junit.app.models;

import exception.DineroInsuficienteException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*; //Lo agregué a manopla

class CuentaTest {

    Cuenta cuenta;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Método beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Método AfterAll");

    }

    @BeforeEach
    void before(){
        cuenta = Cuenta.builder().saldo(new BigDecimal("100")).build();
        System.out.println("MÃ©todo beforeEach");
    }

    @AfterEach
    void after(){
        System.out.println("MÃ©todo afterEach");
    }

    @Test
    void testSaldoCuenta(){
        assertNotNull(cuenta.getSaldo());
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);
    }

    @Test
    void testDineroInsuficiente(){
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.verificarSaldo(new BigDecimal("5000"));
        });
        String esperado = "No alcanza la guita";
        String real = exception.getMessage();
        // Poniendo el mensaje de esta forma crea el String sea true o false.
        assertEquals(esperado,real, "Tiene que ser iguales");
        esperado ="";
        //Con la funciÃ³n de flecha SOLO crea el mensaje si no se cumple la condiciÃ³n
        assertEquals(esperado,real, ()->"Tiene que ser iguales");
    }

    @Test
    void testAssertAll(){
        assertAll(
                ()->assertEquals(1,1, "Tiene que ser iguales"),
                ()-> assertTrue(1==1,"Tiene que ser true"),
                ()-> assertEquals("Hola","Chau", "Tiene que ser iguales pero son <>")
        );
    }

    @Test
    void testDePruebaFail(){
        fail();
        assertEquals(1,1);
    }

    @Disabled
    @Test
    void testDePruebaDisable(){
        fail();
        assertEquals(1,1);
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
        //@DisabledOnOs({OS.WINDOWS})
    void testSoloWindows() {
        System.out.println("Solo en windows");
    }

    @Test
    @EnabledOnOs({OS.LINUX,OS.MAC})
        //@DisabledOnOs({OS.LINUX,OS.MAC, OS.MAC})
    void testSoloLinuxMac() {
        System.out.println("Solo linux y mac. Si se ejcuta la clase no se ve, "
                + "si se ejecuta sol oel test sí se ve.");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
        // @DisabledOnJre(JRE.JAVA_8)
    void testSoloJDK8() {
        System.out.println("Solo en JDK8");
    }


  /*  Properties properties = System.getProperties();
    properties.forEach((k,v)->System.out.println(k + ", " + v));*/

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "11.0.12")
        //   @DisablefSystemProperty(named = "java.version", matches = "11.0.12")
    void testJavaVersion() {
        System.out.println("Método @EnabledIfSystemProperty");
    }


    /* Map<String, String> enviroment = System.getenv();
    enviroment.forEach((k,v)->System.out.println(k + ": "+v));*/

    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-11.0.12.*")
        // @DisabledIfEnvironmentVariable(named = "java.version", matches = "11.0.12")
    void testJavaHome() {
        System.out.println("Método @EnabledIfEnvironmentVariable");
    }

    @Test
        //Para reemplazar los Enable y Disable
    void testJavaHomeConAssume() {
        boolean javaHome = ".*jdk-11.0.12.*".equals(System.getProperty("JAVA_HOME"));
        System.out.println("Método testJavaHomeConAssume");
        assumeTrue(javaHome); //Si es False falla todo el método y no sigue
        System.out.println("Si es False falla todo el método y no viene por acá");

    }

    @Test
    void testJavaHomeConAssume2() {
        boolean javaHome = ".*jdk-11.0.12.*".equals(System.getProperty("JAVA_HOME"));
        System.out.println("Método testJavaHomeConAssume2");
        assumingThat(javaHome, ()->{
            System.out.println("Si es TRUE viene por acá");
        });

        System.out.println("Si es False igualmente sigue");
        //Si es False igualmente sigue
    }


    //Categorizamos tests, o sea son tests que están relacionados. En este caso relacionados en SO.
    @Nested
    class TestClasesAnidadas {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows() {
            System.out.println("Solo en windows");
        }

        @Test
        @EnabledOnOs({OS.LINUX,OS.MAC})
        void testSoloLinuxMac() {
            System.out.println("Solo linux y mac. Si se ejcuta la clase no se ve, "
                    + "si se ejecuta sol oel test sí se ve.");
        }

    }

    @RepeatedTest(5)
    void testRepetir(RepetitionInfo info) {
        System.out.println("Se repite el test núumero "+ info.getCurrentRepetition()
                +" de "+ info.getTotalRepetitions() +" veces");
    }

    @ParameterizedTest
    @ValueSource(ints = {10,20,30,60}) //Hay muchas opciones, incluso se puede llamar desde un archivo .CSV
    void testParametrizado(int numero) {
        assertTrue(numero>=30);
    }

    @ParameterizedTest
    @MethodSource("ListaDeNumeros")
    void testParametrizado2(int numero) {
        assertTrue(numero>=30);
    }

    static List<Integer> ListaDeNumeros(){
        return Arrays.asList(10,20,30,60);
    }

    @ParameterizedTest
    //@CsvSource({"ESPERADO0,ACTUAL0","ESPERADO1,ACTUAL1"...})
    @CsvSource({"20,20","10,30","50,2"})
    void testParametrizado3(String esperado, String actual) {
        System.out.println("ESPERADO " +esperado);
        System.out.println("ACTUAL " +actual);

    }

    @Test
    @Timeout(5)
    void testTimeOut() throws InterruptedException {
        TimeUnit.SECONDS.sleep(6);
    }

    @Test
    @Timeout(5)
    void testTimeOut2() throws InterruptedException {
        assertTimeout(Duration.ofSeconds(5), ()->{
            TimeUnit.SECONDS.sleep(6);
            System.out.println("Nunca va a llegar acá");
        });
    }

}