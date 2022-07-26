This is the scrum-tools project.

scrum-tools is set of tools for scrum meetings and like this.

--------------------------------------------------------
Config git:

1. Open cmd and go to this project folder. Or use IDE terminal.

2. Specify your name and email for this project:
$ git config user.name <YOUR NAME HERE>
$ git config user.email <YOUR EMAIL HERE>

---------------------------------------------------------
Install Heroku.

1. Download Heroku CLI (Command Line Interface).

2. Install Heroku deploy plugin.
$ heroku plugins:install heroku-cli-deploy

---------------------------------------------------------
Run on localhost.

The simplest way:
Ctrl + F10 in Main class.

For run execute Maven goal:
$ mvn spring-boot:run

For clean execute Maven goal:
$ mvn clean

---------------------------------------------------------
Deploy on Heroku

1. Run Maven clean.

2. Run Maven package.

3. Open cmd and go to this project folder. Or use IDE terminal.

4. Login to Heroku (ask dev chat for password).
$ heroku login
<enter password>

5. Deploy scrum-tools project.
$ heroku jar:deploy target\DailyTool-1.0-SNAPSHOT.jar --app scrum-tools-test

6. Go to URL in browser.
https://scrum-tools-test.herokuapp.com/

7. Deploy bookprototype (litfit) project.
$ heroku war:deploy target\BookPrototype-1.0-SNAPSHOT.war --app bookprototype-test

8. Go to URL in browser.
https://bookprototype-test.herokuapp.com/

---------------------------------------------------------
