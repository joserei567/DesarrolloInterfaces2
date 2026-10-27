# DesarrolloInterfaces2

Práctica de Jetpack Compose con dos pantallas accesibles desde las pestañas superiores.

## Ejercicio 1: controles
- Presionar actualiza el mensaje a «Botón presionado», cambia cíclicamente entre tres imágenes locales y muestra CircularProgressIndicator durante cinco segundos. Otro clic reinicia el plazo.
- Activar muestra u oculta el Text con el mensaje. Inicialmente está oculto.
- El icono de controles permanece visible en esta pantalla.
- El switch muestra tres RadioButton; seleccionar uno actualiza el mensaje.

## Ejercicio 2: tarjetas
- Cuatro tareas de ejemplo en Card, agrupadas en pendientes y completadas.
- Checkbox y menú Estado permiten completar o recuperar una tarea.
- Cada tarjeta tiene dos DropdownMenu independientes: Estado y Prioridad.
- BadgedBox con Badge muestra la prioridad alta, media o baja. Es el equivalente de Material 3 a BadgeBox.
- HorizontalDivider separa ambos grupos; es el componente Material 3 correspondiente a Divider.
- Los estados se conservan al cambiar de pestaña y durante la recreación de la actividad. No se guarda una base de datos; los datos iniciales vuelven tras un inicio nuevo de la aplicación.

## Abrir y ejecutar
1. Abrir esta carpeta en Android Studio.
2. Esperar a la sincronización de Gradle. El proyecto compila con Android SDK 37.
3. Seleccionar un emulador o móvil con Android 7.0 (API 24) o superior.
4. Ejecutar la configuración app.

El código de ambos ejercicios está en app/src/main/java/com/example/desarrollointerfaces2/MainActivity.kt. Los tres paisajes vectoriales están en app/src/main/res/drawable y no necesitan Internet.

## Comprobación manual
1. Abrir Controles: el mensaje y los radios no deben aparecer.
2. Pulsar Presionar: cambia la imagen y el indicador desaparece a los cinco segundos.
3. Marcar Activar: aparece «Botón presionado».
4. Activar Mostrar opciones y elegir las tres opciones: cambia el mensaje.
5. Pulsar repetidamente Presionar para recorrer las tres imágenes.
6. Abrir Tarjetas, completar una tarea y comprobar que pasa debajo del separador.
7. Usar ambos menús de una tarjeta para recuperar la tarea y cambiar sus prioridades.
8. Cambiar de pestaña y girar el móvil para comprobar que se conserva el estado.

