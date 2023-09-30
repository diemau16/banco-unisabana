# restapi-banco

##Integrantes del equipo
 -Andres Felipe Lanza Torres
 -Diego Mauricio Acosta Rodriguez
 -Joseph Daniel Mantilla Castro
##Funciones del Proyecto
-Crear Cliente
-Interacturar con productos del banco(Tipo de cuenta de ahorros y tipo de tarjeta de credito)
-Consultar Saldo incluyendo consulta por producto
-Historial de de Transacciones incluyendo depositos, transferencias, retiros y pagos.
-Transferencia de fondos perimite a los usuarios realizar transferencia de dinero entre sus propias cuentas o hacia cuenta de terceros

##Como se ejecuta
Para ejecutar este proyecto, primero asegúrate de tener configurado el entorno de desarrollo y las herramientas necesarias instaladas, como Java, Gradle Klotlin (para construir el proyecto)

Verifica que el servidor de la base de datos esté ejecutándose y que puedes acceder a él desde la dirección sql.freedb.tech en el puerto 3306.
Clona el proyecto:

Clona o descarga el proyecto desde un repositorio de código fuente, como GitHub, y colócalo en tu sistema.
Descarga todas las dependencias y pon a correr el spring
Uso de la aplicación:

La aplicación se ejecutará en el puerto 8081, según lo especificado en server.port=8081 en la configuración.
Puedes acceder a la API a través de un cliente REST como Postman

Puedes acceder a la API a través de un cliente REST como Postman o utilizando un navegador web o corra swagger en el navegador

Endpoints de la API:

Aquí hay algunos ejemplos de cómo utilizar los endpoints de la API:
Agregar un cliente: POST http://localhost:8081/cliente/agregar
Obtener todos los clientes: GET http://localhost:8081/cliente/obtener
Obtener un cliente por ID: GET http://localhost:8081/cliente/obtener/{idCliente}
Desactivar un cliente: PUT http://localhost:8081/cliente/desactivar/{idCliente}
Activar un cliente: PUT http://localhost:8081/cliente/activar/{idCliente}


