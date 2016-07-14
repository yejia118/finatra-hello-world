# Finatra Tic Tac Toe Application

Overview
----------------------------------------------------------
This application is a Finatra server which powers a tic tac toe game. Users can connect their Slack channel with this server to play the game.

Check out
* [product requirements and technical design doc](https://docs.google.com/document/d/1TIIIYDpS9E6-qepE-9gvryTrnsBqMLJYZbk4SFPZhVk/edit#heading=h.u01k6bsi6hbv)
* [A video demo of how to play the game](https://www.youtube.com/)

Setup the Application
----------------------------------------------------------

### Run the server on Heroku ###

Compile and stage the application:

```
$ sbt compile stage
```

Make sure you have the [Heroku Toolbelt](https://toolbelt.heroku.com/) [installed](https://devcenter.heroku.com/articles/getting-started-with-scala#set-up).

Then deploy the example application to Heroku:

```
$ git push heroku master
Counting objects: 480, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (376/376), done.
Writing objects: 100% (480/480), 27.68 MiB | 16.24 MiB/s, done.
Total 480 (delta 101), reused 0 (delta 0)
remote: Compressing source files... done.
remote: Building source:
	...
```

You can then open the application in a browser with `heroku open`, e.g.:

```
$ heroku open
```


### Run the application locally with the Heroku Toolbelt  ###

See the [Heroku documentation](https://devcenter.heroku.com/articles/getting-started-with-scala#run-the-app-locally) on running an app locally with Foreman.


```
$ heroku local web
19:47:56 web.1  | started with pid 77663
19:47:59 web.1  | I 0528 02:47:59.058 THREAD1: HttpMuxer[/admin/metrics.json] = com.twitter.finagle.stats.MetricsExporter(<function1>)
19:47:59 web.1  | I 0528 02:47:59.096 THREAD1: HttpMuxer[/admin/per_host_metrics.json] = com.twitter.finagle.stats.HostMetricsExporter(<function1>)
19:47:59 web.1  | I 0528 02:47:59.183 THREAD1: Serving admin http on 0.0.0.0/0.0.0.0:0
19:47:59 web.1  | I 0528 02:47:59.218 THREAD1: Finagle version 6.25.0 (rev=78909170b7cc97044481274e297805d770465110)
19:48:00 web.1  | 2015-05-27 19:48:00,550 INF            HttpRouter                Adding routes
19:48:00 web.1  | GET     /hi
```

The app will now be running at [http://localhost:5000](http://localhost:5000). `Ctrl-C` to exit.


### Run all the tests locally  ###

```
sbt test
[info] Loading project definition from /Users/jiay/workspace/finatra-tic-tac-toe/project
[info] Set current project to tic-tac-toe-heroku (in build file:/Users/jiay/workspace/finatra-tic-tac-toe/)
[info] Compiling 1 Scala source to /Users/jiay/workspace/finatra-tic-tac-toe/target/scala-2.11/test-classes...
[info] StatusParamParserTest:
[info] - validate returns error if no game is in current channel
[info] - validate returns ok if status is valid
    ...
```

Config You Slack Channel with This Application
----------------------------------------------------------

Follow the steps below to config your slack channel so that you can play tic tac toe game with peers:

* Start a [custom command](https://my.slack.com/services/new/slash-commands) on your own team.
* Click button "Add configuration"
* Choose a command you prefer for tic tac toe game, such as /ttt
* Put the server URL as the URL: https://shrouded-inlet-34013.herokuapp.com/
* Choose GET as Method
* Add your token to the authorized token list in the server
* Save configuration
* Go back to your channel, start to challenge your peer and enjoy the game !

