target(name='tic-tac-toe-heroku',
  dependencies=[
    'finatra/examples/hello-world-heroku/src/main/scala'
  ]
)

jvm_binary(
  name='bin',
  basename='finatra-tic-tac-toe-heroku',
  main='com.twitter.hello.heroku.TicTacToeServerMain',
  dependencies=[
    ':tic-tac-toe-heroku'
  ],
  excludes=[
    exclude('org.slf4j', 'slf4j-jdk14'),
    exclude('log4j', 'log4j')
  ]
)
