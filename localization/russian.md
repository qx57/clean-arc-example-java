# Пример реализации чистой архитектуры в автотестах на Java



## Base
Требования:
- Java 11
- Selenium Web Driver. Для корректной работы вам потребуется файл **_chromedriver_**
  (скачать можно [тут](https://chromedriver.chromium.org/downloads)). Измените настройки
  в файле **_UiWebDriver.java_** (строка 22):
  ```java
    public UiWebDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
  ```


## Test Definition Objects (TDO)

* **Директория:** src/main/java
* **Пакет:** net.niiqa.clean_arc_example_java.tdo

В пакете содержатся примеры TDO для тестов API и UI (в соответствующих пакетах).
Важно: классы TDO являются дженериками, причем в API TDO в качестве обобщения используется
класс объекта ответа (например, Response для RestAssured), а в UI TDO - класс веб-элемента
(WebElement в Selenium Web Driver).

Реализуется при инжекте клиента:
```java
@Inject
private BaseClient<T> client;
```
и при создании переменных:
```java
private T response = null;
```

Паттерн DI реализован при помощи пакета **_Guice_**, реализации клиента и/или веб драйвера
помещаются в контекст в тестах (см. **Test Cases**).

В директории **_src/main/resources/contracts_** находятся используемые для инициализации
клиента для тестов API (и для проверки контрактов) Swagger-файлы.



## Test cases

### Код теста

* **Директория:** src/test/java
* **Пакет:** net.niiqa.clean_arc_example_java.tests

Пример реализации тестов API и UI. В данном примере в качестве API клиента используется
**_RestAssured_**, а UI тесты основаны на Selenium Web Driver.

В тестовый класс инжектится TDO (из контекста):
```java
@Inject
private Endpoint1Obj<Response> endpoint1;
```
В UI классе дополнительно подключается экземпляр веб-драйвера для управления сессиями из теста:
```java
@Inject
private UiWrapper<WebElement> webDriver;
```
При объявлении указываются конкретные классы используемых ответа и веб-элемента.

### Контекст

* **Директория:** src/test/java
* **Пакет:** net.niiqa.clean_arc_example_java.context

Класс контекста имплементирует интерфейс Module из пакета **_Guice_**. В методе **_configure_**
в контекст теста добавляются выбранная для теста реализация клиента/веб-драйвера:
```java
bind(new TypeLiteral<BaseClient<Response>>(){}).toInstance(new BaseClientRa());
```
а также TDO - таким образом происходит подключение конкретного клиента в TDO и использование
результатов его работы (Response, WebELement) в тесте.

### Настройки

* **Директория:** src/test/resources

Здесь находится файл **test_settings.properties** содержащий все необходимые для проведения
тестов настройки и данные. Файл считывается при помощи модуля **_Environment_**, после чего
переменные окружения доступны в тесте в параметре **_settings_** (см. **Custom Framework**).


## Custom Framework

* **Директория:** src/main/java
* **Пакет:** net.niiqa.clean_arc_example_java.core

Фреймворк состоит из 3-х основных частей:
* Класс Framework Core (от которого наследуются тесты)
* Пакет интерфейсов (для использования в TDO и клиентах)
* Базовый контекст тестов

### Framework Core

Класс, являющийся родительским для тестов и реализующий базовый функционал,
например - чтение переменных окружения из файла настроек тестов:
```java
@Inject
protected Environment environment;

protected Map<String, String> settings;

@BeforeSuite
public void getEnv() {
        environment.readSettings("test_settings.properties");
        settings = environment.getEnvironmentSettings();
}
```

### Interfaces

* **Директория:** src/main/java
* **Пакет:** net.niiqa.clean_arc_example_java.core.interfaces

В пакете собраны интерфейсы для клиентов, которые в дальнейшем будут использованы в TDO.

### Базовый контекст

* **Директория:** src/main/java
* **Пакет:** net.niiqa.clean_arc_example_java.core.context

Подключает необходимые для всех тестов зависимости (например, получение переменных окружения).
Инициализация происходит в базовом классе фреймворка **FrameworkCore**:
```java
@Guice(modules = {BaseContext.class})
public class EnsembleCore {
    // Framework code
}
```


## Integrations

* **Директория:** src/main/java
* **Пакет:** net.niiqa.clean_arc_example_java.integrations

В примере приведены реализации клиентов и драйверов для тестов.

**ВАЖНО!** **_Все_** клиенты должны имплементировать соответствующие интерфейсы из пакета
**_core.interfaces_**!

## Maintainers
- [Denis Kudriashov](https://github.com/qx57)