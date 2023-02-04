# PI_Application

PI_Application is a Capstone Project of IT course at Federal Institute of Santa Catarina. It's an Android application developed to help people to find donation places at their city.

<p float="left">
<img align="center" width="20%" src="images/home.png" />
<img align="center" width="20%" src="images/map.png" />
<img align="center" width="20%" src="images/campaign.png" />
<img align="center" width="20%" src="images/create_campaign.png" />
</p>

## 🛠 Architecture
This app was developed using the [MVVM](https://developer.android.com/topic/architecture) architecture pattern and following the [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) concepts.
<br>
Inside the single module, the structure is divided in four main packages:
- `core` - dependecy injection and 'utils' classes
- `data` - repositories
- `domain` -  use cases and project models
- `presentation` - views and viewModels

## 📚 Libraries & Tools
- [Firebase APIs](https://firebase.google.com/) - Authentication, Firestore and Storage
- [Google Maps API](https://developers.google.com/maps) - Provide map experience in the app
- [Glide](https://github.com/bumptech/glide) - Load images in the app
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=pt-br) - Dependency injection
- [Ktlint](https://github.com/pinterest/ktlint) - Format code style
