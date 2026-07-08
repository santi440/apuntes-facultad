# Práctica 13 — Introducción a la Inteligencia Artificial

**Bibliografía** — Disponible en Ideas
- Alan Turing, 1950. *Maquinaria computacional e Inteligencia*

**Temario**
- Introducción a la Inteligencia Artificial.

---

**Ejercicios**

1. **a)** Defina Inteligencia e Inteligencia Artificial.

   **Inteligencia:** Capacidad de adquirir, comprender y aplicar conocimiento, resolver problemas, razonar, aprender de la experiencia y adaptarse al entorno.

   **Inteligencia Artificial:** Rama de la computación que busca crear sistemas capaces de realizar tareas que, cuando son realizadas por humanos, requieren inteligencia. Implica modelar funciones cognitivas como el aprendizaje, el razonamiento, la percepción y la toma de decisiones.

   **b)** Dé ejemplos de sistemas de IA actuales.

   - Asistentes virtuales (Siri, Alexa, Google Assistant)
   - Sistemas de recomendación (Netflix, Spotify, YouTube)
   - Reconocimiento facial y de voz
   - Vehículos autónomos (Tesla, Waymo)
   - Modelos de lenguaje (ChatGPT, Gemini, Claude)
   - Diagnóstico médico asistido por IA
   - Traductores automáticos (Google Translate, DeepL)

   **c)** Explique la diferencia entre IA débil e IA fuerte.

   **IA débil:** Sistemas que simulan inteligencia para tareas específicas sin conciencia ni comprensión real. Solo ejecutan funciones acotadas (ej: un asistente virtual). Es la IA actual.

   **IA fuerte:** Sistemas que poseen verdadera inteligencia, conciencia y entendimiento, equivalentes a la mente humana. Pueden realizar cualquier tarea intelectual que un humano pueda hacer. No existe actualmente.

   **d)** Explique la diferencia entre un sistema de IA y un sistema de software complejo (como el que calcula la trayectoria de un cohete a Marte).

   Un sistema de software complejo tradicional sigue reglas fijas y deterministas programadas explícitamente; su comportamiento está completamente definido por su código. Un sistema de IA, en cambio, puede aprender de datos, generalizar a situaciones no vistas, tomar decisiones en contextos inciertos y mejorar con la experiencia. El cálculo de trayectorias usa ecuaciones físicas exactas; la IA opera con modelos probabilísticos y patrones aprendidos.

   **e)** Defina IA simbólica e IA no-simbólica.

   **IA simbólica:** Representa el conocimiento mediante símbolos y reglas lógicas formales (ej: sistemas expertos, lógica de primer orden). Se basa en la manipulación explícita de símbolos.

   **IA no-simbólica:** Utiliza modelos numéricos y estadísticos, como redes neuronales, que aprenden patrones a partir de datos sin representación simbólica explícita. Ej: deep learning, redes neuronales.

   **f)** Defina Machine Learning (ML). ¿ML está incluido en IA?

   **Machine Learning:** Subcampo de la IA que permite a los sistemas aprender automáticamente a partir de datos, mejorando su rendimiento en una tarea sin ser programados explícitamente para cada caso.

   Sí, ML está incluido en IA. Es una de las ramas más importantes de la IA actual, pero no la única (también existen sistemas basados en reglas, planificación, lógica, etc.). Actualmente ML es el enfoque predominante.

   **g)** ¿Qué es una Red neuronal artificial (ANN)? Dibuje un ejemplo señalando cada una de sus partes.

   Una **red neuronal artificial** es un modelo computacional inspirado en las redes neuronales biológicas. Está compuesta por capas de neuronas artificiales interconectadas que procesan información.

   ```
   Entrada        Capa oculta        Salida
   ┌─────┐                           
   │ x₁  │───\                       
   └─────┘    \   ┌─────┐           
                ──│  n₁ │───\       
   ┌─────┐    /   └─────┘    \   ┌─────┐
   │ x₂  │───/                ──→│  y  │
   └─────┘    \   ┌─────┐    /   └─────┘
                ──│  n₂ │───/       
   ┌─────┐    /   └─────┘           
   │ x₃  │───/                       
   └─────┘                           
   ```

   **Partes:**
   - **Capa de entrada:** recibe los datos (features).
   - **Capas ocultas:** procesan la información mediante pesos, bias y funciones de activación.
   - **Capa de salida:** produce el resultado (clasificación, predicción, etc.).
   - **Neuronas:** unidades que reciben entradas, las ponderan y aplican una función de activación.
   - **Conexiones (pesos):** valores que ponderan la influencia de una neurona sobre otra.

2. Lea el artículo original de Turing sobre IA (Turing 1950). En él se comentan algunas objeciones potenciales a su propuesta y a su prueba de inteligencia. ¿Cuáles de estas objeciones tienen todavía validez? ¿Son válidas sus refutaciones? ¿Se te ocurren nuevas objeciones a esta propuesta teniendo en cuenta los desarrollos realizados desde que se escribió el artículo?

   Turing presentó y refutó varias objeciones:

   - **Objeción teológica:** "Pensar es un alma exclusiva del humano". Turing la refuta diciendo que no hay evidencia de que las máquinas no puedan tener alma. Sigue sin ser una objeción científica válida.

   - **Objeción "cabeza de avestruz"** (riesgos): "No queremos pensar en máquinas inteligentes". No es una objeción lógica sino emocional. Aún presente en debates éticos.

   - **Objeción matemática:** Limitaciones de Gödel (los sistemas formales no pueden probar toda verdad). Turing argumenta que los humanos también tienen limitaciones. Sigue siendo debatida; algunos sostienen que la conciencia humana trasciende lo computable.

   - **Objeción de la conciencia:** "Una máquina no puede sentir ni ser consciente". Es la objeción más persistente. Hoy sigue sin resolverse (el *hard problem of consciousness*). Muchos filósofos (Searle con su *habitación china*) sostienen que la sintaxis no basta para la semántica.

   - **Objeción de las discapacidades:** "Una máquina nunca podría hacer X" (enamorarse, tener humor, etc.). Turing respondió que no hay límite demostrable. Con los LLMs actuales, muchas de estas "discapacidades" se han desdibujado.

   - **Objeción de Lady Lovelace:** "Las máquinas solo hacen lo que se les programa". Turing refuta con máquinas que aprenden (ML). Hoy el aprendizaje automático ha probado que las máquinas pueden sorprender a sus creadores, validando su refutación.

   - **Objeción de la continuidad del sistema nervioso:** "El cerebro no es una máquina discreta". Turing señala que una máquina discreta puede simular una continua. Sigue siendo válida su refutación.

   **Nuevas objeciones actuales:**
   - **Comprensión vs. correlación:** Los LLMs actuales generan texto coherente pero sin comprensión real (Searle actualizado).
   - **Ausencia de embodiment:** Sin cuerpo ni interacción con el mundo, no hay verdadera inteligencia.
   - **Sesgos y alucinaciones:** Los modelos pueden generar información falsa o sesgada.
   - **La prueba de Turing es fácil de engañar:** Hoy hay sistemas que pueden pasar variantes del test sin ser verdaderamente inteligentes (ELIZA ya lo insinuaba). Se requieren pruebas más robustas.

   **Sobre la predicción de Turing:** No se cumplió. Hasta la fecha (2026), ningún sistema ha superado consistentemente una Prueba de Turing con las condiciones que describió. Aunque chatbots como ChatGPT pueden engañar a personas inexpertas en conversaciones cortas, no se considera que hayan superado la prueba de forma convincente y consistente. La predicción fue optimista.
