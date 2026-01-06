# Configuración de entorno (Windows y Linux)

Este documento describe cómo establecer variables de entorno para los desarrolladores del proyecto en ambos sistemas operativos.

## 🪟 Windows

### Script incluido
Se proporciona el script `scripts/setup-env-windows.ps1` que establece `JAVA_HOME` a nivel máquina y, opcionalmente, `MAVEN_HOME` y sus entradas en `Path`.

### Uso (desde PowerShell)
Abrir PowerShell como Administrador y ejecutar:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\setup-env-windows.ps1 -JavaHome "C:\Program Files\Java\jdk-17"
```

Si quieres establecer `MAVEN_HOME` (opcional):

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\setup-env-windows.ps1 -JavaHome "C:\Program Files\Java\jdk-17" -MavenHome "C:\tools\apache-maven-3.9.6"
```

### Notas
- El script intentará relanzarse con privilegios de administrador si no se ejecuta con ellos.
- Los cambios son a nivel `Machine` y requieren abrir una nueva sesión (o reiniciar) para que sean visibles.
- Usar `mvnw` (Maven Wrapper) es preferible para evitar depender de una instalación global de Maven; `MAVEN_HOME` no es estrictamente necesario si usas el wrapper.

## 🐧 Linux / macOS

En sistemas Unix, la configuración se realiza habitualmente en el archivo de perfil de la shell (`.bashrc`, `.zshrc`, etc.).

### Configuración manual

1. Localiza tu instalación de JDK 17. Comúnmente en `/usr/lib/jvm/java-17-openjdk` o `/usr/lib/jvm/jdk-17`.
2. Abre tu terminal y edita el archivo de configuración:
   ```bash
   nano ~/.bashrc
   # O si usas Zsh:
   nano ~/.zshrc
   ```
3. Agrega las siguientes líneas al final:
   ```bash
   export JAVA_HOME="/usr/lib/jvm/jdk-17" # Ajustar ruta según instalación
   export PATH="$JAVA_HOME/bin:$PATH"
   ```
4. Guarda y recarga:
   ```bash
   source ~/.bashrc
   ```