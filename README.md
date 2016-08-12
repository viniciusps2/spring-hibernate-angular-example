Example application using Java with microservices (Spring Mvc + Spring Data + Hibernate + Angular JS)

### Pre-requisites:
(backend)

* JDK 8;
* Maven;
* MySQL / PostgreSQL;

(front-end)

* Node >= 5;

### Install the Application:
Clone this repository:
```sh
git clone https://github.com/viniciusps2/spring-hibernate-angular-example
```

Configure:
```sh
mysql -u root -p -e"
	-- Creating user
	CREATE USER 'bankuser'@'localhost' IDENTIFIED BY 'bankpassword';
	GRANT ALL PRIVILEGES ON *.* TO 'bankuser'@'localhost' WITH GRANT OPTION;
	FLUSH PRIVILEGES;

	-- Creating databases
	CREATE DATABASE bankadmin;
	CREATE DATABASE bankadmin_test;
	CREATE DATABASE bankatm;
	CREATE DATABASE bankatm_test;
"

export BANK_ADMIN_DB='mysql://bankuser:bankpassword@localhost:3306/bankadmin'
export BANK_ATM_DB='mysql://bankuser:bankpassword@localhost:3306/bankatm'

export BANK_ADMIN_DB_TEST='mysql://bankuser:bankpassword@localhost:3306/bankadmin_test'
export BANK_ATM_DB_TEST='mysql://bankuser:bankpassword@localhost:3306/bankatm_test'

export BANK_ACCOUNT_API='http://localhost:3002/accounts'
export BANK_ATM_API='http://localhost:3003/atms'
```

Install dependencies:
```sh
sudo npm install -g gulp bower
bash -c 'cd bank-admin && mvn clean install'
bash -c 'cd bank-atm && mvn clean install'
bash -c 'cd bank-ui && npm install'
```

Run the application (for development):
```sh
(run each line in one terminal window)
bash -c 'cd bank-admin && bash start.sh'
bash -c 'cd bank-atm && bash start.sh'
bash -c 'cd bank-ui && npm run dev'

(or run only this command)
bash start-all.sh
```

Open the application:

[http://localhost:3000](http://localhost:3000)


Stop the application:
```sh
bash stop-all.sh
```

Run the application (for production):
```sh
bash -c 'cd bank-admin && bash start.sh 3002 &'
bash -c 'cd bank-atm && bash start.sh 3003 &'
bash -c 'cd bank-ui && npm start'
```

Run the application tests:
```sh
bash -c 'cd bank-admin && mvn clean test'
bash -c 'cd bank-atm && mvn clean test'
bash -c 'cd bank-ui && gulp test'
```

### Deploy the Application on Heroku:

Generate package:
```sh
heroku login

cd bank-admin
git init
heroku apps:create 'bank-admin-app'
export BANK_ACCOUNT_API=$(heroku info -s | grep web_url | cut -d= -f2)accounts
heroku addons:create heroku-postgresql:hobby-dev
git add . && git commit -m "commit for deploy to heroku"
git push heroku master

cd ../bank-atm
git init
heroku apps:create 'bank-atm-app'
heroku config:set BANK_ACCOUNT_API=$BANK_ACCOUNT_API
export BANK_ATM_API=$(heroku info -s | grep web_url | cut -d= -f2)atms
heroku addons:create heroku-postgresql:hobby-dev
git add . && git commit -m "commit for deploy to heroku"
git push heroku master

cd ../bank-ui
git init
heroku apps:create 'bank-ui-app'
heroku config:set BANK_ACCOUNT_API=$BANK_ACCOUNT_API BANK_ATM_API=$BANK_ATM_API
git add . && git commit -m "commit for deploy to heroku"
git push heroku master
heroku open
```

### Available gulp tasks (bank-ui):
* serve: Run application in developer environment
* serve-prod: Run application using minification files
* jshint: Validation javascript files
* inject: Inject css and javascript files into index.html
* inject-karma: Inject css and javascript files into karma.conf.js
* test: Run tests
* minify: Minify javascript and css files
* minify-js: Minify javascript files
* minify-css: Minify css files
