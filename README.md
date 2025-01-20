# Convertidor de Divisas

Este es un programa de conversión de divisas desarrollado en Java que permite realizar conversiones desde Pesos Colombianos (COP) a diferentes monedas internacionales. El programa utiliza la API de ExchangeRate-API para obtener tasas de cambio en tiempo real.

## Características

- Conversión de Pesos Colombianos (COP) a diferentes monedas
- Tasas de cambio en tiempo real mediante API
- Interfaz por consola fácil de usar
- Historial de transacciones
- Detalles completos de cada conversión

## Requisitos Previos

- Java JDK 17 o superior
- Biblioteca org.json
- Conexión a Internet (para obtener tasas de cambio en tiempo real)

## Instalación

1. Clona este repositorio:
```bash
git clone https://github.com/[tu-usuario]/currency-converter.git
cd currency-converter
```

2. Descarga la biblioteca org.json:
- Visita: https://search.maven.org/artifact/org.json/json/20231013/bundle
- Descarga el archivo .jar
- Colócalo en el directorio del proyecto
- Renómbralo a `json.jar`

## Estructura del Proyecto

```
currency-converter/
│   README.md
│   CurrencyConverterApp.java
│   json.jar
│   .gitignore
```

## Compilación y Ejecución

### En Linux/Mac:
```bash
javac -cp ".:json.jar" CurrencyConverterApp.java
java -cp ".:json.jar:." CurrencyConverterApp
```

### En Windows:
```bash
javac -cp ".;json.jar" CurrencyConverterApp.java
java -cp ".;json.jar;." CurrencyConverterApp
```

## Uso

1. Al iniciar el programa, se mostrará un menú con las siguientes opciones:
   - Realizar conversión
   - Ver historial de conversiones
   - Salir

2. Para realizar una conversión:
   - Seleccione la opción 1
   - Ingrese la cantidad en COP
   - Seleccione la moneda destino (USD, EUR, GBP)
   - El programa mostrará los detalles de la conversión

3. Para ver el historial:
   - Seleccione la opción 2
   - Se mostrarán todas las conversiones realizadas

## Estructura del Código

El programa está organizado en las siguientes clases:

- `CurrencyConverterApp`: Clase principal que contiene el método main
- `CurrencyConverter`: Maneja la interfaz de usuario y la lógica principal
- `ExchangeRateService`: Gestiona las llamadas a la API de tipos de cambio
- `Transaction`: Representa una transacción individual
- `TransactionHistory`: Mantiene un registro de las transacciones

## API Utilizada

El programa utiliza [ExchangeRate-API](https://www.exchangerate-api.com/) para obtener las tasas de cambio en tiempo real.

## Contribuir

1. Haz un Fork del proyecto
2. Crea una rama para tu función (`git checkout -b feature/AmazingFeature`)
3. Haz commit de tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Haz Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request
