# Rest API de un banco

## Integrantes del equipo

 - Andres Felipe Lanza Torres
 - Diego Mauricio Acosta Rodriguez
 - Joseph Daniel Mantilla Castro

## Funciones del Proyecto

Las siguientes son las funciones con las que cuenta la aplicación:
- Crear Cliente.
- Interacturar con productos del banco(Tipo de cuenta de ahorros y tipo de tarjeta de credito).
- Crear productos nuevos.
- Consultar Saldo incluyendo consulta por producto.
- Cambiar entre estados activo e inactivo de Cliente y Producto.
- Hacer transferencias entre cuentas, depósitos y retiros
- Historial de de Transacciones incluyendo depositos, transferencias y retiros.
- Transferencia de fondos perimite a los usuarios realizar transferencia de dinero entre sus propias cuentas o hacia cuenta de terceros.

## Cómo se ejecuta

### Ejecutar el proyecto:

- Debes tener instalados IntelliJ Idea y Java (preferiblemente 17).
- Clona o descarga el repositorio desde GitHub, y colócalo en tu sistema.
- Desde IntelliJ Idea, busca el proyecto y abre build.gradle.kts como proyecto.
- Ve a build.gradle.kts y da clic en "Load script configurations".
- Ejecuta el proyecto desde SucursalApplication

### Uso de la aplicación:

La aplicación se ejecutará en el puerto 8081 del computador, como está especificado en server.port=8081 en la configuración en application.properties.

Puedes utilizar el proyecto de las siguientes formas:
- Accediendo a la API a través de Postman, para poder hacer peticiones HTTP.
- Ingresando la URL "localhost:8081/*", donde podrás hacer peticiones mediante PathVariable, pero no podrás hacer peticiones PUT y POST que utilizan RequestBody.
- Ingresando a Swagger a través del siguiente link "http://localhost:8081/swagger-ui/index.html#/", donde podrás ejecutar las peticiones igual que en Postman, pero de una manera más organizada y con las estructuras preestablecidas.

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
