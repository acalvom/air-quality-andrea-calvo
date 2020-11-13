# AirQuality

#### Enlace del proyecto GitHub:

https://github.com/acalvom/air-quality-andrea-calvo.git

> Este proyecto desarrolla una aplicación Android para medir la calidad del aire en ciudades de
España
Desarrollo de aplicación móvil Android para fomentar los Objetivos de Desarrollo SostenibleDesarrollo de aplicación móvil Android para fomentar los Objetivos de Desarrollo Sostenible

### Tecnologías necesarias

`Java` `Android Studio` `GitHub` `Google` `Firebase` `Postman`

### :gear: Instalación del proyecto

Clonar el repositorio en el equipo, **mediante consola**:

```sh
> cd <folder path>
> git clone https://github.com/acalvom/air-quality-andrea-calvo.git
```

### :page_with_curl: Desarrollo de la aplicación

### AIR QUALITY
AIR QUALITY es una aplicación móvil que recupera cinco parámetros que miden la calidad el aire en
una determinada localización.

El ODS relacionado con el desarrollo de la aplicación es el 13 (ODS 13: Acción por el clima) del que se extraen valores relacionados con el clima, concretamente del aire.

#### Registro y autenticación de usuario
Al lanzar la aplicación, el usuario tendrá que registrarse a través de una cuenta de correo electrónico o a través de una cuenta Google para asociar la aplicación con el perfil.

#### Menú principal
- Una vez que el usuario se ha dado de alta en la aplicación, aparece el menú principal de la
aplicación. En esta pantalla el usuario puede solicitar el listado de aquellas ciudades españolas
de las que se puede obtener información del aire.
- En este proceso, la aplicación lanza una petición al API Rest “api.openaq.org” solicitando todas las
ciudades españolas con información sobre la calidad del aire.
- Un total de 115 ciudades están disponibles
- En el menú principal, el usuario tiene la opción de subir a la nube el listado de ciudades obtenido
tras hacer la petición pinchando en el icono de la nube:

#### Listado de localizaciones por ciudad
- Cuando se selecciona una ciudad de la lista de ciudades del menú principal, se lanza una segunda
actividad en la aplicación en la cual se mostrarán todas las estaciones o puntos de toma de medidas
de calidad del aire.
- En este momento, se realiza otra petición al API solicitando todas las localizaciones disponibles
 para esa ciudad (https://api.openaq.org/v1/locations?city=Madrid) y este retorna el código de la
 localización que consta del código del país (ES) más una serie de caracteres que identifican la
 estación, así como las coordenadas (latitud y longitud) de dicha localización (“Code”).


#### Detalles de una localización
Finalmente, cuando el usuario pincha en una de las localizaciones disponibles en la lista, se lanza
una tercera actividad en la que se muestran los detalles de la calidad del aire para ese punto de
medida.
El API devuelve, si están disponibles, cinco parámetros de calidad del aire y son los siguientes:
- CO: Es la cantidad de monóxido de carbono en el aire.
- NO2: Dióxido de nitrógeno
- O3: Ozono
- PM25: Partículas suspendidas con diámetro menor a 2.5 μm
- PM10: Partículas suspendidas con diámetro menor a 10 μm
- SO2: Dióxido de sulfuro
Internamente, en la aplicación se realiza una comparativa para comprobar la calidad del aire. Para ellos, se toman como referencia varios recursos donde se especifican los rangos tolerables para cada parámetro:
- https://en.wikipedia.org/wiki/Air_quality_index
- https://air.plumelabs.com/learn/en/what-is-an-aqi
- https://www.breeze-technologies.de/blog/what-is-an-air-quality-index-how-is-it-calculated/


- Según el nivel de cada parámetro, la aplicación asigna un icono indicando si dicho parámetro se
encuentra o no dentro del rango de calidad.
- En este menú, se permite además subir a la nube las mediciones obtenidas para dicha estación.
Para ello, el usuario tiene que pinchar en el icono de la nube:
