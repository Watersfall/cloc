# <CLOC

Discord Link: https://discord.gg/x2VwYkS

Todo List:
-   
- Rename The Game

- War
    - ~~War Formulas~~
    - Improved War Formulas
    - ~~Attacking with land~~
    - Naval battles
    - Air battles
    - Bombing
    - ~~UI for it~~
    - Viewing war history
    - Sending and accepting peace offers
    - View all current wars
    - Casualty logging?

- Treaties
    - ~~Inviting~~
    - ~~Leaving~~
    - Disbanding
    - ~~Showing up on nation pages~~
    - ~~Ability to set name, description, and flag~~
    
- Cities
    - ~~Removing mines, factories, etc.~~
    - ~~Make land a hard limit for construction~~
    - Farming improvements
    - Make city type have an effect
    - ~~Potentially~~Definitely rework railroads to reduce factory, etc. upkeep costs instead, or atleast something better than flat production bonus

- Military
    - ~~Creating, reinforcing, training, supplying~~ No longer necessary
    - Building and using ships
    - Building and using aircraft
    
- Decisions
    - More basic decisions
    - Remove some policies in favor of decisions
    - Rename to something "State Policy" or something similar
    
- Policies
    - ~~Re-add Free Prisoners, Arrest, and alignment policies~~
    - Rename to something other than policies

- Research & Technology
    - ~~Create the entire thing lmao~~
    - Equipment technology, better weapons, artillery, planes
    - Economic technology, mining, factory, etc.
    - Chemical weapons
    - Oil power
    
- UI & Frontend
    - ~~See "Research & Technology"~~
    - Mobile support
    - G R A P H S
    
- Database & Backend
    - Improved password security
    - Add logging tables for graphing
    - Add logging for any errors or exceptions

Contents: 
-
- com
    - watersfall
        - clocgame
            - actions : All actions done from their respective .jsp page
            - constants
                - Costs.java : Unified policy costs for policy actions and policy views
                - ProductionConstants.java : Base outputs of the various buildings
                - Responses.java : Contains all responses to policies, decisions, logging in, errors, and anything else
            - database
                - Database.java : Singleton that contains that BasicDataSource object used for all SQL Connections
            - exception
                - CityNotFoundException.java : Thrown when a model is attempted to be instantiated for a city that doesn't exist
                - NationNotFoundException.java : Same as CityNotFoundException.java but for a nation instead of a city
                - NotLoggedInException.java : Thrown when a user attempts to do an action that requires being logged in without being logged in
                - TreatyNotFoundException.java : Same as CityNotFoundException.java but for treaties
                - TreatyPermissionException.java : Thrown when a user attempts a treaty management action they don't have permission to do, 
                for example, inviting or kicking someone with the inviting or kicking permission bit
                - ValueException.java : Thrown when a non-allowed value is entered, for example, a non-number where a number should be
                - WarNotFoundException.java : Same as CityNotFoundException.java but for wars
            - listeners
                - Session.java : All code that gets run when a session is created or destroyed, currently nothing for both
                - Startup.java : All the code that get run when the server starts up, currently setting the database attribute 
                for servlets and jsp's to use, setting the current turn number, and starting up turn change
            - math
                - Math.java : Custom implementations of certain math functions to do the wrong thing with negatives and 0
            - model
                - nation : Models representing each part of a nation, with a Nation.java model containing all of them
                - technology
                    - technologies
                        - repeatable : all repeatable (can be completed more than once) technologies
                        - single : all single unlock (can take multiple attempts, but researched fully when completed) technologies
                        - Category.java : Enum containing all categories a research could fall under, along with their respective display names
                    - RepeatableTechnology.java : Abstract class for technologies that can be researched fully more than once
                    - SingleTechnology.java : Abstract class for technologies that can only be completed once
                    - Technologies.java : Enum containing all technologies, along with various display-related utilities
                    - Technology.java : Interface that all technologies implement
                - treaty : Models representing a treaty and it's members
                - war : Model representing a war
                - CityType.java : Enum class containing the city types, as well as their database names
                - LogType.java : Enum class representing the different types of database action logs
                - Region.java : Enum class containing the different regions, as well as their database names and methods 
                to check bordering
            - schedulers
                - WeekScheduler.java : Cron scheduler for the weekly (in-game) turn change
                - Day Scheduler.java : Cron scheduler for the daily (in-game) turn change
            - servlet
                - controller
                    - filter
                        - LoggedInFilter.java : A filter that runs on all URLs ending in .jsp that checks if the current session 
                        is logged in, and creates and assigns a Nation model for that user
                    - The rest of the controllers are for their respective pages, i.e. MainController is for /main.jsp, etc.
            - tags : Converts the database integer into the display strings
                - Alignment.java 
                - Approval.java 
                - Economic.java
                - Government.java
                - NumberFormatter.java : converts numbers into their header value (i.e. 120,000 -> 120k)
                - Rebel.java
                - Stability.java
            - turn
                - TurnWeek.java : Processes the weekly turn change called from WeekScheduler.java
                - TurnDay.java : Processes money ticks
            - util
                - Md5.java : Class containing the password hashing function
                - UserUtils.java : Contains a method to get a user's id, or throw an exception if they're not logged in
                - Util.java : The turn number and nothing else at the moment
                
- webapp
    - css
    - images
    - js
    - WEB-INF
        - view
        - custom.tld
        - web.xml
    -favicon.ico
    