# Parte I: Conceptos generales
1. Una startup de salud digital quiere lanzar una app de gestión de turnos médicos en un
plazo de 4 meses, con el objetivo de comenzar a operar en una red de clínicas privadas.
El sistema deberá permitir que los pacientes reserven turnos online, que los médicos
gestionen su agenda y se enviarán recordatorios de forma automática.
a) Defina con sus palabras el objetivo del proyecto.

El objetivo primordial es el **desarrollo e implementación de un Producto Mínimo Viable (MVP)** de una plataforma transaccional de salud digital en un ciclo de vida comprimido. El sistema debe garantizar la **interoperabilidad** entre la disponibilidad de los profesionales y la demanda de los pacientes, asegurando la integridad de la información y la automatización del flujo de notificaciones para reducir la tasa de ausentismo (_no-show_) en el entorno clínico.

b) Identifique al menos 4 restricciones y explique cómo afectan al proyecto.

- **Plazo (Time-to-Market):** El cronograma de 4 meses es una restricción temporal crítica. Afecta directamente el alcance, obligando a priorizar funcionalidades mediante metodologías como MoSCoW para no comprometer la fecha de lanzamiento.
- **Cumplimiento Normativo (Compliance):** Al tratarse de datos de salud, el sistema debe regirse por leyes de protección de datos personales (como la **LPDP** o **GDPR** según jurisdicción) y estándares de seguridad. Esto restringe las opciones de arquitectura y almacenamiento de datos.
- **Interoperabilidad Técnica:** La integración con los sistemas preexistentes de las clínicas privadas (HIS - _Hospital Information Systems_) puede limitar la flexibilidad del desarrollo, supeditando la app a APIs o protocolos de terceros.
- **Disponibilidad de Recursos:** Siendo una startup, el presupuesto o el tamaño del equipo técnico suele ser limitado. Esto afecta la capacidad de ejecución paralela de tareas, aumentando la dependencia en la ruta crítica del proyecto

c) Indique 3 riesgos concretos del proyecto y para cada uno de ellos indique la causa, el impacto y proponga un posible plan de mitigación.

|**Riesgo**|**Causa**|**Impacto**|**Plan de Mitigación**|
|---|---|---|---|
|**Incumplimiento del "Deadline"**|Subestimación del esfuerzo en el desarrollo de la lógica de agendas complejas.|**Crítico:** Pérdida de oportunidad de mercado y penalizaciones contractuales con las clínicas.|Implementar un desarrollo iterativo e incremental (Scrum) con entregas quincenales para ajustar el alcance de forma temprana.|
|**Vulneración de Datos Sensibles**|Arquitectura de seguridad deficiente o falta de cifrado en el transporte/almacenamiento.|**Catastrófico:** Responsabilidad legal, multas severas y daño irreparable a la reputación de la startup.|Realizar auditorías de seguridad y _pentesting_ desde fases tempranas (Shift Left Security) y asegurar el cumplimiento de estándares como ISO 27001.|
|**Baja Tasa de Adopción por Usuarios**|Interfaz de usuario (UI) compleja o falta de alineación con el flujo de trabajo médico.|**Alto:** El sistema no se utiliza, resultando en un ROI negativo y el fracaso del producto.|Ejecutar pruebas de usabilidad con _stakeholders_ reales (médicos y administrativos) durante el proceso de diseño y prototipado.

2. Elija dos proyectos reales (pueden ser conocidos o hipotéticos): uno exitoso y uno fallido.
a) Describa brevemente cada uno de los proyectos (objetivo, contexto, resultado final).

### Proyecto Exitoso: El Sistema de Control de Vuelo del Space Shuttle (NASA)
- **Objetivo:** Desarrollar un software de tiempo real crítico para controlar todas las fases del vuelo del transbordador espacial, desde el lanzamiento hasta el aterrizaje.
- **Contexto:** Un entorno de **alta criticidad** donde el fallo de una sola línea de código podía resultar en la pérdida de vidas humanas y activos multimillonarios.
- **Resultado Final:** Considerado uno de los hitos de la ingeniería de software. El sistema operó con una tasa de errores prácticamente nula (0.1 errores por cada 1,000 líneas de código) a lo largo de décadas.
### Proyecto Fallido: Denver International Airport Automated Baggage System (DIA)
- **Objetivo:** Implementar un sistema automatizado de gestión de equipajes mediante carros controlados por computadora para reducir los tiempos de conexión entre terminales.
- **Contexto:** Un proyecto de gran envergadura iniciado en la década de los 90, con una complejidad técnica sin precedentes en automatización logística.
- **Resultado Final:** El proyecto falló catastróficamente. Causó un retraso de 16 meses en la inauguración del aeropuerto, con pérdidas de **4 millones de dólares diarios**. Finalmente, el sistema fue desmantelado y reemplazado por un sistema manual

b) Identifique al menos 3 factores clave en cada caso que expliquen el éxito/fracaso del
proyecto.

### Factores de Éxito (NASA)
1. **Cultura de Calidad Extrema:** Se aplicó un enfoque de **Aseguramiento de Calidad (QA)** preventivo, donde se invertía más tiempo en la planificación y el diseño que en la codificación misma.
2. **Procesos de Verificación y Validación (V&V):** Existía un grupo independiente de auditoría que intentaba activamente encontrar fallos en el código desarrollado por el equipo principal.
3. **Especificaciones Rigurosas:** Ninguna línea de código se escribía sin una especificación previa detallada, eliminando la ambigüedad en los requisitos.
### Factores de Fracaso (DIA)
1. **Complejidad Subestimada:** Se intentó aplicar una tecnología experimental a una escala masiva sin considerar los problemas de sincronización en tiempo real de miles de carros.
2. **Gestión de Cambios Deficiente:** Se añadieron requisitos significativos (como la inclusión de todas las aerolíneas en el sistema) cuando la construcción física de los túneles ya estaba avanzada.
3. **Plazo Poco Realista:** El cronograma fue dictado por necesidades políticas y comerciales, ignorando las advertencias técnicas sobre la inviabilidad de los plazos de pruebas necesarios.

c) Explique las diferencias entre ambos proyectos y proponga 3 acciones concretas que
podrían haberse tomado para evitar los problemas del proyecto fallido.

La diferencia fundamental reside en la **gestión de la incertidumbre**. Mientras que la NASA utilizó un enfoque de **"Diseño para el Fallo"** con redundancias y procesos de auditoría exhaustivos, el proyecto de Denver operó bajo un optimismo ciego, omitiendo fases críticas de pruebas integrales en favor de cumplir hitos del calendario.

### Acciones para evitar el fracaso en el proyecto DIA:
1. **Ejecución de un Análisis de Factibilidad Técnica:** Realizar pruebas de concepto (PoC) a pequeña escala antes de comprometer la infraestructura física del aeropuerto a un diseño específico.
2. **Implementación de un Plan de Contingencia (Fallback Plan):** Diseñar un sistema manual paralelo desde el inicio. Al no tener un "Plan B" viable, el aeropuerto quedó rehén de un software que no funcionaba.
3. **Congelamiento de Requisitos (Scope Freezing):** Establecer una línea base de requisitos sólida y rechazar cambios estructurales de último momento que impactaban directamente en la arquitectura lógica y física del sistema.

4. Eleja una organización y describa a qué se dedica (cuál es su misión). Formule un objetivo estratégico para el cual se necesite la ejecución de un programa y luego:
a) Identifique un programa para la implementación del objetivo estratégico que incluya
al menos tres proyectos.
b) Explique por qué los proyectos forman parte del programa.

### Organización: Hospital Público de Alta Complejidad

- **Misión:** Proveer servicios de salud integrales y especializados a la comunidad, garantizando la equidad en el acceso, la excelencia clínica y la formación de profesionales mediante el uso de tecnología avanzada y protocolos basados en evidencia científica.
- Objetivo Estratégico: **Digitalizar el 100% del ciclo de atención al paciente en un plazo de 24 meses**, con el fin de reducir los errores médicos por legibilidad o pérdida de datos en un **40%** y optimizar la eficiencia operativa del hospital mediante la explotación de datos en tiempo real.
a ) Programa: Transformación Digital y Gestión Hospitalaria Paperless
Para alcanzar el objetivo estratégico, se define este programa que articula los siguientes proyectos:
1. **Proyecto de Implementación de Historia Clínica Electrónica (HCE):** Desarrollo o adquisición y despliegue de una plataforma centralizada para el registro clínico de los pacientes, asegurando que toda la información médica (diagnósticos, antecedentes, alergias) sea accesible de forma segura por cualquier departamento.
2. **Proyecto de Infraestructura y Conectividad Robusta:** Actualización de la red de datos del hospital, incluyendo el despliegue de conectividad Wi-Fi de alta disponibilidad en áreas críticas y la renovación del centro de datos (servidores y almacenamiento) para soportar la carga transaccional de la HCE.
3. **Proyecto de Gestión del Cambio y Capacitación del Personal:** Programa de formación sistemática para el personal médico, de enfermería y administrativo en el uso de las nuevas herramientas digitales, incluyendo el diseño de nuevos flujos de trabajo y protocolos de auditoría interna para asegurar la calidad de la carga de datos.

b) Los proyectos mencionados forman parte de un programa porque están **alineados hacia un beneficio común que no podría obtenerse si se gestionaran de forma aislada**:
- **Interdependencia Técnica:** El proyecto de HCE no puede entrar en producción si el proyecto de infraestructura falla; el software requiere una red que soporte el tráfico y garantice la disponibilidad 24/7.
- **Sinergia de Resultados:** La sola instalación de servidores (Infraestructura) o del software (HCE) no garantiza el éxito del objetivo estratégico si el personal no sabe o no quiere utilizar el sistema (Gestión del Cambio). El éxito de la digitalización depende de la convergencia de estos tres pilares.
- **Optimización de Recursos y Riesgos:** Al gestionar estos proyectos bajo una estructura de programa, se pueden mitigar riesgos transversales, como la resistencia cultural al cambio, y optimizar el presupuesto compartiendo costos de consultoría y gestión de auditorías de calidad de software y procesos.
# Parte II: Planificación y WBS
1. Una universidad necesita desarrollar un sistema de inscripción a materias online. Dicho
sistema deberá permitir que los alumnos se inscriban a las materias, validar las correlatividades y generar listados para los docentes.
a) Proponga una descomposición del proyecto en actividades principales (nivel 1) y
descompónganla en tareas (nivel 2).

### Descomposición del Proyecto
**1. Relevamiento y Análisis de Requisitos**
- 1.1. Identificación de perfiles de usuario (Alumno, Docente, Administrativo).
- 1.2. Especificación de reglas de negocio para validación de correlatividades.
- 1.3. Definición de formatos de reportes y listados docentes.
**2. Diseño de Arquitectura y Datos**
- 2.1. Modelado del esquema de base de datos (Entidad-Relación).
- 2.2. Diseño de la API de servicios para inscripciones.
- 2.3. Diseño de interfaces de usuario (UX/UI) para web y móvil.
**3. Desarrollo y Construcción**
- 3.1. Implementación del módulo de autenticación y perfiles.
- 3.2. Codificación del motor de validación de correlatividades.
- 3.3. Desarrollo del generador de listados y reportes en PDF/Excel.
**4. Aseguramiento de Calidad (QA) y Testing**
- 4.1. Ejecución de pruebas unitarias sobre las reglas de correlatividad.
- 4.2. Pruebas de carga para simular picos de inscripción concurrentes.
- 4.3. Pruebas de aceptación de usuario (UAT).
**5. Despliegue y Cierre**
- 5.1. Configuración del entorno de producción.
- 5.2. Migración de datos históricos de alumnos y materias.
- 5.3. Capacitación a personal administrativo y soporte técnico.

b) Explique qué criterio utilizó (por fases, entregables, etc.) y justifique por qué es
adecuado para este proyecto.

Se utilizó un criterio de **descomposición por fases del ciclo de vida de desarrollo de software (SDLC)**. Este enfoque es el más adecuado para un sistema con reglas de negocio rígidas (correlatividades) y alta criticidad operativa (inscripciones universitarias). Permite establecer hitos de control claros y asegura que no se inicie la codificación sin una validación previa de los requisitos lógicos, minimizando el riesgo de retrabajo por malentendidos en la normativa académica.

2. Revise el siguiente conjunto de tareas y determine cuáles NO cumplen criterios de completitud. Justifique y corríjalas:
- “Desarrollar sistema” : No cumple. Es un objetivo o un proyecto en sí mismo, no una tarea. Carece de granularidad y no permite el seguimiento del progreso. Podia ser "Implementar el módulo de validación de cupos por comisión" pero depende de que sea el sistema, que se quiera implementar y las demás tareas que se dispongan. Asi solito podria ser cualquier cosa (dicho sea de paso que idiota hizo esa tarea??????)
- “Hacer pruebas”:  No cumple. Es ambigua. No especifica qué se prueba, bajo qué condiciones ni cuándo se considera finalizada (criterio de aceptación).“Ejecutar pruebas integrales sobre el flujo de inscripción de alumnos regulares” seria más apropiado.
- “Diseñar base de datos en 2 días”:  No cumple. Mezcla la descripción de la actividad con una restricción temporal o estimación. Las tareas en la EDT deben centrarse en el resultado (output).“Elaborar el diagrama lógico de la base de datos y el diccionario de datos”.
3. Explique con un ejemplo la diferencia entre duración y esfuerzo, mostrando cómo cambia al agregar recursos.
- **Esfuerzo:** Es la cantidad de unidades de trabajo necesarias para completar una tarea (persona-hora, persona-día). Es una medida absoluta de trabajo.
- **Duración:** Es el tiempo calendario que transcurre desde el inicio hasta el fin de la tarea.
Supongamos una tarea que requiere  un **esfuerzo de 40 horas**:
- **Escenario A (1 recurso):** Si trabaja 8 horas diarias, la **duración** será de **5 días**.
- **Escenario B (2 recursos):** Si se asignan dos personas al mismo tiempo (asumiendo que la tarea es perfectamente divisible y no hay penalización por comunicación), el **esfuerzo sigue siendo de 40 horas**, pero la **duración se reduce a 2.5 días**.

> **Nota:** Agregar recursos no siempre reduce la duración de forma lineal (Ley de Brooks), debido al incremento en el "overhead" de comunicación y coordinación.
# Parte III: Costos
1. Una empresa quiere desarrollar un sistema pequeño de gestión interna (aproximadamente 10 KLOC):
a) Clasifique el tipo de sistema (orgánico / semi-embebido / embebido) y justifique.

De acuerdo al modelo **COCOMO (Constructive Cost Model)**, este sistema se clasifica como **Orgánico**.
**Justificación:**
- **Tamaño:** Con un volumen de aproximadamente **10 KLOC** (diez mil líneas de código fuente), se considera un proyecto pequeño.
- **Naturaleza:** Los sistemas de gestión interna suelen tener requisitos flexibles y un entorno de desarrollo familiar.
- **Equipo:** Generalmente, estos proyectos son abordados por equipos pequeños con experiencia previa en aplicaciones similares, lo que reduce la necesidad de protocolos de comunicación complejos.
- **Restricciones:** Al ser una herramienta interna, no suele enfrentar restricciones de hardware extremas ni requisitos de seguridad de tiempo real críticos (como sí ocurriría en un modo embebido).

>[!Note] los otros dos
>## 1. Modo Orgánico
>Se refiere a proyectos de software pequeños y sencillos.
>- **Contexto:** El equipo de desarrollo es pequeño y posee amplia experiencia en proyectos similares. Los requisitos no son estrictos y el entorno de desarrollo es muy familiar.
>  - **Características:** Bajo nivel de incertidumbre, mínima necesidad de comunicación formal y gran flexibilidad en los objetivos.
>  - **Ejemplo:** Sistemas de gestión de inventarios locales o aplicaciones de automatización de oficina.
>## 2. Modo Semi-Embebido
> Es una categoría intermedia que combina características de los otros dos modos. 
> - **Contexto:** El proyecto presenta un tamaño y complejidad moderados. El equipo posee una experiencia mixta (algunos expertos, algunos novatos) y se enfrenta a requisitos con niveles de rigidez variables.
> - **Características:** Incluye interfaces con hardware específico o sistemas operativos con restricciones moderadas. Requiere una gestión de proyectos más estructurada que el modo orgánico.
> - **Ejemplo:** Sistemas de gestión de bases de datos (DBMS) o nuevos intérpretes de lenguajes de programación.
>## 3. Modo Embebido
> Representa el nivel más alto de complejidad y restricción.
> - **Contexto:** El software debe operar dentro de un conjunto de restricciones muy estrictas (hardware, regulaciones, protocolos de red). A menudo, el software y el hardware se desarrollan en paralelo.
> - **Características:** Alta criticidad (el fallo puede ser catastrófico). El equipo suele ser grande y multidisciplinar. No hay flexibilidad en los requisitos de tiempo de respuesta o seguridad. 
>- **Ejemplo:** Sistemas de control de tráfico aéreo, software de control de reactores nucleares o sistemas de frenado ABS.

b) Identifique al menos 5 factores que podrían afectar la estimación. Explique cómo
impacta cada uno.
	1. **Confiabilidad requerida del software (RELY):** Si el sistema gestiona datos financieros o críticos para la operación diaria, el esfuerzo aumenta. La necesidad de auditorías y pruebas más exhaustivas eleva el multiplicador de esfuerzo.
	2. **Experiencia en la aplicación (APEX):** Si el equipo de desarrollo ya ha construido sistemas de gestión interna anteriormente, la productividad es mayor. La falta de experiencia técnica o de dominio del negocio puede incrementar el esfuerzo necesario para completar las mismas 10 KLOC.
	3. **Complejidad del producto (CPLX):** El uso de algoritmos complejos de optimización (ej. para asignación de recursos) o integraciones con múltiples bases de datos legacy aumenta la dificultad, extendiendo la duración del proyecto.
	4. **Restricciones de memoria y tiempo de ejecución (TIME/STOR):** Aunque sea un sistema pequeño, si debe operar en hardware limitado o requiere tiempos de respuesta inmediatos para auditorías en tiempo real, el equipo invertirá más tiempo en optimizar el código que en implementar funcionalidades nuevas.
	5. **Capacidad de los analistas (ACAP):** La eficiencia en la captura y análisis de requisitos es vital. Analistas con baja capacidad para definir el alcance del sistema de gestión pueden generar ambigüedades, lo que deriva en retrabajo y una subestimación del esfuerzo inicial.

2. Explique por qué las estimaciones iniciales suelen ser inexactas y proponga dos
estrategias concretas para mejorar la estimación.
Cono de Incertidumbre.  
Al inicio del ciclo de vida, la variabilidad puede oscilar entre 0.25x y 4x del esfuerzo real debido a los siguientes factores:
- **Ambigüedad de Requisitos:** En fases preliminares, el alcance suele ser difuso. La falta de definiciones funcionales y no funcionales detalladas impide un cálculo preciso.
- **Desconocimiento Tecnológico:** La curva de aprendizaje de nuevas herramientas o arquitecturas no siempre es lineal ni previsible.
- **Efectos Sistémicos y Emergentes:** La complejidad de integración y la deuda técnica no son visibles hasta que el desarrollo está avanzado.
- **Sesgo Cognitivo (Optimismo del Desarrollador):** Existe una tendencia natural a estimar basándose en el "camino feliz" (_happy path_), omitiendo el tiempo necesario para depuración, documentación y reuniones.
Estrategias para mejorar la estimación:
- **Refinamiento Iterativo (Re-estimación):** Aplicar la técnica de estimación continua. A medida que el proyecto avanza y la incertidumbre disminuye, se deben recalcular los costos y plazos en cada hito o iteración.
- **Uso de Datos Históricos y Métricas Reales:** Implementar un repositorio de métricas organizacionales. Basar las estimaciones en proyectos previos de similar complejidad y tecnología reduce la subjetividad y permite ajustar los multiplicadores de esfuerzo según la productividad real del equipo.

2. Compare al menos dos técnicas de estimación aplicadas a un mismo caso práctico. Indique ventajas y limitaciones.

**Caso Práctico:** Desarrollo de un módulo de autenticación biométrica para una app financiera.
- Juicio de expertos:
	- **Ventajas:**
	    - **Agilidad:** Es la técnica más rápida de implementar.
	    - **Contextualización:** El experto puede identificar riesgos específicos (ej. "el SDK de este sensor suele dar problemas de latencia") que un modelo matemático ignoraría.
	- **Limitaciones:**
	    - **Sesgo de Optimismo/Pesimismo:** Depende totalmente del estado subjetivo y la memoria del experto.
	    - **Efecto Halo:** Si se hace en grupo, la opinión del perfil con más jerarquía suele anular las demás (pensamiento de grupo).
- Delphi banda ancha:
	- **Ventajas:**
	    - **Eliminación de Sesgos Grupales:** Al ser anónimo, se evita que los expertos se dejen influenciar por presiones sociales o jerarquías.    
	    - **Fundamentación:** Obliga a los participantes a justificar sus posturas, lo que a menudo revela riesgos ocultos que un solo experto podría pasar por alto.    
	- **Limitaciones:**
	    - **Consumo de Tiempo:** Requiere múltiples rondas y un coordinador que procese la información. No es apto para decisiones inmediatas.    
	    - **Dependencia del Coordinador:** Si el resumen de las rondas está sesgado por quien lo redacta, el resultado final será erróneo.

3. Elija una situación en un proyecto de software en la que una mala estimación haya generado un problema relevante durante su desarrollo.

Caso: El Censo de Salud y Migración (Sistema de Gestión Gubernamental)

a) Descríbalo brevemente, teniendo en cuenta el tipo de sistema, tamaño, complejidad, plazo, etc.

- **Tipo de sistema:** Sistema de Información Geográfica y Transaccional (Modo **Semi-embebido** debido a la necesidad de interoperabilidad con bases de datos civiles preexistentes).
- **Tamaño:** Estimado inicialmente en **40 KLOC**.
- **Complejidad:** Alta, debido a requisitos de sincronización de datos en zonas sin conectividad (offline-first) y el procesamiento de datos biométricos.
- **Plazo:** 6 meses, dictado por una fecha inamovible de inicio del relevamiento de campo a nivel nacional.
- **Contexto:** Una startup contratada por el Estado para digitalizar el censo, reemplazando formularios en papel por tablets con una app dedicada.

b) Indique cuál fue la estimación incorrecta y explique los motivos.

La estimación inicial de esfuerzo fue de **80 personas-mes**, basada en un **Juicio de Expertos informal** realizado por los socios de la startup durante la fase de licitación.

**Motivos de la desviación:**
1. **Omisión de requisitos no funcionales:** Se estimó el desarrollo de la interfaz y el almacenamiento, pero se ignoró el esfuerzo necesario para la seguridad de extremo a extremo y el cifrado de datos sensibles (salud), lo que aumentó la complejidad del producto (**CPLX**).
2. **Sesgo de disponibilidad:** Los expertos basaron su juicio en un proyecto de e-commerce anterior, ignorando que el personal técnico no tenía experiencia en sincronización de bases de datos distribuidas (**APEX** bajo), lo que resultó en una productividad real mucho menor a la prevista.
3. **Presión comercial:** El plazo fue "estimado" para encajar en el presupuesto del cliente, forzando una fecha de entrega sin un análisis de factibilidad técnica.

c) Describa al menos 2 consecuencias concretas que produjo esta mala estimación y cómo las corregiría.

#### Consecuencias:
1. **Deuda Técnica Crítica:** Para cumplir con el plazo, se omitieron las fases de **Pruebas de Carga** y **Auditoría de Seguridad**. Al lanzar la app, el servidor colapsó cuando 5,000 censistas intentaron sincronizar datos simultáneamente, provocando la pérdida de registros médicos reales.
2. **Sobrecosto y Desmotivación (Ley de Brooks):** Se intentó corregir el retraso contratando 5 desarrolladores adicionales en el cuarto mes. Esto aumentó la duración debido al tiempo que los desarrolladores originales perdieron capacitando a los nuevos, empeorando el retraso del cronograma.
#### Acciones para corregir/evitar:
1. **Implementación de Puntos de Función (FP):** Para un proyecto con el Estado, se debió realizar un conteo de FP para obtener una métrica objetiva del tamaño. Esto habría revelado que la complejidad de las interfaces externas (bases de datos gubernamentales) requería un 50% más de esfuerzo del previsto inicialmente.
2. **Sinceramiento del Alcance (MVP):** Ante la imposibilidad de cumplir el plazo, la acción correcta debió ser negociar un **alcance reducido** (por ejemplo, sincronización manual en lugar de automática) para asegurar la calidad de las funciones básicas de carga de datos, en lugar de entregar un sistema completo pero inestable.
# Parte IV: Gestión de proyectos
1. Una municipalidad quiere implementar un sistema digital para gestionar reclamos ciudadanos.
a) Identifique al menos 4 stakeholders.

- **Ciudadanos (Usuarios Finales):** Personas que reportan incidencias en la vía pública o servicios.
- **Secretaría de Servicios Públicos / Operativa:** Personal encargado de ejecutar las reparaciones o soluciones técnicas.
- **Intendente / Gabinete Municipal:** Autoridades políticas responsables del presupuesto y la imagen institucional.
- **Equipo de Soporte IT / Auditoría:** Responsables del mantenimiento, seguridad de datos y transparencia del sistema.

b) Para cada stakeholder explique el interés, la influencia y las expectativas sobre el sistema.

|**Stakeholder**|**Interés**|**Influencia**|**Expectativas**|
|---|---|---|---|
|**Ciudadanos**|Alto: Desean que sus problemas locales se resuelvan.|Baja: Individualmente no deciden sobre el sistema, aunque su adopción es el éxito del proyecto.|Interfaz intuitiva (UX), seguimiento en tiempo real y respuestas rápidas.|
|**Servicios Públicos**|Alto: El sistema dicta su agenda de trabajo diaria.|Alta: Sus procesos actuales deben integrarse al software; si hay resistencia, el sistema falla.|Ordenamiento de tareas, reducción de burocracia y reportes claros de ubicación.|
|**Intendente**|Medio/Alto: Mejora de la imagen pública y eficiencia del gasto.|Muy Alta: Posee el control presupuestario y la decisión final de implementación.|Tableros de control (Dashboards) para toma de decisiones y estadísticas de gestión.|
|**Soporte IT**|Medio: Estabilidad técnica y seguridad de la información.|Media: Definen los estándares técnicos y la infraestructura necesaria.|Interoperabilidad con bases de datos existentes y bajo costo de mantenimiento.|

c) Construya una matriz de impacto y explique 2 decisiones tomadas a partir de ella.

La matriz de impacto (o mapa de stakeholders) agrupa a los involucrados en cuadrantes según su nivel de **Influencia (Poder)** y su nivel de **Interés**, permitiendo al líder de proyecto definir cómo gestionarlos:

| **Poder \ Interés** | **Bajo Interés**                                                              | **Alto Interés**                                                         |
| ------------------- | ----------------------------------------------------------------------------- | ------------------------------------------------------------------------ |
| **Alto Poder**      | **Mantener Satisfechos:** Proveedores de infraestructura / Entes reguladores. | **Gestionar Atentamente:** Intendente, Secretaría de Servicios Públicos. |
| **Bajo Poder**      | **Monitorear (Mínimo esfuerzo):** Otros municipios, prensa general.           | **Mantener Informados:** Ciudadanos, ONGs vecinales.                     |

- **Involucramiento Temprano de Servicios Públicos:** Dado que tienen **Alto Poder y Alto Interés**, se decide incluirlos en la fase de relevamiento de requisitos para asegurar que el sistema no sea solo una "boca de entrada" de reclamos, sino una herramienta de gestión operativa funcional. Esto mitiga el riesgo de boicot por parte del personal de campo.
- **Canales de Comunicación Diferenciados para el Ciudadano:** Al tener **Bajo Poder pero Alto Interés**, la estrategia se centra en "Mantener Informados". Se decide implementar notificaciones automáticas vía email o SMS. Aunque no influyen en el desarrollo técnico, su satisfacción depende de la transparencia del proceso, lo que garantiza el éxito político del proyecto.

2. Identifique 3 beneficios concretos y medibles que se espera obtener con la implementación del proyecto anterior, y para cada uno:
	a) Describa el beneficio de manera específica.
	b) Defina cómo y en qué momento se podría evaluar.
	c) Especifique qué resultado se esperaría observar.
	- Reducción del Tiempo de Ciclo de Resolución (Efficiency)
		- **a) Descripción:** Disminución del tiempo transcurrido desde que el ciudadano registra el reclamo hasta que la Secretaría de Servicios Públicos marca la tarea como "Finalizada" tras la reparación.
		- **b) Evaluación:** Se evalúa mediante la extracción de métricas de la base de datos (Timestamp Final - Timestamp Inicial). Se realiza un corte trimestral comparando contra los registros históricos del sistema manual previo.
		- **c) Resultado esperado:** Una reducción del **25% en el tiempo de respuesta** promedio en los primeros 6 meses de implementación.
	- Disminución de Reclamos Duplicados (Data Integrity)
		- **a) Descripción:** Capacidad del sistema para identificar, mediante geolocalización, si un nuevo reporte se refiere a una incidencia ya registrada (ej. un mismo bache reportado por 10 vecinos).
		- **b) Evaluación:** Auditoría de la base de datos comparando el número de "incidentes únicos" vs. "reportes totales recibidos". Se evalúa mensualmente a través del panel de control de IT.
		- **c) Resultado esperado:** Que el **95% de los reclamos duplicados** sean detectados y unificados automáticamente por el sistema, evitando el desperdicio de recursos logísticos.
	- Tasa de Adopción Digital (Engagement)
		- **a) Descripción:** Incremento del uso de la plataforma digital sobre los canales tradicionales (teléfono o presencial) para la gestión de incidencias.
		- **b) Evaluación:** Comparativa mensual del volumen de reclamos por canal. Se mide al finalizar cada cuatrimestre mediante reportes de gestión.
		- **c) Resultado esperado:** Que al menos el **60% de los reclamos totales** de la municipalidad ingresen a través de la app o portal web tras el primer año.
3. Diseñe un plan de comunicación básico para el proyecto.

|**Audiencia**|**Objetivo**|**Canal**|**Frecuencia**|**Contenido Clave**|
|---|---|---|---|---|
|**Intendente / Gabinete**|Rendición de cuentas y soporte político.|Reporte ejecutivo (Dashboard).|Mensual|Estado de hitos, presupuesto ejecutado y métricas de impacto ciudadano.|
|**Secretaría de Servicios Públicos**|Coordinación operativa y técnica.|Reuniones de seguimiento / Email.|Quincenal|Flujos de trabajo, resolución de bloqueos técnicos y capacitación en el uso.|
|**Ciudadanos**|Fomentar la adopción y confianza.|Redes sociales, web municipal y prensa.|Semanal (fase lanzamiento)|Tutoriales de uso, beneficios de la digitalización y casos de éxito (reclamos resueltos).|
|**Equipo de Soporte IT**|Sincronización técnica y QA.|Stand-up meetings (Daily).|Diaria|Avance de tareas (Sprints), gestión de bugs y despliegues en entornos de prueba.|
# Parte V: Ejercicios
1. Una universidad desea implementar un “Sistema Integral Académico” en 3 meses, el cual
debe permitir: gestionar inscripciones a materias, administrar notas, permitir comunicación entre docentes y alumnos e integrarse con sistemas existentes.
El proyecto aún no tiene un presupuesto definido, cuenta con un equipo de 5 integrantes,
no hay claridad sobre los requerimientos, se aplica a distintas áreas (alumnos, docentes,
administración) con expectativas diferentes y las autoridades esperan que el sistema
“resuelva todos los problemas actuales”.
a) Detecte al menos 5 problemas concretos del proyecto.
- **Plazo irreal para la complejidad descrita:** Intentar un sistema integral con integraciones en solo 3 meses es técnicamente inviable para un equipo de 5 personas
- **Ambigüedad de requisitos:** La falta de claridad ("resuelva todos los problemas") impide la creación de una línea base de alcance.
- **Ausencia de presupuesto:** Sin un análisis de costos, no se puede determinar la factibilidad de infraestructura o licencias necesarias.
- **Conflicto de intereses de Stakeholders:** Expectativas divergentes entre alumnos, docentes y administración sin un proceso de mediación.
- **Subestimación de la integración:** Integrar con sistemas existentes (_Legacy Systems_) suele ser la tarea más compleja y la que más riesgos de auditoría presenta.
b) Clasifique los problemas en categorías (planificación, alcance, recursos, …) y justifique brevemente cada clasificación.

|**Categoría**|**Problema**|**Justificación**|
|---|---|---|
|**Planificación**|Plazo de 3 meses|No existe un cronograma basado en estimaciones técnicas, sino en una imposición externa (plazo arbitrario).|
|**Alcance**|Falta de claridad en requisitos|El alcance es "abierto", lo que garantiza el _Scope Creep_ (corrimiento del alcance) y el fracaso del proyecto.|
|**Recursos**|Presupuesto no definido|La gestión de recursos financieros es el soporte de los recursos técnicos; sin él, el proyecto carece de sustento legal y operativo.|
|**Gestión de Stakeholders**|Expectativas irreales|Se espera una solución mágica ("resolver todo"), lo que indica una falla en la gestión de expectativas y comunicación.|
|**Técnica / Calidad**|Integración con sistemas existentes|Representa un riesgo de interoperabilidad que requiere auditorías previas de los sistemas actuales.|

c) Redefina el proyecto proponiendo un objetivo claro, un alcance acotado y restricciones realistas.

**Objetivo:** Implementar un **Módulo Central de Gestión de Inscripciones y Actas** que digitalice el proceso principal de alumnos y docentes para el próximo ciclo lectivo.
- **Alcance Acotado:** 1. Autenticación de usuarios (Docentes/Alumnos). 2. Inscripción online a materias del cuatrimestre vigente. 3. Carga y visualización de notas finales. 4. _Exclusión:_ Se posterga la comunicación interna y las integraciones complejas para una Fase 2.
- **Restricciones Realistas:**
    1. El equipo de 5 personas se enfocará exclusivamente en el desarrollo core.
    2. Se utilizará la infraestructura de servidores actual para evitar demoras en compras.
    3. El sistema operará de forma independiente con una migración de datos única (sin integración en tiempo real inicial).

d) Defina entre 5 y 10 tareas principales y realice un WBS de no más de 2 niveles.
**1. Gestión y Análisis de Requisitos**
- 1.1. Definición de procesos de inscripción y reglas de negocio.
- 1.2. Especificación de casos de uso para Alumnos y Docentes.

**2. Diseño de Arquitectura y Base de Datos**
- 2.1. Modelado del esquema de datos académico (Materias, Notas, Alumnos).
- 2.2. Diseño de wireframes y prototipos de interfaz de usuario.

**3. Desarrollo del Módulo de Inscripción**
- 3.1. Implementación de lógica de validación de correlatividades.
- 3.2. Desarrollo de la interfaz de selección de materias.

**4. Desarrollo del Módulo de Calificaciones**
- 4.1. Creación de paneles de carga para docentes.
- 4.2. Generación de reportes de situación académica para alumnos.

**5. Verificación y Validación (V&V)**
- 5.1. Ejecución de pruebas unitarias y de integración.
- 5.2. Pruebas de Aceptación de Usuario (UAT) con personal administrativo.

**6. Despliegue y Puesta en Marcha**
- 6.1. Migración de padrones de alumnos y oferta académica.
- 6.2. Publicación en entorno de producción y soporte inicial.

==Recordar preguntar que tarea o actividad completa mato, tengo 12 tareas y 10 me pide el enunciado==
3. Una empresa de salud quiere implementar un sistema para mejorar el bienestar de sus
empleados, con el objetivo de reducir costos de salud mediante programas de prevención.
Condiciones: 6 meses de plazo, presupuesto limitado y múltiples áreas involucradas.
a) Identifique al menos 5 problemas potenciales.

- **Fragmentación de datos de salud:** La información reside en silos (sistemas de RR.HH., seguros médicos externos y registros manuales), dificultando la interoperabilidad necesaria para programas preventivos.
- **Baja tasa de adopción (Engagement):** Los empleados pueden percibir el sistema como una carga adicional o una herramienta de vigilancia, lo que anula la recolección de datos para la prevención.
- **Riesgos de Cumplimiento y Privacidad (Compliance):** El manejo de datos sensibles de salud bajo un presupuesto limitado puede derivar en arquitecturas de seguridad deficientes, vulnerando normativas como la LPDP.
- **Conflictos de prioridad interdepartamentales:** Con múltiples áreas involucradas, los requisitos de RR.HH. (clima laboral) pueden colisionar con los de Finanzas (reducción de costos) o Medicina Laboral (clínica).
- **Subestimación del costo de integración:** Con un presupuesto limitado, los conectores con APIs de proveedores de salud externos suelen ser más costosos y complejos de lo previsto.

b) Proponga soluciones para dichos problemas y justifique el impacto de los mismos.

| **Problema**          | **Solución Propuesta**                                                                             | **Impacto y Justificación**                                                                                              |
| --------------------- | -------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| **Privacidad**        | Implementar **Privacidad desde el Diseño (PbD)** y anonimización de datos en reposo.               | Mitiga riesgos legales catastróficos y fomenta la confianza del empleado, facilitando la adopción.                       |
| **Adopción**          | Gamificación y recompensas vinculadas a objetivos de bienestar no intrusivos.                      | Transforma la percepción de "control" a "beneficio", garantizando una masa crítica de datos para el análisis preventivo. |
| **Silos de datos**    | Desarrollo de una **capa de integración mínima (Middleware)** basada en estándares HL7/FHIR.       | Permite centralizar la información sin necesidad de reemplazar sistemas legacy, optimizando el presupuesto.              |
| **Plazo/Presupuesto** | Adopción de un enfoque **MVP (Producto Mínimo Viable)** priorizando telemedicina básica y alertas. | Asegura el cumplimiento del plazo de 6 meses al evitar el _scope creep_ y concentrar recursos en funciones de alto ROI.  |
| **Conflictos**        | Establecimiento de un **Comité de Gobernanza del Proyecto** con representantes de cada área.       | Alinea las expectativas y agiliza la toma de decisiones, evitando cuellos de botella burocráticos.                       |

c) Identifique 3 stakeholders clave, explique por qué son críticos, qué problema podrían
generar y cómo gestionarlos.

**Director de Recursos Humanos (CHRO):**
- **Por qué es crítico:** Es el dueño del proceso de cultura organizacional.
- **Problema potencial:** Puede priorizar métricas de felicidad sobre métricas de salud técnica, desviando el objetivo de reducción de costos.
- **Gestión:** Involucrarlo en la definición de KPIs de bienestar que tengan correlación directa con la productividad.
**Oficial de Seguridad de la Información (CISO):**
- **Por qué es crítico:** Debe validar que el manejo de datos de salud cumpla con los estándares de auditoría.
- **Problema potencial:** Puede bloquear el despliegue en la fase final si la arquitectura no cumple con protocolos de cifrado robustos.
- **Gestión:** Integrarlo desde la fase de diseño (Shift Left Security) para evitar retrabajo al final del plazo.
 **Empleados de la empresa:**    
   - **Por qué son críticos:** Son los generadores de los datos primarios; sin su participación, el sistema carece de valor.
   - **Problema potencial:** Resistencia al cambio o sospecha de que los datos influyan en despidos o primas de seguro.
 - **Gestión:** Comunicación transparente sobre el uso de datos y garantías de anonimato para fines preventivos.        

d) Genere el mapa de los stakeholder y justifique su elección.

|**Poder \ Interés**|**Bajo Interés**|**Alto Interés**|
|---|---|---|
|**Alto Poder**|**Mantener Satisfechos:** Departamento Legal (Privacidad), CISO.|**Gestionar Atentamente:** Dirección de RR.HH., Finanzas (CFO), Medicina Laboral.|
|**Bajo Poder**|**Monitorear:** Proveedores externos de seguros, Sindicatos.|**Mantener Informados:** Empleados (Usuarios finales).|
- **Gestión Atenta (RR.HH. y Finanzas):** Son quienes financian y definen el éxito del proyecto (reducción de costos vs. bienestar). Su alineación es vital para evitar el fracaso por falta de presupuesto o dirección.
- **Mantener Satisfechos (Legal y CISO):** Aunque no operan el sistema diariamente, tienen el poder de veto por incumplimiento normativo. Sus requisitos de auditoría son restricciones no negociables. 
- **Mantener Informados (Empleados):** Su interés es alto porque afecta su vida diaria, pero su poder individual para cambiar el software es bajo. La transparencia aquí reduce el riesgo de baja adopción.