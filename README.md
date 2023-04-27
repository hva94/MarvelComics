# ✪ Kinedu Android Challenge by hva94 - [linkedin.com/in/hva94](https://www.linkedin.com/in/hva94/)
## Marvel Comics (API-Driven Master-Detail Android Application)
I decide split all the project in 5 commits, to show the development process of the mains steps.

But before I show you the commits, I want to explain the main development steps of the challenge.

### Choose the architecture pattern:
- MVVM with Clean Architecture

### Set up the project with the libraries required
- ViewModel Lifecycle
- Retrofit
- Room
- Dagger Hilt
- Navigation Component
- Glide or Coil

### At this point, I build the commits on a cycle of the next three steps:

### Define the data and business model
- Research the API documentation
- Create the data and domain layer (Model, Repository and Use Cases)

### Create the DI modules required in the last step
- NetworkModule
- DatabaseModule
- RepositoryModule

### Implement the Presentation Layer
- ViewModel
- UI Layouts



### Finally, here are the commits!
### Commit 1: Project Initial Setup
- Libraries (build.gradle, app & project)
- Layouts and Resources
- Folder Structure (Clean Architecture)
- Manifest (Permission and Hilt App)

### Commit 2: Main List
- Create all the files needed for this
- Main Screen feature
- API consumption with flow and coroutines
- Persistence management with Room
- Item list with RecyclerView
- Dependency Injection Setup
- Navigation Component Setup

### Commit 3: Comic Detail
- Create all the files and implementations needed for this
- API consumption to fill the related info
- (Creator and Variants)
- Adapter and RecyclerView for the Variants
- Add/Remove from favorites functionality

### Commit 4: Favorites List
- Create all the files and implementations needed for this
- Adapter and RecyclerView for the favorite Comics
- Navigation with the others screens

### Commit 5: General improvements
- Empty states for main and favorite list
- Icon added
- Splash screen added
- Animations for RecyclerViews
- Transitions for ImageViews
- Validations and debugging
- Documentation created



### Tech Stack
* Kotlin
* Architecture Components
* MVVM Design pattern 
* Dependency Injection
* Navigation Component
* ViewBinding

<p align="left">
<a><img src="https://i.imgur.com/rK7QnUk.png" style="height: 20%; width:20%;" alt="Challenge App"></a>
<a><img src="https://i.imgur.com/wqThrJs.png" style="height: 20%; width:20%;" alt="Challenge App"></a>
<a><img src="https://i.imgur.com/uzdEDhs.png" style="height: 20%; width:20%;" alt="Challenge App"></a>
</p>

### Dependencies
* ViewModel
* Retrofit
* Room
* Dagger Hilt
* Navigation Component
* Coil

<p align="left">
<a><img src="https://i.imgur.com/TPLNu4v.png" style="height: 20%; width:20%;" alt="Challenge App"></a>
<a><img src="https://i.imgur.com/bSkFcYK.png" style="height: 20%; width:20%;" alt="Challenge App"></a>
</p>

### Main features
* Separation of concerns
* Single Activity Architecture
* Navigation between screens
* Animations and transitions
* Item lists
* Flow usage
* Coroutines usage
* Extension functions
* Exception handling
* Best practices
* Empty states

<p align="left">
<a><img src="https://i.imgur.com/G70iveG.png" style="height: 20%; width:20%;" alt="Challenge App"></a>
<a><img src="https://i.imgur.com/EFz7I2J.png" style="height: 20%; width:20%;" alt="Challenge App"></a>
</p>

### Android components
* Material design components
* Lifecycle aware components
* ListAdapter and DiffUtil
* Rotation support
* DayNight Themes
* Splash Screen
* Recyclerview
* XML based

### Extra features
* Progress indicators
* Constraint layouts
* Strings values
* Dimens values