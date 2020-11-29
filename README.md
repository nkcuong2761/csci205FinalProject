# CSCI 205 - Software Engineering and Design 
## FINAL PROJECT
# Team01: MasterMind
Bucknell University 

Lewisburg, PA

### Course Info
Instructor: Brian King

Semester: Fall 2020

## Team Information 

- Cuong Nguyen (Dev)
- Minh Anh Phan (Scrum Master) 
- Lily Parker (Product Owner)
- Anurag Vaidya (Dev)

## Project Information 
In this final project for CSCI 205 at Bucknell University, we created a GUI-based version of the popular game Mastermind. 
In the original version of this game, the codeMaker sets a secret code consisting of four colored pegs. The codeBreaker has
to guess the code in 12 turns, with the codeMaker giving feedback on the answer after each try. 

We upgraded this game to include three difficulty levels: easy, medium, and difficult. As the difficulty levels progress
the number of pegs in the secret code increase whereas the available number of guesses decrease. We also created a custom 
mode in which the user can select the number of pegs in the secret code and number of guesses. Additionally, we created a 
sound on/off mode for the user to choose from. Finally, we integrated GIFs to animate the wining/ losing messages and 
added three themes: original, zen, pink, and wooden. 

## Package Structure 
src/main/java: Holds all of the files used to run the game 

src/main/java/mvcmodel: package that holds the files for the MVC design pattern 

src/main/java/mvcmodel/controllers: package that holds all of the controllers

src/main/java/mvcmodel/model: package that holds the model

src/main/java/mvcmodel/view: package that holds all of the views

src/main/java/mvcmodel/MastermindMain.java: main program to run the game

src/main/java/objects: the different components of the game (codemaker, player, code sequence, etc) 

src/main/resources/assets: holds additional files like pictures, sound clips, etc

src/main/resources: holds the CSS sheets that create the GUI

src/test/java: JUnit tests for different classes 

## Third parties used
JavaFX 14.0.2

JavaFX modules: [javafx.controls](https://docs.oracle.com/javase/8/javafx/api/toc.htm) , [javafx.media](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/media/Media.html)

## URL for video presentation

