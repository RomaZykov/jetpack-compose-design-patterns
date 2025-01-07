Project maintained by [@Cavin](https://www.github.com/cavin-macwan/)

![jetpack compose design pattern banner](banner/jetpack-compose-design-pattern.png)

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white) ![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)

- <h2 align="left">What are Design Patterns briefly?</h2>
  Design patterns are important tools widely used in software development. These patterns can
  improve code quality, consistency and reusability by controlling the creation, assembly and
  communication of objects.

- Creational Patterns

    - [Factory Method](#factory-method)
    - [Abstract Factory](#abstract-factory)
    - [Singleton](#singleton)
    - [Prototype](#prototype)

- Structural Patterns

    - [Adapter](#adapter)
    - [Bridge](#bridge)
    - [Composite](#composite)
    - [Decorator](#decorator)
    - [Facade](#facade)
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

A factory design pattern is a generative design pattern that helps to abstract how an object is
created. This makes your code more flexible and extensible.

The basic idea of the factory design pattern is to delegate object creation to a factory class. This
factory class determines which object is created.

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

Here's a real-world example of the Factory Design Pattern in Jetpack Compose, focusing on a scenario
where you want to implement different card layouts for displaying various types of content in a news
application:

Scenario: You have a news app where each news item can be displayed in several formats like '
simple' (only text), 'rich' (with image), or 'interactive' (includes interactive elements like a
poll).

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

This approach allows for a flexible and extensible design where new types of cards can be added by
creating new factories without altering existing code that uses these cards. It uses the Factory
Pattern to manage the creation of different UI components based on the type of news item, enhancing
modularity and maintainability of the UI.

[Back to the beginning of the documentation](#head)

- <h2 align="left"><a id="abstract-factory">Abstract Factory (Creational Patterns)</h2>

The abstract factory design pattern uses a factory class to create objects from multiple families.
This pattern abstracts the object creation process, making your code more readable and flexible.

<h4 align="left">The abstract factory design pattern has two main components</h4>

- **Abstract factory:** A class used to create objects from multiple families.
- **Concrete factory:** A class that concretises the abstract factory and is used to create objects
  from a specific family.

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
  The Singleton design pattern allows only one object to be created from a class. This pattern is
  used when a single object is needed.

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
Imagine we want to manage a global theme configuration for an app, allowing access to the theme
state from multiple places without passing it explicitly.

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
- **Lazy Initialization**: Ideal when the instance creation is resource-intensive and you want to
  delay it until needed.
- **Double-Checked Locking**: Use for thread safety in Java-style singletons.

[Return to the beginning of the documentation](#head)

- <h2 align="left"><a id="prototype">Prototype (Creational Patterns)</h2>
  A prototype design pattern is a design pattern that uses a prototype object to create copies of
  objects. This can be more efficient than creating objects directly, especially if the creation of
  objects is complex or time-consuming.

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
- When the property of the prototype object is changed, it is also necessary to change the
  properties of the copied objects.

**Sample Scenario**

In Kotlin, `data class` provides a built-in `copy()` method that simplifies the implementation of
the Prototype Pattern. This is particularly useful when creating multiple variations of an object
with similar properties.

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

- <h2 align="left"><a id="adapter">Adapter (Structural Patterns)</h2>
  The Adapter design pattern is a structural design pattern that allows objects with incompatible
  interfaces to work together. This pattern is applied to reuse an existing class or interface class
  by adapting it to a different interface class.

The Adapter pattern makes the interfaces of two different classes or interfaces similar to each
other, allowing these classes or interfaces to be used together. In this way, it is possible to use
an existing class or interface class in a new system or project without having to change or rewrite
it.

<h4 align="left">The adapter design pattern has two main components</h4>

- **Adapted class or interface:** The purpose of the adapter pattern is to adapt this class or
  interface to have a different interface.
- **Adaptor class:** The adapter class is the class that adapts the adapted class or interface to
  have a different interface.
- **Customer class:** A class that uses the interface of the adapter class.

<h5 align="left">Adapter design pattern advantages</h5>

- It allows you to use an existing class or interface in a new system or project without changing
  it.
- It makes it easier to bring together different technologies or platforms.
- It allows to extend the functionality of a class or interface.

<h5 align="left"> Disadvantages of the Adapter design pattern</h5>

- The adapter class must support the full functionality of the adapted class or interface.
- The adapter class may be dependent on the code of the adapted class or interface.

**Sample Scenario**

This example demonstrates the Adapter pattern in a scenario where you want to adapt one type of data
model to another for display purposes within Jetpack Compose, without involving any legacy
components. You're building a weather app where you fetch weather data in a particular format (
`WeatherData`) from an API. However, your UI layer expects a different format (
`WeatherPresentation`) for rendering. Instead of changing the data fetching logic or the UI layer,
you can use an Adapter to transform `WeatherData` to `WeatherPresentation`.

### Data Models:

```kotlin
data class WeatherData(
    val temperature: Float,
    val humidity: Float,
    val windSpeed: Float,
    val condition: String
)

data class WeatherPresentation(
    val tempDisplay: String,
    val humidityDisplay: String,
    val windDisplay: String,
    val weatherIcon: Int
)
```

### Problem:

The WeatherData format needs to be converted into WeatherPresentation for display in the UI.

### Solution:

Create an Adapter to convert WeatherData to WeatherPresentation.

### Implementation

1. Create the Adapter:

```kotlin
class WeatherDataAdapter {
    fun adapt(data: WeatherData): WeatherPresentation {
        return WeatherPresentation(
            tempDisplay = "${data.temperature}°C",
            humidityDisplay = "${data.humidity}%",
            windDisplay = "${data.windSpeed} m/s",
            weatherIcon = when (data.condition.lowercase()) {
                "sunny" -> R.drawable.logo
                "cloudy" -> R.drawable.ic_launcher_background
                "rainy" -> R.drawable.ic_launcher_foreground
                else -> R.drawable.logo
            }
        )
    }
}
```

This adapter class takes WeatherData and formats it into WeatherPresentation, which is more suitable
for UI display. It converts temperatures, humidity, and wind speed into user-friendly strings and
selects an appropriate icon based on the weather condition.

```kotlin
@Composable
fun WeatherScreen(weatherData: WeatherData) {
    val adapter = remember { WeatherDataAdapter() }
    val presentation = adapter.adapt(weatherData)

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Temperature: ${presentation.tempDisplay}")
        Text("Humidity: ${presentation.humidityDisplay}")
        Text("Wind Speed: ${presentation.windDisplay}")
        Image(
            painter = painterResource(presentation.weatherIcon),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(50.dp)
        )
    }
}
```

Here, we use the adapter to transform the incoming WeatherData into WeatherPresentation format
before displaying it in our Composable UI.

[Return to the beginning of the documentation](#head)

- <h2 align="left"><a id="bridge">Bridge (Structural Patterns)</h2>
  Bridge design pattern is a design pattern used to combine two independent hierarchical
  structures (abstraction and implementation) and to allow them to be modified separately. This
  pattern aims to create a more flexible structure by separating the abstraction of an object and
  the functionality (implementation) that operates on that abstraction.

<h4 align="left">Bridge design pattern has 4 main components</h4>

**Abstraction:** This is the layer where the client interacts with an interface and where
functionality is not fully realised.

**Refined Abstraction:** These are subclasses of Abstraction and address a specific situation.

**Implementation:** This is the layer that actually implements the abstraction.

**Concrete Implementation:** These are subclasses of Implementation and actually implement a
specific case.

<h5 align="left">Advantages of the Bridge design pattern</h5>

- Flexibility and Extensibility: The abstraction and implementation can be changed independently of
  each other, which facilitates changes to the system.

- Encapsulation: Application details can be hidden from the abstraction. The client interacts only
  with the abstraction.

- Change Management: Changes on one side do not affect the other. For example, only the abstraction
  can change and the application can remain unchanged, or vice versa.

<h5 id="bridge" align="left"> Disadvantages of the Bridge design pattern</h5>

- Complexity: The implementation of the pattern can sometimes lead to complexity, especially if the
  size of the project is small or the requirements are simple, this complexity may be unnecessary.

**Sample Scenario**

So how can we implement this in a real application, package, etc. Let's look at it. Due to our
scenario, we want to use our own video processing technology instead of the video processing
technology of applications such as Youtube, Netflix, Amazon Prime, etc. in our project. While doing
this, we need to consider the potential for applications with different video processing
technologies to be included in our project in the future. At this point, **Birdge Design Pattern**
comes into play. Our aim is to ensure that the old code structure can be renewed and continue to
function whenever it is renewed.

As per our scenario, we are writing an **abstract** class for our own **Video Processor**technology.
In it we have a method signature named **process(String videoFile)**.

```kotlin
// 1. Define the VideoProcessor abstraction.
interface VideoProcessor {
    fun process(videoFile: String)
}
```

Now it is time to implement our **Video Processor** technology for the related video/videos. For
this, let's assume that we support HD, UHD (4K) and QUHD (8K) video quality. For each video quality,
we get **instantiation** from our **Video Processor** **abstract** class.

```kotlin
class HDProcessor : VideoProcessor {
    override fun process(videoFile: String) {
        println("$videoFile is being processed with HD quality.")
    }
}
```

```kotlin
class UHD4KProcessor : VideoProcessor {
    override fun process(videoFile: String) {
        println("$videoFile is being processed with UHD 4K quality.")
    }
}
```

```kotlin
class QUHD8KProcessor : VideoProcessor {
    override fun process(videoFile: String) {
        println("$videoFile is being processed with QUHD 8K quality.")
    }
}
```

Then we define an interface for **Video**. In it, we ensure that our **Video Processor** technology
is implemented compulsorily. Then we define an empty method named **play(String videoFile)** for the
video.

```kotlin
abstract class Video(private val processor: VideoProcessor) {
    abstract fun play(videoFile: String)

    protected fun process(videoFile: String) {
        processor.process(videoFile)
    }
}
```

```kotlin
class YoutubeVideo(processor: VideoProcessor) : Video(processor) {
    override fun play(videoFile: String) {
        process(videoFile)
        println("Playing $videoFile on YouTube.")
    }
}
```

Now let's start running our scenario for Netflix and Youtube. We create separate classes for both
Netflix and Youtube and inherit from the **Video** interface.

```kotlin
class NetflixVideo(processor: VideoProcessor) : Video(processor) {
    override fun play(videoFile: String) {
        process(videoFile)
        println("Playing $videoFile on Netflix.")
    }
}
```

```kotlin
class AmazonPrimeVideo(processor: VideoProcessor) : Video(processor) {
    override fun play(videoFile: String) {
        process(videoFile)
        println("Playing $videoFile on Amazon Prime.")
    }
}
```

So how can we use this on the UI side?

```kotlin
@Composable
fun BridgeView() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val youtubeVideo = YoutubeVideo(HDProcessor())
                youtubeVideo.play("video_hd.mp4")
                Toast.makeText(context, "Playing HD video on YouTube", Toast.LENGTH_SHORT).show()
            }) {
            Text("Watch HD Video on YouTube")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val netflixVideo = NetflixVideo(UHD4KProcessor())
                netflixVideo.play("video_4k.mp4")
                Toast.makeText(context, "Playing UHD 4K video on Netflix", Toast.LENGTH_SHORT)
                    .show()
            }) {
            Text("Watch UHD 4K Video on Netflix")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val amazonPrimeVideo = AmazonPrimeVideo(QUHD8KProcessor())
                amazonPrimeVideo.play("video_8k.mp4")
                Toast.makeText(context, "Playing QUHD 8K video on Amazon Prime", Toast.LENGTH_SHORT)
                    .show()
            }) {
            Text("Watch QUHD 8K Video on Amazon Prime")
        }
    }
}
```

- <h2 align="left"><a id="composite">Composite (Structural Patterns)</h2>
  The Composite design pattern is a powerful structural pattern that allows you to treat individual
  objects and composites of objects in the same way. It helps you create object hierarchies that
  treat both parts (individual objects) and wholes (composite objects) in the same way.

<h4 align="left">There are three main components of the composite design pattern</h4>

- **Abstract Interface:** This is the foundation of the model. It defines the common behaviour that
  all objects in the hierarchy, both individual and composite, must follow.
- **Concrete Classes:** These are implementations of the abstract interface representing specific
  types of objects. Each class defines its own behaviour for the interface methods.
- **Client Code:** This is the code that interacts with objects in the hierarchy. Clients only see
  the abstract interface, allowing them to treat both individual objects and composites in the same
  way

<h5 align="left">Advantages of composite design pattern</h5>

- Clients do not need to handle different object types differently.
- More flexible and reusable code You can easily add new item types that fit the common interface.
- Easier maintenance Changes to one item type do not necessarily affect others.
- Improved code readability: The code reflects the real-world structure of your data.

<h5 align="left"> Disadvantages of the Composite design pattern</h5>

- Let's not make a big hierarchical scan, performance can be impressive.

**Sample Scenario**

For example, there are certain categories that you feel belong to the same group. For example, there
are certain categories in an e-commerce application. Under these categories, there are subcategories
or headings related to the relevant category. To build these structures more easily and flexibly, *
*Composite Design Pattern** comes into play. We can either handle related objects individually, or
we can handle categories as multiple and build the hierarchy. Our goal will be to provide this
flexibility.

Now the first thing we will build according to our scenario will be **Abstract Class** which will
provide the common structure.

```kotlin
interface CartItem {
    fun getName(): String

    fun getPrice(): Double

    @Composable
    fun Render()
}
```

Then we create a class named **Product** for any product. We start building the structure for a
single product by inheriting from the **Abstract** class named **CartItem** that we have created
this class. Since it will hold information about the product, we write the necessary variables.
Remember, our scenario will be an E-commerce application. Since the **buildItemWidget()** method
returns generic, we prefer to write a **Card** for the product.

```kotlin
data class Product(
    val title: String,
    val description: String,
    val imageUrl: String,
    private val price: Double,
    var quantity: Int = 0
) : CartItem {
    override fun getName() = title
    override fun getPrice() = price * quantity

    @Composable
    override fun Render() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                    Text(text = description, style = MaterialTheme.typography.bodyMedium)
                }
                Row {
                    IconButton(onClick = { if (quantity > 0) quantity-- }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove")
                    }
                    Text(text = "$quantity", modifier = Modifier.align(Alignment.CenterVertically))
                    IconButton(onClick = { quantity++ }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        }
    }
}
```

Now it is time to build a product tree collectively. In our scenario, we will consider the **Car**
and **Desktop Computer** categories. Since these categories can be divided into many parts (wheels,
motherboard, etc.)
We create a class named **Category** that inherits from **CartItem** **Abstract** class to manage
related structures in a common way. The **buildItemWidget()** method returns the **ExpansionPanel**
and we collect other similar products in the **final List<CartItem<dynamic>> children** list under a
single common category heading.

```kotlin
data class Category(
    private val name: String,
    val children: List<CartItem>,
    var isExpanded: Boolean = false
) : CartItem {
    override fun getName() = name
    override fun getPrice() = children.sumOf { it.getPrice() }

    @Composable
    override fun Render() {
        var expanded by remember { mutableStateOf(isExpanded) }
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = null
                )
            }
            if (expanded) {
                Column {
                    children.forEach { it.Render() }
                }
            }
        }
    }
}
```

Let's see what kind of usage scenario can be on the UI side. First of all, we create a list to set
the relevant category and single products. As I said before, our categories will be **Desktop
Computer** and **Car**. There will be related products in the sub-products.

```kotlin
val categories = listOf(
    Category(
        name = "Desktop Computer",
        children = listOf(
            Product(
                "Main Board",
                "Part of the computer",
                "https://via.placeholder.com/150",
                1000.0
            ),
            Product("CPU", "Part of the computer", "https://via.placeholder.com/150", 2000.0)
        )
    ),
    Category(
        name = "Car",
        children = listOf(
            Product("Electric Car", "Type of the car", "https://via.placeholder.com/150", 9000.0),
            Product("Wheel", "Part of the car", "https://via.placeholder.com/150", 1000.0)
        )
    )
)

```

We display categories and single product using **ExpansionPanelList**.

```kotlin
@Composable
fun ShoppingCartScreen(categories: List<Category>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(categories) { category ->
            category.Render()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShoppingCart() {
    MaterialTheme {
        ShoppingCartScreen(categories)
    }
}
```

[Return to the beginning of the documentation](#head)

- <h2 align="left"><a id="decorator">Decorator (Structural Patterns)</h2>

The Decorator design pattern is a design pattern used to dynamically add new properties to an
object. This is done without changing or extending the functionality of the base object. The
Decorator design pattern provides flexibility and maintainability when used correctly. However, like
any design pattern, it is important to evaluate whether it suits your application requirements.

<h4 align="left">The Decorator design pattern has 3 main components</h4>

- **Abstract Component Interface(OPTIONAL):** This interface is completely optional. You can create
  an **abstract** behaviour for the component to be decorated.
- **Concrete Component:** This is the pure form of the component to be decorated. Optionally *
  *abstract component interface** can be implemented.
- **Abstract Decorator Class:** Provides an **abstract** layer to decorator classes for the
  component to be decorated. The decor classes to be used inherit from this class.
- **Decorator Class:** Decorates the component to be decorated. More than one **decorator** class
  can be made for the component to be decorated.

<h5 align="left">Advantages of the Decorator design pattern</h5>

- Flexibility: The Decorator pattern provides a flexible way of dynamically adding behaviour to
  objects. Adding new responsibilities or removing existing ones can be done without changing
  classes.
- Open-Closed Principle: The Decorator pattern ensures that classes are open (allowing adding new
  behaviours) and closed (not modifying existing code). This helps your code to be more
  maintainable.
- Composite Objects: The Decorator pattern allows you to combine other objects on top of an object.
  This allows you to create complex structures by combining an object in different combinations.

<h5 align="left">Disadvantages of the Decorator design pattern</h5>

- Code Complexity: When the Decorator pattern is used, a number of classes are created to add
  additional responsibilities to an object. This can lead to code complexity over time.
- Lots of Small Objects: The Decorator pattern requires a class to be created for each decorator
  class. This can lead to a large number of small objects and an increase in project size.
- Logical Ordering of Wrappers: The order of the decorators is important in the Decorator pattern.
  In some cases, incorrect determination of the order of the decorators may lead to unexpected
  results.
- Complexity of Composite Objects: The complexity of composite objects can increase by adding
  multiple decorators. This can lead to a structure that is difficult to understand and maintain.

**Example Scenario**
We want to create a `Card` composable that can be dynamically enhanced with additional features like
shadows, borders, or padding. This ensures flexibility and reusability without modifying the core
implementation of the `Card`.

### Step 1: Base Composable

This is the base composable that will be decorated.

```kotlin
@Composable
fun BaseCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        content()
    }
}
```

### Step 2: Decorator Composables

**Add Border Decorator:**
Wrap the `BaseCard` with a border.

```kotlin
@Composable
fun BorderDecorator(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.border(2.dp, Color.Blue)
    ) {
        content()
    }
}
```

**Add Shadow Decorator:**
Add shadow to the `BaseCard`.

```kotlin
@Composable
fun ShadowDecorator(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.shadow(8.dp, shape = RoundedCornerShape(8.dp))
    ) {
        content()
    }
}
```

**Add Padding Decorator:**
Add extra padding around the `BaseCard`.

```kotlin
@Composable
fun PaddingDecorator(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        content()
    }
}
```

#### Step 3: Combine Decorators

You can dynamically combine these decorators as needed.

```kotlin
@Composable
fun DecoratedCard() {
    ShadowDecorator {
        BorderDecorator {
            PaddingDecorator {
                BaseCard {
                    Text(
                        text = "This is a decorated card!",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
```

### Usage in a Jetpack Compose Screen

```kotlin
@Composable
fun DecoratorPatternExample() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Decorator Pattern Example") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DecoratedCard()
        }
    }
}
```

[Return to the beginning of the documentation](#head)

- <h2 align="left"><a id="facade">Facade (Structural Patterns)</h2>
  The Facade design pattern is a structural design pattern used to manage complex systems with a
  simple interface. This pattern is used to facilitate the use of systems and hide their complexity.
  The Facade design pattern facilitates the understandability and use of code, especially in large
  software systems, by limiting direct access to subsystems and combining a set of subsystem
  functions into a single, high-level interface.

Facade enables complex subsystems to be exposed to the outside world through a simplified interface.
Users can use these systems without having in-depth knowledge about the complex structures and
functioning of the subsystems.

<h4 align="left">The Facade design pattern has two main components</h4>

- **Facade:** Provides the simplified interface presented to the outside world. It combines the
  functions of subsystems and presents them to the user.
- **Subsystems:** Classes that contain the complex functionality covered by the Facade interface.
  These are not called directly by the user, but are managed by the Facade class.

<h5 align="left">Advantages of the Facade design pattern</h5>

- Enables the use of complex systems with a simpler interface.
- Reduces direct interaction with subsystems, making code easier to maintain and update.
- Facilitates testing of subsystems individually.

<h5 align="left"> Disadvantages of the Facade design pattern</h5>

- An extra layer of abstraction can sometimes lead to performance loss.
- A very simplified interface can in some cases restrict access to all features of subsystems.

**Sample Scenario**
Imagine a music player app. The app has different subsystems like audio playback, notifications, and
analytics. Instead of interacting directly with these subsystems, the client (UI) interacts with a
`MusicPlayerFacade` that simplifies the process.

### Step 1: Create Subsystems

#### Audio Player Subsystem

```kotlin
class AudioPlayer {
    fun play(track: String) {
        println("Playing track: $track")
    }

    fun pause() {
        println("Audio paused")
    }

    fun stop() {
        println("Audio stopped")
    }
}
```

#### Notification Subsystem

```kotlin
class NotificationManager {
    fun showNotification(track: String) {
        println("Showing notification: Now playing $track")
    }

    fun clearNotification() {
        println("Clearing notification")
    }
}
```

#### Analytics Subsystem

```kotlin
class AnalyticsManager {
    fun logEvent(event: String) {
        println("Logging event: $event")
    }
}
```

### Step 2: Create the Facade

The `MusicPlayerFacade` simplifies interactions with these subsystems.

```kotlin
class MusicPlayerFacade(
    private val audioPlayer: AudioPlayer,
    private val notificationManager: NotificationManager,
    private val analyticsManager: AnalyticsManager
) {

    fun playTrack(track: String) {
        audioPlayer.play(track)
        notificationManager.showNotification(track)
        analyticsManager.logEvent("Track played: $track")
    }

    fun pauseTrack() {
        audioPlayer.pause()
        analyticsManager.logEvent("Track paused")
    }

    fun stopTrack() {
        audioPlayer.stop()
        notificationManager.clearNotification()
        analyticsManager.logEvent("Track stopped")
    }
}
```

### Step 3: Using the Facade in Jetpack Compose

The UI layer interacts only with the `MusicPlayerFacade`.

```kotlin
@Composable
fun FacadeView() {
    val musicPlayerFacade = remember {
        MusicPlayerFacade(
            audioPlayer = AudioPlayer(),
            notificationManager = NotificationManager(),
            analyticsManager = AnalyticsManager()
        )

    }

    var isPlaying by remember { mutableStateOf(false) }
    val track = "My Favorite Song"

    Scaffold {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(it)
        ) {
            Text("Music Player", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isPlaying) {
                        musicPlayerFacade.pauseTrack()
                    } else {
                        musicPlayerFacade.playTrack(track)
                    }
                    isPlaying = !isPlaying
                }) {
                Text(if (isPlaying) "Pause" else "Play")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    musicPlayerFacade.stopTrack()
                    isPlaying = false
                }) {
                Text("Stop")
            }
        }
    }
}
```

[Return to the beginning of the documentation](#head)

- <h2 align="left"><a id="flyweight">Flyweight (Structural Patterns)</h2>
  The Flyweight design pattern is a structural design pattern used to optimize memory usage. This
  pattern aims to reduce repetitive states by separating intrinsic states and non-shareable states (
  extrinsic states) between objects, thus efficiently reducing memory usage. It becomes especially
  important in cases where many similar objects are created. An example of using the Flyweight
  design pattern in Jetpack Compose would be optimizing repeating composables, especially in widget trees. In
  Jetpack Compose applications, some composables are used repeatedly, especially in list or grid views. In this
  case, by applying the Flyweight pattern, we can optimize memory usage and improve the performance
  of the application.

<h4 align="left">The Flyweight design pattern has 4 main components</h4>

- **Flyweight Interface:** Defines a common interface of shared objects.
- **Concrete Flyweight:** Class that implements the Flyweight interface and stores the intrinsic
  state.
- **Flyweight Factory:** Creates and manages Flyweight objects. If the same object has been created
  before, it allows it to be reused.
- **Client:** Uses Flyweight objects. It provides the extrinsic state and combines it with
  Flyweight.

<h5 align="left">Advantages of the Flyweight design pattern</h5>

- Reduces memory usage by preventing similar objects from being created over and over again.
- Performance increases because fewer objects are created.

<h5 align="left"> Disadvantages of the Flyweight design pattern</h5>

- Design can get complicated.
- Management of internal and external situations may become difficult.

**Sample Scenario**

How about doing this in an actual application, package, etc. How can we implement it? Let's look at
it. According to our scenario, we want to make a social media application. Let's imagine a list
showing posts in this application. Instead of creating the same icons over and over again for
actions such as comments, likes and shares on each post, we will optimize them with the **Flyweight
** design pattern.

First, we start by making the **Flyweight Interface** layer. We place a method with the method
signature **Widget createWidget(Color color, double size)**, which returns **Widget**.

```kotlin
interface Flyweight {
    @Composable
    fun render(color: Color, size: Dp)
}
```

Then it's time for the **Concrete Flyweight** layer. We will store the **intrinsic state** in this
layer. In our case, this will be an icon. At the same time, we **@override** the **createWidget**
method by **implementing** the **Flyweight** layer.

```kotlin
class IconFlyweight(private val icon: ImageVector) : Flyweight {

    @Composable
    override fun render(color: Color, size: Dp) {
        Icon(imageVector = icon, tint = color, modifier = Modifier.size(size))
    }
}
```

It's time to create **Flyweight** objects in the **Flyweight Factory** layer. Here, if there is a
previously created object, **icons** are pulled from the map. If it is an object that comes for the
first time, it is added to the map.

```kotlin
object IconFactory {
    private val icons = mutableMapOf<ImageVector, IconFlyweight>()

    fun getIcon(icon: ImageVector): IconFlyweight {
        return icons.getOrPut(icon) { IconFlyweight(icon) }
    }
}
```

So how can we use this on the UI (**Client**) side?

```kotlin
@Composable
fun PostList(posts: List<String>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        posts.forEach { post ->
            PostItem(postContent = post)
        }
    }
}

@Composable
fun PostItem(postContent: String) {
    val likeIcon = IconFactory.getIcon(Icons.Default.Favorite)
    val shareIcon = IconFactory.getIcon(Icons.Default.Share)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = postContent, style = MaterialTheme.typography.bodyMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            likeIcon.render(color = Color.Red, size = 24.dp)
            shareIcon.render(color = Color.Blue, size = 24.dp)
        }
    }
}
```

[Return to the beginning of the documentation](#head)

- <h2 align="left"><a id="proxy">Proxy (Structural Patterns)</h2>
  A proxy design pattern is a structural design pattern used to control access to an object or to
  make this access through another object. While this pattern is used to extend or modify the
  functionality of an object, it operates without changing the structure of the original object. The
  proxy serves as a kind of interface or representative to the real object.

<h4 align="left">The proxy design pattern has three main components</h4>

- **Subject Interface:** The actual object and the interface that the proxy should implement.
- **Real Subject:** The actual object that the client wants to access.
- **Proxy:** An object that controls access to or replaces the real object.

<h5 align="left">Advantages of proxy design pattern</h5>

- Proxy allows you to control access to real objects. For example, you can add security controls or
  access permissions.
- Can improve the performance of the application by delaying the loading of expensive resources. It
  is especially useful for large objects or data coming over the network.
- It can increase performance by reducing unnecessary network traffic, especially when retrieving
  data from remote servers. For example, by caching data, it can prevent the same data from being
  loaded repeatedly.
- The proxy can log operations performed on the real object and add extra layers of security.
- Users or other objects can interact with real objects without being aware of the existence of the
  proxy.

<h5 align="left"> Disadvantages of proxy design pattern</h5>

- Implementation of proxy pattern can increase the overall complexity of the system. For simple
  cases, this extra complexity may be unnecessary.
- Proxy class may create extra processing load in some cases. In particular, going through a proxy
  on every request can increase processing time.
- Proper management of the proxy is necessary, especially if features such as caching or security
  have been added. A mismanaged proxy can lead to data inconsistency or security vulnerabilities.
- Implementing the proxy pattern correctly can make the design difficult to understand and extend in
  some cases.
- The layers added by the proxy can make testing processes more complex in some cases.

**Sample Scenario**

How about doing this in an actual application, package, etc. How can we implement it? Let's look at
it. As a real-life scenario in Jetpack Compose, a proxy can be used to access a remote API. For example,
when an application is pulling data from a remote server, it can use a proxy to manage these
requests and add a caching mechanism if necessary. Let's assume we are using a **Weather** API in
this scenario.

First of all, we create an **interface** named **WeatherService** as **Subject Interface**. This
interface has a method called **getWeatherData** to retrieve data from the API. We will implement
this layer to the original object and the proxy layer.

```kotlin
interface WeatherService {
    suspend fun getWeatherData(): String
}
```

Then, we implement the **Subject Interface** while writing a **Real Subject** layer named *
*WeatherApiService**.

```kotlin
class WeatherApiService : WeatherService {
    override suspend fun getWeatherData(): String {
        return "Sunny, 25°C"
    }
}
```

Now it's time for the **Proxy** layer, which is the key point of the **Proxy Design Pattern**. The
proxy layer captures API requests and, if necessary, adds a cache mechanism or logs the requests.

```kotlin
class WeatherServiceProxy(private val apiService: WeatherApiService = WeatherApiService()) :
    WeatherService {
    private var cachedData: String? = null

    override suspend fun getWeatherData(): String {
        return if (cachedData == null) {
            println("Fetching data from API...")
            cachedData = apiService.getWeatherData()
            cachedData!!
        } else {
            println("Returning cached data...")
            cachedData!!
        }
    }
}
```

So how can we use this? In this example, the WeatherServiceProxy class controls the data retrieval
from the API and caches the data. In the first request, it accesses the real API and retrieves the
data, and in subsequent requests it uses the cached data. This approach can improve performance and
reduce network traffic, especially in situations where the same data is needed frequently. The proxy
design pattern provides an efficient solution in such scenarios. In our case, we will assign the
data pulled 5 times to the cache after it is pulled for the first time, and we will quickly obtain
the answers to the remaining 4 requests from the cache. In this way, we will not be loaded with
unnecessary network traffic.

```kotlin
@Composable
fun ProxyView() {
    val weatherService: WeatherService = WeatherServiceProxy()

    val weatherData = remember { mutableStateOf("Loading...") }

    LaunchedEffect(Unit) {
        weatherData.value = getWeatherFiveTimes(weatherService)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Weather App") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = weatherData.value, style = MaterialTheme.typography.body1)
        }
    }
}

suspend fun getWeatherFiveTimes(service: WeatherService): String {
    val results = mutableListOf<String>()
    repeat(5) {
        val data = service.getWeatherData()
        results.add(data)
    }
    return results.joinToString("\n")
}
```

[Return to the beginning of the documentation](#head)

- <h2 align="left"><a id="chainofresponsibility">Chain of Responsibility (Behavioral Patterns)</h2>
  Let's discuss the Chain of Responsibility design pattern in Jetpack Compose in more detail. This pattern
  is useful for managing incoming requests or commands across different composables or screens,
  especially in large and modular Jetpack Compose applications.

<h4 align="left">The Chain of Responsibility design pattern has three main components</h4>

- **Handler:** An interface that defines how to process the request and pass the request to the next
  handler in the chain.
- **Concrete Handlers:** Classes that implement the Handler interface. Each processor decides
  whether to process the request or pass it to the next processor in the chain.
- **Client:** The person or system that initiates the request and sends it to the first handler of
  the chain.

<h5 align="left">Working Mechanism</h5>

- The client sends the request to the first handler in the chain.
- Each processor checks the request and decides whether to process it or not.
- If a handler can process the request, it performs the action and the process ends.
- If the handler cannot process the request, it forwards it to the next handler in the chain.
- This process continues until a handler processes the request or the chain ends.

<h5 align="left">Advantages of the Chain of Responsibility design pattern</h5>

- Sender and receiver become independent, encouraging loose coupling in the system.
- Easy to add new handlers or change the order of existing ones.
- Each handler has a single responsibility, making the code easier to maintain.

<h5 align="left"> Disadvantages of proxy design pattern</h5>

- The request may pass through multiple processors, which may impact performance.
- Can be difficult to debug because the request passes through various handlers.

**Sample Scenario**

How about doing this in an actual application, package, etc. How can we implement it? Let's look at
it. Consider a Jetpack Compose application that processes different types of user input (gestures, button
clicks, text input). The application can use the Chain of Responsibility model to process these
inputs.

The **Handler** interface, which forms the basis of the Chain of Responsibility pattern, defines the
basic methods that each **Concrete Handlers** class must implement. In Jetpack Compose, this is usually done
in the form of an abstract class. In our case, **InteractionHandler** will be our **Handler** *
*abstarct** class. This abstract class will be inherited by **Concrete Handlers**'s. *
*setNextHandler** will be a method to establish connections between chains. In this way, when an
incompatible situation occurs, the next chain will run.

```kotlin
abstract class InteractionHandler {
  private var nextHandler: InteractionHandler? = null

  fun setNextHandler(handler: InteractionHandler) {
    nextHandler = handler
  }

  fun handleNext(interactionType: String, onResult: (String) -> Unit) {
    if (nextHandler != null) {
      nextHandler!!.handleInteraction(interactionType, onResult)
    } else {
      onResult("Unrecognized interaction: $interactionType")
    }
  }

  abstract fun handleInteraction(interactionType: String, onResult: (String) -> Unit)
}
```

Then we define our **Concrete Handlers** classes. In our case, we are writing 2 different **Concrete
Handlers** classes, **ButtonInteractionHandler** and **FormInteractionHandler**, as an example. If *
*ButtonInteractionHandler** from these classes is used, we want to display an **AlertBox** on the
screen as per the scenario. If the **FormInteractionHandler** class is used, we want to print the
_Form submitted_ log by submitting. If **interactionType** is not found, we provide relevant
information by running the **handleUnrecognizedInteraction** method.

```kotlin
class ButtonInteractionHandler : InteractionHandler() {
  override fun handleInteraction(interactionType: String, onResult: (String) -> Unit) {
    if (interactionType == "buttonClick") {
      onResult("Button Clicked: Button interaction handled.")
    } else {
      handleNext(interactionType, onResult)
    }
  }
}

class FormInteractionHandler : InteractionHandler() {
  override fun handleInteraction(interactionType: String, onResult: (String) -> Unit) {
    if (interactionType == "formSubmit") {
      onResult("Form submitted successfully.")
    } else {
      handleNext(interactionType, onResult)
    }
  }
}
```

So, in what scenario can we use this on the UI side? Let's assume we have 3 buttons: **Click Me**, *
*Submit Form** and **Unknown**. First of all, we create a **ButtonInteractionHandler** and set its *
*interactionType** to **buttonClick**. The purpose of this button is to display an **AlertDialog**if
**buttonClick** exists. If **interactionType** is not technically supported in the current handler,
the next handler will be processed. If **interactionType** is not supported at all, we notify the
user with **handleUnrecognizedInteraction**.

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChainOfResponsibilityView() {
    var message by remember { mutableStateOf("") }

    // Initialize handlers
    val buttonHandler = ButtonInteractionHandler()
    val formHandler = FormInteractionHandler()
    buttonHandler.setNextHandler(formHandler)

    // UI
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Chain of Responsibility in Compose") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    buttonHandler.handleInteraction("buttonClick") { result ->
                        message = result
                    }
                }) {
                    Text("Click Me")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    buttonHandler.handleInteraction("formSubmit") { result ->
                        message = result
                    }
                }) {
                    Text("Submit Form")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    buttonHandler.handleInteraction("unknown") { result ->
                        message = result
                    }
                }) {
                    Text("Unknown")
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    )
}
```

[Return to the beginning of the documentation](#head)


- <h2 align="left"><a id="interpreter">Interpreter (Behavioral Patterns)</h2>
The Interpreter design pattern is a behavioral design pattern that allows us to define a grammar for a language and provide an interpreter that processes expressions in that language.

<h4 align="left">The Interpreter design pattern has 4 main components</h4>

- **Expression Interface:** This interface declares a method of interpreting a particular context. It is the core of the interpreter pattern.
- **Concrete Expression Classes:** These classes implement the Expression interface and interpret specific rules in the language.
- **Context Class(optional):** This class contains general information about the interpreter.
- **Client:** The client creates the syntax tree representing a particular sentence that defines the grammar of the language. The tree consists of instances of Concrete Expression classes.

<h5 align="left">Advantages of the Interpreter design pattern</h5>

- Grammar rules and interpreters can be easily changed and new expressions added as needed.
- It ensures that the code is modular and reusable.
- Can be optimized for processing complex expressions.

<h5 align="left"> Disadvantages of the Interpreter design pattern</h5>

- Developing interpreters for complex languages can be difficult.
- For simple expressions the interpreter may be slower than direct code.

**Sample Scenario**

Under normal circumstances, **Interpreter Design Pattern** is used more in _programming languages_, _SQL queries_, _Mathematical expressions_, _Game engines_, but since our current focus is **Jetpack Compose**, **Interpreter Design Pattern** is based on **Jetpack Compose Framework**. We will try to use it. For our scenario, let's consider a mobile application that allows users to define customizable widget structures using a text-based language. Users can dynamically build their interfaces using a simple language that specifies specific widget types, features, and layouts. For example, a user may want to show text by typing something like `"Text: Deatsilence"` or they might want to show an image by typing `"Image: https://picsum.photos/200"`.

First, we define an **Expression Interface** named **WidgetExpression**. We write a method signature called _interpret()_ in **WidgetExpression** that returns a Widget. This interface will be implemented by **Concrete Expression** classes.

```kotlin
sealed interface UIComponent {
  @Composable
  fun interpret()
}
```

Afterwards, we create two **Concrete Expression Class** named **ConcreteExpressionText** and **ConcreteExpressionImage** and implement the abstract class named **WidgetExpression**. We _override_ the _interpret_ method in the **Concrete Expression** classes and return **Text or Image** according to the text script received from the user. We can do this for other composables as well, but according to our scenario, we continue with these two specifically.

```kotlin
class ConcreteExpressionText(private val text: String) : UIComponent {
  @Composable
  override fun interpret() {
    Text(text = text, style = TextStyle(fontSize = 20.sp))
  }
}


class ConcreteExpressionImage(private val url: String) : UIComponent {
  @Composable
  override fun interpret() {
    AsyncImage(
      model = url,
      contentDescription = null,
      modifier = Modifier.size(100.dp)
    )
  }
}
```

Then, we add a method called **parseScript()** into the **WidgetParser** class to interpret the scripts coming from the user.

```kotlin
class WidgetParser {
  fun parseScript(script: String): List<UIComponent> {
    val expressions = mutableListOf<UIComponent>()
    script.lines().forEach { line ->
      val trimmedLine = line.trim()
      when {
        trimmedLine.startsWith("Text:") -> {
          val text = trimmedLine.substringAfter("Text:").trim()
          expressions.add(ConcreteExpressionText(text))
        }
        trimmedLine.startsWith("Image:") -> {
          val url = trimmedLine.substringAfter("Image:").trim()
          if (url.startsWith("https://")) {
            expressions.add(ConcreteExpressionImage(url))
          }
        }
      }
    }
    return expressions
  }
}
```

Finally, how can we use them on the UI side? Let's look at it. For example, let's interpret some scripts from the user via a **TextField**. Let's show the image or text to the user as a result of the interpretation.

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterpreterView() {
  var script by remember { mutableStateOf("") }
  val parser = remember { WidgetParser() }
  var expressions by remember { mutableStateOf(parser.parseScript(script)) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Interpreter Pattern") }
      )
    },
    content = { paddingValues ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        TextField(
          value = script,
          onValueChange = {
            script = it
          },
          modifier = Modifier.fillMaxWidth(),
        )
        Button(onClick = { expressions = parser.parseScript(script) }) {
          Text("Interpret the Script")
        }
        Spacer(modifier = Modifier.height(16.dp))
        expressions.forEach { expression ->
          expression.interpret()
          Spacer(modifier = Modifier.height(8.dp))
        }
      }
    }
  )
}
```

- When the user sees any text following the **Text:** keyword to display text, the relevant text will be displayed on the screen.

- To display an image, the user must provide a url followed by the **Image:** keyword. It will display the image found in the URL on the screen.

[Return to the beginning of the documentation](#head)


- <h2 align="left"><a id="command">Command (Behavioral Patterns)</h2>
Command pattern is a pattern frequently used in software engineering, especially object-oriented programming. This pattern allows encapsulating a request or action as an object. The main purpose of this approach is to create an abstraction layer between the code that performs operations and the code that calls these operations.

## The Command design pattern has two main components

- **Command Interface:** Create an interface that all commands will implement. It usually contains a single `execute()` method.
- **Concrete Command:** Create classes that implement the command interface and perform a specific operation.
- **Invoker:** Triggers commands. For example, a button can take on this role.
- **Receiver:** The object on which the command actually does the work. For example, a class that performs a specific operation within an application.
- **Client:** Creates the command object and assigns it to the caller.

### Advantages of the Command design pattern

- Commands can be reused in different contexts.
- Provides a clear separation between UI and business logic.
- New commands can be added easily.
- It makes writing unit tests easier because each command contains separate functionality that can be tested independently.

### Disadvantages of the Command design pattern

- It may be too complex for simple operations.
- Extra classes may be required for each new command, which can bloat the code base.

---

## Sample Scenario
Let's create a simple text editor in an Android application using Jetpack Compose. As the user edits text, each editing action will be recorded as a command, providing undo and redo functions.

### Command Interface
First, we start by writing the **Command Interface** component named `TextCommand`.

```kotlin
/**
 * [TextCommand] is the abstract class for the Command Pattern.
 */
interface TextCommand {
    fun execute()
    fun undo()
}
```

### Concrete Command
Next, we write the **Concrete Command** component named `UpdateTextCommand`. The `TextCommand` interface is implemented in this component. This class will be used to keep track of new and old text statuses.

```kotlin
/**
 * [UpdateTextCommand] is the concrete class for the Command Pattern.
 */
class UpdateTextCommand(
    private val textState: MutableState<String>,
    private val newText: String
) : TextCommand {

    private val oldText: String = textState.value

    override fun execute() {
        textState.value = newText
    }

    override fun undo() {
        textState.value = oldText
    }
}
```

### Invoker
Now, we create the **Invoker** component named `TextEditorController`. This class will manage the transaction history using methods such as `undo()` and `redo()`.

```kotlin
class TextEditorController {
    private val commandHistory = mutableListOf<TextCommand>()
    private var currentCommandIndex = -1

    fun executeCommand(command: TextCommand) {
        if (currentCommandIndex != commandHistory.size - 1) {
            commandHistory.subList(currentCommandIndex + 1, commandHistory.size).clear()
        }
        commandHistory.add(command)
        currentCommandIndex++
        command.execute()
    }

    fun undo() {
        if (currentCommandIndex >= 0) {
            commandHistory[currentCommandIndex].undo()
            currentCommandIndex--
        }
    }

    fun redo() {
        if (currentCommandIndex < commandHistory.size - 1) {
            currentCommandIndex++
            commandHistory[currentCommandIndex].execute()
        }
    }
}
```

### UI Integration with Jetpack Compose
Finally, we create the UI using Jetpack Compose. A `TextField` will be used for text input, with **Undo** and **Redo** buttons.

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandPatternView() {
  val textFieldState = remember { mutableStateOf(TextFieldValue(text = "")) }
  val textEditorController = remember { TextEditorController() }

  Scaffold(
    topBar = {
      TopAppBar(title = { Text("Command Pattern in Jetpack Compose") })
    }
  ) { padding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      TextField(
        value = textFieldState.value,
        onValueChange = { newValue ->
          textEditorController.executeCommand(
            UpdateTextCommand(
              controller = textFieldState.value,
              onTextChanged = { updatedValue -> textFieldState.value = updatedValue },
              newText = newValue.text
            )
          )
        },
        modifier = Modifier.fillMaxWidth()
      )

      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Button(
          onClick = { textEditorController.undo() },
          modifier = Modifier.weight(1f)
        ) {
          Text("Undo")
        }

        Button(
          onClick = { textEditorController.redo() },
          modifier = Modifier.weight(1f)
        ) {
          Text("Redo")
        }
      }
    }
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

- [X] Adapter
- [X] Bridge
- [X] Composite
- [X] Decorator
- [X] Facade
- [X] Flyweight
- [X] Proxy

## Behavioral Patterns

- [X] Chain of Responsibility
- [X] Interpreter
- [X] Command
- [ ] Iterator
- [ ] Observer
- [ ] Mediator
- [ ] State
- [ ] Strategy
- [ ] Template Method
- [ ] Visitor
- [ ] Memento

More to come soon ⏳ (Create a PR if you wish to contribute 😄)

![Hey\! Thanks\!](https://media.giphy.com/media/ip6n2oVNZBHiM/giphy.gif)

<a href="https://www.buymeacoffee.com/cavin.macwan" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" style="height: 60px !important;width: 217px !important;" ></a>
