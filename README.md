# Food Store API

API REST para un sistema de ecommerce de comida, desarrollada con Spring Boot 3 como trabajo práctico integrador de Programación 3.

## Stack

- **Java 21** + Spring Boot 3
- **Spring Security** + JWT
- **Spring Data JPA** + H2 (base de datos en memoria)
- **Lombok** + MapStruct
- **Springdoc OpenAPI** (Swagger UI)

## Cómo ejecutar

### Requisitos

- Java 21
- Gradle (incluido via wrapper)

### Pasos

1. Clonar el repositorio
2. Copiar el archivo de variables de entorno:
   ```bash
   cp .env.example .env
   ```
3. Completar los valores en `.env` (o dejar los valores de ejemplo para desarrollo local)
4. Ejecutar:
   ```bash
   ./gradlew bootRun
   ```

La API queda disponible en `http://localhost:8080`.

## Credenciales por defecto

Al iniciar, el sistema crea automáticamente:

| Rol   | Email              | Password |
|-------|--------------------|----------|
| Admin | admin@admin.com    | 123456   |
| Cliente | juan@mail.com    | cliente1234 |
| Cliente | maria@mail.com   | cliente1234 |

## Documentación

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console

## Endpoints principales

### Auth
| Método | Ruta               | Descripción         | Auth |
|--------|--------------------|---------------------|------|
| POST   | /api/auth/login    | Login               | No   |
| POST   | /api/auth/register | Registro de usuario | No   |

### Categorías
| Método | Ruta                    | Descripción             | Auth  |
|--------|-------------------------|-------------------------|-------|
| GET    | /api/categories         | Listar categorías       | No    |
| GET    | /api/categories/{id}    | Obtener por ID          | No    |
| POST   | /api/categories         | Crear categoría         | Admin |
| PUT    | /api/categories/{id}    | Actualizar categoría    | Admin |
| DELETE | /api/categories/{id}    | Eliminar (soft delete)  | Admin |

### Productos
| Método | Ruta                          | Descripción              | Auth  |
|--------|-------------------------------|--------------------------|-------|
| GET    | /api/products                 | Listar productos         | No    |
| GET    | /api/products/{id}            | Obtener por ID           | No    |
| GET    | /api/products/categoria/{id}  | Listar por categoría     | No    |
| POST   | /api/products                 | Crear producto           | Admin |
| PUT    | /api/products/{id}            | Actualizar producto      | Admin |
| DELETE | /api/products/{id}            | Eliminar (soft delete)   | Admin |

### Usuarios
| Método | Ruta               | Descripción             | Auth  |
|--------|--------------------|-------------------------|-------|
| GET    | /api/users         | Listar usuarios         | Admin |
| GET    | /api/users/{id}    | Obtener por ID          | Admin |
| PUT    | /api/users/{id}    | Actualizar usuario      | Admin |
| DELETE | /api/users/{id}    | Eliminar (soft delete)  | Admin |

### Pedidos
| Método | Ruta                      | Descripción               | Auth       |
|--------|---------------------------|---------------------------|------------|
| GET    | /api/orders               | Listar todos              | Auth       |
| GET    | /api/orders/{id}          | Obtener por ID            | Auth       |
| GET    | /api/orders/usuario/{id}  | Pedidos por usuario       | Auth       |
| POST   | /api/orders               | Crear pedido              | Auth       |
| PUT    | /api/orders/{id}          | Actualizar estado         | Admin      |
| DELETE | /api/orders/{id}          | Eliminar (soft delete)    | Admin      |
