Project maintained by [@Cavin](https://www.github.com/in/cavin-macwan/)

<a href="https://www.buymeacoffee.com/cavin.macwan" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" style="height: 60px !important;width: 217px !important;" ></a>

![jetpack compose design pattern banner](banner/jetpack-compose-design-pattern.png)

<h1 align="center"><a id="head">Design Patterns with Jetpack Compose</h1>

- <h2 align="left">What are Design Patterns briefly?</h2>
  Design patterns are important tools widely used in software development. These patterns can improve code quality, consistency and reusability by controlling the creation, assembly and communication of objects.

- Creational Patterns

    - [Factory Method](#factory-method)
    - [Abstract Factory](#abstract-factory)
    - [Singleton](#singleton)
    - [Prototype](#prototype)


More to come soon ⏳ (Create a PR if you wish to contribute 😄)

- Structural Patterns

    - [Adapter](#adapter)
    - [Bridge](#bridge)
    - [Composite](#composite)
    - [Decorator](#decorator)
    - [Facade](#fecade)
    - [Flyweight](#flyweight)
    - [Proxy](#proxy)

- Behavioral Patterns

    - [Chain of Responsibility](#chainofresponsibility)
    - [Iterator](#iterator)
    - [Interpreter](#interpreter)
    - [Observer](#observer)
    - [Command](#command)
    - [Mediator](#mediator)
    - [State](#state)
    - [Strategy](#strategy)
    - [Template Method](#template-method)
    - [Visitor](#visitor)
    - [Memento](#momento)

- <h2 align="left"><a id="factory-method">Factory Method (Creational Patterns)</h2>
  
A factory design pattern is a generative design pattern that helps to abstract how an object is created. This makes your code more flexible and extensible.

The basic idea of the factory design pattern is to delegate object creation to a factory class. This factory class determines which object is created.

<h4 align="left">The factory design pattern has two main components</h4>

- **Product:** The object to be created.
- **Factory:** The class that creates the product object.

<h5 align="left">Advantages of factory design pattern:</h5>

- Makes your code more flexible and extensible.
- It makes your code more readable and understandable by abstracting the object creation process.
- It makes the software development process more efficient.

<h5 align="left"> Disadvantages of the factory design pattern:</h5>

- It may be difficult to use in complex applications.
- It may cause you to write more code.

**Sample Scenario**

Here's a real-world example of the Factory Design Pattern in Jetpack Compose, focusing on a scenario where you want to implement different card layouts for displaying various types of content in a news application:

Scenario: You have a news app where each news item can be displayed in several formats like 'simple' (only text), 'rich' (with image), or 'interactive' (includes interactive elements like a poll).

### 1. Define an Interface for Card Factories:

```kotlin
interface NewsCardFactory {
    @Composable
    fun CreateCard(newsItem: NewsItem)
}

data class NewsItem(val title: String, val content: String, val imageUrl: String?)
```

### 2. Implement Concrete Factories for Each Card Type:

- Simple Card Factory:

```kotlin
class SimpleCardFactory : NewsCardFactory {
  @Composable
  override fun CreateCard(newsItem: NewsItem) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      shape = RoundedCornerShape(8.dp)
    ) {
      Column(
        modifier = Modifier.padding(16.dp)
      ) {
        Text(newsItem.title, style = MaterialTheme.typography.titleSmall)
        Text(newsItem.content, style = MaterialTheme.typography.bodyMedium)
      }
    }
  }
}
```


- Rich Card Factory:

```kotlin
class RichCardFactory : NewsCardFactory {
  @Composable
  override fun CreateCard(newsItem: NewsItem) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      shape = RoundedCornerShape(8.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        newsItem.imageUrl?.let { url ->
          AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
              .fillMaxWidth()
              .height(200.dp)
              .clip(RoundedCornerShape(3.dp))
          )
        }
        Text(newsItem.title, style = MaterialTheme.typography.titleSmall)
        Text(newsItem.content, style = MaterialTheme.typography.bodyMedium)

      }
    }
  }
}
```


### 3. Create a Factory Provider:

```kotlin
enum class CardType { SIMPLE, RICH }

@Composable
fun CardFactoryProvider(
    cardType: CardType = CardType.SIMPLE,
    content: @Composable () -> Unit
) {
    val factory = when (cardType) {
        CardType.SIMPLE -> SimpleCardFactory()
        CardType.RICH -> RichCardFactory()
    }
    CompositionLocalProvider(LocalCardFactory provides factory) {
        content()
    }
}

private val LocalCardFactory = staticCompositionLocalOf<NewsCardFactory> { 
    SimpleCardFactory() // Default
}
```

### 4. Usage in your App:

```kotlin
@Composable
fun NewsFeed(
  newsItems: List<NewsItem>,
  modifier: Modifier,
) {
  var selectedCardType by remember { mutableStateOf(CardType.SIMPLE) }

  CardFactoryProvider(cardType = selectedCardType) { // Or dynamically choose based on item type
    LazyColumn(modifier = modifier) {
      item {
        Row(modifier = Modifier.padding(8.dp)) {
          Button(onClick = { selectedCardType = CardType.SIMPLE }) {
            Text("Simple Card")
          }
          Spacer(modifier = Modifier.width(8.dp))
          Button(onClick = { selectedCardType = CardType.RICH }) {
            Text("Rich Card")
          }
        }
      }
      items(newsItems) { item ->
        val cardFactory = LocalCardFactory.current
        cardFactory.CreateCard(item)
      }
    }
  }
}
```

This approach allows for a flexible and extensible design where new types of cards can be added by creating new factories without altering existing code that uses these cards. It uses the Factory Pattern to manage the creation of different UI components based on the type of news item, enhancing modularity and maintainability of the UI.

[Back to the beginning of the documentation](#head)

- <h2 align="left"><a id="abstract-factory">Abstract Factory (Creational Patterns)</h2>

The abstract factory design pattern uses a factory class to create objects from multiple families. This pattern abstracts the object creation process, making your code more readable and flexible.

<h4 align="left">The abstract factory design pattern has two main components</h4>

- **Abstract factory:** A class used to create objects from multiple families.
- **Concrete factory:** A class that concretises the abstract factory and is used to create objects from a specific family.

<h5 align="left">Advantages of the abstract factory design pattern</h5>

- Makes your code more flexible and extensible.
- It makes your code more readable and understandable by abstracting the object creation process.
- It makes the software development process more efficient.

<h5 align="left"> Disadvantages of the abstract factory design pattern</h5>

- It may be difficult to use in complex applications.
- It may cause you to write more code.

**Sample Scenario**

```
ThemeComponentsFactory (Abstract Factory)
├── LightThemeFactory
└── DarkThemeFactory
    ├── ThemeButton
    │   ├── LightThemeButton
    │   └── DarkThemeButton
    └── ThemeCard
        ├── LightThemeCard
        └── DarkThemeCard
```

### 1. Abstract Products

Define interfaces for themed components:

```kotlin
interface ThemeButton {
    @Composable
    fun Create(onClick: () -> Unit, content: @Composable () -> Unit)
}

interface ThemeCard {
    @Composable
    fun Create(content: @Composable () -> Unit)
}
```

### 2. Abstract Factory

Define the factory interface that creates themed components:

```kotlin
interface ThemeComponentsFactory {
    fun createButton(): ThemeButton
    fun createCard(): ThemeCard
}
```

### 3. Concrete Factories

Implement theme-specific factories:

```kotlin
class LightThemeFactory : ThemeComponentsFactory {
    override fun createButton(): ThemeButton = LightThemeButton()
    override fun createCard(): ThemeCard = LightThemeCard()
}

class DarkThemeFactory : ThemeComponentsFactory {
    override fun createButton(): ThemeButton = DarkThemeButton()
    override fun createCard(): ThemeCard = DarkThemeCard()
}
```

### Usage

```kotlin
@Composable
fun ThemeSwitchingApp() {
    var isDarkTheme by remember { mutableStateOf(false) }
    val themeFactory: ThemeComponentsFactory = if (isDarkTheme) {
        DarkThemeFactory()
    } else {
        LightThemeFactory()
    }
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        themeFactory.createButton().Create(
            onClick = { isDarkTheme = !isDarkTheme }
        ) {
            Text("Switch Theme")
        }
        
        themeFactory.createCard().Create {
            Text("This is a themed card")
        }
    }
}
```

[Return to the beginning of the documentation](#head)

- <h2 align="left"><a id="singleton">Singleton (Creational Patterns)</h2>
  The Singleton design pattern allows only one object to be created from a class. This pattern is used when a single object is needed.

<h4 align="left">The Singleton design pattern has two main components</h4>

- **Singleton class:** This class allows only one object to be created.
- **Singleton object:** The only object created from the Singleton class.

<h5 align="left">Advantages of Singleton design pattern</h5>

- Useful in situations where a single object is needed.
- Makes your code more readable and understandable.
- It makes the software development process more efficient.

<h5 align="left"> Disadvantages of the factory design pattern</h5>

- It may be difficult to use in complex applications.
- It may cause you to write more code.

**Sample Scenario**
Imagine we want to manage a global theme configuration for an app, allowing access to the theme state from multiple places without passing it explicitly.

### Singleton Implementation

```kotlin
object ThemeConfig {
    private var darkModeEnabled: Boolean = false

    fun isDarkModeEnabled(): Boolean = darkModeEnabled

    fun toggleDarkMode() {
        darkModeEnabled = !darkModeEnabled
    }
}
```

### Usage in Composable Functions

```kotlin
@Composable
fun ThemeToggleButton() {
    val isDarkMode = remember { mutableStateOf(ThemeConfig.isDarkModeEnabled()) }

    Button(onClick = {
        ThemeConfig.toggleDarkMode()
        isDarkMode.value = ThemeConfig.isDarkModeEnabled()
    }) {
        Text(if (isDarkMode.value) "Switch to Light Mode" else "Switch to Dark Mode")
    }
}

@Composable
fun AppContent() {
    val isDarkMode = ThemeConfig.isDarkModeEnabled()
    MaterialTheme(colorScheme = if (isDarkMode) darkColors() else lightColors()) {
        ThemeToggleButton()
    }
}
```

## Ways to Implement Singleton in Kotlin

### 1. **Object Declaration** (Most Common)
- Kotlin's `object` keyword inherently implements the singleton pattern.
```kotlin
object MySingleton {
    fun doSomething() {
        println("Singleton Instance")
    }
}
```

### 2. **Lazy Initialization**
- Use the `lazy` delegate to create a singleton only when accessed for the first time.
```kotlin
class MySingleton private constructor() {
    companion object {
        val instance: MySingleton by lazy { MySingleton() }
    }
}
```

### 3. **Double-Checked Locking** (Thread-Safe Singleton)
- Ensures thread safety in a multithreaded environment.
```kotlin
class MySingleton private constructor() {
    companion object {
        @Volatile
        private var instance: MySingleton? = null

        fun getInstance(): MySingleton {
            return instance ?: synchronized(this) {
                instance ?: MySingleton().also { instance = it }
            }
        }
    }
}
```

## Choosing the Right Approach
- **Object Declaration**: Best for simplicity and Kotlin idiomatic code.
- **Lazy Initialization**: Ideal when the instance creation is resource-intensive and you want to delay it until needed.
- **Double-Checked Locking**: Use for thread safety in Java-style singletons.

[Return to the beginning of the documentation](#head)

- <h2 align="left"><a id="prototype">Prototype (Creational Patterns)</h2>
  A prototype design pattern is a design pattern that uses a prototype object to create copies of objects. This can be more efficient than creating objects directly, especially if the creation of objects is complex or time-consuming.

<h4 align="left">The Prototype design pattern has three main components</h4>

- **Prototype:** The object to be copied.
- **Copier:** The class that copies the prototype object.
- **Users:** Classes that use the copied objects.

<h5 align="left">Advantages of the Prototype design pattern</h5>

- Makes the creation of objects more efficient.
- Facilitates the creation of a number of copies with the same properties of objects.
- It allows objects to be created independently of a given state.

<h5 align="left"> Disadvantages of the Prototype design pattern</h5>

- Changing the prototype object can also change all copied objects.
- When the property of the prototype object is changed, it is also necessary to change the properties of the copied objects.

**Sample Scenario**

In Kotlin, `data class` provides a built-in `copy()` method that simplifies the implementation of the Prototype Pattern. This is particularly useful when creating multiple variations of an object with similar properties.

### Prototype Implementation

```kotlin
data class Document(
    val title: String,
    val content: String,
    val author: String
)
```
```kotlin
@Composable
fun DocumentCard(document: AppDocument, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier.padding(8.dp),
    shape = MaterialTheme.shapes.medium
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = "Title: ${document.title}", style = MaterialTheme.typography.bodyLarge)
      Text(text = "Content: ${document.content}", style = MaterialTheme.typography.bodyMedium)
      Text(text = "Author: ${document.author}", style = MaterialTheme.typography.bodySmall)
    }
  }
}
```

```kotlin
@Composable
fun ProtoTypeView() {
  // Original prototype
  val originalDocument = AppDocument(
    title = "Prototype Pattern",
    content = "This is the original document content.",
    author = "John Doe"
  )

  // Clone the prototype and modify properties
  val clonedDocument = originalDocument.copy(
    title = "Cloned Prototype",
    content = "This is the cloned document content."
  )

  // UI Layout
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Text("Documents", style = MaterialTheme.typography.titleLarge)

    // Display Original Document
    DocumentCard(document = originalDocument, modifier = Modifier.fillMaxWidth())

    // Display Cloned Document
    DocumentCard(document = clonedDocument, modifier = Modifier.fillMaxWidth())
  }
}
```

[Return to the beginning of the documentation](#head)


# Design Patterns TODO List

## Creational Patterns

- [X] Factory Method
- [X] Abstract Factory
- [X] Singleton
- [X] Builder
- [X] Prototype

## Structural Patterns
- [ ] Adapter
- [ ] Bridge
- [ ] Composite
- [ ] Decorator
- [ ] Facade
- [ ] Flyweight
- [ ] Proxy

## Behavioral Patterns
- [ ] Chain of Responsibility
- [ ] Iterator
- [ ] Interpreter
- [ ] Observer
- [ ] Command
- [ ] Mediator
- [ ] State
- [ ] Strategy
- [ ] Template Method
- [ ] Visitor
- [ ] Memento

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white) ![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)

![Hey\! Thanks\!](https://media.giphy.com/media/ip6n2oVNZBHiM/giphy.gif)
