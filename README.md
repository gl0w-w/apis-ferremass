Requisitos
----------

primero que nada, revizar si tienes instalado:

- Java 17 o superior
- Postman (para pruebas de la API)

---

Ejecución del proyecto
----------------------

1. Clonar o descargar el repositorio

Clonar el repositorio o descargarlo de manera manual.

2. Verficar si estas loggeado en Postman

Este paso es importante ya que si no estas loggeado no te dejara agregar el archivo al Postman.

3. Importar la colección de Postman

- Abre Postman.
- Ve a *File > Import*.
- Selecciona el archivo `api_ferremas_collection.json` desde la carpeta `/postman` del proyecto o arrástralo a la interfaz de Postman.
- La colección será importada con todos los endpoints listos para usar.

4. Ejecutar la API

- Abre el proyecto en un IDE como **IntelliJ IDEA** o **Visual Studio Code**.
- Ejecuta el proyecto en modo *Run and Debug*.
- La API estará disponible en: `http://localhost:8080`

---

Pruebas de la API (orden recomendado)
-------------------------------------

a) Crear una Sucursal
- Método: `POST`
- Endpoint: `/sucursales`
- Cuerpo (JSON):
```json
{
  "nombre": "Sucursal Centro",
  "direccion": "Av. Principal 123, Ciudad"
}
```

b) Crear un Producto
- Método: `POST`
- Endpoint: `/productos`
- Cuerpo (JSON):
```json
{
  "nombre": "Martillo de acero",
  "categoria": "herramientas",
  "precio": 45.99,
  "stock": 30,
  "sucursales": [
    { "id": 1 }
  ]
}
```

c) Consultar productos por sucursal
- Método: `GET`
- Endpoint: `/sucursales/{id}/productos`
- Reemplaza `{id}` con el ID correspondiente (por ejemplo: `1`)

---

Otros Endpoints Disponibles
---------------------------

| Método | Endpoint             | Descripción                                 |
|--------|----------------------|---------------------------------------------|
| GET    | `/productos`         | Lista todos los productos o con filtros     |
| GET    | `/productos/{id}`    | Obtiene un producto por su ID               |
| PUT    | `/productos/{id}`    | Reemplaza completamente un producto         |
| PATCH  | `/productos/{id}`    | Actualiza parcialmente un producto          |
| DELETE | `/productos/{id}`    | Elimina un producto                         |
