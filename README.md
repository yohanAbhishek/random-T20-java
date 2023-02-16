# random-T20-java
Upgraded version of random-T20-py

This is a extended application of the project "https://github.com/yohanAbhishek/random-T20-py". In this project I have redone the entire application using OOP concepts that I learnt in my first year "Programming-Fundamentals-2" module. Additionally I have done the GUI using JavaFX. Apart from this I have done the below advancemenets as well.

Program level;

• Separating the GUI and the algorithm
• The GUI has 3 sections in the program as pregame, game, and post-game. These can be seen in the form of controllers.
• Instead of one cluttered class, the program was divided into sub classes where each class handles one functionality of the game.
• Use of private access modifiers when needed to secure the data(encapsulation)
• All classes have been tested using Junit to ensure that the methods do not fail their purpose
• Organized folder structure to make the program more maintainable.

System level;

• In a real-world scenario, only specific authorized individuals will be able to create, remove, or alter profiles from a database. As a result, the program will launch with a login screen where the user may input their password and access the add player pane.
• As in an actual player profile database, the player ID is generated within the program to ensure that all player IDs are consistent.
• A button to view the team's standings in the form of a bar chart is available in the tournament standings pane as a way to visualize the team's performance.
• CSS was used to improve the GUI's appearance and user experience.
• The entire program's controls have been designed to be simple to use and understand for the user.
• All of the program's controls (buttons, for example) have complete error handling coverage, with the relevant error message displayed in the form of a label.
• In order to make the panes more organized, tables and choice boxes were used when necessary.

On startup, the user is greeted with the below login screen

![image](https://user-images.githubusercontent.com/100549603/219361313-7af9fd7f-f86d-48a1-bb65-913009c9a08b.png)

After entering the correct password, the user will be directed into the panel where player profiles can be created, added, or deleted.
o Scenario 1:1 If no profiles have been established, the user will be prompted to choose the group and team from which he wants to add, update, or remove profiles.

![image](https://user-images.githubusercontent.com/100549603/219361440-836e0051-91b2-49c2-95b4-27ff1708c475.png)

o Scenario 1:2 After selection a group and team, the user will be directed to the following pane

![image](https://user-images.githubusercontent.com/100549603/219361492-4d65849f-65c5-479b-89b9-7bbf9503968b.png)

o Scenario 2: If all profiles have been created, the user will be sent to a screen where the match will begin. It was built this way since the specification stipulates that after initiating a match, the user cannot make any modifications to their profile.

![image](https://user-images.githubusercontent.com/100549603/219361552-bfebd1f2-405c-40d8-9f68-6aa978e8d861.png)

The user can load the data and add, delete, or change an existing player profile after arriving at the window to add the players.

![image](https://user-images.githubusercontent.com/100549603/219361595-88011b67-29ce-49d4-8886-ab749e2274c1.png)

After all profiles are created, the user can navigate to the match executing panel by clicking on the start button as bellow and click on Start match.

![image](https://user-images.githubusercontent.com/100549603/219361795-684dc352-7e19-47eb-a024-5698acef2f5e.png)

Apart from generating a random match, the application can do the following as well;

1) View top 5 batsman and bowlers
![image](https://user-images.githubusercontent.com/100549603/219361963-8bd3e85b-4a34-403f-896c-e4b9050dbad4.png)

2) View tournament standings
![image](https://user-images.githubusercontent.com/100549603/219362042-a0209910-9147-4104-bcb4-5c6676777ad4.png)

3) View the tournament standings in the form of a chart
![image](https://user-images.githubusercontent.com/100549603/219362168-1d0a533c-e31e-47d0-98d6-e4b68910f8a9.png)
