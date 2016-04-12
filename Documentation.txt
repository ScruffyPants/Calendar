"Calendar" is a software project developed by Vilius Vaičiūnas and Linas Jonas Žilinskas. It is designed to provide the user with a highly flexible time-managing user interface, designed for institutions with authoritative hierarchies. The program provides functionality for multiple users, hierarchies and administration, as well as personal events, personal schedules, public events, user groups and customization. All code is hand-written with the use of the Eclipse Mars.2 IDE.

Documentation of functionality:

Admin -> User Control Panel (UCP): If a user is a verified administrator, they can access the User Control Panel, which provides necessary databased information about all users and the functionality to perform necessary adminstrative operations on them. (Keep in mind that the software project is intended to be shipped with a customizable, but built-in administrator account.)

UCP -> Options -> Ban: Irrevocably deletes the selected user's account file, subsequently banning them from the pseudo-database. 

UCP -> Options -> Toggle Verification: Toggles the verification status of the selected user. 

UCP -> Options -> Change Rank: Changes the rank of the selected user.

All options explained above can only be performed on other users, not on yourself, as to prevent possible vertical privilege escalation in the future, as well as administrators accidentally losing their authority.

UCP -> Window -> Refresh: Updates the table containing user information, and refreshes the window.

UCP -> Edit -> Account: Opens a new panel in which an administrator can edit the selected user's first name, last name and password. The original password is not shown, while the rest is.

This concludes the UCP functionality.

Calendar -> Exit: Exits the Calendar program.

Calendar -> Reload: Reloads the Calendar program.

Calendar -> Get To Date: Uses a year and month as input, and transports the calendar to that month immediately.

Account -> Add -> Event: Adds a private (or, if the user carries sufficient privileges, public) event to the calendar. Public events are stored centrally, and refreshed upon login for each user. (This can be changed, to allow for more real-time updating. An event takes place once and only once.

Account -> Add -> Schedule: Adds a private scheduled event to the user's account file. 

Account -> Groups: Unfinished.

Account -> Style: Allows one to customize the calendar's looks by changing its color scheme. Currently, the user needs to reload the calendar before the style change is applied.

Account -> Settings: Analog to UCP -> Edit -> Account, for non-admins.

Account -> Logout: Logs out the user and returns to the Login window.

Info -> About: Unfinished.

