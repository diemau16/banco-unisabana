# Banco Unisabank

## Integrantes del equipo

 - Andres Felipe Lanza Torres
 - Diego Mauricio Acosta Rodriguez
 - Joseph Daniel Mantilla Castro

## Funciones del Proyecto

El aplicativo contiene las funciones mencionadas a continuación:
- Crear un cliente.
- Interacturar con productos del banco (Tipo de cuenta de ahorros y tipo de tarjeta de credito).
- Crear nuevos productos.
- Consultar saldo incluyendo consulta por producto.
- Cambiar entre estados activo e inactivo de cliente y producto.
- Hacer transferencias entre cuentas, depósitos y retiros
- Historial de de transacciones incluyendo depósitos, transferencias y retiros.
- Movimiento de fondos que permite a los usuarios realizar transferencia de dinero entre sus propias cuentas o hacia cuenta de otros usuarios.

## Cómo se ejecuta

### Ejecutar el proyecto:

- Instalar IntelliJ Idea y Java (preferiblemente 17).
- Clonar o descargar el repositorio desde GitHub y colocarlo en el sistema local.
- Buscar el proyecto desde IntelliJ Idea y abrir build.gradle.kts como un nuevo proyecto.
- Ir a build.gradle.kts y dar clic en "Load script configurations".
- Instalar Docker.
- Ejecutar una terminal en la ubicación del proyecto.
- Ejecutar el siguiente comando para crear la imagen con el archivo jar ubicado en la carpeta build/libs/ :

  docker build --build-arg JAR_FILE=build/libs/*.jar -t myorg/myapp .

  NOTA: En el nombre, "myorg" y "myapp" se pueden personalizar según se requiera.
- Ejecutar el siguiente comando para iniciar la imagen en un contedor de Docker:

  docker run -p 8081:8081 myorg/myapp

  NOTA: Cambiar "myorg" y "myapp" por los nombres previamente asignados.
- Probar desde el navegador el uso de la aplicación.
  

### Uso de la aplicación:

La aplicación se ejecutará en el puerto 8081 del computador, como está especificado en server.port=8081 en la configuración en application.properties.

Puedes utilizar el proyecto de las siguientes formas:
- Accediendo a la API a través de Postman, para poder hacer peticiones HTTP.
- Ingresando la URL "localhost:8081/*", donde podrás hacer peticiones mediante PathVariable, pero no podrás hacer peticiones PUT y POST que utilizan RequestBody.
- Ingresando a Swagger a través del siguiente link "http://localhost:8081/docs-swagger/", donde podrás ejecutar las peticiones igual que en Postman, pero de una manera más organizada y con las estructuras preestablecidas.

### Autenticación

La aplicación cuenta con el método de seguridad Basic Authentication, el cual solicitará un usuario y contraseña para interactuar con la API.

Las siguientes son las credenciales utilizadas:

- Usuario: admin
- Contraseña: admin1234

### Endpoints de la API:

Los siguientes son los endpoints mediante los cuales se puede hacer uso de la API:
#### Cliente
- Agregar un cliente (POST): /cliente/agregar
- Obtener todos los clientes (GET): /cliente/obtener
- Obtener un cliente por ID (GET): /cliente/obtener/{idCliente}
- Desactivar un cliente (PUT): /cliente/desactivar/{idCliente}
- Activar un cliente (PUT): /cliente/activar/{idCliente}
#### Producto
- Agregar un producto (POST): /producto/agregar
- Obtener todos los productos (GET): /producto/obtener
- Obtener producto por ID producto (GET): /producto/obtener/{idProducto}
- Obtener productos por ID cliente (GET): /producto/obtener/cliente/{idCliente]
- Activar producto (PUT): /producto/activar/{idProducto}
- Desactivar producto (PUT): /producto/desactivar/{idProducto}
#### Transaccion
- Nueva transacción (POST): /transaccion/nueva
- Ver todas las transacciones (GET): /transaccion/consultar
- Ver transacciones por ID cliente (GET): /transaccion/consultar/{idCliente}
- Ver transacciones por ID cliente y tipo de transacción (GET): /transaccion/consultar/{idCliente}/{tipoTransaccion}

## Enlaces

https://spring.io/guides/topicals/spring-boot-docker/
