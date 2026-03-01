# Guía de Contribución

Para que podamos trabajar los 5 al mismo tiempo sin sobrescribir el código del otro ni romper la aplicación, vamos a
seguir un flujo de trabajo llamado Feature Branching Simplificado.

Por favor, lee esta guía antes de empezar a programar tu primera tarea del tablero Kanban.

## La Regla de Oro

Nadie hace `push` directo a la rama `main`.
La rama `main` es sagrada. Solo contiene código que funciona al 100% y que está listo para ser presentado.


## Nomenclatura de Ramas

Cada vez que tomes una tarea del tablero, debes crear una rama nueva. El nombre de la rama debe ser en minúsculas,
sin espacios (usa guiones) y descriptivo.

Formato: `tipo/descripcion-corta`

Ejemplos:

- `feature/pantalla-login`
- `ui/diseño-tarjeta-lugar`
- `docs/estructura-word`
- `fix/error-carga-imagen`

---

## El Flujo de Trabajo (Paso a Paso)

Sigue estos 6 pasos cada vez que vayas a programar:

### 1. Actualiza tu código (Pull)

Antes de empezar cualquier tarea nueva, asegúrate de tener los últimos cambios de todo el equipo.

```bash
# 1. Ve a la rama main
git switch main

# 2. Recupera los cambios
git pull origin main
```

### 2. Crea tu rama (Branch)

Desde la rama `main` actualizada, crea tu nueva rama.

```bash
git switch -c feature/ui-detalle
```

### 3. Programar

Escribe tu código, diseña tus XMLs, prueba que funcione en tu emulador.

### 4. Guarda y sube tus cambios (Commit & Push)

Cuando tu tarea esté terminada (o si terminaste por hoy y quieres guardar tu progreso en la nube):

- Haz **Commit** de tus cambios Usa un mensaje claro.
    - *Bien:* `git commit -m "Agrega diseño inicial de activity_detail.xml"`
    - *Mal:* `git commit -m "listo"`

- Haz Push para subir tu rama a GitHub.

### 5. Crea el Pull Request (PR)

Ve a la página del repositorio en GitHub. Verás un botón verde resaltado que dice "Compare & pull request" junto a tu
rama recién subida. Haz clic ahí.

- **Titulo**: Breve descripción de lo que hicise.
- **Descripción**: Explicación detallada de lo que hiciste.

### 6. Revisión y Merge (Aprobación)

Se revisará tu código para asegurarse de que no haya conflictos y que todo compile correctamente.
Si hay algo que corregir, se dejará un comentario.