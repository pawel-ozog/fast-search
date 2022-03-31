# fast-search

In order to run locally (via run command) use the following Idea run params:
VM options: 
-Xmx512m
-Xms512m
-XX:+UseG1GC
-Drunner=true

CLI arguments:
/home/pawel/programming/java/resources/names             - content to be searched, every name in a separate line 
/home/pawel/programming/java/resources/search            - content to search for every name in a separate line
com.example.search.engine.strategy.MatchFirstStrategy    - matching strategy