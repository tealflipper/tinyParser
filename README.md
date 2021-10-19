# antlr4_scientific_calculator

[Repositorio en Github de **Arturo Fuentes Velasco**](https://github.com/tealflipper/antlr4_scientific_calculator)
- [antlr4_scientific_calculator](#antlr4_scientific_calculator)
  - [Cómo está articulado el traductor:](#cómo-está-articulado-el-traductor)
    - [Visitor](#visitor)
  - [clases usadas](#clases-usadas)
  - [Ejemplo de la calculadora](#ejemplo-de-la-calculadora)
  - [instrucciones para compilar y correr:](#instrucciones-para-compilar-y-correr)

Antes de instalar la calculadora, es importante seguir los pasos en tu maquina local para instalar java para [linux](https://openjdk.java.net/install/) o [windows](https://docs.microsoft.com/en-us/java/openjdk/install). Además, debes de [instalar antlr4](https://www.antlr.org/) para tu maquina.

## Cómo está articulado el traductor:
![Diagrama del traductor](imgs/diagrama_visitor.png)

Como se ve en el diagrama de arriba, el traductor se compone por dos tipos de analizadores. El primero es un analizador léxico que toma una gramática como entrada y a partir de esta gramática el analizador léxico crea los tokens. Después, estos mismos tokens los usa el analizador sintáctico para construir un árbol sintáctico. Con este árbol se puede asignar los tokens a los caracteres que le mandemos de entrada al analizador. 

### Visitor
![Diagrama UML de visitor](imgs/Visitor_Design_Pattern_UML.jpg)

 Para poder hacer una calculadora a partir de los dos analizadores que tenemos, es necesario poder tomar alguna acción según el lema, o el valor del token. Para hacer esto usamos un patrón de diseño llamado visitor. Como se ve en el siguiente diagrama, este patrón toma varios elementos, y el visitor es el encargado de implementar las funcionalidades de los elementos.

Para la calculadora, el visitor se encargará de implementar métodos para los tokens que elegimos con un # en nuestra gramática. Un ejemplo de la implementación se muestra enseguida:

![Diagrama del traductor con visitor](imgs/visitorExample.png)

En la calculadora, el visitor será usado para evaluar cadenas de texto generadas por un interfaz gráfica de una calculadora. 

## clases usadas
![Diagrama del traductor con visitor](imgs/calculator_classes.png)

En nuestra calculadora estamos usando nuestra implementación del visitor para ejecutar todas nuestras las instrucciones que definimos en la gramática WhileLang. El diagrama UML muestra todas las dependencias usadas para la calculadora. En este diagrama, se toma un analizador léxico, WhileLangLexer, para producir los tokens que usará el analizador sintáctico llamado WhileLangParser. Al final se produce ParseTree que es nuestro árbol sintáctico, este se usará por nuestra clase del tipo visitor llamada ConcreteVisitor. Es esta última clase que podemos usar en nuestra calculadora, y se implementa de la siguiente manera:

![Diagrama del traductor con visitor](imgs/calculatorMain.png)

## Ejemplo de la calculadora

![Diagrama del traductor con visitor](imgs/CalculatorGui.png)


## instrucciones para compilar y correr:

```git clone https://github.com/tealflipper/antlr4_scientific_calculator.git```

```cd antlr4_scientific_calculator```

```antlr4 -no-listener -visitor Whilelang.g4```

```javac *.java```

```java CalculatorGui```
