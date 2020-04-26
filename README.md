# Superhero

App Name: Superhero

Purpose of the app: Get all SuperHeroes and Villians data from all universes under a single API

Programming Language: Kotlin


Modular development approach


Model This is an interface defining the data to be displayed or otherwise
acted upon in the user interface.


View This is a passive interface that displays data (the model) and routes
user commands ( events ) to the presenter to act upon that.


Presenter The middle man that acts upon the model and the view. It
retrieves data from repositories (the model), and formats it for display in the
view.


Manager This is part of the model layer, but allows reuse of model layer
across modules.


Repository This is responsible for accessing the API and receiving API

 Offline - Room Database

 No migration included on the DB

 When a user is online - Just search your superhero, for every super hero searched,
that specific hero is saved on the room database, so that when you are no longer
connected you can still be able to access your favourate super hero's information.

