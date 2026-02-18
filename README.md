# ITANES Turismo - App Móvil Android
###### *Trabajo final - Desarrollo de aplicaciones móviles*

**ITANES Turismo** es una solución tecnológica móvil desarrollada para la empresa de
turismo nacional ITANES. La aplicación permite a los viajeros acceder a recorridos
guiados, visualizar información multimedia y gestionar rutas a sitios emblemáticos del
país, con la capacidad crítica de funcionar **completamente offline** (sin conexión a
internet) mediante sincronización de datos.

## Funcionalidades principales

De acuerdo con los requerimientos del caso práctico:

* **Modo offline-first**: La aplicación descarga los datos al tener conexión y permite
  la navegación completa sin internet posteriormente.
* **Recorrido Turístico**: Visualización interactiva de 5 puntos turísticos clave por
  recorrido.
* **Multimedia**: Carga efiiente de fotografías y descripciones detalladas de cada lugar.
* **Geolocalización y rutas** Integración con Google Maps para sugerir rutas en auto
  hacia los puntos turísticos.
* **Sistema de favoritos**: Persistencia local para guardar los destinos preferidos del
  usuario.

## Stack Tecnológico

El proyecto está construido sobre una arquitectura robusta y escalable:

* **Lenguaje**: Java 17
* **IDE**: Android Studio 2025.3.1
* **Arquitectura**: MVVM (Model-View-ViewModel) + Repository Pattern
* **Base de datos Local (Offline)**: Room Database
* **Red y API**: Retrofit + GSON
* **Gestión de imágenes**: Glide
* **Mapas**: Google Maps SDK for Android
* **Diseño**: XML layouts con Material Design Components

## Estructura del proyecto

El código fuente sigue el principio package by feature en la capa de UI y Clean
Architecture en la capa de datos.

```txt
com.senati.itanesturismo
 ├── data/                  # Capa de Datos (Single Source of Truth)
 │    ├── local/            # Room (DAO, Database) - Funcionamiento Offline
 │    ├── remote/           # Retrofit (API Client, Services) - Nube
 │    ├── model/            # Entidades y Modelos de Datos
 │    └── repository/       # Lógica de sincronización de datos
 │
 ├── ui/                    # Capa de Presentación (MVVM)
 │    ├── adapters/         # Adaptadores para RecyclerViews
 │    ├── main/             # Pantalla Principal (Listado)
 │    ├── detalle/          # Pantalla de Detalle del lugar
 │    ├── mapa/             # Pantalla de Mapas y Rutas
 │
 └── utils/                 # Constantes y utilitarios
 ```

## Configuración e Instalación

### 1. Clonar el repositorio:

```bash
  git clone https://github.com/tu-usuario/itanesturismo.git
 ```

### 2. Configurar API Keys

* Crear un archivo `secrets.properties` (o editar `local.properties`) en la raíz.
* Agregar tu clave de Google Maps: `MAPS_API_KEY=tu_clave_aquí`.

### 3. Compilar

* Abrir en Android Studio
* Sincronizar Gradle
* Ejecutar en un emulador o dispositivo físico (Min SDK: Android 8.0 "Oreo")

---

*Desarrollado como parte del curso de Desarrollo de Aplicaciones Móviles - SENATI.*