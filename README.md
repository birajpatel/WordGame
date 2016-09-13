# WordGame

Architecture
===
Coding Approach : TDD, 
I have followed Clean Architecture from Uncle Bob to implement the above solution.
Contains Mainly Three layers : 

Core - Its the inner most layer, which is mostly kinda Util layer, doesn’t know anything about Game’s existence, Contains general Rules. Example: ScoreManager (knows about score maintenance but not about game rules), Clocks, Timers, Repository classes.

Domain - Its the Java layer which uses Core layer heavily, this layer provides all the Business (Game) Rules to the system by means of UseCase classes. Presenter(Brain of UI) talks to various UseCases.

Presentation : Its the Android Layer which depends on Domain layer to perform all the work needed for Game, this layer is mostly dumb and has only knowledge to wire things together using DI & routing data events to UI, I have used MVP in this layer for better separation of concerns.

Please refer Tests & Code for more detailed info.

Libs
====
Rx, Dagger, Junit, Mockito, Hamcrest, GSON, RxAndroid, Butterknife

Timings 
===
Took roughly 6:30 hours.

1. 1:00 hour : Thinking & Breaking the moving parts & thinking them in Clean way.
2. 1:30 hour : For Core layer
3. 2:00 hour : For Domain layer
4. 2:00 hour : presentation layer


Decisions made
===
1. Made two screens, first Home-screen displays high-score & provides entry point, second screen as actual game.
2. Din’t focus on UI beautification.
3. Highscore only updates after game end.
4. Total Game Play is of 30 seconds after which Game closes, for each word use gets 3 seconds max.
5. Right answer +10, Wrong answer -5, No answer - no penalty.

Things to improve
===
Highest priorities first
First to thing to improve

1. Want to write Espresso tests.
2. Gradle module separation for core & domain layer.
3. UI beautification
4. Animation & Sounds.
